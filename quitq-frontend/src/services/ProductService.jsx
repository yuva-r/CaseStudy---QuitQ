import axios from 'axios';

const API_URL = 'http://localhost:8080/api/products';

class ProductService {
  getAllProducts() {
    return axios.get(API_URL);
  }

  getProductById(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  // createProduct(product) {
  //   return axios.post(API_URL, product);
  // }
   createProduct(product, token) {
    return axios.post(API_URL, product, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }



  updateProduct(id, product) {
    return axios.put(`${API_URL}/${id}`, product);
  }

  // deleteProduct(id) {
  //   return axios.delete(`${API_URL}/${id}`);
  // }
  deleteProduct(id, token) {
    return axios.delete(`${API_URL}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  getProductsBySeller(sellerId) {
    return axios.get(`${API_URL}/seller/${sellerId}`);
  }
}

export default new ProductService();