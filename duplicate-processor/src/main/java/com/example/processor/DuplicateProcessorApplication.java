package com.example.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.handler.advice.IdempotentReceiverInterceptor;
import org.springframework.integration.selector.MetadataStoreSelector;

@SpringBootApplication
@EnableSchemaRegistryClient
public class DuplicateProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuplicateProcessorApplication.class, args);
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

