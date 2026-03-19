import React, { useContext } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import {
  Box,
  Typography,
  Paper,
  Grid,
  Button,
  Alert,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import { useHikeDetailsQuery } from '../hooks/useHikeQueries';
import { useHikeMutations } from '../hooks/useHikeMutations';
import LoadingComponent from '../components/LoadingComponent';
import { AuthContext } from '../context/AuthContext';

function HikeDetailsPage() {
  const { hikeId } = useParams<{ hikeId: string }>();
  const id = parseInt(hikeId as string, 10);
  const navigate = useNavigate();

  const { isAdmin } = useContext(AuthContext);

  // for confirmation dialog
  const [openDialog, setOpenDialog] = React.useState(false);
  const { data: hike, isLoading, isError, error } = useHikeDetailsQuery(id);
  const { deleteHike } = useHikeMutations();

  const handleDelete = () => {
    deleteHike.mutate(id, {
      onSuccess: () => {
        navigate('/');
      },
    });
  };

  const handleOpenDialog = () => setOpenDialog(true);
  const handleCloseDialog = () => setOpenDialog(false);

  if (isLoading) {
    return <LoadingComponent />;
  }

  if (isError) {
    return <Alert severity="error">Error loading hike: {(error as Error).message}</Alert>;
  }

  if (!hike) {
    return <Alert severity="warning">Hike with ID {id} not found.</Alert>;
  }

  const formatDate = (isoString: string) => {
    if (!isoString) return 'N/A';
    return new Intl.DateTimeFormat('en-GB', {
      dateStyle: 'full',
      timeStyle: 'short',
    }).format(new Date(isoString));
  };

  const gridItemProps = {
    item: true,
    xs: 12,
    sm: 6,
  };

  return (
    <Box>
      <Typography variant="h3" component="h1" gutterBottom>
        {hike.nameOfTrail}
      </Typography>

      <Paper elevation={3} sx={{ p: 4, mb: 3 }}>
        <Grid container spacing={2}>
          <Grid {...gridItemProps}>
            <Typography variant="h6" color="text.secondary">
              ID
            </Typography>
            <Typography variant="body1">{hike.id}</Typography>
          </Grid>
          <Grid {...gridItemProps}>
            <Typography variant="h6" color="text.secondary">
              Start Location
            </Typography>
            <Typography variant="body1">{hike.startLocation}</Typography>
          </Grid>
          <Grid {...gridItemProps}>
            <Typography variant="h6" color="text.secondary">
              Start Time
            </Typography>
            <Typography variant="body1">{formatDate(hike.startTime)}</Typography>
          </Grid>
          <Grid {...gridItemProps}>
            <Typography variant="h6" color="text.secondary">
              Price
            </Typography>
            <Typography variant="body1">{hike.price.toFixed(2)}</Typography>
          </Grid>
          <Grid {...gridItemProps}>
            <Typography variant="h6" color="text.secondary">
              Length of Trail
            </Typography>
            <Typography variant="body1">{hike.lengthOfTrail.toFixed(2)} km</Typography>
          </Grid>
        </Grid>
      </Paper>

      {isAdmin && (
        <Box sx={{ display: 'flex', gap: 2, mt: 4 }}>
          <Button variant="contained" color="secondary" component={Link} to={`/${hike.id}/edit`}>
            Modify Hike
          </Button>

          <Button variant="outlined" color="error" onClick={handleOpenDialog} disabled={deleteHike.isPending}>
            {deleteHike.isPending ? 'Deleting...' : 'Delete Hike'}
          </Button>
        </Box>
      )}

      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Confirm Deletion</DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to permanently delete the hike {hike.nameOfTrail} (ID: {hike.id})?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary" disabled={deleteHike.isPending}>
            Cancel
          </Button>
          <Button onClick={handleDelete} color="error" variant="contained" disabled={deleteHike.isPending}>
            {deleteHike.isPending ? 'Deleting...' : 'Confirm Delete'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default HikeDetailsPage;
