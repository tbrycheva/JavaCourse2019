package io.github.bael.spring.data.service;

import io.github.bael.spring.data.SpringDataApplication;
import io.github.bael.spring.data.data.*;
import io.github.bael.spring.data.entity.Author;
import io.github.bael.spring.data.entity.AuthorOfBook;
import io.github.bael.spring.data.entity.Book;
import io.github.bael.spring.data.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataApplication.class)
public class BuyingBookServiceImplTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BuyingBookRepository buyingBookRepository;

	@Autowired
	private BuyingBookService buyingBookService;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void init() {
		Author mark = new Author();
		mark.setFirstname("Mark");
		mark.setLastname("Twain");
		authorRepository.save(mark);
		Book book = new Book();
		book.setDescription("Увлекательные приключения Тома Сойера");
		book.setTitle("Приключения Тома Сойера");
		book.setYear(1876);
		bookRepository.save(book);

		Book book2 = new Book();
		book2.setDescription("Увлекательные приключения Гекльберри Финна");
		book2.setTitle("Приключения Гекльберри Финна");
		book2.setYear(1876);
		bookRepository.save(book2);

		AuthorOfBook aob1 = new AuthorOfBook();
		aob1.setAuthor(mark);
		aob1.setBook(book);
		authorOfBookRepository.save(aob1);

		AuthorOfBook aob2 = new AuthorOfBook();
		aob2.setAuthor(mark);
		aob2.setBook(book2);
		authorOfBookRepository.save(aob2);

		Customer customer = new Customer();
		customer.setName("Alex");
		customer.setAddress("Krasnoyarsk, Lin street, 45");
		customerRepository.save(customer);

		Customer customer2 = new Customer();
		customer2.setName("Helen");
		customer2.setAddress("Krasnoyarsk, Mira street, 67");
		customerRepository.save(customer2);
	}

	@After
	public void clear() {
		buyingBookRepository.deleteAll();
		authorOfBookRepository.deleteAll();
		authorRepository.deleteAll();
		bookRepository.deleteAll();
		customerRepository.deleteAll();
	}

	@Test
	public void testBuyingBook() {
		Customer customer = customerRepository.findAll().stream().findFirst().orElse(null);
		Book book = bookRepository.findFirstByTitle("Приключения Тома Сойера");

		buyingBookService.buyBook(book, BigDecimal.valueOf(560), customer);

		boolean exists = buyingBookRepository.findByBook(book).stream().findFirst().isPresent();

		assertTrue(exists);
	}

	@Test
	public void testBuyingNullBook() {
		Customer customer = customerRepository.findAll().stream().findFirst().orElse(null);
		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("buyBook: book is null");

		buyingBookService.buyBook(null, BigDecimal.valueOf(560), customer);
	}

	@Test
	public void testBuyingByNullCustomer() {
		Book book = bookRepository.findFirstByTitle("Приключения Тома Сойера");
		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("buyBook: customer is null");

		buyingBookService.buyBook(book, BigDecimal.valueOf(560), null);
	}

	@Test
	public void testCountTotalSalesCostByBook() {
		Customer customerAlex = customerRepository.findFirstByName("Alex");
		Customer customerHelen = customerRepository.findFirstByName("Helen");
		Book book = bookRepository.findAll().stream().findFirst().orElse(null);
		buyingBookService.buyBook(book, BigDecimal.valueOf(560), customerAlex);
		buyingBookService.buyBook(book, BigDecimal.valueOf(570), customerHelen);

		assertEquals(
				BigDecimal.valueOf(1130).setScale(2),
				buyingBookService.countTotalSalesCostByBook(book));
	}

	@Test
	public void testCountTotalSalesCostByNullBook() {
		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("countTotalSalesCostByBook: book is null");

		buyingBookService.countTotalSalesCostByBook(null);
	}

	@Test
	public void testCountTotalSalesCostByCustomer() {
		Customer customer = customerRepository.findFirstByName("Helen");

		Book book1 = bookRepository.findFirstByTitle("Приключения Тома Сойера");
		Book book2 = bookRepository.findFirstByTitle("Приключения Гекльберри Финна");

		buyingBookService.buyBook(book1, BigDecimal.valueOf(560), customer);
		buyingBookService.buyBook(book2, BigDecimal.valueOf(650), customer);

		assertEquals(
				BigDecimal.valueOf(1210).setScale(2),
				buyingBookService.countTotalBuyingBooksByCustomer(customer));
	}

	@Test
	public void testCountTotalBuyingBooksByNullCustomer() {
		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("countTotalBuyingBooksByCustomer: customer is null");

		buyingBookService.countTotalBuyingBooksByCustomer(null);
	}
}