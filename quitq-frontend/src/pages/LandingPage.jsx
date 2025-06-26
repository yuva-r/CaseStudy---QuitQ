import React, { useState, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Navbar, Nav, FormControl, NavDropdown, Container, Row, Col } from "react-bootstrap";
import { FaUser } from "react-icons/fa";
import "../styles/LandingPage.css";
import tv from '../assets/images/download.jpg';
import shirt from '../assets/images/shirt.jpg';
import ac from '../assets/images/ac.jpg';
import cyc from '../assets/images/cyc.jpg';
import cup from '../assets/images/cup.jpg';
import toy from '../assets/images/toy.jpg';

const LandingPage = () => {
  const [isLoginDropdownOpen, setIsLoginDropdownOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate();
  const productsRef = useRef();

  const handleLoginDropdownToggle = () => setIsLoginDropdownOpen(!isLoginDropdownOpen);

  const products = {
    electronics: { name: "Smart LED Television", image: tv, price: "₹29,999", path: "/products" },
    fashion:     { name: "Men's Casual Shirt",   image: shirt, price: "₹1,299",  path: "/products" },
    homeFurniture:{ name: "Wooden Cupboard",      image: cup,  price: "₹14,999", path: "/products" },
    appliances:  { name: "Split Air Conditioner", image: ac,   price: "₹34,999", path: "/products" },
    beautyToys:  { name: "Plush Toy",             image: toy,  price: "₹799",    path: "/products" },
    cycles:      { name: "Ladybird Cycle",        image: cyc,  price: "₹4,999",  path: "/products" },
  };

  const handleCategoryClick = category => {
    setSelectedCategory(category);
    setSearchQuery("");
    productsRef.current.scrollIntoView({ behavior: "smooth" });
  };

  const handleSearchChange = e => {
    setSearchQuery(e.target.value);
    setSelectedCategory(null);
    if (e.target.value) productsRef.current.scrollIntoView({ behavior: "smooth" });
  };

  const filteredProducts = () => {
    if (searchQuery) {
      return Object.values(products).filter(p => p.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }
    if (selectedCategory) {
      return [products[selectedCategory]];
    }
    return Object.values(products);
  };

  return (
    <div className="fluid-container">
      <Navbar expand="lg" className="navbar">
        <Container fluid>
          <Navbar.Brand as={Link} to="/" className="logo">QUITQ</Navbar.Brand>
          <Navbar.Toggle aria-controls="navbar-nav" />
          <Navbar.Collapse id="navbar-nav">
            <FormControl
              type="text"
              placeholder="Search for Accessories"
              className="search-input"
              value={searchQuery}
              onChange={handleSearchChange}
            />
            <Nav className="ms-auto nav-links">
              <NavDropdown
                title={<><FaUser className="me-1" />Login</>}
                id="login-dropdown"
                show={isLoginDropdownOpen}
                onToggle={handleLoginDropdownToggle}
                align="end"
              >
                <NavDropdown.Item as={Link} to="/register" onClick={() => setIsLoginDropdownOpen(false)}>Sign Up</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/login" onClick={() => setIsLoginDropdownOpen(false)}>Login</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <div className="categories">
        {/* //converts object array into array of key value */}
        {Object.entries(products).map(([key, prod]) => (
          <Nav.Link
            as="button"
            key={key}
            onClick={() => handleCategoryClick(key)}
            className="category-item"
          >
            <img src={prod.image} alt={prod.name} />
            {/* //gets 1st char to toUpperCase then adds remaining */}
            {key.charAt(0).toUpperCase() + key.slice(1)}
          </Nav.Link>
        ))}
      </div>

      <section className="hero">
        <h1>Welcome to QUITQ!</h1>
        <p>Discover Amazing Deals - Starting at Just ₹999</p>
        <button className="cta-button" onClick={() => navigate("/products")}>Shop Now</button>
      </section>

      <section className="products" ref={productsRef}>
        <h2>Explore Our Products</h2>
        <div className="product-grid">
          {filteredProducts().length ? (
            filteredProducts().map((product, i) => (
              <div className="product-card" key={i}>
                <img src={product.image} alt={product.name} />
                <h3>{product.name}</h3>
                <p>{product.price}</p>
                <button onClick={() => navigate(product.path)}>View Details</button>
              </div>
            ))
          ) : (
            <p>No products found.</p>
          )}
        </div>
      </section>

      <footer className="footer">
        <div className="footer-content">
          <div className="footer-section"><h3>About QUITQ</h3><p>Your one-stop shop for electronics, fashion, furniture, and more. Shop with ease and trust.</p></div>
          <div className="footer-section"><h3>Quick Links</h3><ul><li><Link to="/products">Products</Link></li><li><Link to="/about">About Us</Link></li><li><Link to="/terms">Terms & Conditions</Link></li></ul></div>
          <div className="footer-section"><h3>Contact Us</h3><p>Email: support@quitq.com</p><p>Phone: +91 123 456 7890</p><p>Address: 123 QUITQ Street, Bangalore, India</p></div>
        </div>
        <div className="footer-bottom"><p>©️ 2025 QUITQ. All rights reserved.</p></div>
      </footer>
    </div>
  );
};

export default LandingPage;