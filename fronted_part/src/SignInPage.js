import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function SignInPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    
    if (!email || !password) {
      setErrorMessage('Please fill in all the fields.');
      return;
    }

    const credentials = {
      email,
      password,
    };

    axios
      .post('http://localhost:8080/signIn', credentials)
      .then((response) => {
        console.log(response.data);
       
        setEmail('');
        setPassword('');
        setErrorMessage('');
        
        if (response.data.token) {
          navigate('/'); 
        } else {
          setErrorMessage('Invalid Username or Password');
        }
      })
      .catch((error) => {
        console.error(error);
        
        if (error.response && error.response.status === 401) {
          setErrorMessage('Invalid Username or Password');
        } else {
          setErrorMessage('An error occurred, please try again later.');
        }
      });
  };

  return (
    <div className="container">
      <h1>Sign In</h1>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <form onSubmit={handleSubmit}>
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
        <button type="submit">Sign In</button>
      </form>
    </div>
  );
}

export default SignInPage;
