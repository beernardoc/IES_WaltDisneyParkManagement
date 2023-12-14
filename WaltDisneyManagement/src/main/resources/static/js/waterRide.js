var stompClient = null;
var jsonRecebido = {"velocity_kmh":0,"height_m":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0};  // Variável para armazenar o JSON recebido
var charts = {};  // Objeto para armazenar instâncias de gráficos

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var attractionName, parkName;
        [attractionName, parkName] = getAttractionNameFromURL();

        stompClient.subscribe(`/topic/${parkName}/${attractionName}`, function (mensagem) { // essa pagina funciona para qualquer Roller Coaster, por exemplo trocar o nome para /topic/MagicKingdom/Seven Dwarfs Mine Train
            try {

                jsonRecebido = JSON.parse(mensagem.body);

                renderChart();
                renderVel();
                renderDuration();
                renderQueue();
                renderVisitorsExpected();
                renderWaitingTime();



            } catch (error) {
                console.log(error);


            }

        });

        stompClient.subscribe(`/topic/${parkName}/${attractionName}/Alert`, function (mensagem) {
            showUrgentAlert(mensagem.body);


        });

        stompClient.subscribe(`/topic/${parkName}/${attractionName}/Reload`, function (mensagem) {
            window.location.href = mensagem.body;



        });


    })


}

function sendMaintenance() {
    // Obtenha os dados do formulário
    const maintenanceDetails = document.getElementById('maintenanceDetails').value;

    var attractionName, parkName;
    [attractionName, parkName] = getAttractionNameFromURL();

    // Construa a URL da API
    const apiUrl = `/api/park/${encodeURIComponent(parkName)}/attraction/${encodeURIComponent(attractionName)}/SetMaintenance`;

    // Construa a descrição formatada para URL
    const formattedDescription = encodeURIComponent(maintenanceDetails);

    // Enviar a solicitação usando fetch
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // Defina o tipo de conteúdo para formulário
        },
        body: `description=${formattedDescription}`, // Use o formato de formulário para os dados
    })
        .then(response => response.json())
        .then(result => {
            $('#maintenanceModal').modal('hide');
            // Faça qualquer outra coisa que você queira após o registro bem-sucedido
            document.getElementById('maintenanceDetails').value = '';
            window.location.reload();
        })
        .catch(error => {
            console.error('Erro no registro de manutenção:', error);
            // Lide com o erro conforme necessário
        });
}

// Função para obter o nome da atração a partir da URL
function getAttractionNameFromURL() {

    var url = window.location.href;
    var parts = url.split('/');

    var attractionIndex = parts.indexOf('attraction');
    var parkIndex = parts.indexOf('park');

    var attractionName
    var parkName

    if (attractionIndex !== -1 && attractionIndex < parts.length - 1) {
        attractionName = parts[attractionIndex + 1].replace(/%20/g, ' ');
    }

    if (parkIndex !== -1 && parkIndex < parts.length - 1) {
        parkName = parts[parkIndex + 1].replace(/%20/g, ' ');
    }

    return [attractionName, parkName];
}




function sendCloseOrOpenMessage(element) {
    var attraction = element.getAttribute('data-attraction');

    var status = element.getAttribute('status')

    var message = JSON.stringify({
        'attraction': attraction,
        'status': status
    });


    if (stompClient && stompClient.connected) {
        stompClient.send('/topic/CloseOrOPenAttraction', {}, message);
        setTimeout(function () {
            window.location.reload();
        }, 100);
    }
    else
        console.log('Websocket não está conectado. Não foi possível enviar a mensagem.');





}



function showUrgentAlert(message) {
    // Obtenha a referência para o modal existente
    var modal = document.getElementById('urgentModal');
    message = "A atracção " + message + " está com problemas técnicos e o seu funcionamento foi interrompido. Por favor, dirija-se ao local";
    // Atualize o conteúdo do modal com a mensagem recebida
    var modalBody = modal.querySelector('.modal-body');
    modalBody.innerHTML = '<div class="alert alert-danger">' + message + '</div>';

    // Mostrar o modal
    $(modal).modal('show');

    // Adicione um temporizador para esconder o modal após alguns segundos (opcional)
    setTimeout(function() {
        // Esconder o modal
        $(modal).modal('hide');
        window.location.reload();
    }, 10000); // Esconder após 5 segundos, ajuste conforme necessário
}



function renderDuration() {
    if (jsonRecebido) {
        duration = parseInt(jsonRecebido.duration);
        document.getElementById("duration").innerHTML = duration;
    }
}


