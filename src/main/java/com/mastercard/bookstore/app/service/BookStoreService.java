package com.mastercard.bookstore.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mastercard.bookstore.app.model.Book;
import com.mastercard.bookstore.app.repository.BookRepository;

@Service
public class BookStoreService implements IBookStoreService {

	//Autowiring the Book repository to interact with the Databse
	@Autowired
	BookRepository repository;

	// Method to fetch list of books,construct the paging and sorting parameters
	// here and make a final call to the repository
	public Map<String, Object> getAllBooks(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {

		List<Book> books = new ArrayList<Book>();

		// create Object of Sort in order to pass to PageRequest
		Sort sorting = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable paging = PageRequest.of(pageNo, pageSize, sorting);

		Page<Book> pagedResult = repository.findAll(paging);

		books = pagedResult.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("books", books);
		response.put("currentPage", pagedResult.getNumber());
		response.put("totalItems", pagedResult.getTotalElements());
		response.put("totalPages", pagedResult.getTotalPages());

		return response;

	}

	// Method to delete a book identified by its Id
	public void deleteBookById(int id) {

		repository.deleteById(id);

	}

	// Method to fetch a book identified by its Id
	public Optional<Book> getBookById(int id) {

		return repository.findById(id);

	}

}
