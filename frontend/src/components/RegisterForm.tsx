import React, { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { Button, Typography, Box, TextField, Paper } from '@mui/material';
import { authApi } from '../api/authApi';
import { useAuth } from '../hooks/useAuth';

export function RegisterForm() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    firstName: '',
    lastName: '',
  });
  const [error, setError] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const token = await authApi.register(formData);
      login(token); // Log them in immediately
      navigate('/'); // Redirect to home/hikes
    } catch (err) {
      setError('Registration failed. Email might be taken.');
    }
  };

  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '80vh',
      }}
    >
      <Paper elevation={3} sx={{ p: 4, width: '100%', maxWidth: 400 }}>
        <Typography variant="h4" gutterBottom align="center">
          Create Account
        </Typography>

        <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          {error && <Typography color="error">{error}</Typography>}

          <TextField label="First Name" name="firstName" onChange={handleChange} required fullWidth />
          <TextField label="Last Name" name="lastName" onChange={handleChange} required fullWidth />
          <TextField label="Email" name="email" type="email" onChange={handleChange} required fullWidth />
          <TextField label="Password" name="password" type="password" onChange={handleChange} required fullWidth />

          <Button type="submit" variant="contained" fullWidth size="large" sx={{ mt: 2 }}>
            Register
          </Button>

          <Box sx={{ mt: 2, textAlign: 'center' }}>
            <Typography variant="body2">
              Already have an account?{' '}
              <Button component={RouterLink} to="/login" variant="text">
                Login
              </Button>
            </Typography>
          </Box>
        </Box>
      </Paper>
    </Box>
  );
}
