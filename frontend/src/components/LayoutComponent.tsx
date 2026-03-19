import { AppBar, Toolbar, Typography, Container, Button, Box } from '@mui/material';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useSeasonTheme } from '../theme/ThemeContext';
import { useAuth } from '../hooks/useAuth';

function Layout() {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();
  const handleLogout = () => {
    logout();
    navigate('/login');
  };
  const { season, nextSeason } = useSeasonTheme();

  return (
    <>
      <AppBar position="sticky">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            <Link to="/" style={{ color: 'inherit', textDecoration: 'none' }}>
              Hike Tracker
            </Link>
          </Typography>
          <Button color="inherit" onClick={nextSeason}>
            {season.toUpperCase()}
          </Button>

          {isAuthenticated ? (
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button color="inherit" component={Link} to="/create">
                New Hike
              </Button>
              <Button color="error" variant="contained" onClick={handleLogout}>
                Logout
              </Button>
            </Box>
          ) : (
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button color="inherit" component={Link} to="/login">
                Login
              </Button>
              <Button color="inherit" component={Link} to="/register">
                Register
              </Button>
            </Box>
          )}
        </Toolbar>
      </AppBar>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Outlet />
      </Container>
    </>
  );
}

export default Layout;
