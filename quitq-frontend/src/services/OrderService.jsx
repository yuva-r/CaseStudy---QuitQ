// src/services/orderService.jsx
import axios from 'axios';

// Base URL for your backend API
const API_URL = 'http://localhost:8080/api'; // Adjust to your backend URL

const orderService = {
  // Get all orders
  getAllOrders: async () => {
    try {
      const response = await axios.get(`${API_URL}/orders`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      return response;
    } catch (error) {
      throw new Error('Failed to fetch orders: ' + error.message);
    }
  },

  // Get order by ID
  getOrderById: async (id) => {
    try {
      const response = await axios.get(`${API_URL}/orders/${id}`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      return response;
    } catch (error) {
      throw new Error('Failed to fetch order: ' + error.message);
    }
  },

  // Get orders by user ID
  getOrdersByUser: async (userId) => {
    try {
      const response = await axios.get(`${API_URL}/orders/user/${userId}`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      return response;
    } catch (error) {
      throw new Error('Failed to fetch user orders: ' + error.message);
    }
  },

  // Create a new order
  createOrder: async (orderData) => {
    try {
      const response = await axios.post(`${API_URL}/orders/place`, orderData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      return response;
    } catch (error) {
      throw new Error('Failed to create order: ' + (error.response ? error.response.data.message : error.message));
    }
  },
};

export default orderService;