function renderChart() {
    if (jsonRecebido) {
        if (charts.barChart) {
            charts.barChart.destroy();
        }
        charts.barChart = new ApexCharts(document.querySelector("#barChart"), {
            series: [{
                data: [
                    parseInt(jsonRecebido.height_m),
                    parseInt(jsonRecebido.temperature),
                    parseInt(jsonRecebido.vibration)
                ]
            }],
            chart: {
                type: 'bar',
                height: 350
            },
            plotOptions: {
                bar: {
                    borderRadius: 4,
                    horizontal: true,
                }
            },
            dataLabels: {
                enabled: false
            },
            xaxis: {
                categories: ['Height m', 'Temperature ºC', 'Vibration'],
            },
            yaxis: {
                max: 120
            },
            colors: ['#00008b', '#00008b', '#00008b']
        });

        charts.barChart.render(); // Renderize o gráfico

    }
}

function getCurrentTime() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
}

function renderWaitingTime() {
    if (jsonRecebido) {
        // Obtenha o número de pessoas na fila
        const peopleInQueue = parseInt(jsonRecebido.people_queue);

        let waitingTime = 0;

        if (peopleInQueue > 7) {
            const additionalPeople = peopleInQueue - 7;
            const additionalWaitTime = Math.ceil(additionalPeople / 7) * 15;
            waitingTime = additionalWaitTime;
        }

        document.getElementById("waitingTime").innerHTML = waitingTime + " minutes";
    }
}

// Array para armazenar os dados do gráficos
var dadosGrafico = [0];
var tempo = [""];

var dadosGraficoQueue = [0];
var tempoQueue = [""];






function renderVel() {
    if (jsonRecebido) {

        if (charts.lineChart) {
            charts.lineChart.destroy();
        }


        // Adicione o novo valor ao array
        dadosGrafico.push(jsonRecebido.velocity_kmh);
        var now = getCurrentTime();
        tempo.push(now);

        // Mantenha apenas os últimos 11 elementos
        if (dadosGrafico.length > 11) {
            dadosGrafico.shift(); // Remova o primeiro elemento
            tempo.shift();
        }


        // Renderize o novo gráfico
        charts.lineChart = new ApexCharts(document.querySelector("#lineChart"), {
            series: [{
                name: "Velocity",
                data: dadosGrafico
            }],
            chart: {
                height: 350,
                type: 'line',
                zoom: {
                    enabled: false
                }
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                curve: 'straight'
            },
            grid: {
                row: {
                    colors: ['#f3f3f3', 'transparent'],
                    opacity: 0.5
                },
            },
            xaxis: {
                categories: tempo,
            },
            colors: ['#00008b']
        });

        charts.lineChart.render(); // Renderize o gráfico

    }
}

function renderQueue() {
    if (jsonRecebido) {

        if (charts.areaChart) {
            charts.areaChart.destroy();
        }


        // Adicione o novo valor ao array
        dadosGraficoQueue.push(jsonRecebido.people_queue);
        var now = getCurrentTime();
        tempoQueue.push(now);

        // Mantenha apenas os últimos 11 elementos
        if (dadosGraficoQueue.length > 11) {
            dadosGraficoQueue.shift(); // Remova o primeiro elemento
            tempoQueue.shift();
        }


        // Renderize o novo gráfico
        charts.areaChart = new ApexCharts(document.querySelector("#areaChart"), {
            series: [{
                name: "Number of People",
                data: dadosGraficoQueue
            }],
            chart: {
                height: 350,
                type: 'area',
                zoom: {
                    enabled: false
                }
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                curve: 'straight'
            },
            grid: {
                row: {
                    colors: ['#f3f3f3', 'transparent'],
                    opacity: 0.5
                },
            },
            xaxis: {
                categories: tempoQueue,
            },
            colors: ['#00008b']
        });

        charts.areaChart.render(); // Renderize o gráfico

    }
}

function calculateExpectedVisitors() {
    // Lógica para calcular o número esperado de visitantes para o dia atual
    // Vamos usar um valor de exemplo, você precisará adaptar isso com dados reais
    const averageVisitorsPerDay = 500;  // Substitua pelo valor real

    // Calcula a média dos últimos 10 anos (exemplo)
    const expectedVisitors = Math.round(averageVisitorsPerDay * 10);

    return expectedVisitors;
}

function renderVisitorsExpected() {
    if (jsonRecebido) {
        const expectedVisitors = calculateExpectedVisitors();

       /* // Exibe o número esperado de visitantes
        document.getElementById("expectedVisitors").innerHTML = expectedVisitors;

        // Atualiza a porcentagem de mudança (exemplo: 100% de decréscimo)
        const decreasePercentage = 5;  // Substitua pelo valor real
        document.getElementById("decreasePercentage").innerHTML = `${decreasePercentage}%`;

        // Atualiza o texto de status (exemplo: "decrease")
        const statusText = decreasePercentage > 0 ? "decrease" : "increase";  // Substitua pela lógica real
        document.getElementById("statusText").innerHTML = statusText;

        */
    }
}


$(document).ready(function () {
    renderChart();
    renderVel();
    renderDuration();
    renderQueue();
    renderVisitorsExpected();
    renderWaitingTime();
    connect();

});