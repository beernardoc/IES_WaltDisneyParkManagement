
var stompClient = null;

function connect() {
    var socket = new SockJS('/websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/atualizacao', function (mensagem) {
            console.log('Mensagem recebida via WebSocket:', mensagem.body);
            var jsonRecebido = mensagem.body;
            document.getElementById('jsonRecebido').innerText = jsonRecebido;
        });

    });
}

$(document).ready(function () {
    connect();
});
