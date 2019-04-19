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
import io.agilehandy.commons.RandomNumber;

import org.springframework.stereotype.Component;

/**
 * @author Haytham Mohamed
 **/
@Component
public class PersonGenerator {

	private final String[] NAMES = {"Lok", "Derek", "Ann"};
	private final String[] STATUS = {"NEW", "PENDING", "APPROVED", "CANCELLED"};

	public Person getPerson() {
		Person person = new Person();
		person.setId(RandomNumber.range(1, 5));
		person.setName(pickName());
		person.setStatus(pickStatus());
		person.setDepartment("NONE");
		person.setRole("NONE");
		return person;
	}

	private String pickName() {
		return NAMES[RandomNumber.range(0, NAMES.length)];
	}

	private String pickStatus() {
		return STATUS[RandomNumber.range(0, STATUS.length)];
	}

}
