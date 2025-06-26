
package com.hexaware.QuitQ.serviceimpl;

import com.hexaware.QuitQ.dto.CategoryDTO;
import com.hexaware.QuitQ.entities.Category;
import com.hexaware.QuitQ.repository.CategoryRepository;
import com.hexaware.QuitQ.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDTO createCategory(CategoryDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());
		return mapToDTO(categoryRepository.save(category));
	}

	@Override
	public CategoryDTO getCategoryById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.map(this::mapToDTO).orElse(null);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		Optional<Category> optional = categoryRepository.findById(id);
		if (optional.isPresent()) {
			Category category = optional.get();
			category.setName(dto.getName());
			return mapToDTO(categoryRepository.save(category));
		}
		return null;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	private CategoryDTO mapToDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		return dto;
	}
}
