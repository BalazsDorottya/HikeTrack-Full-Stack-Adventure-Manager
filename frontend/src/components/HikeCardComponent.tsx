import React from 'react';
import { Link } from 'react-router-dom';
import { Card, CardContent, Typography, Button, Box, IconButton, Stack } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { HikeShortDTO } from '../types/hikeTypes';
import { useHikeMutations } from '../hooks/useHikeMutations';

interface HikeCardProps {
  hike: HikeShortDTO;
}

function HikeCard({ hike }: HikeCardProps) {
  const { toggleFavorite } = useHikeMutations();
  const { completeHike } = useHikeMutations();
  const handleToggleFavorite = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();

    toggleFavorite.mutate({ id: hike.id, favorite: !hike.favorite });
  };

  const handleComplete = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    completeHike.mutate(hike.id);
  };

  // converts to Boolean to prevent null or undefined
  const isHeartRed = Boolean(hike.favorite);

  return (
    <Card variant="outlined" sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
      <CardContent sx={{ flexGrow: 1 }}>
        {' '}
        {/* Extends the section. Sends the buttons to the next row */}
        <Stack direction="row" justifyContent="space-between" alignItems="flex-start" spacing={2}>
          <Typography variant="h6" component="div" sx={{ lineHeight: 1.2, wordBreak: 'break-word', flex: 1 }}>
            {hike.nameOfTrail}
          </Typography>

          <IconButton
            onClick={handleToggleFavorite}
            disabled={toggleFavorite.isPending}
            size="small"
            sx={{
              color: isHeartRed ? '#d32f2f' : 'action.disabled',
              transition: 'transform 0.2s',
              '&:hover': { transform: 'scale(1.2)' },
              mt: -0.5,
            }}
          >
            {isHeartRed ? <FavoriteIcon /> : <FavoriteBorderIcon />}
          </IconButton>
        </Stack>
      </CardContent>
      <Box sx={{ p: 2 }}>
        <Stack direction="row" spacing={1}>
          <Button variant="contained" color="primary" fullWidth component={Link} to={`/${hike.id}`}>
            View Details
          </Button>

          {/* 'nowrap' = Text in one line */}
          <Button
            variant="outlined"
            color="success"
            onClick={handleComplete}
            disabled={completeHike.isPending}
            sx={{
              whiteSpace: 'nowrap',
              minWidth: 'fit-content',
              px: 2,
            }}
          >
            ✅ {hike.timesCompleted ?? 0}
          </Button>
        </Stack>
      </Box>
    </Card>
  );
}

export default HikeCard;
