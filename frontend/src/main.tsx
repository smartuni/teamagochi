import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { AuthProvider } from "react-oidc-context";

const oidcConfig = {
  authority: "http://localhost:4000/kc/realms/teamagochi",
  client_id: "teamagochi-webapp",
  redirect_uri: "http://localhost:5173/teamagochi/",
  // ...
};

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider {...oidcConfig}>
      <App />
    </AuthProvider>
  </React.StrictMode>,
)

