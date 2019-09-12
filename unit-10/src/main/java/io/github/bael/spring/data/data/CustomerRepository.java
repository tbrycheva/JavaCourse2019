package io.github.bael.spring.data.data;

import io.github.bael.spring.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findAll();

	Customer findFirstByName(String name);

}
