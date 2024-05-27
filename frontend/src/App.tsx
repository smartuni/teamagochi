import React from "react";
import { useAuth } from "react-oidc-context";
import { useState } from 'react'
import './App.css'
import LinkDevice from './Components/LinkDevice'
import Footer from './Components/Footer';
function App() {
  const auth = useAuth();
  const [count, setCount] = useState(0)

  switch (auth.activeNavigator) {
    case 'signinSilent':
      return <div>Signing you in...</div>;
    case 'signoutRedirect':
      return <div>Signing you out...</div>;
  }
  
  if (auth.isLoading) {
    return <div>Loading...</div>;
  }

  if (auth.error) {
    return <div>Oops... {auth.error.message}</div>;
  }

  if (auth.isAuthenticated) {
    return (
      <div>
        <div>
          <h1 className='font-extrabold text-9xl'>
            Click <a href="./docs">HERE</a> for Docs!
          </h1>
          <a href="https://vitejs.dev" target="_blank">
            <img src={viteLogo} className="logo" alt="Vite logo" />
          </a>
          <a href="https://react.dev" target="_blank">
            <img src={reactLogo} className="logo react" alt="React logo" />
          </a>
        </div>
        <h1>Frontend Demo Page</h1>
        <div className="card">
          <button onClick={() => setCount((count) => count + 1)}>
            count is {count}
          </button>
          <button onClick={() => void auth.removeUser()}>Log out</button>
        </div>
        <p className="read-the-docs">
          Click on the Vite and React logos to learn more
        </p>
        <div>Hello {auth.user?.profile.sub}</div>
      </div>
    );
  }

  return <button onClick={() => void auth.signinRedirect()}>Log in</button>;
}

export default App;