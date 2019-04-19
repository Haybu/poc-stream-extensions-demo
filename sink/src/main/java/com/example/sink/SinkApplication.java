package com.example.sink;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agilehandy.commons.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.handler.advice.IdempotentReceiverInterceptor;
import org.springframework.integration.selector.MetadataStoreSelector;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
@EnableBinding(Sink.class)
public class SinkApplication {

	Logger log = LoggerFactory.getLogger(SinkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SinkApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void handle(Person person) {
		log.info(person.toString());
	}

	//@Bean
	//@ServiceActivator(inputChannel = Sink.INPUT)
	//@IdempotentReceiver("idempotentReceiverInterceptor")
	public MessageHandler handle() {
		return message -> {
			log.info(message.getPayload().toString());
		};
	}

	//@Bean
	//@ConditionalOnMissingBean
	public IdempotentReceiverInterceptor idempotentReceiverInterceptor() {
		MetadataStoreSelector selector = new MetadataStoreSelector(
				m -> {
					String payload = m.getPayload().toString();
					ObjectMapper mapper = new ObjectMapper();
					try {
						Person person = mapper.readValue(payload, Person.class);
						return person.getId() + person.getName() + person.getStatus();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					return m.getPayload().toString();
				}
		);

		IdempotentReceiverInterceptor idempotentReceiverInterceptor =
				new IdempotentReceiverInterceptor(selector);

		idempotentReceiverInterceptor.setDiscardChannel(new NullChannel());

		return idempotentReceiverInterceptor;
	}

}

