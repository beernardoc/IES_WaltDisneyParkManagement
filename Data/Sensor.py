import asyncio
import json
import random
import pika
import time


previous_railroad = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_pirates = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_dwarfs = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_haunted = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_tomorrowland = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

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
            (velocity_walt_disney, height_walt_disney, temperature_walt_disney,vibration_walt_disney, people_queue_walt_disney, duration_walt_disney) = await generate_data("Rollercoaster", previous_railroad)
            (velocity_pirates, temperature_pirates, vibration_pirates,people_queue_pirates, duration_pirates) = await generate_data("Darkride", previous_pirates)
            (velocity_haunted_mansion, temperature_haunted_mansion, vibration_haunted_mansion, people_queue_haunted_mansion, duration_haunted_mansion) = await generate_data("Darkride", previous_haunted)
            (velocity_seven_dwarfs, height_seven_dwarfs, temperature_seven_dwarfs,vibration_seven_dwarfs, people_queue_seven_dwarfs, duration_seven_dwarfs) = await generate_data("Rollercoaster", previous_dwarfs)
            (velocity_tomorrowland, height_tomorrowland, temperature_tomorrowland, vibration_tomorrowland, people_queue_tomorrowland, duration_tomorrowland) = await generate_data("Rollercoaster", previous_tomorrowland)

            data = {
                "Walt Disney World RailRoad": {
                    "velocity_kmh": velocity_walt_disney,
                    "height_m": height_walt_disney,
                    "temperature": temperature_walt_disney,
                    "vibration": vibration_walt_disney,
                    "people_queue" : people_queue_walt_disney,
                    "duration" : duration_walt_disney
                },
                "Pirates of the Caribbean": {
                    "velocity_kmh": velocity_pirates,
                    "temperature": temperature_pirates,
                    "vibration": vibration_pirates,
                    "people_queue": people_queue_pirates,
                    "duration": duration_pirates
                },
                "Haunted Mansion": {
                    "velocity_kmh": velocity_haunted_mansion,
                    "temperature": temperature_haunted_mansion,
                    "vibration": vibration_haunted_mansion,
                    "people_queue": people_queue_haunted_mansion,
                    "duration": duration_haunted_mansion
                },
                "Seven Dwarfs Mine Train": {
                    "velocity_kmh": velocity_seven_dwarfs,
                    "height_m": height_seven_dwarfs,
                    "temperature": temperature_seven_dwarfs,
                    "vibration": vibration_seven_dwarfs,
                    "people_queue" : people_queue_seven_dwarfs,
                    "duration" : duration_seven_dwarfs
                },
                "Tomorrowland Speedway": {
                    "velocity_kmh": velocity_tomorrowland,
                    "height_m": height_tomorrowland,
                    "temperature": temperature_tomorrowland,
                    "vibration": vibration_tomorrowland,
                    "people_queue" : people_queue_tomorrowland,
                    "duration" : duration_tomorrowland
                },
                "Time": time.time()
            }

            self.channel.basic_publish(exchange='', routing_key='MagicKingdom', body=json.dumps(data))
            #print(f'Queue: MagicKingdom, Mensagem enviada: {data}')

            await asyncio.sleep(20)


async def random_value(previous, max_difference, precision=2):
    lower_limit = max(0.0, previous - max_difference)
    upper_limit = min(120, previous + max_difference)
    value = random.uniform(lower_limit, upper_limit)
    return round(value, precision)

async def random_value_people(previous, max_difference):
    lower_limit = max(0, previous - max_difference)
    upper_limit = min(1000, previous + max_difference)
    value = random.uniform(lower_limit, upper_limit)
    return round(value)

async def random_value_duration(previous, max_difference, precision=2):
    new_duration = previous - max_difference
    if new_duration <= 0:
        new_duration = 900

    return new_duration


    

async def generate_data(type, previous_data, max_difference=20.0):
    if type == "Rollercoaster":
        velocity = await random_value(previous_data['velocity'], max_difference)
        previous_data['velocity'] = velocity
        height = await random_value(previous_data['height'], max_difference)
        previous_data['height'] = height
        temperature = await random_value(previous_data['temperature'], max_difference)
        previous_data['temperature'] = temperature
        vibration = await random_value(previous_data['vibration'], max_difference)
        previous_data['vibration'] = vibration
        people_queue = await random_value_people(previous_data['people_queue'], 5)
        previous_data['people_queue'] = people_queue
        duration = await random_value_duration(previous_data['duration'], 20)
        previous_data['duration'] = duration
        return velocity, height, temperature, vibration, people_queue, duration
    elif type == "Darkride":
        velocity = await random_value(previous_data['velocity'], max_difference)
        previous_data['velocity'] = velocity
        temperature = await random_value(previous_data['temperature'], max_difference)
        previous_data['temperature'] = temperature
        vibration = await random_value(previous_data['vibration'], max_difference)
        previous_data['vibration'] = vibration
        people_queue = await random_value_people(previous_data['people_queue'], 5)
        previous_data['people_queue'] = people_queue
        duration = await random_value_duration(previous_data['duration'], 20)
        previous_data['duration'] = duration
        return velocity, temperature, vibration, people_queue, duration






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
