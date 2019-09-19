package io.github.bael.spring.data.service;

import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;

import java.math.BigDecimal;

public interface BuyingBookService {

	void buyBook(Book book, BigDecimal cost, Customer customer);

	BigDecimal countTotalSalesCostByBook(Book book);

	BigDecimal countTotalBuyingBooksByCustomer(Customer customer);

}
