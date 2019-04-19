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

import io.agilehandy.commons.exceptions.DepartmentNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Haytham Mohamed
 **/

@Service
public class DepartmentService {

	@Autowired
	private final RestTemplate restTemplate;

	public DepartmentService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Retryable(include=DepartmentNotFoundException.class, maxAttempts=12, backoff=@Backoff(delay=100, maxDelay=500))
	public String getDepartment(int id) throws DepartmentNotFoundException {
		return
				restTemplate.getForObject("http://localhost:8080/departments/"+id, String.class);
	}

	@Recover
	public String recover1(DepartmentNotFoundException e, int id) throws DepartmentNotFoundException {
		return "Recovered-NotFound";
	}

	@Recover
	public String recover2(HttpServerErrorException.InternalServerError e, int id)
			throws HttpServerErrorException.InternalServerError {
		return "Recovered-InternalErr";
	}


}
