package com.example.processor;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agilehandy.commons.models.Person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.advice.IdempotentReceiverInterceptor;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.selector.MetadataStoreSelector;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.MimeTypeUtils;

@SpringBootApplication
//@EnableSchemaRegistryClient
public class ProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	public IdempotentReceiverInterceptor idempotentReceiverInterceptor() {
		// key lookup depends on a specific header
		MetadataStoreSelector selector =
				new MetadataStoreSelector(m -> (String)m.getHeaders().get("person-key"));

		IdempotentReceiverInterceptor idempotentReceiverInterceptor =
				new IdempotentReceiverInterceptor(selector);

		// to ignore duplicate messages
		idempotentReceiverInterceptor.setDiscardChannel(new NullChannel());

		return idempotentReceiverInterceptor;
	}

}

