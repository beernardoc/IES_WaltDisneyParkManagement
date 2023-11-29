var stompClient = null;
var jsonRecebido = null;
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

// Array para armazenar os dados do gráficos
var dadosGrafico = [0];
var tempo = [""];






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
                name: "Desktops",
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



$(document).ready(function () {
    connect();
    renderChart();
    renderVel();
    renderDuration();

});