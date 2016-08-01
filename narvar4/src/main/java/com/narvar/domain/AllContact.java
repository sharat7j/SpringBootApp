package com.narvar.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contacts", path = "contactlist")
public interface AllContact extends PagingAndSortingRepository<Contacts, Long>  {
	List<Contacts> findByName(@Param("name") String name);
	
}
