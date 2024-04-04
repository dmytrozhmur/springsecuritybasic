package com.griddynamics.springsecuritybasic.repo;

import java.util.List;

import com.griddynamics.springsecuritybasic.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(int customerId);

}
