import asyncio
import json
import random
import pika
import time

#MAGICKINGDOM
previous_railroad = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_pirates = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_dwarfs = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_haunted = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_tomorrowland = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#EPCOT
previous_spaceship = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_cosmic = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_test = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#hollywood_studios
previous_runaway = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_rocknroller = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#Disney's Animal Kingdom
previous_triceratop = {'velocity': 0.0, 'rpm': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_kaliriver = {'velocity': 0.0, 'height': 0.0, 'temperaturewater': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}
previous_dinosaur = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}

#Dinsey Springs
previous_marketplace = {'velocity': 0.0, 'height': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_classic = {'velocity': 0.0, 'rpm': 0.0, 'temperature': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#Blizzard Beach
previous_summit = {'velocity': 0.0, 'height': 0.0, 'temperaturewater': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_slush = {'velocity': 0.0, 'height': 0.0, 'temperaturewater': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#Typhoon Lagoon
previous_gangplank = {'velocity': 0.0, 'height': 0.0, 'temperaturewater': 0.0, 'vibration': 0.0,'people_queue': 50, 'duration' : 900}
previous_humunga = {'velocity': 0.0, 'height': 0.0, 'temperaturewater': 0.0, 'vibration': 0.0,'people_queue': 0, 'duration' : 900}

#ParkingLot
previous_parkingLot1 = {"cars_in": 0, "cars_out" : 0}
previous_parkingLot2 = {"cars_in": 0, "cars_out" : 0}


class Generators:
    def __init__(self):

        self.connection = pika.BlockingConnection(pika.ConnectionParameters('mq'))
        self.channel = self.connection.channel()

        self.channel.queue_declare(queue='Magic Kingdom')
        self.channel.queue_declare(queue='Epcot')
        self.channel.queue_declare(queue='Hollywood Studios')
        self.channel.queue_declare(queue='Animal Kingdom')
        self.channel.queue_declare(queue='Disney Springs')
        self.channel.queue_declare(queue='Blizzard Beach')
        self.channel.queue_declare(queue='Typhoon Lagoon')
        self.channel.queue_declare(queue='ParkingLot')

    async def MagicKingdom(self):

        while True:
            (velocity_walt_disney, height_walt_disney, temperature_walt_disney,vibration_walt_disney, people_queue_walt_disney, duration_walt_disney) = await generate_data("Rollercoaster", previous_railroad)
            (velocity_pirates, temperature_pirates, vibration_pirates,people_queue_pirates, duration_pirates) = await generate_data("Darkride", previous_pirates)
            (velocity_haunted_mansion, temperature_haunted_mansion, vibration_haunted_mansion, people_queue_haunted_mansion, duration_haunted_mansion) = await generate_data("Darkride", previous_haunted)
            (velocity_seven_dwarfs, height_seven_dwarfs, temperature_seven_dwarfs,vibration_seven_dwarfs, people_queue_seven_dwarfs, duration_seven_dwarfs) = await generate_data("Rollercoaster", previous_dwarfs)
            (velocity_tomorrowland, height_tomorrowland, temperature_tomorrowland, vibration_tomorrowland, people_queue_tomorrowland, duration_tomorrowland) = await generate_data("Rollercoaster", previous_tomorrowland)
            People_in_out = await simulate_sensor()
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
                "Visitors": {
                    "entry_exit": People_in_out
                },

                "Time": time.time()
            }



            self.channel.basic_publish(exchange='', routing_key='Magic Kingdom', body=json.dumps(data))
            if velocity_walt_disney > 100:
                previous_railroad['velocity'] = 0.0
            if velocity_pirates > 100:
                previous_pirates['velocity'] = 0.0
            if velocity_haunted_mansion > 100:
                previous_haunted['velocity'] = 0.0
            if velocity_seven_dwarfs > 100:
                previous_dwarfs['velocity'] = 0.0
            if velocity_tomorrowland > 100:
                previous_tomorrowland['velocity'] = 0.0
            
            if height_walt_disney > 100:
                previous_railroad['height'] = 0.0
            if height_seven_dwarfs > 100:
                previous_dwarfs['height'] = 0.0
            if height_tomorrowland > 100:
                previous_tomorrowland['height'] = 0.0
            
            if temperature_walt_disney > 100:
                previous_railroad['temperature'] = 0.0
            if temperature_pirates > 100:
                previous_pirates['temperature'] = 0.0
            if temperature_haunted_mansion > 100:
                previous_haunted['temperature'] = 0.0
            if temperature_seven_dwarfs > 100:
                previous_dwarfs['temperature'] = 0.0
            if temperature_tomorrowland > 100:
                previous_tomorrowland['temperature'] = 0.0
            

            if vibration_walt_disney > 100:
                previous_railroad['vibration'] = 0.0
            if vibration_pirates > 100:
                previous_pirates['vibration'] = 0.0
            if vibration_haunted_mansion > 100:
                previous_haunted['vibration'] = 0.0
            if vibration_seven_dwarfs > 100:
                previous_dwarfs['vibration'] = 0.0
            if vibration_tomorrowland > 100:
                previous_tomorrowland['vibration'] = 0.0
            
            
            
            


            await asyncio.sleep(5)



    async def epcot(self):

        while True:
            (velocity_spaceship, temperature_spaceship, vibration_spaceship,people_queue_spaceship, duration_spaceship) = await generate_data("Darkride", previous_spaceship)
            (velocity_cosmic, temperature_cosmic, vibration_cosmic,people_queue_cosmic, duration_cosmic) = await generate_data("Darkride", previous_cosmic)
            (velocity_test, temperature_test, vibration_test,people_queue_test, duration_test) = await generate_data("Darkride", previous_test)
            People_in_out = await simulate_sensor()

            data = {
                "Spaceship Earth": {
                    "velocity_kmh": velocity_spaceship,
                    "temperature": temperature_spaceship,
                    "vibration": vibration_spaceship,
                    "people_queue": people_queue_spaceship,
                    "duration": duration_spaceship
                },
                "Guardians of the Galaxy: Cosmic Rewind": {
                    "velocity_kmh": velocity_cosmic,
                    "temperature": temperature_cosmic,
                    "vibration": vibration_cosmic,
                    "people_queue": people_queue_cosmic,
                    "duration": duration_cosmic
                },
                "Test Track": {
                    "velocity_kmh": velocity_test,
                    "temperature": temperature_test,
                    "vibration": vibration_test,
                    "people_queue": people_queue_test,
                    "duration": duration_test
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()
            }

            self.channel.basic_publish(exchange='', routing_key='Epcot', body=json.dumps(data))

            if velocity_spaceship > 100:
                previous_spaceship['velocity'] = 0.0
            if velocity_cosmic > 100:
                previous_cosmic['velocity'] = 0.0
            if velocity_test > 100:
                previous_test['velocity'] = 0.0
            
            if temperature_spaceship > 100:
                previous_spaceship['temperature'] = 0.0
            if temperature_cosmic > 100:
                previous_cosmic['temperature'] = 0.0
            if temperature_test > 100:
                previous_test['temperature'] = 0.0
            
            if vibration_spaceship > 100:
                previous_spaceship['vibration'] = 0.0
            if vibration_cosmic > 100:
                previous_cosmic['vibration'] = 0.0
            if vibration_test > 100:
                previous_test['vibration'] = 0.0
            

            await asyncio.sleep(5)

    async def hollywood_studios(self):
        while True:
            (velocity_runaway, height_runaway, temperature_runaway,vibration_runaway, people_queue_runaway, duration_runaway) = await generate_data("Rollercoaster", previous_runaway)
            (velocity_rocknroller, height_rocknroller, temperature_rocknroller,vibration_rocknroller, people_queue_rocknroller, duration_rocknroller) = await generate_data("Rollercoaster", previous_rocknroller)
            People_in_out = await simulate_sensor()

            data = {
                "Mickey & Minnie's Runaway Railway" : {
                    "velocity_kmh": velocity_runaway,
                    "height_m": height_runaway,
                    "temperature": temperature_runaway,
                    "vibration": vibration_runaway,
                    "people_queue" : people_queue_runaway,
                    "duration" : duration_runaway
                },
                "Rock n Roller Coaster" : {
                    "velocity_kmh": velocity_rocknroller,
                    "height_m": height_rocknroller,
                    "temperature": temperature_rocknroller,
                    "vibration": vibration_rocknroller,
                    "people_queue" : people_queue_rocknroller,
                    "duration" : duration_rocknroller
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()

            }
            self.channel.basic_publish(exchange='', routing_key='Hollywood Studios', body=json.dumps(data))

            if velocity_runaway > 100:
                previous_runaway['velocity'] = 0.0
            if velocity_rocknroller > 100:
                previous_rocknroller['velocity'] = 0.0
            
            if height_runaway > 100:
                previous_runaway['height'] = 0.0
            if height_rocknroller > 100:
                previous_rocknroller['height'] = 0.0
            
            if temperature_runaway > 100:
                previous_runaway['temperature'] = 0.0
            if temperature_rocknroller > 100:
                previous_rocknroller['temperature'] = 0.0
            
            if vibration_runaway > 100:
                previous_runaway['vibration'] = 0.0
            if vibration_rocknroller > 100:
                previous_rocknroller['vibration'] = 0.0
            
            await asyncio.sleep(5)

    async def animal_kingdom(self):

        while True:

            (velocity_triceratop, rpm_triceratop, temperature_triceratop,vibration_triceratop, people_queue_triceratop, duration_triceratop) = await generate_data("Carousel", previous_triceratop)
            (velocity_kaliriver, height_kaliriver, temperaturewater_kaliriver,vibration_kaliriver, people_queue_kaliriver, duration_kaliriver) = await generate_data("WaterRide", previous_kaliriver)
            (velocity_dinosaur, temperature_dinosaur,vibration_dinosaur, people_queue_dinosaur, duration_dinosaur) = await generate_data("Darkride", previous_dinosaur)
            People_in_out = await simulate_sensor()

            data = {
                "Triceratop Spin" : {
                    "velocity_kmh": velocity_triceratop,
                    "rpm": rpm_triceratop,
                    "temperature": temperature_triceratop,
                    "vibration": vibration_triceratop,
                    "people_queue" : people_queue_triceratop,
                    "duration" : duration_triceratop
                },
                "Kali River Rapids" : {
                    "velocity_kmh": velocity_kaliriver,
                    "height_m": height_kaliriver,
                    "temperature": temperaturewater_kaliriver,
                    "vibration": vibration_kaliriver,
                    "people_queue" : people_queue_kaliriver,
                    "duration" : duration_kaliriver
                },
                "Dinosaur" : {
                    "velocity_kmh": velocity_dinosaur,
                    "temperature": temperature_dinosaur,
                    "vibration": vibration_dinosaur,
                    "people_queue" : people_queue_dinosaur,
                    "duration" : duration_dinosaur
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()
            }
            self.channel.basic_publish(exchange='', routing_key='Animal Kingdom', body=json.dumps(data))

            if velocity_triceratop > 100:
                previous_triceratop['velocity'] = 0.0
            if velocity_dinosaur > 100:
                previous_dinosaur['velocity'] = 0.0
            if velocity_kaliriver > 100:
                previous_kaliriver['velocity'] = 0.0
            
            if rpm_triceratop > 100:
                previous_triceratop['rpm'] = 0.0
            
            if temperature_triceratop > 50:
                previous_triceratop['temperature'] = 0.0
            if temperature_dinosaur > 100:
                previous_dinosaur['temperature'] = 0.0
            if temperaturewater_kaliriver > 100:
                previous_kaliriver['temperaturewater'] = 0.0

            if vibration_triceratop > 100:
                previous_triceratop['vibration'] = 0.0
            if vibration_dinosaur > 100:
                previous_dinosaur['vibration'] = 0.0
            if vibration_kaliriver > 100:
                previous_kaliriver['vibration'] = 0.0
            


            await asyncio.sleep(5)

    async def disney_springs(self):

        while True:
            (velocity_marketplace, height_marketplace, temperature_marketplace,vibration_marketplace, people_queue_marketplace, duration_marketplace) = await generate_data("Rollercoaster", previous_marketplace)
            (velocity_classic, rpm_classic, temperature_classic,vibration_classic, people_queue_classic, duration_classic) = await generate_data("Carousel", previous_classic)
            People_in_out = await simulate_sensor()


            data = {
                "Marketplace Train Express" : {
                    "velocity_kmh": velocity_marketplace,
                    "height_m": height_marketplace,
                    "temperature": temperature_marketplace,
                    "vibration": vibration_marketplace,
                    "people_queue" : people_queue_marketplace,
                    "duration" : duration_marketplace
                },
                "Classic Carousel" : {
                    "velocity_kmh": velocity_classic,
                    "rpm": rpm_classic,
                    "temperature": temperature_classic,
                    "vibration": vibration_classic,
                    "people_queue" : people_queue_classic,
                    "duration" : duration_classic
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()

            }
            self.channel.basic_publish(exchange='', routing_key='Disney Springs', body=json.dumps(data))

            if velocity_marketplace > 100:
                previous_marketplace['velocity'] = 0.0
            if velocity_classic > 100:
                previous_classic['velocity'] = 0.0
            
            if height_marketplace > 100:
                previous_marketplace['height'] = 0.0
            
            if rpm_classic > 100:
                previous_classic['rpm'] = 0.0
            
            if temperature_marketplace > 100:
                previous_marketplace['temperature'] = 0.0
            if temperature_classic > 50:
                previous_classic['temperature'] = 0.0

            if vibration_marketplace > 100:
                previous_marketplace['vibration'] = 0.0
            if vibration_classic > 100:
                previous_classic['vibration'] = 0.0

            await asyncio.sleep(5)

    async def blizzard_beach(self):

        while True:
            (velocity_summit, height_summit, temperaturewater_summit,vibration_summit, people_queue_summit, duration_summit) = await generate_data("WaterRide", previous_summit)
            (velocity_slush, height_slush, temperaturewater_slush,vibration_slush, people_queue_slush, duration_slush) = await generate_data("WaterRide", previous_slush)
            People_in_out = await simulate_sensor()


            data = {
                "Summit Plummet" : {
                    "velocity_kmh": velocity_summit,
                    "height_m": height_summit,
                    "temperature": temperaturewater_summit,
                    "vibration": vibration_summit,
                    "people_queue" : people_queue_summit,
                    "duration" : duration_summit
                },
                "Slush Gusher" : {
                    "velocity_kmh": velocity_slush,
                    "height_m": height_slush,
                    "temperature": temperaturewater_slush,
                    "vibration": vibration_slush,
                    "people_queue" : people_queue_slush,
                    "duration" : duration_slush
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()

            }
            self.channel.basic_publish(exchange='', routing_key='Blizzard Beach', body=json.dumps(data))

            if velocity_summit > 100:
                previous_summit['velocity'] = 0.0
            if velocity_slush > 100:
                previous_slush['velocity'] = 0.0

            if height_summit > 100:
                previous_summit['height'] = 0.0
            if height_slush > 100:
                previous_slush['height'] = 0.0
            
            if temperaturewater_summit > 100:
                previous_summit['temperaturewater'] = 0.0
            if temperaturewater_slush > 100:
                previous_slush['temperaturewater'] = 0.0

            if vibration_summit > 100:
                previous_summit['vibration'] = 0.0
            if vibration_slush > 100:
                previous_slush['vibration'] = 0.0

            await asyncio.sleep(5)

    async def typhoon_lagoon(self):

        while True:
            (velocity_gangplank, height_gangplank, temperaturewater_gangplank,vibration_gangplank, people_queue_gangplank, duration_gangplank) = await generate_data("WaterRide", previous_gangplank)
            (velocity_humunga, height_humunga, temperaturewater_humunga,vibration_humunga, people_queue_humunga, duration_humunga) = await generate_data("WaterRide", previous_humunga)
            People_in_out = await simulate_sensor()


            data = {
                "Gangplank Falls" : {
                    "velocity_kmh": velocity_gangplank,
                    "height_m": height_gangplank,
                    "temperature": temperaturewater_gangplank,
                    "vibration": vibration_gangplank,
                    "people_queue" : people_queue_gangplank,
                    "duration" : duration_gangplank
                },
                "Humunga Kowabunga" : {
                    "velocity_kmh": velocity_humunga,
                    "height_m": height_humunga,
                    "temperature": temperaturewater_humunga,
                    "vibration": vibration_humunga,
                    "people_queue" : people_queue_humunga,
                    "duration" : duration_humunga
                },
                "Visitors": {
                    "entry_exit": People_in_out
                },
                "Time": time.time()

            }
            self.channel.basic_publish(exchange='', routing_key='Typhoon Lagoon', body=json.dumps(data))
            
            if velocity_gangplank > 100:
                previous_gangplank['velocity'] = 0.0
            if velocity_humunga > 100:
                previous_humunga['velocity'] = 0.0

            if height_gangplank > 100:
                previous_gangplank['height'] = 0.0
            if height_humunga > 100:
                previous_humunga['height'] = 0.0

            if temperaturewater_gangplank > 100:
                previous_gangplank['temperaturewater'] = 0.0
            if temperaturewater_humunga > 100:
                previous_humunga['temperaturewater'] = 0.0

            if vibration_gangplank > 100:
                previous_gangplank['vibration'] = 0.0
            if vibration_humunga > 100:
                previous_humunga['vibration'] = 0.0


            await asyncio.sleep(5)

    async def parkingLot(self):

        while True:
            (cars_in, cars_out) = await generate_data("parkinglot", previous_parkingLot1)
            (cars_in, cars_out) = await generate_data("parkinglot", previous_parkingLot2)

            data = {
                "ParkingLot1": {
                    "cars_in" : cars_in,
                    "cars_out" : cars_out
                },
                "ParkingLot2": {
                    "cars_in" : cars_in,
                    "cars_out" : cars_out
                },
                "Time" : time.time()
            }

            self.channel.basic_publish(exchange='', routing_key='Parking Lot', body=json.dumps(data))

            await asyncio.sleep(50)



async def random_value(previous, max_difference, precision=2):
    lower_limit = max(0.0, previous - max_difference)
    upper_limit = min(120, previous + max_difference)
    value = random.uniform(lower_limit, upper_limit)
    return round(value, precision)

async def random_value_people(previous, max_difference):
    lower_limit = max(0, previous - max_difference)
    upper_limit = min(200, previous + max_difference)
    value = random.uniform(lower_limit, upper_limit)
    return round(value)

async def random_value_duration(previous, max_difference, precision=2):
    new_duration = previous - max_difference
    if new_duration <= 0:
        new_duration = 900

    return new_duration

async def randomCars():
    value = random.randint(0, 5)

    return value

async def simulate_sensor():
    # Simula o fluxo contínuo de pessoas
    # Neste exemplo, há uma probabilidade maior de entrada do que saída para refletir o aumento típico durante o dia
    probability_of_entry = 0.7
    probability_of_exit = 0.3

    # Simula entradas e saídas com base nas probabilidades
    entry_value = 5
    exit_value = -3

    value = entry_value if random.uniform(0, 1) < probability_of_entry else exit_value if random.uniform(0, 1) < probability_of_exit else 0

    return value



async def generate_data(type, previous_data, max_difference=5.0):
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
    elif type == "Carousel":
        velocity = await random_value(previous_data['velocity'], max_difference)
        previous_data['velocity'] = velocity
        rpm = await random_value(previous_data['rpm'], max_difference)
        previous_data['rpm'] = rpm
        temperature = await random_value(previous_data['temperature'], max_difference)
        previous_data['temperature'] = temperature
        vibration = await random_value(previous_data['vibration'], max_difference)
        previous_data['vibration'] = vibration
        people_queue = await random_value_people(previous_data['people_queue'], 5)
        previous_data['people_queue'] = people_queue
        duration = await random_value_duration(previous_data['duration'], 20)
        previous_data['duration'] = duration
        return velocity, rpm, temperature, vibration, people_queue, duration
    elif type == "WaterRide":
        velocity = await random_value(previous_data['velocity'], max_difference)
        previous_data['velocity'] = velocity
        height = await random_value(previous_data['height'], max_difference)
        previous_data['height'] = height
        temperaturewater = await random_value(previous_data['temperaturewater'], max_difference)
        previous_data['temperaturewater'] = temperaturewater
        vibration = await random_value(previous_data['vibration'], max_difference)
        previous_data['vibration'] = vibration
        people_queue = await random_value_people(previous_data['people_queue'], 5)
        previous_data['people_queue'] = people_queue
        duration = await random_value_duration(previous_data['duration'], 20)
        previous_data['duration'] = duration
        return velocity, height, temperaturewater, vibration, people_queue, duration
    elif type == "parkinglot" : 
        cars_in = await randomCars()
        previous_data['cars_in'] = cars_in
        cars_out = await randomCars()
        previous_data['cars_out'] = cars_out
        return cars_in, cars_out








if __name__ == '__main__':
    generators = Generators()
    loop = asyncio.get_event_loop()

    magic_kingdom = loop.create_task(generators.MagicKingdom())
    epcot = loop.create_task(generators.epcot())
    disney_studios = loop.create_task(generators.hollywood_studios())
    animal_kingdom = loop.create_task(generators.animal_kingdom())
    disney_springs = loop.create_task(generators.disney_springs())
    blizzard_beach = loop.create_task(generators.blizzard_beach())
    typhoon_lagoon = loop.create_task(generators.typhoon_lagoon())
    parkinglot = loop.create_task(generators.parkingLot())


    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    finally:
        loop.close()
