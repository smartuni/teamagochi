import { createContext } from 'react';
import { AuthProviderProps } from 'react-oidc-context';

type AppConfig = {
  api: {
    baseUrl: string,
  }
}

function readEnvVars(envs: ImportMetaEnv): {
  appConfig: AppConfig,
  oidcConfig: AuthProviderProps
} {
  return {
    appConfig: {
      api: {
        baseUrl: envs.VITE_API_BASE_URL,
      }
    },
    oidcConfig: {
      authority: envs.VITE_OIDC_AUTHORITY,
      client_id: envs.VITE_OIDC_CLIENT_ID,
      redirect_uri: envs.VITE_OIDC_REDIRECT_URI,
    }
  };
}

const ConfigContext = createContext<AppConfig | undefined>(undefined);

export {
  type AppConfig,
  ConfigContext,
  readEnvVars
}
