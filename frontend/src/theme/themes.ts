import { createTheme, Theme } from '@mui/material/styles';
import { Season } from './seasons';

declare module '@mui/material/styles' {
  interface Palette {
    accent: Palette['primary'];
    surface: Palette['background'];
  }
  interface PaletteOptions {
    accent?: PaletteOptions['primary'];
    surface?: PaletteOptions['background'];
  }
}

const base = {
  typography: {
    fontFamily: "'Roboto', 'Segoe UI', sans-serif",
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          transition: 'background-color 300 ms ease, color 300ms ease',
        },
      },
    },
  },
};

export const themes: Record<Season, Theme> = {
  spring: createTheme({
    ...base,
    palette: {
      mode: 'light',
      primary: { main: '#4caf50' },
      secondary: { main: '#81c784' },
      accent: { main: '#ffb300' },
      surface: {
        default: '#f1f89e9',
        paper: '#ffffff',
      },
      background: {
        default: '#f1f8e9',
        paper: '#ffffff',
      },
    },
    shape: {
      borderRadius: 16,
    },
    components: {
      MuiCard: {
        styleOverrides: {
          root: {
            boxShadow: '0 8px 24px rgba(76,175,80,0.25)',
          },
        },
      },
    },
  }),

  summer: createTheme({
    ...base,
    palette: {
      mode: 'light',
      primary: { main: '#ff9800' },
      secondary: { main: '#ffb74d' },
      accent: { main: '#f44336' },
      surface: {
        default: '#fff8e1',
        paper: '#ffffff',
      },
      background: {
        default: '#fff8e1',
        paper: '#ffffff',
      },
    },
    shape: {
      borderRadius: 20,
    },
    components: {
      MuiCard: {
        styleOverrides: {
          root: {
            boxShadow: '0 10px 30px rgba(255, 152, 0, 0.35)',
          },
        },
      },
    },
  }),

  autumn: createTheme({
    ...base,
    palette: {
      mode: 'light',
      primary: { main: '#8d6e63' },
      secondary: { main: '#bcaaa4' },
      accent: { main: '#d84315' },
      surface: {
        default: '#fbe9e7',
        paper: '#ffffff',
      },
      background: {
        default: '#fbe9e7',
        paper: '#ffffff',
      },
    },
    shape: {
      borderRadius: 8,
    },
    components: {
      MuiCard: {
        styleOverrides: {
          root: {
            boxShadow: '0 6px 18px rgba(141, 110, 99, 0.35)',
          },
        },
      },
    },
  }),

  winter: createTheme({
    ...base,
    palette: {
      mode: 'dark',
      primary: { main: '#90caf9' },
      secondary: { main: '#b3e5fc' },
      accent: { main: '#4fc3f7' },
      surface: {
        default: '#121212',
        paper: '#1e1e1e',
      },
      background: {
        default: '#121212',
        paper: '#1e1e1e',
      },
    },
    shape: {
      borderRadius: 4,
    },
    components: {
      MuiAppBar: {
        styleOverrides: {
          root: {
            backgroundImage: 'linear-gradient(180deg, #1e3c72 0%, #2a5298 100%)',
          },
        },
      },
      MuiCard: {
        styleOverrides: {
          root: {
            boxShadow: '0 4px 12px rgba(0, 0, 0, 0.6)',
          },
        },
      },
    },
  }),
};
