/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.source;

import io.agilehandy.commons.models.Person;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Haytham Mohamed
 **/

@Component
@EnableBinding(Source.class)
public class PersonSource {

	/**
	 * A message source of some payload with a chosen header to illustrate
	 * a value that acts as a unique identifier.
	 *
	 * @param personGenerator
	 * @return
	 */
	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT,
			poller = @Poller(fixedRate = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<Person> timerMessageSource(PersonGenerator personGenerator) {
		return () -> {
			Person person = personGenerator.getPerson();
			// TODO: choose a key that uniquely identifies a message
			String key =
					person.getId() + person.getName().toLowerCase() + person.getStatus().toLowerCase();
			return MessageBuilder.withPayload(person)
					.setHeader("person-key", key).build();
		};
	}

}
