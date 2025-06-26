import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container, Spinner } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import WishlistService from '../services/WishlistService';
import mobile from '../assets/images/mobile.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';
import pen from '../assets/images/pen.jpg';
import download from '../assets/images/download.jpg';
import dine from '../assets/images/dine.jpg';
import glasspot from '../assets/images/glasspot.jpg';
import stainlesssteelpan from '../assets/images/stainless steel pan.jpg';
import '../styles/WishlistPage.css'; // Create this CSS file
import printer from '../assets/images/printer.jpg';
import book from '../assets/images/book.jpg';

const WishlistPage = () => {
  const [wishlist, setWishlist] = useState([]);
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
     dinnerware:dine,
      printer: printer,
         book:book,
     glasspot:glasspot,
  };

  useEffect(() => {
    const fetchWishlist = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await WishlistService.getWishlistByUserId(userId);
        const wishlistItems = response.data.map((item) => ({
          id: item.id,
          name: item.name,
          image: productImageMap[item.name?.toLowerCase()] || cup,
          path: `/details/${item.id}`,
        }));
        setWishlist(wishlistItems);
      } catch (err) {
        setError('Failed to fetch wishlist. Please try again.');
        toast.error('Failed to fetch wishlist.');
        console.error('Error fetching wishlist:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchWishlist();
  }, []);

  const handleProfileDropdownToggle = () => setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const handleLogout = () => {
    navigate('/login');
  };

  const handleRemoveFromWishlist = async (productId) => {
    try {
      const response = await WishlistService.removeFromWishlist({
        userId,
        productId,
      });
      toast.success(response.data);
      setWishlist(wishlist.filter((item) => item.id !== productId));
    } catch (err) {
      toast.error('Failed to remove from wishlist.');
      console.error('Error removing from wishlist:', err);
    }
  };

  if (loading) {
    return (
      <div className="loading">
        <Spinner animation="border" variant="primary" />
        <p>Loading wishlist...</p>
      </div>
    );
  }

  if (error) {
    return <p className="error">{error}</p>;
  }

  return (
    <div className="wishlist-page">
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

      <section className="wishlist">
        <h2>Your Wishlist</h2>
        {wishlist.length ? (
          <div className="wishlist-grid">
            {wishlist.map((item) => (
              <div className="wishlist-card" key={item.id}>
                <img src={item.image} alt={item.name} />
                <h3>{item.name}</h3>
                <div className="wishlist-actions">
                  <button onClick={() => navigate(item.path)}>View Details</button>
                  <button
                    className="remove-from-wishlist"
                    onClick={() => handleRemoveFromWishlist(item.id)}
                  >
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <p className="no-wishlist">Your wishlist is empty.</p>
        )}
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

export default WishlistPage;