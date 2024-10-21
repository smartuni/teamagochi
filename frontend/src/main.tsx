import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { AuthProvider } from "react-oidc-context";
import { ConfigContext, readEnvVars } from "./lib/ConfigContext.ts";

import "bootstrap/dist/js/bootstrap.bundle.min";
import "bootstrap/dist/css/bootstrap.min.css";
import "./index.css";

const {appConfig, oidcConfig} = readEnvVars(import.meta.env);

oidcConfig.onSigninCallback = (): void => {
  // Remove the payload from the URL upon successful login
  window.history.replaceState(
    {},
    document.title,
    window.location.pathname
  )
}

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ConfigContext.Provider value={appConfig}>
      <AuthProvider {...oidcConfig}>
        <App />
      </AuthProvider>
    </ConfigContext.Provider>
  </React.StrictMode>
);
