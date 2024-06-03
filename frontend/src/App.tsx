import React from "react";
import { useAuth } from "react-oidc-context";
import { useState } from "react";
import "./App.css";
import LinkDevice from "./Components/LinkDevice";
import Footer from "./Components/Footer";
import Navbar from "./Components/Navbar/Navbar";
import Pet from "./Components/Pet/Pet";

function App() {
  const auth = useAuth();
  const [count, setCount] = useState(0);

  switch (auth.activeNavigator) {
    case "signinSilent":
      return <div>Signing you in...</div>;
    case "signoutRedirect":
      return <div>Signing you out...</div>;
  }

  if (auth.isLoading) {
    return <div>Loading...</div>;
  }

  if (auth.error) {
    console.error(auth.error);
    return <div>Oops... {auth.error.message}</div>;
  }

  if (auth.isAuthenticated) {
    return (
      <div>
        <div>
          <h1 className="font-extrabold text-9xl">
            Click <a href="./docs">HERE</a> for Docs!
          </h1>
        </div>
        <h1>Frontend Demo Page</h1>
        <div className="card">
          <button onClick={() => setCount((count) => count + 1)}>
            count is {count}[]
          </button>
          <button onClick={() => void auth.removeUser()}>Log out</button>
        </div>
        <p className="read-the-docs">Hello</p>
        <div>Hello {auth.user?.profile.sub}</div>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <button onClick={() => void auth.signinRedirect()}>Log in</button>
      <Pet />
    </div>
  );
}

export default App;
