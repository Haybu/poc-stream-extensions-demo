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
package com.example.processor;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

/**
 * @author Haytham Mohamed
 **/

@Component
@EnableBinding(Processor.class)
public class DuplicateMessageProcessor {

	/**
	 * this message handler is intercepted with an idempotentReceiverInterceptor
	 * that eliminate duplicate messages based on a key. The header is chosen
	 * as a header in this illustration, but you can form it from whatever key
	 * with/without the payload content.
	 *
	 * @return
	 */
	@Bean
	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	@IdempotentReceiver("idempotentReceiverInterceptor")
	public MessageProcessor<Message<?>> handleDuplicates() {
		return message -> {
			MessageBuilder<?> builder = MessageBuilder.fromMessage(message)
					.copyHeaders(message.getHeaders())
					//.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
					;
			return builder.build();
		};
	}

}
