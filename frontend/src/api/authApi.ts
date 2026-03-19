import axios from 'axios';
import { AuthResponse, RegisterDTO } from '../types/hikeTypes';

const API_BASE_URL = 'http://localhost:8080/auth';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    if (!config.headers) {
      config.headers = {};
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token'); // log out
      window.location.href = '/login'; // redirect to login page
    }
    return Promise.reject(error);
  },
);

export const authApi = {
  register: async (user: RegisterDTO): Promise<string> => {
    const response = await api.post<AuthResponse>('/register', user);
    if (response.data && response.data.token) {
      localStorage.setItem('token', response.data.token);
      return response.data.token;
    }
    throw new Error('No token received');
  },

  login: async (email: string, password: string): Promise<string> => {
    const response = await api.post<AuthResponse>('/login', { email, password });
    localStorage.setItem('token', response.data.token);
    return response.data.token;
  },

  logout: (): void => {
    localStorage.removeItem('token');
    window.location.href = '/login';
  },
};
