import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { AuthProvider } from "react-oidc-context";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { ConfigContext, readEnvVars } from "./lib/ConfigContext.ts";

const {appConfig, oidcConfig} = readEnvVars(import.meta.env);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ConfigContext.Provider value={appConfig}>
      <AuthProvider {...oidcConfig}>
        <App />
      </AuthProvider>
    </ConfigContext.Provider>
  </React.StrictMode>
);
