import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container, Button, Spinner } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../styles/OrderConfirmed.css';
import mobile from '../assets/images/mobile.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';
import pen from '../assets/images/pen.jpg';
import download from '../assets/images/download.jpg';
import printer from '../assets/images/printer.jpg';
import stainlesssteelpan from '../assets/images/stainless steel pan.jpg';
import book from '../assets/images/book.jpg';
import dine from '../assets/images/dine.jpg';
import glasspot from '../assets/images/glasspot.jpg';

const OrderConfirmed = () => {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const username = 'User'; 
  const userId = localStorage.getItem('userId') || 1;

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
    book: book,
     dinnerware:dine,
      glasspot:glasspot,
  };

  // Use cart items passed from Checkout.jsx via location.state with debugging
  useEffect(() => {
    console.log('Location state:', location.state);
    const items = location.state?.cartItems || [];
    if (Array.isArray(items) && items.length > 0) {
      const mappedItems = items.map(item => ({
        ...item,
        productImage: productImageMap[item.productName?.toLowerCase()] || cup,
      }));
      console.log('Mapped cart items:', mappedItems);
      setCartItems(mappedItems);
      setLoading(false);
    } else {
      setError('No cart items available or invalid data passed.');
      setLoading(false);
      toast.error('No cart items to display or invalid data.');
    }
  }, [location.state]);

  const handleProfileDropdownToggle = () => {
    // Placeholder for dropdown toggle logic
  };

  const handleLogout = () => {
    navigate('/login');
    toast.success('Logged out successfully.');
  };

  if (loading) {
    return (
      <div className="loading">
        <Spinner animation="border" variant="primary" />
        <p>Loading order details...</p>
      </div>
    );
  }

  if (error) {
    return <p className="error">{error}</p>;
  }

  return (
    <div className="order-confirmed-page">
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
                show={false}
                onToggle={handleProfileDropdownToggle}
                align="end"
              >
                <NavDropdown.Item as={Link} to="/cart" onClick={() => {}}>
                  <FaCartPlus className="me-2" /> Cart
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/orders" onClick={() => {}}>
                  <FaListAlt className="me-2" /> Orders
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/wishlist" onClick={() => {}}>
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

      <Container className="order-confirmed-section">
        <div className="confirmation-content">
          <h1 className="thank-you">Thank You</h1>
          <div className="tick-mark">✓</div>
          <h2 className="order-status">Order Successfully Placed!</h2>

          <div className="order-items">
            <h3>Ordered Items</h3>
            {cartItems.length > 0 ? (
              <div className="items-list">
                {cartItems.map((item) => (
                  <div key={item.id} className="order-item">
                    <img
                      src={item.productImage}
                      alt={item.productName || 'Product'}
                      className="order-item-image"
                    />
                    <div className="order-item-details">
                      <h4>{item.productName || 'Unknown Product'}</h4>
                      <p>Price: ₹{Math.floor(item.productPrice || 0).toLocaleString('en-IN')}</p>
                      <p>Quantity: {item.quantity || 1}</p>
                      <p>Subtotal: ₹{Math.floor(item.totalCost || 0).toLocaleString('en-IN')}</p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p>No items to display.</p>
            )}
          </div>

          <Button variant="success" className="view-order-btn" onClick={() => navigate('/orders')}>
            View Order
          </Button>
        </div>
      </Container>

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

export default OrderConfirmed;