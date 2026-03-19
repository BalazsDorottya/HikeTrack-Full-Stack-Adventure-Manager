import React, { createContext, useContext, useMemo, useState, useCallback } from 'react';
import { ThemeProvider } from '@mui/material/styles';
import { themes } from './themes';
import { Season, seasons } from './seasons';

interface ThemeContextValue {
  season: Season;
  nextSeason: () => void;
}

const ThemeContext = createContext<ThemeContextValue | null>(null);

export const useSeasonTheme = () => {
  const ctx = useContext(ThemeContext);
  if (!ctx) {
    throw new Error('useSeasonTheme must be used within SeasonThemeProvider');
  }
  return ctx;
};

function SeasonThemeProvider({ children }: { children: React.ReactNode }) {
  const [season, setSeason] = useState<Season>('spring');

  const nextSeason = useCallback(() => {
    setSeason((prev) => {
      const index = seasons.indexOf(prev);
      return seasons[(index + 1) % seasons.length];
    });
  }, []);

  const theme = useMemo(() => themes[season], [season]);
  // creates context value object
  const contextValue = useMemo(() => ({ season, nextSeason }), [season, nextSeason]);

  return (
    <ThemeContext.Provider value={contextValue}>
      <ThemeProvider theme={theme}>{children}</ThemeProvider>
    </ThemeContext.Provider>
  );
}

// will surround main
export default SeasonThemeProvider;
