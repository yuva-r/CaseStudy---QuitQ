package com.hexaware.QuitQ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.QuitQ.entities.Category;
import com.hexaware.QuitQ.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("http://localhost:5173")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping
	public Category create(@RequestBody Category category) {
		return categoryRepository.save(category);
	}

	@GetMapping
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@GetMapping("/{id}")
	public Category get(@PathVariable Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@PutMapping("/{id}")
	public Category update(@PathVariable Long id, @RequestBody Category cat) {
		Category c = categoryRepository.findById(id).orElse(null);
		if (c != null) {
			c.setName(cat.getName());
			return categoryRepository.save(c);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		categoryRepository.deleteById(id);
	}
}