# Demo to run Spring Cloud Stream with Avro Schema and Idempotency using Kafka binder

This demo uses Spring Cloud Stream to illustrate message schema evolution, 
idempotency interception pattern to detect duplicate messages and shows how to
enrich a message from data obtain from external web service. Spring retry with
circuit breaker is used when invoke external services in case something goes wrong.

The stream use case is for illustration 
purpose only, it uses Kafka as a binder message broker. 

To run the stream applications, clone this repository, build all modules using
```mvn clean install``` then follow the steps below.

1- Run kafka docker containers using the attached script 
```shell
 > ./runKafka.sh
```
2- This sample contains self contained a source, two processors and a sink applications, 
run each one in a separate terminal window using the command below.

Run the web-service and schema-registry-service first

```shell
 > mvn spring-boot:run
```
3- Sink application console is expected to log messages out. No duplicate message
with same combination of (id, name, status) should be written out to the console.


The web-service application is expected to throw exception that the enricher processor
application would recover from.

Message Avro schemas are dynamically auto-generated. 