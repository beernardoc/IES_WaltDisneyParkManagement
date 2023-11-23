import asyncio
import json
import random
import pika
import time


class Generators:
    def __init__(self):

        self.connection = pika.BlockingConnection(pika.ConnectionParameters('mq'))
        self.channel = self.connection.channel()

        self.channel.queue_declare(queue='MagicKingdom')
        self.channel.queue_declare(queue='Epcot')
        self.channel.queue_declare(queue='HollywoodStudios')
        self.channel.queue_declare(queue='AnimalKingdom')
        self.channel.queue_declare(queue='DisneySprings')
        self.channel.queue_declare(queue='BlizzardBeach')
        self.channel.queue_declare(queue='TyphoonLagoon')

    async def MagicKingdom(self):

        while True:
            (velocity_walt_disney, height_walt_disney, temperature_walt_disney,
             vibration_walt_disney) = await generate_data("Rollercoaster")
            (velocity_pirates, temperature_pirates, vibration_pirates) = await generate_data("Darkride")
            (velocity_haunted_mansion, temperature_haunted_mansion,
             vibration_haunted_mansion) = await generate_data("Darkride")
            (velocity_seven_dwarfs, height_seven_dwarfs, temperature_seven_dwarfs,
             vibration_seven_dwarfs) = await generate_data("Rollercoaster")
            (velocity_tomorrowland, height_tomorrowland, temperature_tomorrowland,
             vibration_tomorrowland) = await generate_data("Rollercoaster")

            data = {
                "Walt Disney World RailRoad": {
                    "velocity_kmh": velocity_walt_disney,
                    "height_m": height_walt_disney,
                    "temperature": temperature_walt_disney,
                    "vibration": vibration_walt_disney
                },
                "Pirates of the Caribbean": {
                    "velocity_kmh": velocity_pirates,
                    "temperature": temperature_pirates,
                    "vibration": vibration_pirates
                },
                "Haunted Mansion": {
                    "velocity_kmh": velocity_haunted_mansion,
                    "temperature": temperature_haunted_mansion,
                    "vibration": vibration_haunted_mansion
                },
                "Seven Dwarfs Mine Train": {
                    "velocity_kmh": velocity_seven_dwarfs,
                    "height_m": height_seven_dwarfs,
                    "temperature": temperature_seven_dwarfs,
                    "vibration": vibration_seven_dwarfs
                },
                "Tomorrowland Speedway": {
                    "velocity_kmh": velocity_tomorrowland,
                    "height_m": height_tomorrowland,
                    "temperature": temperature_tomorrowland,
                    "vibration": vibration_tomorrowland
                },
                "Time": time.time()
            }

            self.channel.basic_publish(exchange='', routing_key='MagicKingdom', body=json.dumps(data))
            print(f'Queue: MagicKingdom, Mensagem enviada: {data}')

            await asyncio.sleep(1)


async def random_value(lower, upper, precision=2):
    value = random.uniform(lower, upper)
    return round(value, precision)


async def generate_data(type):
    if type == "Rollercoaster":
        velocity = await random_value(0.0, 150.0)
        height = await random_value(0.0, 140.0)
        temperature = await random_value(0.0, 100.0)
        vibration = await random_value(0.0, 100.0)
        return velocity, height, temperature, vibration
    elif type == "Darkride":
        velocity = await random_value(0.0, 100.0)
        temperature = await random_value(0.0, 100.0)
        vibration = await random_value(0.0, 100.0)
        return velocity, temperature, vibration





if __name__ == '__main__':
    generators = Generators()
    loop = asyncio.get_event_loop()

    magic_kingdom = loop.create_task(generators.MagicKingdom())

    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    finally:
        loop.close()
