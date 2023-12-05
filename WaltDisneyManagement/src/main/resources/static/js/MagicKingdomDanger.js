
var socket = new SockJS('/websocket-endpoint');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/MagicKingdom/Danger', function (mensagem) {
        console.log('Mensagem de texto simples:', mensagem.body);
        // Exibir o alerta HTML
        showUrgentAlert(mensagem.body);

    });
})

function showUrgentAlert(message) {
    // Obtenha a referência para o modal existente
    var modal = document.getElementById('urgentModal');

    // Atualize o conteúdo do modal com a mensagem recebida
    var modalBody = modal.querySelector('.modal-body');
    modalBody.innerHTML = '<div class="alert alert-danger">' + message + '</div>';

    // Mostrar o modal
    $(modal).modal('show');

    // Adicione um temporizador para esconder o modal após alguns segundos (opcional)
    setTimeout(function() {
        // Esconder o modal
        $(modal).modal('hide');
        }, 5000); // Esconder após 5 segundos, ajuste conforme necessário
}


