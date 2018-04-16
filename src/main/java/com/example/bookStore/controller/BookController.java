package com.example.bookStore.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
import com.example.bookStore.model.Purchase;
import com.example.bookStore.model.Role;
import com.example.bookStore.model.User;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.PurchaseRepository;
import com.example.bookStore.repository.UserRepository;
import com.example.judoku.dto.Year;

@Controller
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PurchaseRepository purchaseRepository;

	@GetMapping("/createBook")
	public String createBook(Model model) {
		model.addAttribute("Book", new Book());
		return "createBook";
	}

	@PostMapping("/createBook")
	public String createBook(@ModelAttribute("Book") @Valid BookDTO bookDto, BindingResult result) {

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
		// List<Book> books = bookRepository.findAll();

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
		System.out.println(category.getOrder());
		System.out.println(category.getAuthor());
		System.out.println(category.getTitle());
		System.out.println("=================================");
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> links = new ArrayList<String>();

		categories.add("Science Fiction");
		categories.add("Romance");
		categories.add("Travel");
		categories.add("Science");
		categories.add("History");
		categories.add("Other");
		
		if (category.getAuthor() != "") {

			for (int i = 0; i < books.size(); i++) {

				if (books.get(i).getAuthor().toLowerCase().contains((category.getAuthor().toLowerCase()))) {
					String link = "";

					System.out.println("=================================");
					System.out.println("TEST");

					link = "<a href=\"http://localhost:8080/book/" + books.get(i).getId() + "\"> "
							+ books.get(i).getTitle() + "</a>" + " by " + books.get(i).getAuthor();
					links.add(link);
				} else {

				}

			}

			//this was my attempt to sort the results in ascending order, I did not have time to get it working. 
/*			boolean swap = true;
			while (swap == true) {
				for (int i = 0; i < links.size()-1; i++) {
	
					swap = false;
					
					if (Character.toLowerCase(links.get(i).charAt(links.get(i).indexOf('<') + 2)) > Character
							.toLowerCase(links.get(i + 1).charAt(links.get(i).indexOf('<') + 2)))
						;

					String toMove = links.get(i);
					links.set(i, links.get(i + 1));
					links.set(i + 1, toMove);
					swap = true;
					}
				}

			
*/
		}

		else if (category.getTitle() != "") {

			for (int i = 0; i < books.size(); i++) {

				if (books.get(i).getTitle().toLowerCase().contains((category.getTitle().toLowerCase()))) {
					String link = "";

					System.out.println("=================================");
					System.out.println("TEST");

					link = "<a href=\"http://localhost:8080/book/" + books.get(i).getId() + "\"> "
							+ books.get(i).getTitle() + "</a>" + " by " + books.get(i).getAuthor();
					links.add(link);
				} else {

				}

			}

		} else {
			for (int i = 0; i < books.size(); i++) {

				if (books.get(i).getCategory().equals(category.getCategory())) {
					String link = "";

					System.out.println("=================================");
					System.out.println("TEST");

					link = "<a href=\"http://localhost:8080/book/" + books.get(i).getId() + "\"> "
							+ books.get(i).getTitle() + "</a>" + " by " + books.get(i).getAuthor();
					links.add(link);
				} else {

				}

			}
		}
		
		
		
		System.out.println("=================================");
		System.out.println(books.toString());

		map.addAttribute("categories", categories);
		map.addAttribute("links", links);
		return "bookSearch";

	}
	
	@GetMapping("/add/{id}")
	public String addToCart(@PathVariable(value = "id") Long id, Principal principal) {

	

		User user = userRepository.findByEmail(principal.getName());
		
		if(user.getCart() == null)
		{
			user.setCart("");
		}
		
		
		
		user.setCart(user.getCart() + id + "x");
        userRepository.save(user);

		return "addToCart";
	}

	@GetMapping("/cart")
	public String cart(ModelMap map, Principal principal) {
		// List<Book> books = bookRepository.findAll();

		User user = userRepository.findByEmail(principal.getName());
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<Book> books = new ArrayList<Book>();
		System.out.println("=======");
		System.out.println(user.getCart());
		System.out.println("=======");
		List<String> ids = new ArrayList<String>(Arrays.asList(user.getCart().split("x")));
		System.out.println(ids.toString());

		for(String s : ids)
		{
			
			books.add(bookRepository.findOne(Long.parseLong(s)));
			System.out.println(bookRepository.findOne(Long.parseLong(s)).getTitle());

		}
		for(int i=0; i<books.size();i++) 
		{
			String link = "";


			link = "<a href=\"http://localhost:8080/book/" + books.get(i).getId() + "\"> "
					+ books.get(i).getTitle() + "</a>" + " by " + books.get(i).getAuthor();
			links.add(link);
		}
		

		map.addAttribute("ids", ids);
		map.addAttribute("links", links);
		return "viewCart";

	}
	
	@PostMapping("/cart")
	public String cartPost(ModelMap map, Principal principal) {
		ArrayList<Book> books = new ArrayList<Book>();

		User user = userRepository.findByEmail(principal.getName());
		
		List<String> ids = new ArrayList<String>(Arrays.asList(user.getCart().split("x")));
		
		for(String s : ids)
		{
			
			books.add(bookRepository.findOne(Long.parseLong(s)));
		
		}
		
		for(int i = 0; i < books.size(); i++) 
		{
			Collection<Purchase> purchases = books.get(i).getPurchases();
			Collection<Purchase> uPurchases = user.getPurchases();
			
			
			Purchase purchase = new Purchase();
			purchase.setAmountPaid(books.get(i).getPrice());
			purchase.setDate(Calendar.getInstance().getTime().toGMTString());
			
			purchases.add(purchase);
			uPurchases.add(purchase);
			
		    books.get(i).setPurchases(purchases);
		    user.setPurchases(uPurchases);
		    
		    purchaseRepository.save(purchase);
		    userRepository.save(user);
		    bookRepository.save(books.get(i));
		}
		
		
		return "viewCart";

	}

	
}
