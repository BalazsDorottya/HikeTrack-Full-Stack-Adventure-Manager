import React from 'react';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { CssBaseline } from '@mui/material';
import App from './App';
import './style.css';
import SeasonThemeProvider from './theme/ThemeContext';
import { AuthProvider } from './context/AuthContext';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <SeasonThemeProvider>
          <CssBaseline />
          <App />
        </SeasonThemeProvider>
      </AuthProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
