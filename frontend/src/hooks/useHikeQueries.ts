import { useQuery } from '@tanstack/react-query';
import { hikesApi } from '../api/hikesApi';

// Listing all the hikes
export const useHikesListQuery = (nameOfTrail?: string, locationName?: string) => {
  return useQuery({
    queryKey: ['hikes', { nameOfTrail, locationName }],
    queryFn: () => hikesApi.readAll(nameOfTrail, locationName),
    staleTime: 5 * 60 * 1000, // when gets the data outdated
  });
};

// Getting one hike
export const useHikeDetailsQuery = (id: number) => {
  return useQuery({
    queryKey: ['hike', id],
    queryFn: () => hikesApi.read(id),
    enabled: !!id, // truthy?
  });
};
