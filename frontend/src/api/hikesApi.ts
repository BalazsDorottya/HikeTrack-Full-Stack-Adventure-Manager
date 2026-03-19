import axios from 'axios';
import { HikeCreationDTO, HikeLongDTO, HikeShortDTO } from '../types/hikeTypes';

const API_BASE_URL = 'http://localhost:8080/hikes';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && config.headers) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const hikesApi = {
  readAll: async (nameOfTrail?: string, locationName?: string): Promise<HikeShortDTO[]> => {
    const response = await api.get<HikeShortDTO[]>('', {
      params: {
        nameOfTrail: nameOfTrail || undefined,
        locationName: locationName || undefined,
      },
    });
    return response.data;
  },

  read: async (id: number): Promise<HikeLongDTO> => {
    const response = await api.get<HikeLongDTO>(`/${id}`);
    return response.data;
  },

  create: async (hike: HikeCreationDTO): Promise<HikeLongDTO> => {
    const response = await api.post<HikeLongDTO>('', hike);
    return response.data;
  },

  update: async (id: number, hike: HikeCreationDTO): Promise<HikeLongDTO> => {
    const response = await api.put<HikeLongDTO>(`/${id}`, hike);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/${id}`);
  },

  toggleFavorite: async (id: number, favorite: boolean): Promise<HikeLongDTO> => {
    const response = await api.patch<HikeLongDTO>(`/${id}/stats/favorite`, { favorite });
    return response.data;
  },

  completeHike: async (id: number): Promise<HikeLongDTO> => {
    const response = await api.patch<HikeLongDTO>(`/${id}/stats/complete`);
    return response.data;
  },
};
