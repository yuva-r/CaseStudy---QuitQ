
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.CategoryDTO;
  
  public interface CategoryService { CategoryDTO createCategory(CategoryDTO
  categoryDTO); CategoryDTO getCategoryById(Long id); List<CategoryDTO>
  getAllCategories(); CategoryDTO updateCategory(Long id, CategoryDTO
  categoryDTO); void deleteCategory(Long id); }
 