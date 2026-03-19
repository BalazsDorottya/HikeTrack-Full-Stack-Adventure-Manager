import { useState } from 'react';
import { TextField, Button, Box } from '@mui/material';

interface HikeFilterProps {
  onFilter: (name?: string, location?: string) => void;
}

function HikeFilter({ onFilter }: HikeFilterProps) {
  const [nameQuery, setNameQuery] = useState('');
  const [locationQuery, setLocationQuery] = useState('');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    onFilter(nameQuery || undefined, locationQuery || undefined);
  };

  const handleClear = () => {
    setNameQuery('');
    setLocationQuery('');
    onFilter(undefined, undefined);
  };

  return (
    <Box
      component="form"
      onSubmit={handleSearch}
      sx={{ display: 'flex', gap: 2, mb: 4, flexWrap: 'wrap', alignItems: 'center' }}
    >
      <TextField
        label="Trail Name"
        variant="outlined"
        size="small"
        value={nameQuery}
        onChange={(e) => setNameQuery(e.target.value)}
      />

      <TextField
        label="Location"
        variant="outlined"
        size="small"
        value={locationQuery}
        onChange={(e) => setLocationQuery(e.target.value)}
      />

      <Button type="submit" variant="contained" color="primary">
        Search
      </Button>

      <Button variant="text" color="inherit" onClick={handleClear}>
        Clear
      </Button>
    </Box>
  );
}

export default HikeFilter;
