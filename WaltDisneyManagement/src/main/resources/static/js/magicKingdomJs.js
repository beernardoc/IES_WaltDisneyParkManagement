var stompClient = null;
var jsonRecebido = {"railRoad":{"velocity_kmh":0,"height_m":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0},"piratesOfTheCaribbean":{"velocity_kmh":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0},"hauntedMansion":{"velocity_kmh":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0},"sevenDwarfsMineTrain":{"velocity_kmh":0,"height_m":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0},"tomorrowlandSpeedway":{"velocity_kmh":0,"height_m":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0}}
var charts = {};  // Objeto para armazenar instâncias de gráficos

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/MagicKingdom', function (mensagem) {
            jsonRecebido = JSON.parse(mensagem.body);
            console.log('Dados recebidos:', jsonRecebido);

            renderChart();
            renderVel();
            renderDuration();
            renderQueue();
            renderVisitorsExpected();
            renderWaitingTime();
        });
    });
}

function renderDuration() {
    console.log('Renderizando duração');
    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        duration = parseInt(jsonRecebido["railRoad"]["duration"]);
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
                    parseInt(jsonRecebido["railRoad"]["height_m"]),
                    parseInt(jsonRecebido["railRoad"]["temperature"]),
                    parseInt(jsonRecebido["railRoad"]["vibration"])
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
        console.log('ApexCharts version:', ApexCharts.version);
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
        const peopleInQueue = parseInt(jsonRecebido["railRoad"]["people_queue"]);
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
        dadosGrafico.push(jsonRecebido["railRoad"]["velocity_kmh"]);
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
        console.log('ApexCharts version:', ApexCharts.version);
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
        dadosGraficoQueue.push(jsonRecebido["railRoad"]["people_queue"]);
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
        console.log('ApexCharts version:', ApexCharts.version);
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