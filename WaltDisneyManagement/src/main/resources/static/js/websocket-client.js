
var stompClient = null;

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/MagicKingdom', function (mensagem) {

            var jsonRecebido = JSON.parse(mensagem.body);




            document.getElementById('jsonRecebido').innerText = JSON.stringify(jsonRecebido); // ja como string

            document.getElementById('railRoadVel').innerText = jsonRecebido.railRoad.velocity_kmh;

        });

    });
}

$(document).ready(function () {
    connect();
});
