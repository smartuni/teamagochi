import { UserConfigExport, defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd());

  const commonConfig: UserConfigExport = {
    plugins: [react()],
    base: '/'
  };

  if (command === 'serve') {
    return {
      ...commonConfig,
      server: {
        // open: true,
        host: '127.0.0.1',
        port: 3030,
        strictPort: true,
      }
    };
  }

  // command === 'build'
  return {
    ...commonConfig
  };
});
