import React, { createContext, useState, useEffect, ReactNode, useMemo } from 'react';
import { jwtDecode } from 'jwt-decode';
import { AuthContextType, CustomJwtPayload } from '../types/hikeTypes';

export const AuthContext = createContext<AuthContextType>({
  token: null,
  role: null,
  isAdmin: false,
  login: () => {},
  logout: () => {},
  isAuthenticated: false,
});

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(null);

  const role = useMemo(() => {
    if (!token) return null;
    try {
      const decoded = jwtDecode<CustomJwtPayload>(token);
      return decoded.role || null;
    } catch {
      return null;
    }
  }, [token]);

  useEffect(() => {
    const savedToken = localStorage.getItem('token');
    if (savedToken) setToken(savedToken);
  }, []);

  const login = (newToken: string) => {
    localStorage.setItem('token', newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    window.location.href = '/login';
  };

  const value = useMemo(
    () => ({
      token,
      role,
      isAdmin: role === 'ADMIN',
      login,
      logout,
      isAuthenticated: !!token,
    }),
    [token, role],
  );

  return <AuthContext.Provider value={value}> {children}</AuthContext.Provider>;
}
