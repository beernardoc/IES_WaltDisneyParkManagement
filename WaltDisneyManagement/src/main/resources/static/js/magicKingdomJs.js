var stompClient = null;
var jsonRecebido;  // Variável para armazenar o JSON recebido
var charts = {};  // Objeto para armazenar instâncias de gráficos

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/MagicKingdom/Walt Disney World Railroad', function (mensagem) { // essa pagina funciona para qualquer Roller Coaster, por exemplo trocar o nome para /topic/MagicKingdom/Seven Dwarfs Mine Train
            try {

                jsonRecebido = JSON.parse(mensagem.body);

                renderChart();
                renderVel();
                renderDuration();
                renderQueue();
                renderVisitorsExpected();
                renderWaitingTime();


                console.log('Dados recebidos:', jsonRecebido);

                console.log('Dados recebidos:', jsonRecebido.duration);



            } catch (error) {
                console.log(error);


            }

        });

        stompClient.subscribe('/topic/MagicKingdom/Walt Disney World Railroad/Alert', function (mensagem) {
            console.log('Mensagem de texto simples:', mensagem.body);
            // Exibir o alerta HTML
            showUrgentAlert(mensagem.body);


        });


    })


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
    }, 10000); // Esconder após 5 segundos, ajuste conforme necessário
}



function renderDuration() {
    console.log('Renderizando duração');
    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        duration = parseInt(jsonRecebido.duration);
        console.log('Duração:', duration);
        document.getElementById("duration").innerHTML = duration;
    }
}


function renderChart() {
    console.log('Renderizando gráfico');
    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        // Limpe o gráfico se já existir uma instância
        if (charts.barChart) {
            charts.barChart.destroy();
        }
        // Renderize o novo gráfico
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
            }
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
    console.log('Renderizando tempo de espera');

    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        // Obtenha o número de pessoas na fila
        const peopleInQueue = parseInt(jsonRecebido.people_queue);
        console.log('People in queue:', peopleInQueue);

        // Calcule o tempo de espera com base na média de 7 pessoas por 15 minutos
        let waitingTime = 0;

        if (peopleInQueue > 7) {
            const additionalPeople = peopleInQueue - 7;
            const additionalWaitTime = Math.ceil(additionalPeople / 7) * 15;
            waitingTime = additionalWaitTime;
        }

        console.log('Waiting time:', waitingTime);

        document.getElementById("waitingTime").innerHTML = waitingTime + " minutes";
    }
}

// Array para armazenar os dados do gráficos
var dadosGrafico = [0];
var tempo = [""];

var dadosGraficoQueue = [0];
var tempoQueue = [""];






function renderVel() {
    console.log('Renderizando gráfico');
    // Certifique-se de que jsonRecebido é definido
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
            }
        });

        charts.lineChart.render(); // Renderize o gráfico

    }
}

function renderQueue() {
    console.log('Renderizando gráfico');
    // Certifique-se de que jsonRecebido é definido
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
            }
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
    console.log('Renderizando número esperado de visitantes');

    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        const expectedVisitors = calculateExpectedVisitors();

        // Exibe o número esperado de visitantes
        document.getElementById("expectedVisitors").innerHTML = expectedVisitors;

        // Atualiza a porcentagem de mudança (exemplo: 100% de decréscimo)
        const decreasePercentage = 5;  // Substitua pelo valor real
        document.getElementById("decreasePercentage").innerHTML = `${decreasePercentage}%`;

        // Atualiza o texto de status (exemplo: "decrease")
        const statusText = decreasePercentage > 0 ? "decrease" : "increase";  // Substitua pela lógica real
        document.getElementById("statusText").innerHTML = statusText;
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