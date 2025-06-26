import React, { useContext, useState } from 'react';
import AuthService from '../services/AuthService';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../context/AuthProvider';
import '../styles/AuthPages.css';

const LoginPage = () => {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { setAuth } = useContext(AuthContext);

  const LoginUser = (e) => {
    e.preventDefault();
    console.log("LoginUser Handler Triggered........");

    const user = { name, password };
    console.log("User Object Created:", user);

    AuthService.loginUser(user)
      .then(response => {
        console.log("Response Received from Login API:", JSON.stringify(response.data));
        //setAuth({ accessToken: response.data.accessToken });
        setAuth({ 
  accessToken: response.data.accessToken, 
  user: response.data.userDto 
});
        console.log("Auth Context Updated Successfully");
        alert('Login Successful');
        navigate('/products');
      })
      .catch(error => {
        console.error('Login Failed:', error);
        alert('Login Failed');
      });
  };

  const handleNameChange = (e) => {
    console.log("Name Change Event Triggered:", e.target.value);
    setName(e.target.value);
  };

  const handlePasswordChange = (e) => {
    console.log("Password Change Event Triggered:", e.target.value);
    setPassword(e.target.value);
  };

  return (
    <div className='margin-wrapper'>
    <div className="page-wrapper">
      
      <h1>QuitQ</h1>
      <h2>Login</h2>

      <form onSubmit={LoginUser}>
        <div className="form-group">
          <label>Full Name</label>
          <input
            type="text"
            placeholder="Enter your Name"
            value={name}
            onChange={handleNameChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            placeholder="Enter your Password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
        </div>

        <button type="submit">Login</button>

        <div className="login-link">
          <p>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
      </form>
    </div>
    </div>
  );
}

export default LoginPage;