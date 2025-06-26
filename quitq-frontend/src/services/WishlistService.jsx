import axios from 'axios';

const API_URL = 'http://localhost:8080/api/wishlist'; // Adjust to your backend URL

const WishlistService = {
  addToWishlist: async (wishlistData) => {
    return axios.post(`${API_URL}/add`, wishlistData);
  },

  getWishlistByUserId: async (userId) => {
    return axios.get(`${API_URL}/user/${userId}`);
  },

  removeFromWishlist: async (wishlistData) => {
    return axios.delete(`${API_URL}/remove`, { data: wishlistData });
  },
};

export default WishlistService;