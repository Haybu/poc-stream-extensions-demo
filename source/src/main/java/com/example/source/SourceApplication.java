package com.example.source;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.agilehandy.commons.models.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.annotation.PollableSource;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

@SpringBootApplication
@EnableBinding(Source.class)
public class SourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourceApplication.class, args);
	}

	@Autowired
	PersonGenerator personGenerator;

	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT,
			poller = @Poller(fixedRate = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<Person> timerMessageSource() {
		return () -> {
			Person person = personGenerator.getPerson();
			String key =
					person.getId() + person.getName().toLowerCase() + person.getStatus().toLowerCase();
			return MessageBuilder.withPayload(person).setHeader("person-key", key).build();
		};
	}

}

