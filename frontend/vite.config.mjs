import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

const reactPlugin = react();

const config = defineConfig({
  plugins: [reactPlugin],
});

export default config;
