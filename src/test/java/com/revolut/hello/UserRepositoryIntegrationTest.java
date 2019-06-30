package com.revolut.hello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
    private UserRepository userRepository;
 
	@Test
	public void whenFindByName_thenReturnEmployee() {
	    // given
	    RevolutUser alex = new RevolutUser("alex", "2000-02-20");
	    entityManager.persist(alex);
	    entityManager.flush();
	 
	    // when
	    RevolutUser found = userRepository.findById(alex.getName()).orElse(null);;
	 
	    // then
	    assertThat(found.getName())
	      .isEqualTo(alex.getName());
	}
}
