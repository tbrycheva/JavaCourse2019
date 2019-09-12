package io.github.bael.spring.data.service;


import io.github.bael.spring.data.data.BuyingBookRepository;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.BuyingBook;
import io.github.bael.spring.data.entity.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BuyingBookServiceImpl implements BuyingBookService {

	private final BuyingBookRepository buyingBookRepository;

	public BuyingBookServiceImpl(BuyingBookRepository buyingBookRepository) {
		this.buyingBookRepository = buyingBookRepository;
	}

	@Override
	public void buyBook(Book book, BigDecimal cost, Customer customer) {
		Optional.ofNullable(book)
				.map(bookValue -> Optional.ofNullable(customer)
						.map(customerValue -> {
							BuyingBook buyingBook = new BuyingBook();
							buyingBook.setBook(book);
							buyingBook.setCost(cost);
							buyingBook.setCustomer(customer);
							return this.buyingBookRepository.save(buyingBook);
						})
						.<RuntimeException>orElseThrow(() -> new RuntimeException("buyBook: customer is null")))
				.<RuntimeException>orElseThrow(() -> new RuntimeException("buyBook: book is null"));
	}

	@Override
	public BigDecimal countTotalSalesCostByBook(Book book) {
		return Optional.ofNullable(book)
				.map(value -> this.buyingBookRepository.findByBook(book).stream()
						.map(BuyingBook::getCost)
						.reduce(new BigDecimal(0), BigDecimal::add))
				.<RuntimeException>orElseThrow(() -> new RuntimeException("countTotalSalesCostByBook: book is null"));
	}

	@Override
	public BigDecimal countTotalBuyingBooksByCustomer(Customer customer) {
		return Optional.ofNullable(customer)
				.map(value -> this.buyingBookRepository.findByCustomer(customer).stream()
						.map(BuyingBook::getCost)
						.reduce(new BigDecimal(0), BigDecimal::add))
				.<RuntimeException>orElseThrow(() -> new RuntimeException("countTotalBuyingBooksByCustomer: customer is null"));
	}
}
