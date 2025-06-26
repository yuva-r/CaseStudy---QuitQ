import axios from 'axios';

const API_URL = 'http://localhost:8080/api/sellers';

class SellerService {
  getAllSellers() {
    return axios.get(API_URL);
  }

  getSellerById(id) {
    return axios.get(`${API_URL}/${id}`);
  }

  // createSeller(seller) {
  //   return axios.post(API_URL, seller);
  // }
createSeller(seller, token) {
    return axios.post(API_URL, seller, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
  }

  updateSeller(id, seller) {
    return axios.put(`${API_URL}/${id}`, seller);
  }

  // deleteSeller(id) {
  //   return axios.delete(`${API_URL}/${id}`);
  // }
  deleteSeller(id, token) {
    return axios.delete(`${API_URL}/${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
  }
}

export default new SellerService();