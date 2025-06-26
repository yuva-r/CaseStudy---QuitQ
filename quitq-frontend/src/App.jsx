import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import LandingPage from './pages/LandingPage';
import ProductsPage from './pages/ProductsPage';
import ProductDetailsPage from './pages/ProductDetailsPage';
import WishlistPage from './pages/WishlistPage';
import SellerDashboard from './pages/SellerDashboard';
import CartPage from './pages/CartPage';
import Checkout from './pages/Checkout';
import OrderConfirmed from './pages/OrderConfirmed';
import Orders from './pages/Orders';
import Unauthorized from './pages/Unauthorized';
import AdminPage from './pages/AdminPage';
import { RequireAuth } from './context/RequireAuth';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/details/:id" element={<ProductDetailsPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/unauthorized" element={<Unauthorized />} />

        {/* Protected Routes */}
        <Route element={<RequireAuth allowedRoles={["ROLE_USER", "ROLE_SELLER", "ROLE_ADMIN"]} />}>
          <Route path="/wishlist" element={<WishlistPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/order-confirmed" element={<OrderConfirmed />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="/orders/:id" element={<Orders />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/products/add" element={<ProductsPage />} />
          <Route path="/products/edit/:id" element={<ProductsPage />} />
          <Route path="/product/:id" element={<div>Product Details Page</div>} />
        </Route>

        {/* Seller & Admin Routes */}
        <Route element={<RequireAuth allowedRoles={["ROLE_SELLER", "ROLE_ADMIN"]} />}>
          <Route path="/seller" element={<SellerDashboard />} />
        </Route>

        {/* Admin Only Routes */}
        <Route element={<RequireAuth allowedRoles={["ROLE_ADMIN"]} />}>
          <Route path="/admin" element={<AdminPage />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;