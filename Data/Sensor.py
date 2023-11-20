import pika
import time

def send_message(channel):
    numero = 0  # Inicializa o número
    while True:
        # Enviar o número para a fila
        channel.basic_publish(exchange='', routing_key='minha_fila', body=str(numero))
        print(f'Mensagem enviada: {numero}')

        # Incrementar o número para a próxima iteração
        numero += 1

        # Aguardar por algum tempo antes de enviar a próxima mensagem
        time.sleep(1)

# Configuração da conexão com o RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('mq'))
channel = connection.channel()
channel.queue_declare(queue='minha_fila')

# Chamar a função para enviar mensagens em um loop infinito
send_message(channel)