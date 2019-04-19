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

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * @author Haytham Mohamed
 **/

@Configuration
public class PollerConfiguration {

	@Bean
	public PollerMetadata defaultPoller(Trigger trigger) {
		PollerMetadata pollerMetadata = new PollerMetadata();
		pollerMetadata.setTrigger(trigger);
		// default it to 1
		pollerMetadata.setMaxMessagesPerPoll(1);
		return pollerMetadata;
	}

	@Bean
	public Trigger periodicTrigger(TriggerProperties triggerProperties) {
		PeriodicTrigger trigger = new PeriodicTrigger(
				triggerProperties.getFixedDelay(),
				TimeUnit.valueOf(triggerProperties.getTimeUnit()));
		trigger.setInitialDelay(triggerProperties.getInitialDelay());
		return trigger;
	}

}
