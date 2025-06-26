import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, FormControl, NavDropdown, Container, Spinner } from 'react-bootstrap';
import { FaUser, FaHome, FaCartPlus, FaListAlt, FaHeart, FaSignOutAlt, FaSearch } from 'react-icons/fa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProductService from '../services/ProductService';
import CategoryService from '../services/CategoryService';
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
import '../styles/ProductsPage.css';
import glasspot from '../assets/images/glasspot.jpg';

const ProductsPage = () => {
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const productsRef = useRef();

  const username = 'User'; 

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
    const fetchCategories = async () => {
      try {
        const response = await CategoryService.getAllCategories();
        const fetchedCategories = response.data.map((cat) => ({
          id: cat.id.toString(),
          name: cat.name,
        }));
        setCategories([{ id: 'all', name: 'All' }, ...fetchedCategories]);
      } catch (err) {
        toast.error('Failed to fetch categories. Please try again.');
        console.error('Error fetching categories:', err);
      }
    };

    const fetchProducts = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await ProductService.getAllProducts();
        const fetchedProducts = response.data.map((item) => {
          const productName = item.name?.toLowerCase() || 'unknown';
          return {
            id: item.id,
            category: item.categoryName?.toLowerCase() || 'uncategorized',
            name: item.name,
            image: productImageMap[productName] || cup,
            price: `₹${Math.floor(item.price).toLocaleString('en-IN')}`,
            seller: item.sellerName || 'Unknown Seller',
            path: `/details/${item.id}`,
          };
        });
        setProducts(fetchedProducts);
      } catch (err) {
        setError('Failed to fetch products. Please try again later.');
        toast.error('Failed to fetch products. Please try again.');
        console.error('Error fetching products:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
    fetchProducts();
  }, []);

  const handleProfileDropdownToggle = () => setIsProfileDropdownOpen(!isProfileDropdownOpen);

  const handleCategoryClick = (categoryId) => {
    setSelectedCategory(categoryId);
    setSearchQuery('');
    productsRef.current.scrollIntoView({ behavior: 'smooth' });
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
    setSelectedCategory('all');
    if (e.target.value) productsRef.current.scrollIntoView({ behavior: 'smooth' });
  };

  const handleLogout = () => {
    navigate('/login');
  };

  const filteredProducts = () => {
    let filtered = products;
    if (searchQuery) {
      filtered = filtered.filter((p) => p.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }
    if (selectedCategory !== 'all') {
      const categoryName = categories.find((c) => c.id === selectedCategory)?.name.toLowerCase();
      filtered = filtered.filter((p) => p.category === categoryName);
    }
    return filtered;
  };

  return (
    <div className="products-page">
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
                value={searchQuery}
                onChange={handleSearchChange}
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

      <div className="categories">
        {categories.map((category) => (
          <button
            key={category.id}
            className={`category-item ${selectedCategory === category.id ? 'active' : ''}`}
            onClick={() => handleCategoryClick(category.id)}
          >
            <span>{category.name}</span>
          </button>
        ))}
      </div>

      <section className="products" ref={productsRef}>
        <h2>Explore Products</h2>
        {loading && (
          <div className="loading">
            <Spinner animation="border" variant="primary" />
            <p>Loading products...</p>
          </div>
        )}
        {error && <p className="error">{error}</p>}
        <div className="product-grid">
          {filteredProducts().length ? (
            filteredProducts().map((product) => (
              <div className="product-card" key={product.id}>
                <img src={product.image} alt={product.name} />
                <h3>{product.name}</h3>
                <p className="price">{product.price}</p>
                <p className="seller">Sold by: {product.seller}</p>
                <button onClick={() => navigate(product.path)}>View Details</button>
              </div>
            ))
          ) : (
            <p className="no-products">No products found.</p>
          )}
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

export default ProductsPage;