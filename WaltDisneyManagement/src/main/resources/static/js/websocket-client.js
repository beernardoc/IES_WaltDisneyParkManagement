var stompClient = null;
var jsonRecebido = null;

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/MagicKingdom', function (mensagem) {
            jsonRecebido = JSON.parse(mensagem.body);
            console.log('Dados recebidos:', jsonRecebido);

            //document.getElementById('jsonRecebido').innerText = JSON.stringify(jsonRecebido);
            //document.getElementById('railRoadVel').innerText = jsonRecebido["railRoad"]["velocity_kmh"];

            renderChart();
        });
    });
}

function renderChart() {
    console.log('Renderizando gráfico');
    // Certifique-se de que jsonRecebido é definido
    if (jsonRecebido) {
        new ApexCharts(document.querySelector("#barChart"), {
            series: [{
                data: [
                    parseInt(jsonRecebido["railRoad"]["velocity_kmh"]),
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
                categories: ['Velocity km/h', 'Height m', 'Temperature ºC', 'Vibration'],
            },
            yaxis: {
                max: 120
            }
        }).render();

        console.log('ApexCharts version:', ApexCharts.version);
    }
}

$(document).ready(function () {
    connect();
    renderChart(); // Adicione esta chamada para garantir a renderização inicial
});