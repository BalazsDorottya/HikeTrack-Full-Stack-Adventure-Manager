import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, CircularProgress, Alert, Paper } from '@mui/material';
import LoadingComponent from '../components/LoadingComponent';
import { useHikeMutations } from '../hooks/useHikeMutations';
import { useHikeDetailsQuery } from '../hooks/useHikeQueries';
import { HikeCreationDTO } from '../types/hikeTypes';
import { hikeSchema } from '../validation/hikeSchema';

const initialFormState: HikeCreationDTO = {
  nameOfTrail: '',
  startLocation: '',
  startTime: new Date().toISOString().slice(0, 16),
  price: 0,
  lengthOfTrail: 0,
};

// From page for creat and update
function HikeFormPage() {
  const { hikeId } = useParams<{ hikeId: string }>();
  const isEditMode = !!hikeId;

  const id = isEditMode ? parseInt(hikeId as string, 10) : 0;
  const navigate = useNavigate();

  const { data: hikeData, isLoading: isFetching, isError: isFetchError } = useHikeDetailsQuery(id);
  const [formData, setFormData] = useState<HikeCreationDTO>(initialFormState);
  const { createHike, updateHike } = useHikeMutations();
  const currentMutation = isEditMode ? updateHike : createHike;
  const [errors, setErrors] = useState<Record<string, string>>({});

  // for modifying a hike
  useEffect(() => {
    if (isEditMode && hikeData) {
      const formattedTime = new Date(hikeData.startTime).toISOString().slice(0, 16);

      setFormData({
        nameOfTrail: hikeData.nameOfTrail,
        startLocation: hikeData.startLocation,
        startTime: formattedTime,
        price: hikeData.price,
        lengthOfTrail: hikeData.lengthOfTrail,
      });
    }
  }, [isEditMode, hikeData]);

  // checks each input, converts type for numbers from string
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;

    let newValue: string | number = value;

    if (type === 'number') {
      if (value === '') {
        newValue = '';
      } else {
        newValue = parseFloat(value);
      }
    }
    // only overwrite the changed fields
    setFormData((prev) => ({
      ...prev,
      [name]: newValue,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({});

    const dataToValidate = {
      nameOfTrail: formData.nameOfTrail,
      startLocation: formData.startLocation,
      price: Number(formData.price),
      lengthOfTrail: Number(formData.lengthOfTrail),
      startTime: formData.startTime,
    };

    // zod
    // parseSafe - object, not exception, easier in forms
    const result = hikeSchema.safeParse(dataToValidate);

    if (!result.success) {
      const fieldErrors: Record<string, string> = {};

      result.error.issues.forEach((issue) => {
        // personalized error for each field
        const field = issue.path[0];
        if (typeof field === 'string') {
          fieldErrors[field] = issue.message;
        }
      });

      setErrors(fieldErrors);
      return;
    }

    const dataToSubmit: HikeCreationDTO = {
      ...result.data,
      startTime: new Date(formData.startTime).toISOString(),
    };

    if (isEditMode) {
      updateHike.mutate(
        { id, hike: dataToSubmit },
        {
          onSuccess: (updatedHike) => {
            navigate(`/${updatedHike.id}`);
          },
        },
      );
    } else {
      createHike.mutate(dataToSubmit, {
        onSuccess: (newHike) => {
          navigate(`/${newHike.id}`);
        },
      });
    }
  };

  if (isEditMode && isFetching) {
    return <LoadingComponent />;
  }

  if (isEditMode && isFetchError) {
    return <Alert severity="error">Error loading hike details for editing.</Alert>;
  }

  const title = isEditMode ? `Edit Hike: ${hikeData?.nameOfTrail || ''}` : 'Create New Hike';
  const buttonText = isEditMode ? 'Update Hike' : 'Create Hike';

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto' }}>
      <Typography variant="h4" gutterBottom>
        {title}
      </Typography>

      {currentMutation.isError && (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to {isEditMode ? 'update' : 'create'} hike: {(currentMutation.error as Error).message}
        </Alert>
      )}

      <Paper component="form" onSubmit={handleSubmit} sx={{ p: 4, display: 'flex', flexDirection: 'column', gap: 2 }}>
        <TextField
          label="Name of Trail"
          name="nameOfTrail"
          value={formData.nameOfTrail}
          onChange={handleChange}
          error={!!errors.nameOfTrail}
          helperText={errors.nameOfTrail}
          fullWidth
        />

        <TextField
          label="Start Location"
          name="startLocation"
          value={formData.startLocation}
          onChange={handleChange}
          error={!!errors.startLocation}
          helperText={errors.startLocation}
          fullWidth
        />

        <TextField
          label="Start Time"
          name="startTime"
          type="datetime-local"
          value={formData.startTime}
          onChange={handleChange}
          error={!!errors.startTime}
          helperText={errors.startTime}
          fullWidth
          InputLabelProps={{ shrink: true }}
        />

        <TextField
          label="Price"
          name="price"
          type="number"
          value={formData.price}
          onChange={handleChange}
          inputProps={{ step: '0.01', min: '0' }}
          error={!!errors.price}
          helperText={errors.price}
          fullWidth
        />

        <TextField
          label="Length of Trail (km)"
          name="lengthOfTrail"
          type="number"
          value={formData.lengthOfTrail}
          onChange={handleChange}
          inputProps={{ step: '0.01', min: '0' }}
          error={!!errors.lengthOfTrail}
          helperText={errors.lengthOfTrail}
          fullWidth
        />

        <Button type="submit" variant="contained" color="primary" disabled={currentMutation.isPending} sx={{ mt: 2 }}>
          {currentMutation.isPending ? <CircularProgress size={24} color="inherit" /> : buttonText}
        </Button>
      </Paper>
    </Box>
  );
}

export default HikeFormPage;
