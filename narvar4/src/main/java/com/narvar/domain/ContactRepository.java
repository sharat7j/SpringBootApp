package com.narvar.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contactlist")
public interface ContactRepository extends CrudRepository<Contacts, Long>  {
	List<Contacts> findByName(@Param("name") String name);
	
}
