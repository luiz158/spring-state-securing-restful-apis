/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sample.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import sample.security.ReadableMessage;

/**
 * Manages {@link Message} instances
 *
 * @author Rob Winch
 *
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

	@Query("select m from Message m where m.to.id = ?#{principal.id}")
	Iterable<Message> inbox();

	@Query("select m from Message m where m.from.id = ?#{principal.id}")
	Iterable<Message> sent();

	@ReadableMessage
	Message findOne(@Param("id") Long id);

	@ReadableMessage
	Message findBySummary(@Param("summary") String summary);

	<S extends Message> S save(@P("message") S message);

	void delete(Long id);
}
