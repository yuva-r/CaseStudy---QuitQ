import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart'; // Adjust to your backend URL

const CartService = {
  addToCart: async (cartData) => {
    try {
      // Log the cartData to debug null values
      console.log('Sending cartData to addToCart:', cartData);
      if (!cartData.userId || !cartData.productId) {
        throw new Error('userId or productId is missing in cartData');
      }
      const response = await axios.post(API_URL, cartData);
      console.log('Add to cart response:', response.data);
      return response;
    } catch (error) {
      console.error('Add to cart error:', error.response?.data || error.message);
      throw error.response?.data || 'Failed to add to cart';
    }
  },
  getCartByUserId: async (userId) => {
    try {
      console.log('Fetching cart for userId:', userId);
      const response = await axios.get(`${API_URL}/user/${userId}`);
      console.log('Get cart response:', response.data);
      return response;
    } catch (error) {
      console.error('Get cart error:', error.response?.data || error.message);
      throw error.response?.data || 'Failed to fetch cart';
    }
  },
  updateCart: async (id, cartData) => {
    try {
      console.log('Updating cart item id:', id, 'with data:', cartData);
      const response = await axios.put(`${API_URL}/${id}`, cartData);
      console.log('Update cart response:', response.data);
      return response;
    } catch (error) {
      console.error('Update cart error:', error.response?.data || error.message);
      throw error.response?.data || 'Failed to update cart';
    }
  },
  deleteCartItem: async (id) => {
    try {
      console.log('Deleting cart item id:', id);
      const response = await axios.delete(`${API_URL}/${id}`);
      console.log('Delete cart item response:', response.data);
      return response;
    } catch (error) {
      console.error('Delete cart item error:', error.response?.data || error.message);
      throw error.response?.data || 'Failed to delete cart item';
    }
  },
  clearCart: async (userId) => {
    try {
      console.log('Clearing cart for userId:', userId);
      const response = await axios.delete(`${API_URL}/clear/${userId}`);
      console.log('Clear cart response:', response.data);
      return response;
    } catch (error) {
      console.error('Clear cart error:', error.response?.data || error.message);
      throw error.response?.data || 'Failed to clear cart';
    }
  },
};

export default CartService;