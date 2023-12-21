var stompClient = null;
var jsonRecebido = {"velocity_kmh":0,"height_m":0,"temperature":0,"vibration":0,"people_queue":0,"duration":0};
var charts = {};

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var attractionName, parkName;
        [attractionName, parkName] = getAttractionNameFromURL();

        stompClient.subscribe(`/topic/${parkName}/${attractionName}`, function (mensagem) {
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
    const maintenanceDetails = document.getElementById('maintenanceDetails').value;

    var attractionName, parkName;
    [attractionName, parkName] = getAttractionNameFromURL();

    const apiUrl = `/api/park/${encodeURIComponent(parkName)}/attraction/${encodeURIComponent(attractionName)}/SetMaintenance`;

    const formattedDescription = encodeURIComponent(maintenanceDetails);

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `description=${formattedDescription}`,
    })
        .then(response => response.json())
        .then(result => {
            $('#maintenanceModal').modal('hide');
            document.getElementById('maintenanceDetails').value = '';
            window.location.reload();
        })
        .catch(error => {
            console.error('Erro no registro de manutenção:', error);
        });
}


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
    var modal = document.getElementById('urgentModal');
    message = "A atracção " + message + " está com problemas técnicos e o seu funcionamento foi interrompido. Por favor, dirija-se ao local";
    var modalBody = modal.querySelector('.modal-body');
    modalBody.innerHTML = '<div class="alert alert-danger">' + message + '</div>';

    $(modal).modal('show');

    setTimeout(function() {
        $(modal).modal('hide');
        window.location.reload();
    }, 10000);
}



function renderDuration() {

    durationElement = document.getElementById("duration")
    if (durationElement && jsonRecebido) {
        duration = parseInt(jsonRecebido.duration);
        durationElement.innerHTML = duration;
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

        charts.barChart.render();

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
        const peopleInQueue = parseInt(jsonRecebido.people_queue);

        let waitingTime = 0;

        if (peopleInQueue > 7) {
            const additionalPeople = peopleInQueue - 7;
            const additionalWaitTime = Math.ceil(additionalPeople / 7) * 15;
            waitingTime = additionalWaitTime;
        }

        var waitingTimeElement = document.getElementById("waitingTime");
        if (waitingTimeElement) {
            waitingTimeElement.innerHTML = waitingTime + " minutes";
        }
    }
}

var dadosGrafico = [0];
var tempo = [""];

var dadosGraficoQueue = [0];
var tempoQueue = [""];






function renderVel() {
    if (jsonRecebido) {

        if (charts.lineChart) {
            charts.lineChart.destroy();
        }


        dadosGrafico.push(jsonRecebido.velocity_kmh);
        var now = getCurrentTime();
        tempo.push(now);

        if (dadosGrafico.length > 11) {
            dadosGrafico.shift();
            tempo.shift();
        }


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

        charts.lineChart.render();

    }
}

function renderQueue() {
    if (jsonRecebido) {

        if (charts.areaChart) {
            charts.areaChart.destroy();
        }


        dadosGraficoQueue.push(jsonRecebido.people_queue);
        var now = getCurrentTime();
        tempoQueue.push(now);

        if (dadosGraficoQueue.length > 11) {
            dadosGraficoQueue.shift();
            tempoQueue.shift();
        }


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

        charts.areaChart.render();

    }
}

function calculateExpectedVisitors() {
    const averageVisitorsPerDay = 500;

    const expectedVisitors = Math.round(averageVisitorsPerDay * 10);

    return expectedVisitors;
}

function renderVisitorsExpected() {
    if (jsonRecebido) {
        const expectedVisitors = calculateExpectedVisitors();
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