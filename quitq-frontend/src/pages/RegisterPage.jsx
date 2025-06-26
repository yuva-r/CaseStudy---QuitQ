import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../services/AuthService';
import '../styles/AuthPages.css';

const RegisterPage = () => {
    const [name, setName] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const registerUser = (e) => {
        e.preventDefault();
        console.log("Registration handler triggered.....");

        const user = { name, username, email, password };
        console.log("User data to register: ", JSON.stringify(user));

        AuthService.registerUser(user).then(response => {
            console.log("Response received from register API",JSON.stringify(response.data));
            alert('Registration Successful');
            navigate('/login');
        }).catch(error => {
            console.error("Registration failed:", error);
            if (!error?.response) {
                alert('No server response');
            } else if (error.response?.status === 409) {
                alert('Username Taken');
            } else {
                alert('Registration Failed');
            }
        });
    };

    return (
        <div>
            {console.log("Registration form rendered...")}
        <div className="page-wrapper">
            <h1>QuitQ</h1>
            <h2>Register</h2>
            <form onSubmit={registerUser}>
                <div className="form-group">
                    <label>Full Name</label>
                    <input type="text"  placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Username</label>
                    <input type="text"  placeholder="UserName" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Email</label>
                    <input type="email"  placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input type="password"  placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <button type="submit">Register</button>
                <div className="login-link">
                    Already have an account? <Link to="/login">Login</Link>
                </div>
            </form>
        </div>
        </div>
    );
};

export default RegisterPage;