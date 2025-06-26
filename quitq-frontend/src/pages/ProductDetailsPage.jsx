import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container, Spinner } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProductService from '../services/ProductService';
import WishlistService from '../services/WishlistService';
import CartService from '../services/CartService'; 
import mobile from '../assets/images/mobile.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';
import pen from '../assets/images/pen.jpg';
import download from '../assets/images/download.jpg';
import printer from '../assets/images/printer.jpg';
import book from '../assets/images/book.jpg';
import dine from '../assets/images/dine.jpg';
import stainlesssteelpan from '../assets/images/stainless steel pan.jpg';
import '../styles/ProjectDetailsPage.css';
import glasspot from '../assets/images/glasspot.jpg';

const ProductDetailsPage = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const navigate = useNavigate();
  const username = 'User';
  const userId = 1;

  const productImageMap = {
    mobile: mobile,
    'air conditioner': ac,
    shirt: shirt,
    'smart led television': download,
    cycle: cyc,
    cup: cup,
    toy: toy,
    pen: pen,
    pan: stainlesssteelpan,
    'stainless steel pan': stainlesssteelpan,
      printer: printer,
        book:book,
         dinnerware:dine,
          glasspot:glasspot,
  };

  useEffect(() => {
    const fetchProduct = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await ProductService.getProductById(id);
        setProduct(response.data);
      } catch (err) {
        setError('Failed to fetch product details. Please try again.');
        toast.error('Failed to fetch product details.');
        console.error('Error fetching product:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  const handleProfileDropdownToggle = () => setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const handleLogout = () => {
    navigate('/login');
  };

  const handleAddToWishlist = async () => {
    try {
      const response = await WishlistService.addToWishlist({
        userId,
        productId: id,
      });
      toast.success(response.data);
      navigate('/wishlist');
    } catch (err) {
      toast.error('Failed to add to wishlist.');
      console.error('Error adding to wishlist:', err);
    }
  };

  const handleAddToCart = async () => {
    try {
      const cartData = {
        userId,
        productId: id,
        quantity: 1, // Default quantity
      };
      console.log('Sending cartData:',cartData);
      const response = await CartService.addToCart(cartData);
      toast.success('Product added to cart!');
      navigate('/cart'); // Navigate to CartPage
    } catch (err) {
      toast.error('Failed to add to cart.');
      console.error('Error adding to cart:', err);
    }
  };

  if (loading) {
    return (
      <div className="loading">
        <Spinner animation="border" variant="primary" />
        <p>Loading product details...</p>
      </div>
    );
  }

  if (error || !product) {
    return <p className="error">{error || 'Product not found.'}</p>;
  }

  const productImage = productImageMap[product.name?.toLowerCase()] || cup;

  return (
    <div className="product-details-page">
      <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} />
      <Navbar expand="lg" className="navbar">
        <Container fluid>
          <Navbar.Brand as={Link} to="/" className="logo">
            QUITQ
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="navbar-nav" />
          <Navbar.Collapse id="navbar-nav">
            <Nav className="ms-auto nav-links">
              <Nav.Link as={Link} to="/" className="nav-link">
                <FaHome className="me-1" /> Home
              </Nav.Link>
                  <Nav.Link as={Link} to="/admin" className="nav-link">
                    Admin
                  </Nav.Link>
              <Nav.Link as={Link} to="/seller" className="nav-link">
                 Seller
              </Nav.Link>
              <NavDropdown
                title={
                  <>
                    <FaUser className="me-1" /> {username}
                  </>
                }
                id="profile-dropdown"
                show={isProfileDropdownOpen}
                onToggle={handleProfileDropdownToggle}
                align="end"
              >
                <NavDropdown.Item as={Link} to="/cart" onClick={() => setIsProfileDropdownOpen(false)}>
                  <FaCartPlus className="me-2" /> Cart
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/orders" onClick={() => setIsProfileDropdownOpen(false)}>
                  <FaListAlt className="me-2" /> Orders
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/wishlist" onClick={() => setIsProfileDropdownOpen(false)}>
                  <FaHeart className="me-2" /> Wishlist
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  <FaSignOutAlt className="me-2" /> Logout
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <section className="product-details">
        <h2>Product Details</h2>
        <div className="product-details-container">
          <div className="product-image">
            <img src={productImage} alt={product.name} />
          </div>
          <div className="product-info">
            <h3>{product.name}</h3>
            <p className="price">₹{Math.floor(product.price).toLocaleString('en-IN')}</p>
            <p className="description">{product.description}</p>
            <p className="category">Category: {product.categoryName}</p>
            <p className="stock">Stock: {product.stock} units</p>
            <p className="seller">Sold by: {product.sellerName}</p>
            <p className="store">Store: {product.storeName}</p>
            <p className="gst">GST Number: {product.gstNumber}</p>
            <div className="button-group">
              <button className="add-to-cart" onClick={handleAddToCart}>
                Add to Cart
              </button>
              <button className="add-to-wishlist" onClick={handleAddToWishlist}>
                <FaHeart className="me-1" /> Add to Wishlist
              </button>
            </div>
          </div>
        </div>
      </section>

      <footer className="footer">
        <div className="footer-content">
          <div className="footer-section">
            <h3>About QUITQ</h3>
            <p>Your one-stop shop for electronics, fashion, furniture, and more. Shop with ease and trust.</p>
          </div>
          <div className="footer-section">
            <h3>Quick Links</h3>
            <ul>
              <li><Link to="/products">Products</Link></li>
              <li><Link to="/about">About Us</Link></li>
              <li><Link to="/terms">Terms & Conditions</Link></li>
            </ul>
          </div>
          <div className="footer-section">
            <h3>Contact Us</h3>
            <p>Email: support@quitq.com</p>
            <p>Phone: +91 123 456 7890</p>
            <p>Address: 123 QUITQ Street, Bangalore, India</p>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© 2025 QUITQ. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};

export default ProductDetailsPage;