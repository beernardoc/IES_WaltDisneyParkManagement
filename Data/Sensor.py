import pika
import time

def send_message(channel):
    while True:
        # Gerar um número aleatório de 0 a 10
        numero = "teste"
        # Enviar a mensagem para a fila
        channel.basic_publish(exchange='', routing_key='minha_fila', body=str(numero))
        print(f'Mensagem enviada: {numero}')
        # Aguardar por algum tempo antes de enviar a próxima mensagem
        time.sleep(1)

# Configuração da conexão com o RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('mq'))
channel = connection.channel()
channel.queue_declare(queue='minha_fila')

# Chamar a função para enviar mensagens em um loop infinito
send_message(channel)