import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt } from 'react-icons/fa';
import axios from 'axios';
import upi from '../assets/images/upi.jpeg';
import mobile from '../assets/images/mobile.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';
import pen from '../assets/images/pen.jpg';
import download from '../assets/images/download.jpg';
import stainlesssteelpan from '../assets/images/stainless steel pan.jpg';
import printer from '../assets/images/printer.jpg';
import book from '../assets/images/book.jpg';
import dine from '../assets/images/dine.jpg';
import '../styles/Checkout.css';
import glasspot from '../assets/images/glasspot.jpg';

const Checkout = () => {
  const [user, setUser] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [shippingAddresses, setShippingAddresses] = useState([]);
  const [selectedShippingAddressId, setSelectedShippingAddressId] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState('');
  const [showUPI, setShowUPI] = useState(false);
  const [showCard, setShowCard] = useState(false);
  const [cardDetails, setCardDetails] = useState({
    cardNumber: '',
    expiryDate: '',
    cvv: '',
  });
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);

  const navigate = useNavigate();
  const username = 'User';
  const userId = localStorage.getItem('userId') || 1;

  const productImageMap = {
    mobile: mobile,
    'air conditioner': ac,
    shirt: shirt,
    'smart led television': download,
    cycle: cyc,
     glasspot:glasspot,
    cup: cup,
    toy: toy,
    pen: pen,
    pan: stainlesssteelpan,
    'stainless steel pan': stainlesssteelpan,
    printer: printer,
    book: book,
     dinnerware:dine,
  };

  const totalCost = cartItems.reduce((total, item) => total + (item.totalCost || 0), 0);

  // Fetch user details
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/users/${userId}`)
      .then((response) => setUser(response.data))
      .catch((error) => console.error('Error fetching user:', error));
  }, [userId]);

  // Fetch cart items
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/cart/user/${userId}`)
      .then((response) => {
        const mappedItems = response.data.map((item) => ({
          ...item,
          productImage: productImageMap[item.productName.toLowerCase()] || cup,
        }));
        setCartItems(mappedItems);
      })
      .catch((error) => console.error('Error fetching cart:', error));
  }, [userId]);

  // Fetch shipping addresses
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/shipping/user/${userId}`)
      .then((response) => {
        const addresses = response.data;
        setShippingAddresses(addresses);
        if (Array.isArray(addresses) && addresses.length > 0) {
          setSelectedShippingAddressId(addresses[0].id); // Default to first address
        } else {
          setSelectedShippingAddressId(null);
        }
      })
      .catch((error) => {
        console.error('Error fetching shipping addresses:', error);
        setShippingAddresses([]);
        setSelectedShippingAddressId(null);
      });
  }, [userId]);

  const handlePaymentMethodChange = (e) => {
    const method = e.target.value;
    setPaymentMethod(method);
    setShowUPI(method === 'UPI');
    setShowCard(method === 'Card');
  };

  const handleCardInputChange = (e) => {
    const { name, value } = e.target;
    setCardDetails((prev) => ({ ...prev, [name]: value }));
  };

  const handlePaymentSubmit = async (e) => {
  e.preventDefault();

  if (!selectedShippingAddressId) {
    alert('Please select a shipping address.');
    return;
  }

  try {
    // 1. Create order first
    const orderData = {
      totalAmount: totalCost,
      orderDate: new Date().toISOString(),
      status: 'PLACED',
      userId: userId,
      shippingAddressId: selectedShippingAddressId,
      orderItems: cartItems.map((item) => ({
        productId: item.productId || item.id,
        price: item.productPrice,
        quantity: item.quantity,
      })),
    };

    const orderResponse = await axios.post('http://localhost:8080/api/orders', orderData, {
      headers: { 'Content-Type': 'application/json' },
    });
    console.log('Order created:', orderResponse.data);
    const createdOrderId = orderResponse.data.id;

    // 2. Create payment with orderId
    const paymentData = {
      amount: totalCost,
      paymentMethod: paymentMethod,
      paymentStatus: 'SUCCESS',
      paymentDate: new Date().toISOString(),
      userId: userId,
      orderId: createdOrderId, // Important: Pass the correct orderId here
    };

    const paymentResponse = await axios.post('http://localhost:8080/api/payments', paymentData, {
      headers: { 'Content-Type': 'application/json' },
    });
    console.log('Payment success:', paymentResponse.data);

    // 3. Clear cart
    await axios.delete(`http://localhost:8080/api/cart/clear/${userId}`);

    // 4. Navigate to order confirmation
    navigate('/order-confirmed', { state: { cartItems } });
  } catch (error) {
    console.error('Error during payment or order:', error.response ? error.response.data : error.message);
    alert('Something went wrong. Please try again. Error: ' + (error.response ? error.response.data.message : error.message));
  }
};

  const handleProfileDropdownToggle = () => setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const handleLogout = () => {
    navigate('/login');
  };

  // Get selected address for display
  const selectedAddress = shippingAddresses.find((addr) => addr.id == selectedShippingAddressId);

  return (
    <div className="checkout-page">
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

      <div className="checkout-container">
        <h1 className="checkout-title">Checkout</h1>
        <div className="checkout-content">
          <div className="billing-info">
            <h2>Billing Information</h2>
            {user && (
              <div className="user-details">
                <p>
                  <strong>Name:</strong> {user.name}
                </p>
                <p>
                  <strong>Email:</strong> {user.email}
                </p>
              </div>
            )}
            <div className="form-group">
              <label htmlFor="shippingAddress">Select Shipping Address:</label>
              <select
                id="shippingAddress"
                value={selectedShippingAddressId || ''}
                onChange={(e) => setSelectedShippingAddressId(e.target.value)}
                required
              >
                <option value="">Select an address</option>
                {shippingAddresses.map((addr) => (
                  <option key={addr.id} value={addr.id}>
                    {addr.addressLine1}, {addr.city}, {addr.state}, {addr.zipCode}, {addr.country}
                  </option>
                ))}
              </select>
            </div>
            {selectedAddress && (
              <div className="address-details">
                <h3>Selected Address</h3>
                <p>
                  <strong>Address:</strong> {selectedAddress.addressLine1}
                  {selectedAddress.addressLine2 && `, ${selectedAddress.addressLine2}`}
                </p>
                <p>
                  <strong>City:</strong> {selectedAddress.city}
                </p>
                <p>
                  <strong>State:</strong> {selectedAddress.state}
                </p>
                <p>
                  <strong>Zip Code:</strong> {selectedAddress.zipCode}
                </p>
                <p>
                  <strong>Country:</strong> {selectedAddress.country}
                </p>
              </div>
            )}
            {!selectedAddress && shippingAddresses.length === 0 && (
              <p>No shipping address available. Please add one.</p>
            )}
          </div>

          <div className="order-summary">
            <h2>Order Summary</h2>
            {cartItems.length > 0 ? (
              <div className="checkout-cart-items">
                {cartItems.map((item) => (
                  <div key={item.id} className="cart-item">
                    <img src={item.productImage} alt={item.productName} className="product-image" />
                    <div className="item-details">
                      <p>
                        <strong>{item.productName}</strong>
                      </p>
                      <p>Price: ₹{item.productPrice.toLocaleString('en-IN')}</p>
                      <p>Quantity: {item.quantity}</p>
                      <p>Total: ₹{item.totalCost.toLocaleString('en-IN')}</p>
                    </div>
                  </div>
                ))}
                <div className="total-cost">
                  <h3>Total: ₹{totalCost.toLocaleString('en-IN')}</h3>
                </div>
              </div>
            ) : (
              <p>Your cart is empty.</p>
            )}
          </div>
        </div>

        <div className="payment-section">
          <h2>Payment</h2>
          <form onSubmit={handlePaymentSubmit}>
            <div className="payment-method">
              <label htmlFor="paymentMethod">Select Payment Method:</label>
              <select
                id="paymentMethod"
                value={paymentMethod}
                onChange={handlePaymentMethodChange}
                required
              >
                <option value="">--Select Payment--</option>
                <option value="UPI">UPI</option>
                <option value="Card">Debit/Credit Card</option>
              </select>
            </div>

            {showUPI && (
              <div className="upi-payment">
                <p>Scan the QR code to pay via UPI:</p>
                <img src={upi} alt="UPI QR Code" className="upi-qr" />
              </div>
            )}

            {showCard && (
              <div className="card-payment">
                <div className="card-inputs">
                  <div className="form-group">
                    <label htmlFor="cardNumber">Card Number:</label>
                    <input
                      type="text"
                      id="cardNumber"
                      name="cardNumber"
                      value={cardDetails.cardNumber}
                      onChange={handleCardInputChange}
                      placeholder="1234 5678 9012 3456"
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="expiryDate">Expiry Date:</label>
                    <input
                      type="text"
                      id="expiryDate"
                      name="expiryDate"
                      value={cardDetails.expiryDate}
                      onChange={handleCardInputChange}
                      placeholder="MM/YY"
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label htmlFor="cvv">CVV:</label>
                    <input
                      type="text"
                      id="cvv"
                      name="cvv"
                      value={cardDetails.cvv}
                      onChange={handleCardInputChange}
                      placeholder="123"
                      required
                    />
                  </div>
                </div>
              </div>
            )}

            <button type="submit" className="submit-payment">
              Submit Payment
            </button>
          </form>
        </div>
      </div>

      <footer className="footer">
        <div className="footer-content">
          <div className="footer-section">
            <h3>About QUITQ</h3>
            <p>Your one-stop shop for electronics, fashion, furniture, and more. Shop with ease and trust.</p>
          </div>
          <div className="footer-section">
            <h3>Quick Links</h3>
            <ul>
              <li>
                <Link to="/products">Products</Link>
              </li>
              <li>
                <Link to="/about">About Us</Link>
              </li>
              <li>
                <Link to="/terms">Terms & Conditions</Link>
              </li>
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

export default Checkout;