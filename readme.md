# Easycrypto

[PoC]

Application that enriches a request info message with cryptocoin`s 
price and geo location.

## Getting Started

### Prerequisites

```
docker
```

```
gradle 
```

Register yourself and get a key
```
https://openexchangerates.org/
```

### Configuring

Inside docker-compose set **OPENEXCHANGE_API_KEY** with your key

### Running

Build jar

```
gradle jar
```

Build and run images

```
docker-compose up --build
```

Create kafka topics with script inside project

```
./kafka-setup.sh
```

### Testing

After containers are running, you can access the kafka container
```
docker exec -it easycrypto_kafka_1 bash
```

Start the producer in the input topic
```
kafka-console-producer --broker-list localhost:29092 --topic input-topic
```

Then send valid messages
```
{"event": "CUSTOMER_CONSULTS_ETHPRICE", "customer": {"id": "14862768", "name": "Snowden, Edward", "ipAddress": "95.31.18.111"}, "currency": {"name": "ethereum", "price": "RUB"}, "timestamp": "2018-09-28T09:09:09Z"}

{"event": "CUSTOMER_CONSULTS_ETHPRICE", "customer": {"id": "13548310", "name": "Assange, Julian", "ipAddress": "185.86.151.11"}, "currency": {"name": "ethereum", "price": "EUR"}, "timestamp": "2018-09-28T08:08:14Z"}

{"event": "CUSTOMER_CONSULTS_ETHPRICE", "customer": {"id": "15887564", "name": "Mills, Lindsay", "ipAddress": "186.46.129.15"}, "currency": {"name": "ethereum", "price": "USD"}, "timestamp": "2018-09-28T19:51:35Z”}
```

Or invalid

```
{"event": "CUSTOMER_CONSULTS_ETHPRICE", "customer": {"id": "14862768", "name": "Snowden, Edward"}, "currency": {"name": "ethereum", "price": "RUB"}, "timestamp": "2018-09-28T09:09:09Z"}
```

The output topic can be consume to see the result.
Inside kafka container.

```
kafka-console-consumer --bootstrap-server localhost:29092 --from-beginning --topic valid-messages
```

```
kafka-console-consumer --bootstrap-server localhost:29092 --from-beginning --topic invalid-messages
```

### Troubleshooting

Kafka log
```
docker exec -it easycrypto_kafka_1 bash
```

```
In case of local running, you must change network
in docker-compose (KAFKA_ADVERTISED_LISTENERS and KAFKA_ADVERTISED_HOST_NAME)
```
