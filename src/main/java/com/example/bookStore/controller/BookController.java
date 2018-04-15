package com.example.bookStore.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import com.example.bookStore.dto.BookDTO;
import com.example.bookStore.dto.Category;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.Role;
import com.example.bookStore.model.User;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.UserRepository;




@Controller
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/createBook")
	public String createBook(Model model) {
		model.addAttribute("Book", new Book());
		return "createBook";
	}

	@PostMapping("/createBook")
	public String createBook(@ModelAttribute("Book") @Valid BookDTO bookDto,
			BindingResult result) {

		if (result.hasErrors()) {
			return "createBook";
		}

		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(bookDto.getCategory());
		book.setPrice(bookDto.getPrice());
		book.setTitle(bookDto.getTitle());
		
		
		bookRepository.save(book);

		return "redirect:/createBook?success";

	}
	

	@GetMapping("/book/{id}")
	public String viewBook(@PathVariable(value = "id") Long bookId, ModelMap map, Principal principal) {
		Book book = bookRepository.findOne(bookId);
		
		String role = "Role_User";
		User user = userRepository.findByEmail(principal.getName());
		for (Role r : user.getRoles()) {
			if (r.getName().equalsIgnoreCase("Role_Admin")) {
				role = "Role_Admin";
			}
		}

		map.addAttribute("book", book);

		map.addAttribute("user", user);
		map.addAttribute("role", role);
		return "viewBook";
	}
	
	
	@GetMapping("/book/search")
	public String bookSearch(ModelMap map) {
	//	List<Book> books = bookRepository.findAll();
		
		
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> links = new ArrayList<String>();

		categories.add("Science Fiction");
		categories.add("Romance");
		categories.add("Travel");
		categories.add("Science");
		categories.add("History");
		categories.add("Other");

		map.addAttribute("categories", categories);
		map.addAttribute("links", links);
		return "bookSearch";

	}

	@PostMapping("/book/search")
	public String bookSearchPost(ModelMap map, @ModelAttribute Category category) {
		List<Book> books = bookRepository.findAll();
		System.out.println("=================================");
		System.out.println(category.getCategory());
		System.out.println("=================================");
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> links = new ArrayList<String>();
		
		categories.add("Science Fiction");
		categories.add("Romance");
		categories.add("Travel");
		categories.add("Science");
		categories.add("History");
		categories.add("Other");

		for (int i = 0; i < books.size(); i++) {
		
				if(books.get(i).getCategory().equals(category.getCategory()))
				{
					String link = "";

					System.out.println("=================================");
					System.out.println("TEST");
					
					link = "<a href=\"http://localhost:8080/book/" + books.get(i).getId() + "\"> "
							+ books.get(i).getTitle() + "</a>" + " by " + books.get(i).getAuthor();
					links.add(link);
				} else {

				}
			
		}
		
		System.out.println("=================================");
		System.out.println(books.toString());

		map.addAttribute("categories", categories);
		map.addAttribute("links", links);
		return "bookSearch";

	}
	
	
	
}
