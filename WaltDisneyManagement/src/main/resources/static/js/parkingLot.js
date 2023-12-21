var stompClient = null;
var jsonRecebido = {"cars_in":0, "cars_out":0, "atual":0};  // Variável para armazenar o JSON recebido
var charts = {};  // Objeto para armazenar instâncias de gráficos

function connect() {

    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var parkName = getCarParkNameFromURL();

        stompClient.subscribe(`/topic/${parkName}`, function (mensagem) { // essa pagina funciona para qualquer Roller Coaster, por exemplo trocar o nome para /topic/MagicKingdom/Seven Dwarfs Mine Train
            try {

                jsonRecebido = JSON.parse(mensagem.body);
                updateValueCars();



            } catch (error) {
                console.log(error);


            }

        });


    })


}


// Função para obter o nome da atração a partir da URL
function getCarParkNameFromURL() {

    var url = window.location.href;
    var parts = url.split('/');

    var parkIndex = parts.indexOf('ParkCars');
    var parkName

    if (parkIndex !== -1 && parkIndex < parts.length - 1) {
        parkName = parts[parkIndex + 1].replace(/%20/g, ' ');
    }

    return parkName;
}




function getCurrentTime() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
}


// Array para armazenar os dados do gráficos

var dadosGraficoCars = [0];
var tempoCars = [""];


function getValueCars() {

    var atualValue = 0;
    var status = document.getElementById("carStatus").innerText;
    var statusElement = document.getElementById("carStatusCard");

    if (jsonRecebido) {

        status = document.getElementById('carStatus').innerText;
        atualValue = jsonRecebido.atual;
    }

    var capacity = parseInt(document.getElementById("maxCapacity").innerText);

    if (atualValue > capacity) {
        document.getElementById("atualValue").style.color = "#b32222";
    }
    else {
        document.getElementById("atualValue").style.color = "#00008b";
    }

    if (atualValue >= capacity && status === "Open") {
        document.getElementById("carStatus").innerText = "Closed";

        statusElement.style.backgroundColor = "#b32222";
        statusElement.style.color = "#ffffff";
        statusElement.querySelectorAll("*").forEach(function (child) {
            child.style.color = "#ffffff";
        });

        showUrgentAlert();
    }
    else if (atualValue < capacity && status === "Closed") {
        document.getElementById("carStatus").innerText = "Open";

        statusElement.style.backgroundColor = "#ffffff";
        statusElement.style.color = "#00008b";
        statusElement.querySelectorAll("*").forEach(function (child) {
            child.style.color = "#00008b";
        });

        closeUrgentCarModal()
    }

    if (atualValue > 0) {
        document.getElementById("atualValue").innerText = atualValue;
    }

    return atualValue;
}

function updateValueCars() {

    var parkedCarsNow = getValueCars();

    if (charts.areaChart) {
        charts.areaChart.destroy();
    }


    // Adicione o novo valor ao array
    dadosGraficoCars.push(parkedCarsNow);
    var now = getCurrentTime();
    tempoCars.push(now);

    // Mantenha apenas os últimos 11 elementos
    if (dadosGraficoCars.length > 11) {
        dadosGraficoCars.shift(); // Remova o primeiro elemento
        tempoCars.shift();
    }

    var capacity = parseInt(document.getElementById("maxCapacity").innerText);

    // Renderize o novo gráfico
    charts.areaChart = new ApexCharts(document.querySelector("#areaChart"), {
        series: [{
            name: "Number of Cars",
            data: dadosGraficoCars
        }],
        chart: {
            height: 500,
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
            categories: tempoCars,
        },
        yaxis: {
            min: 0,
            max: capacity
        },
        colors: ['#00008b']
    });

    charts.areaChart.render(); // Renderize o gráfico

}


function showUrgentAlert() {
    // Obtenha a referência para o modal existente
    var modal = document.getElementById('urgentModal');
    var message = "O " + getCarParkNameFromURL() + " foi temporariamente fechado devido à sua capacidade atual estar totalmente ocupada.";
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

function closeUrgentCarModal() {
    var modal = $('#urgentModal');
    modal.modal('hide');
}

$(document).ready(function () {
    updateValueCars();
    connect();

});