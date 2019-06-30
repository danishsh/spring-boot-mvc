package com.revolut.hello;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<RevolutUser, String>{

	
}
