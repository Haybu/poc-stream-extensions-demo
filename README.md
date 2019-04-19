# Demo to run Spring Cloud Stream using Kafka binder



1- Run kafka docker containers using the attached script 
```shell
 > ./runKafka.sh
```
2- This sample contains self contained source, processor and sink application, run each one in a separate terminal window using
```shell
 > mvn spring-boot:run
```
3- Sink application console is expected to log messages out.
