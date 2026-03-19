import { useMutation, useQueryClient } from '@tanstack/react-query';
import { hikesApi } from '../api/hikesApi';
import { HikeCreationDTO } from '../types/hikeTypes';

export const useHikeMutations = () => {
  const queryClient = useQueryClient();

  // Create new hike
  const createMutation = useMutation({
    mutationFn: (hike: HikeCreationDTO) => hikesApi.create(hike),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['hikes'] });
    },
  });

  // Update hike
  const updateMutation = useMutation({
    mutationFn: ({ id, hike }: { id: number; hike: HikeCreationDTO }) => hikesApi.update(id, hike),
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: ['hikes'] });
      queryClient.invalidateQueries({ queryKey: ['hike', data.id] });
    },
  });

  // Delete hike
  const deleteMutation = useMutation({
    mutationFn: (id: number) => hikesApi.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['hikes'] });
    },
  });

  // Add to favorites or delete from favorites
  const toggleFavoriteMutation = useMutation({
    mutationFn: ({ id, favorite }: { id: number; favorite: boolean }) => hikesApi.toggleFavorite(id, favorite),
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: ['hikes'] });
      queryClient.invalidateQueries({ queryKey: ['hike', data.id] });
    },
  });

  // Counter for how many times was the hike completed
  const completeHikeMutation = useMutation({
    mutationFn: (id: number) => hikesApi.completeHike(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['hikes'] });
    },
  });

  return {
    createHike: createMutation,
    updateHike: updateMutation,
    deleteHike: deleteMutation,
    toggleFavorite: toggleFavoriteMutation,
    completeHike: completeHikeMutation,
  };
};
