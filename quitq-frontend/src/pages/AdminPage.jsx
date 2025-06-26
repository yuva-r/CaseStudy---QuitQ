import React, { useState, useEffect, useContext } from 'react';
import { Container, Button, Form, Table, Spinner, Alert } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SellerService from '../services/SellerService';
import ProductService from '../services/ProductService';
import AuthContext from '../context/AuthProvider';
import '../styles/SellerDashboard.css'; // Reuse SellerDashboard styles for consistency

const AdminPage = () => {
  const [sellers, setSellers] = useState([]);
  const [sellerProducts, setSellerProducts] = useState({}); 
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [newSeller, setNewSeller] = useState({
    name: '',
    storeName: '',
    gstNumber: '',
    userId: '',
  });

  const { auth } = useContext(AuthContext);
  const token = auth?.accessToken;

  useEffect(() => {
    const fetchSellers = async () => {
      setLoading(true);
      try {
        const response = await SellerService.getAllSellers(token);
        setSellers(response.data);
      } catch (err) {
        setError('Failed to fetch sellers. Please try again.');
        toast.error('Failed to fetch sellers.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchSellers();
  }, [token]);

  const fetchProductsForSeller = async (sellerId) => {
    try {
      const response = await ProductService.getProductsBySeller(sellerId, token);
      setSellerProducts((prev) => ({ ...prev, [sellerId]: response.data }));
    } catch (err) {
      toast.error(`Failed to load products for seller ${sellerId}.`);
      console.error(err);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewSeller((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddSeller = async (e) => {
    e.preventDefault();
    try {
      const response = await SellerService.createSeller(newSeller, token);
      setSellers([...sellers, response.data]);
      setNewSeller({ name: '', storeName: '', gstNumber: '', userId: '' });
      toast.success('Seller added successfully!');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to add seller.');
      console.error(err);
    }
  };

  const handleDeleteSeller = async (sellerId) => {
    if (window.confirm('Are you sure you want to delete this seller?')) {
      try {
        await SellerService.deleteSeller(sellerId, token);
        setSellers(sellers.filter((s) => s.id !== sellerId));
        setSellerProducts((prev) => {
          const updated = { ...prev };
          delete updated[sellerId];
          return updated;
        });
        toast.success('Seller deleted successfully!');
      } catch (err) {
        toast.error('Failed to delete seller.');
        console.error(err);
      }
    }
  };

  if (loading) return <Spinner animation="border" className="d-block mx-auto mt-5" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="seller-dashboard">
      <ToastContainer position="top-right" autoClose={3000} />
      <h2>Admin Page</h2>
      <h3 className="mt-3">Seller Management</h3>

      <Form onSubmit={handleAddSeller} className="product-form mb-5">
        <h4 className="mt-4">Add New Seller</h4>
        <Form.Group className="mb-3">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={newSeller.name}
            onChange={handleInputChange}
            placeholder="Enter seller name"
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Store Name</Form.Label>
          <Form.Control
            type="text"
            name="storeName"
            value={newSeller.storeName}
            onChange={handleInputChange}
            placeholder="Enter store name"
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>GST Number</Form.Label>
          <Form.Control
            type="text"
            name="gstNumber"
            value={newSeller.gstNumber}
            onChange={handleInputChange}
            placeholder="Enter GST number"
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>User ID</Form.Label>
          <Form.Control
            type="number"
            name="userId"
            value={newSeller.userId}
            onChange={handleInputChange}
            placeholder="Enter user ID"
            required
          />
        </Form.Group>
        <Button type="submit" className="add-product-btn">
          Add Seller
        </Button>
      </Form>

      <h4 className="mt-5">Sellers</h4>
      {sellers.length === 0 ? (
        <p>No sellers found.</p>
      ) : (
        <Table striped bordered hover className="product-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Store Name</th>
              <th>GST Number</th>
              <th>Products</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sellers.map((seller) => (
              <tr key={seller.id}>
                <td>{seller.id}</td>
                <td>{seller.name}</td>
                <td>{seller.storeName}</td>
                <td>{seller.gstNumber}</td>
                <td>
                  <Button
                    variant="info"
                    size="sm"
                    onClick={() => fetchProductsForSeller(seller.id)}
                  >
                    View Products
                  </Button>
                  {sellerProducts[seller.id] && sellerProducts[seller.id].length > 0 ? (
                    <ul className="list-group mt-2">
                      {sellerProducts[seller.id].map((product) => (
                        <li key={product.id} className="list-group-item">
                          {product.name} (₹{product.price})
                        </li>
                      ))}
                    </ul>
                  ) : sellerProducts[seller.id] ? (
                    <p className="mt-2">No products found.</p>
                  ) : null}
                </td>
                <td>
                  <Button
                    variant="danger"
                    size="sm"
                    className="delete-btn"
                    onClick={() => handleDeleteSeller(seller.id)}
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Container>
  );
};

export default AdminPage;