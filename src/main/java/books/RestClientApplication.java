package books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClientApplication implements CommandLineRunner {

	@Autowired
	private RestOperations restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String serverUrl = "http://localhost:8080/api/v1/books";

		System.out.println("----------- Book-1 & Book-2 added -----------------------");
		// add Book-1
		restTemplate.postForLocation(serverUrl, new Book("Book-1", "Subtle Art of not giving a Fuck", "author1", 12.09));
		// add Book-2
		restTemplate.postForLocation(serverUrl, new Book("Book-2", "7 Habits of effective people", "author2", 15.56));

		// get Book-1
		Book book = restTemplate.getForObject(serverUrl + "/{isbn}", Book.class, "Book-1");
		System.out.println("----------- get Book-1 -----------------------");
		System.out.println(book);

		// get all
		Books books = restTemplate.getForObject(serverUrl, Books.class);
		System.out.println("----------- get all Books-----------------------");
		System.out.println(books);

		// delete Book-2
		System.out.println("----------- Book-2 deleted -----------------------");
		restTemplate.delete(serverUrl + "/{isbn}", "Book-2");

		// update Book-1
		System.out.println("----------- Book-1 author name updated -----------------------");
		book.setAuthor("frank");
		restTemplate.put(serverUrl + "/{isbn}", book, book.getIsbn());

		// get all
		books = restTemplate.getForObject(serverUrl, Books.class);
		System.out.println("----------- get all Books after update -----------------------");
		System.out.println(books);
	}

	@Bean
	RestOperations restTemplate() {
		return new RestTemplate();
	}
}
