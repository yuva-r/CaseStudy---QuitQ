import axios from 'axios';

const API_URL = 'http://localhost:8080/api/categories';

class CategoryService {
  /**
   * Fetch all categories from the backend.
   * @returns {Promise} Resolves to an array of categories or rejects with an error.
   */
  async getAllCategories() {
    try {
      const response = await axios.get(API_URL);
      return response;
    } catch (error) {
      console.error('Error fetching categories:', error.response?.data || error.message);
      throw new Error('Failed to fetch categories. Please try again.');
    }
  }

  /**
   * Fetch a category by its ID.
   * @param {number} id - The category ID.
   * @returns {Promise} Resolves to the category object or rejects with an error.
   */
  async getCategoryById(id) {
    try {
      const response = await axios.get(`${API_URL}/${id}`);
      if (!response.data) {
        throw new Error(`Category with ID ${id} not found`);
      }
      return response;
    } catch (error) {
      console.error(`Error fetching category with ID ${id}:`, error.response?.data || error.message);
      throw new Error(`Failed to fetch category with ID ${id}. Please try again.`);
    }
  }

  /**
   * Create a new category.
   * @param {Object} category - The category object (e.g., { name: "New Category" }).
   * @returns {Promise} Resolves to the created category or rejects with an error.
   */
  async createCategory(category) {
    try {
      const response = await axios.post(API_URL, category);
      return response;
    } catch (error) {
      console.error('Error creating category:', error.response?.data || error.message);
      throw new Error('Failed to create category. Please try again.');
    }
  }

  /**
   * Update an existing category.
   * @param {number} id - The category ID.
   * @param {Object} category - The updated category object (e.g., { name: "Updated Category" }).
   * @returns {Promise} Resolves to the updated category or rejects with an error.
   */
  async updateCategory(id, category) {
    try {
      const response = await axios.put(`${API_URL}/${id}`, category);
      if (!response.data) {
        throw new Error(`Category with ID ${id} not found`);
      }
      return response;
    } catch (error) {
      console.error(`Error updating category with ID ${id}:`, error.response?.data || error.message);
      throw new Error(`Failed to update category with ID ${id}. Please try again.`);
    }
  }

  /**
   * Delete a category by its ID.
   * @param {number} id - The category ID.
   * @returns {Promise} Resolves when deleted or rejects with an error.
   */
  async deleteCategory(id) {
    try {
      const response = await axios.delete(`${API_URL}/${id}`);
      return response;
    } catch (error) {
      console.error(`Error deleting category with ID ${id}:`, error.response?.data || error.message);
      throw new Error(`Failed to delete category with ID ${id}. Please try again.`);
    }
  }
}

export default new CategoryService();