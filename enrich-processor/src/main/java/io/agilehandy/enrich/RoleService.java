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

import io.agilehandy.commons.exceptions.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Haytham Mohamed
 **/

@Service
public class RoleService {

	@Autowired
	private final RestTemplate restTemplate;

	public RoleService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@CircuitBreaker(maxAttempts = 1, openTimeout = 10000, resetTimeout=10000)
	public String getRole(int id) throws RoleNotFoundException {
		return
				restTemplate.getForObject("http://localhost:8080/roles/"+id, String.class);
	}

	@Recover
	public String recover1(RoleNotFoundException e, int id) throws RoleNotFoundException {
		return "Recovered-NotFound";
	}

	@Recover
	public String recover2(HttpServerErrorException.InternalServerError e, int id)
			throws HttpServerErrorException.InternalServerError {
		return "Recovered-InternalErr";
	}

	//

}
