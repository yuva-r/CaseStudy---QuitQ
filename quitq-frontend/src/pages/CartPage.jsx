import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container, Spinner, Button } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt, FaTrash } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CartService from '../services/CartService';
import '../styles/CartPage.css';
import mobile from '../assets/images/mobile.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';
import pen from '../assets/images/pen.jpg';
import download from '../assets/images/download.jpg';
import printer from '../assets/images/printer.jpg';
import dine from '../assets/images/dine.jpg';
import stainlesssteelpan from '../assets/images/stainless steel pan.jpg';
import book from '../assets/images/book.jpg';
import glasspot from '../assets/images/glasspot.jpg';

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation(); // Use location to trigger refetch
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
     glasspot:glasspot,
    pan: stainlesssteelpan,
    'stainless steel pan': stainlesssteelpan,
    printer: printer,
    book: book,
     dinnerware:dine,
  };

  const fetchCartItems = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await CartService.getCartByUserId(userId);
      const items = response.data || [];
      console.log('Normalized cart items:', items);
      setCartItems(items);
    } catch (err) {
      setError('Failed to load cart items.');
      toast.error('Failed to load cart items.');
      console.error('Error fetching cart items:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCartItems();
  }, [userId, location.key]); // Refetch when location changes

  const handleProfileDropdownToggle = () => {
    setIsProfileDropdownOpen(!isProfileDropdownOpen);
  };

  const handleLogout = () => {
    navigate('/login');
    toast.success('Logged out successfully.');
  };

  const handleQuantityChange = async (cartItemId, newQuantity) => {
    if (newQuantity < 1 || isNaN(newQuantity)) {
      handleRemoveItem(cartItemId);
      return;
    }
    try {
      const updatedCartItem = cartItems.find((item) => item.id === cartItemId);
      const cartData = {
        quantity: newQuantity,
        userId: userId,
        productId: updatedCartItem.productId,
      };
      await CartService.updateCart(cartItemId, cartData);
      setCartItems(cartItems.map((item) =>
        item.id === cartItemId
          ? { ...item, quantity: newQuantity, totalCost: newQuantity * item.productPrice }
          : item
      ));
      toast.success('Cart updated successfully!');
    } catch (err) {
      toast.error('Failed to update quantity.');
      console.error('Error updating cart item:', err);
    }
  };

  const handleRemoveItem = async (cartItemId) => {
    try {
      await CartService.deleteCartItem(cartItemId);
      setCartItems(cartItems.filter((item) => item.id !== cartItemId));
      toast.success('Item removed from cart.');
    } catch (err) {
      toast.error('Failed to remove item.');
      console.error('Error removing cart item:', err);
    }
  };

  const handleClearCart = async () => {
    try {
      await CartService.clearCart(userId);
      setCartItems([]);
      toast.success('Cart cleared successfully!');
    } catch (err) {
      toast.error('Failed to clear cart.');
      console.error('Error clearing cart:', err);
    }
  };

  const handleCheckout = () => {
    navigate('/checkout');
    toast.success('Proceeding to checkout...');
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + (item.totalCost || 0), 0);
  };

  if (loading) {
    return (
      <div className="loading">
        <Spinner animation="border" variant="primary" />
        <p>Loading cart items...</p>
      </div>
    );
  }

  if (error) {
    return <p className="error">{error}</p>;
  }

  return (
    <div className="cart-page">
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

      <Container className="cart-section">
        <h2>Cart</h2>
        <h3>Your Cart</h3>
        {cartItems.length === 0 ? (
          <p className="empty-cart">
            Your cart is empty. <Link to="/products">Shop now!</Link>
          </p>
        ) : (
          <div className="cart-items">
            {cartItems.map((item) => {
              const normalizedProductName = item.productName?.trim().toLowerCase() || '';
              console.log('Product name for image mapping:', normalizedProductName); // Debug log
              const productImage = productImageMap[normalizedProductName] || cup;
              return (
                <div key={item.id} className="cart-item">
                  <img
                    src={productImage}
                    alt={item.productName || 'Product'}
                    className="cart-item-image"
                  />
                  <div className="cart-item-details">
                    <h4>{item.productName || 'Unknown Product'}</h4>
                    <p>Price: ₹{Math.floor(item.productPrice || 0).toLocaleString('en-IN')}</p>
                    <div className="quantity-control">
                      <label>Quantity: </label>
                      <input
                        type="number"
                        min="1"
                        value={item.quantity || 1}
                        onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                      />
                    </div>
                    <p>Subtotal: ₹{Math.floor(item.totalCost || 0).toLocaleString('en-IN')}</p>
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={() => handleRemoveItem(item.id)}
                      className="remove-button"
                    >
                      <FaTrash /> Remove
                    </Button>
                  </div>
                </div>
              );
            })}
            <div className="cart-summary">
              <h4>Total Amount: ₹{Math.floor(calculateTotal()).toLocaleString('en-IN')}</h4>
              <div className="button-group">
                <Button variant="success" onClick={handleCheckout}>
                  Proceed to Checkout
                </Button>
                <Button variant="outline-danger" onClick={handleClearCart}>
                  Clear Cart
                </Button>
              </div>
            </div>
          </div>
        )}
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

export default CartPage;