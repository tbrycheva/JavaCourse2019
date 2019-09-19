package io.github.bael.spring.data.data;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.BuyingBook;
import io.github.bael.spring.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuyingBookRepository extends CrudRepository<BuyingBook, Long> {

	List<BuyingBook> findAll();

	List<BuyingBook> findByBook(Book book);

	List<BuyingBook> findByCustomer(Customer customer);
}

