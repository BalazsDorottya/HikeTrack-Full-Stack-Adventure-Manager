import { useState } from 'react';
import { Typography, Grid, CircularProgress, Alert, Box } from '@mui/material';
import HikeCard from '../components/HikeCardComponent';
import { useHikesListQuery } from '../hooks/useHikeQueries';
import HikeFilter from '../components/HikeFilterComponent';

function HikeListPage() {
  const [filters, setFilters] = useState<{ name?: string; location?: string }>({});
  const { data: hikes, isLoading, isError, error } = useHikesListQuery(filters.name, filters.location);

  const handleFilter = (name?: string, location?: string) => {
    setFilters({ name, location });
  };

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (isError) {
    return <Alert severity="error">Error loading hikes: {error.message}</Alert>;
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        All Hikes
      </Typography>

      <HikeFilter onFilter={handleFilter} />

      <Grid container spacing={3}>
        {hikes && hikes.length > 0 ? (
          hikes.map((hike) => {
            const gridItemProps = {
              item: true,
              xs: 12,
              sm: 6,
              md: 4,
            };

            return (
              <Grid key={hike.id} {...gridItemProps}>
                <HikeCard hike={hike} />
              </Grid>
            );
          })
        ) : (
          <Typography sx={{ ml: 3 }}>No hikes found.</Typography>
        )}
      </Grid>
    </Box>
  );
}

export default HikeListPage;
