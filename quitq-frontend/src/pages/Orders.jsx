// src/components/Orders.jsx
import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Container, Card, Button, Spinner, Row, Col, ListGroup, Navbar, Nav, NavDropdown, FormControl } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt, FaSearch } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import orderService from '../services/orderService';
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
import '../styles/Orders.css';
import dine from '../assets/images/dine.jpg';
import glasspot from '../assets/images/glasspot.jpg';

const Orders = () => {
  const { id } = useParams(); // Get order ID from URL (e.g., /orders/123)
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [singleOrder, setSingleOrder] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const userId = localStorage.getItem('userId') || '1'; // Match OrderConfirmed.jsx
  const username = 'User'; 

  // Product image mapping 
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

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        setLoading(true);
        if (id) {
          // Fetch single order if ID is provided
          const response = await orderService.getOrderById(id);
          //updating the state with the new obj
          setSingleOrder({
            ...response.data,
            orderItems: (response.data.orderItems || []).map(item => ({
              ...item,
              productImage: productImageMap[item.productName?.toLowerCase()] || cup,
            })),
          });
          setOrders([]); // Clear all orders when viewing single order
        } else {
          // Fetch all orders for the user
          setSingleOrder(null); // Clear single order when viewing all orders
          const response = await orderService.getOrdersByUser(userId);
          const mappedOrders = response.data.map(order => ({
            ...order,
            orderItems: (order.orderItems || []).map(item => ({
              ...item,
              productImage: productImageMap[item.productName?.toLowerCase()] || cup,
            })),
          }));
          setOrders(mappedOrders);

          // Clear the newOrder flag once used
          if (localStorage.getItem('newOrder') === 'true') {
            localStorage.removeItem('newOrder');
          }
        }
      } catch (err) {
        setError('Failed to fetch orders. Please try again.');
        toast.error('Failed to fetch orders.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [id, userId]); // Dependency array unchanged, flag handled in logic

  const handleProfileDropdownToggle = () => setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const handleLogout = () => {
    navigate('/login');
  };

  // Format date for display
  const formatDate = (date) => {
    return date ? new Date(date).toLocaleDateString('en-IN', {
      month: 'short',
      day: '2-digit',
      year: 'numeric',
    }) : 'N/A';
  };

  // Format price in INR
  const formatPrice = (price) => {
    return price ? `₹${Math.floor(price).toLocaleString('en-IN')}` : '₹0';
  };

  // Render single order details
  const renderOrderDetails = (order) => (
    <Card className="order-card mb-4">
      <Card.Header>
        <h3>Order #{order.id}</h3>
      </Card.Header>
      <Card.Body>
        <Row>
          <Col md={6}>
            <p><strong>Date:</strong> {formatDate(order.orderDate)}</p>
            <p><strong>Status:</strong> {order.status || 'N/A'}</p>
            <p><strong>Total:</strong> {formatPrice(order.totalAmount)}</p>
          </Col>
          <Col md={6}>
            <p><strong>Customer:</strong> {order.userName || 'N/A'}</p>
            <p><strong>Email:</strong> {order.userEmail || 'N/A'}</p>
          </Col>
        </Row>

        {/* Shipping Info */}
        <h4 className="mt-4">Shipping Address</h4>
        {order.shippingAddress ? (
          <div>
            <p>{order.shippingAddress.addressLine1}</p>
            {order.shippingAddress.addressLine2 && <p>{order.shippingAddress.addressLine2}</p>}
            <p>
              {order.shippingAddress.city}, {order.shippingAddress.state} {order.shippingAddress.zipCode}
            </p>
            <p>{order.shippingAddress.country}</p>
          </div>
        ) : (
          <p>No shipping address provided.</p>
        )}

        {/* Payment Info */}
        <h4 className="mt-4">Payment Details</h4>
        {order.payment ? (
          <div>
            <p><strong>Method:</strong> {order.payment.paymentMethod || 'N/A'}</p>
            <p><strong>Status:</strong> {order.payment.paymentStatus || 'N/A'}</p>
            <p><strong>Amount:</strong> {formatPrice(order.payment.amount)}</p>
            <p><strong>Date:</strong> {formatDate(order.payment.paymentDate)}</p>
          </div>
        ) : (
          <p>No payment details available.</p>
        )}

        {/* Order Items */}
        <h4 className="mt-4">Ordered Products</h4>
        <ListGroup variant="flush">
          {order.orderItems && order.orderItems.length > 0 ? (
            order.orderItems.map((item) => (
              <ListGroup.Item key={item.id} className="d-flex align-items-center">
                <img
                  src={item.productImage || item.productImageUrl} // Use productImage (local) or productImageUrl (backend)
                  alt={item.productName || 'Product'}
                  className="order-item-image me-3"
                  style={{ width: '80px', height: '80px', objectFit: 'cover' }}
                />
                <div>
                  <h5>{item.productName || 'Unknown Product'}</h5>
                  <p>Quantity: {item.quantity || 1}</p>
                  <p>Price: {formatPrice(item.price)}</p>
                </div>
              </ListGroup.Item>
            ))
          ) : (
            <p>No items in this order.</p>
          )}
        </ListGroup>

        <Button
          variant="primary"
          className="mt-3"
          onClick={() => navigate('/orders')}
        >
          Back to All Orders
        </Button>
      </Card.Body>
    </Card>
  );

  // Render list of orders
  const renderOrderList = () => (
    <div>
      <h2 className="mb-4">Your Orders</h2>
      {orders.length > 0 ? (
        <Row>
          {orders.map((order) => (
            <Col md={6} key={order.id} className="mb-4">
              <Card className="order-card">
                <Card.Body>
                  <Card.Title>Order #{order.id}</Card.Title>
                  <p><strong>Date:</strong> {formatDate(order.orderDate)}</p>
                  <p><strong>Status:</strong> {order.status}</p>
                  <p><strong>Total:</strong> {formatPrice(order.totalAmount)}</p>
                  <Button
                    variant="outline-primary"
                    as={Link}
                    to={`/orders/${order.id}`}
                  >
                    View Details
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      ) : (
        <p>No orders found.</p>
      )}
    </div>
  );

  if (loading) {
    return (
      <div className="orders-page">
        <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} />
        <Navbar expand="lg" className="navbar">
          <Container fluid>
            <Navbar.Brand as={Link} to="/" className="logo">
              QUITQ
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="navbar-nav" />
            <Navbar.Collapse id="navbar-nav">
              <div className="search-container">
                <FaSearch className="search-icon" />
                <FormControl
                  type="text"
                  placeholder="Search for Products"
                  className="search-input"
                />
              </div>
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
        <div className="loading text-center mt-5">
          <Spinner animation="border" variant="primary" />
          <p>Loading orders...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="orders-page">
        <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} />
        <Navbar expand="lg" className="navbar">
          <Container fluid>
            <Navbar.Brand as={Link} to="/" className="logo">
              QUITQ
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="navbar-nav" />
            <Navbar.Collapse id="navbar-nav">
              <div className="search-container">
                <FaSearch className="search-icon" />
                <FormControl
                  type="text"
                  placeholder="Search for Products"
                  className="search-input"
                />
              </div>
              <Nav className="ms-auto nav-links">
                <Nav.Link as={Link} to="/" className="nav-link">
                  <FaHome className="me-1" /> Home
                </Nav.Link>
                <Nav.Link as={Link} to="/seller" className="nav-link">
                  Become a Seller
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
        <p className="error text-danger text-center mt-5">{error}</p>
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
  }

  return (
    <div className="orders-page">
      <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} />
      <Navbar expand="lg" className="navbar">
        <Container fluid>
          <Navbar.Brand as={Link} to="/" className="logo">
            QUITQ
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="navbar-nav" />
          <Navbar.Collapse id="navbar-nav">
            <div className="search-container">
              <FaSearch className="search-icon" />
              <FormControl
                type="text"
                placeholder="Search for Products"
                className="search-input"
              />
            </div>
            <Nav className="ms-auto nav-links">
              <Nav.Link as={Link} to="/" className="nav-link">
                <FaHome className="me-1" /> Home
              </Nav.Link>
              <Nav.Link as={Link} to="/seller" className="nav-link">
                Become a Seller
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
      <Container className="py-5">
        {singleOrder ? renderOrderDetails(singleOrder) : renderOrderList()}
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

export default Orders;