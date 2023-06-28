import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import axios from 'axios';
import SignInPage from './SignInPage';
import './App.css';

function App() {
  return (
    <Router>
      <div>
        <div className="navbar">
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/login">Login</Link>
            </li>
          </ul>
        </div>
        <div className="container">
          <Routes>
            <Route path="/" element={<RegistrationForm />} />
            <Route path="/login" element={<SignInPage />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

function RegistrationForm() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [address, setAddress] = useState('');
  const [role, setRole] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();


    if (!name || !email || !password || !address || !role) {
      alert('Please fill in all the fields.');
      return;
    }

    const customer = {
      name,
      email,
      password,
      address,
      role,
    };

    axios
      .post('http://localhost:8080/customers', customer)
      .then((response) => {
        console.log(response.data);
     
        setName('');
        setEmail('');
        setPassword('');
        setAddress('');
        setRole('');
        setErrorMessage('');
      })
      .catch((error) => {
        console.error(error);
        
        if (error.response && error.response.status === 409) {
          setErrorMessage('An error occurred, please try again later.');
        } else {
          alert('User already exists.');
        }
      });
  };

  return (
    <>
      <h1>Register Customer</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </label>
        <br />
        <label>
          Email:
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </label>
        <br />
        <label>
          Password:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <br />
        <label>
          Address:
          <input
            type="text"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
        </label>
        <br />
        <label>
          Role:
          <input
            type="text"
            value={role}
            onChange={(e) => setRole(e.target.value)}
          />
        </label>
        <br />
        <button type="submit">Register</button>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
      </form>
    </>
  );
}

export default App;
