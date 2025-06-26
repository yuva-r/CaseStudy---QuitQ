import React, { useState, useEffect, useContext } from 'react';
import { Container, Button, Form, Table, Spinner, Alert, FormSelect } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProductService from '../services/ProductService';
import SellerService from '../services/SellerService';
import CategoryService from '../services/CategoryService';
import '../styles/SellerDashboard.css';
import AuthContext from '../context/AuthProvider';

const SellerDashboard = () => {
  const [sellers, setSellers] = useState([]);
  const [selectedSellerId, setSelectedSellerId] = useState('');
  const [seller, setSeller] = useState(null);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [newProduct, setNewProduct] = useState({
    name: '',
    description: '',
    price: '',
    stock: '',
    imageUrl: '',
    categoryId: '',
    sellerId: '',
  });

  const { auth } = useContext(AuthContext);
  const token = auth?.accessToken;

  // Fetch all sellers and categories on mount
  useEffect(() => {
    const fetchInitialData = async () => {
      setLoading(true);
      try {
        const sellerResponse = await SellerService.getAllSellers();
        setSellers(sellerResponse.data);
        const categoryResponse = await CategoryService.getAllCategories();
        setCategories(categoryResponse.data);
      } catch (err) {
        setError('Failed to fetch sellers or categories. Please try again.');
        toast.error('Failed to fetch data.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchInitialData();
  }, []);

  // Fetch seller details and products when a seller is selected
  useEffect(() => {
    if (selectedSellerId) {
      const fetchSellerData = async () => {
        setLoading(true);
        try {
          const sellerResponse = await SellerService.getSellerById(selectedSellerId);
          setSeller(sellerResponse.data);
          const productResponse = await ProductService.getProductsBySeller(selectedSellerId);
          setProducts(productResponse.data);
          setNewProduct((prev) => ({ ...prev, sellerId: selectedSellerId }));
        } catch (err) {
          setError('Failed to fetch seller details or products. Please try again.');
          toast.error('Failed to fetch seller data.');
          console.error(err);
        } finally {
          setLoading(false);
        }
      };
      fetchSellerData();
    }
  }, [selectedSellerId]);

  const handleSellerChange = (e) => {
    setSelectedSellerId(e.target.value);
    setSeller(null);
    setProducts([]);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct({ ...newProduct, [name]: value });
  };

  const handleAddProduct = async (e) => {
    e.preventDefault();
    if (!selectedSellerId) {
      toast.error('Please select a seller first.');
      return;
    }
    try {
      const response = await ProductService.createProduct(newProduct, token);
      setProducts([...products, response.data]);
      setNewProduct({
        name: '',
        description: '',
        price: '',
        stock: '',
        imageUrl: '',
        categoryId: '',
        sellerId: selectedSellerId,
      });
      toast.success('Product added successfully!');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to add product.');
      console.error(err);
    }
  };

  const handleDeleteProduct = async (productId) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await ProductService.deleteProduct(productId, token);
        setProducts(products.filter((p) => p.id !== productId));
        toast.success('Product deleted successfully!');
      } catch (err) {
        toast.error('Failed to delete product.');
        console.error(err);
      }
    }
  };

  if (loading) return <Spinner animation="border" className="d-block mx-auto mt-5" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="seller-dashboard">
      <ToastContainer position="top-right" autoClose={3000} />
      <h2>Seller Dashboard</h2>

      <Form.Group className="mb-3">
        <Form.Label>Select Seller Store</Form.Label>
        <FormSelect value={selectedSellerId} onChange={handleSellerChange}>
          <option value="">Choose a store</option>
          {sellers.map((s) => (
            <option key={s.id} value={s.id}>
              {s.storeName} (ID: {s.id})
            </option>
          ))}
        </FormSelect>
      </Form.Group>

      {seller && (
        <div className="seller-details">
          <h4>Store: {seller.storeName}</h4>
          <p>GST Number: {seller.gstNumber}</p>

          <h3 className="mt-5">Your Products</h3>
          {products.length === 0 ? (
            <p>No products found.</p>
          ) : (
            <Table striped bordered hover className="product-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Price (₹)</th>
                  <th>Stock</th>
                  <th>Category</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr key={product.id}>
                    <td>{product.id}</td>
                    <td>{product.name}</td>
                    <td>{product.price}</td>
                    <td>{product.stock}</td>
                    <td>{product.categoryName || 'No Category'}</td>
                    <td>
                      <Button
                        variant="danger"
                        size="sm"
                        className="delete-btn"
                        onClick={() => handleDeleteProduct(product.id)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}

          <h3 className="mt-5">Add New Product</h3>
          <Form onSubmit={handleAddProduct} className="product-form">
            <Form.Group className="mb-3">
              <Form.Label>Product Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={newProduct.name}
                onChange={handleInputChange}
                placeholder="Enter product name"
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                name="description"
                value={newProduct.description}
                onChange={handleInputChange}
                placeholder="Enter product description"
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Price (₹)</Form.Label>
              <Form.Control
                type="number"
                name="price"
                value={newProduct.price}
                onChange={handleInputChange}
                placeholder="Enter price"
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Stock</Form.Label>
              <Form.Control
                type="number"
                name="stock"
                value={newProduct.stock}
                onChange={handleInputChange}
                placeholder="Enter stock quantity"
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Image URL</Form.Label>
              <Form.Control
                type="text"
                name="imageUrl"
                value={newProduct.imageUrl}
                onChange={handleInputChange}
                placeholder="Enter image URL (optional)"
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Category</Form.Label>
              <Form.Select
                name="categoryId"
                value={newProduct.categoryId}
                onChange={handleInputChange}
                required
              >
                <option value="">Select Category</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Button type="submit" className="add-product-btn">
              Add Product
            </Button>
          </Form>
        </div>
      )}
    </Container>
  );
};

export default SellerDashboard;