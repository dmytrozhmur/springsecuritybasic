package com.griddynamics.springsecuritybasic.repo;

import com.griddynamics.springsecuritybasic.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
