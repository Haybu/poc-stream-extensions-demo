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
package io.agilehandy.enrich;

import io.agilehandy.commons.models.Person;
import io.agilehandy.commons.RandomNumber;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author Haytham Mohamed
 **/

@Component
@EnableBinding(Processor.class)
public class EnrichProcessor {

	private final DepartmentService departmentService;
	private final RoleService roleService;

	public EnrichProcessor(DepartmentService departmentService, RoleService roleService) {
		this.departmentService = departmentService;
		this.roleService = roleService;
	}

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Person enrich(Person person) {
		person.setDepartment(departmentService.getDepartment(RandomNumber.range(0, 7)));
		person.setRole(roleService.getRole(RandomNumber.range(0, 7)));
		return person;
	}

}
