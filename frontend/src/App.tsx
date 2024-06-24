import React from "react";
import { useAuth } from "react-oidc-context";
import { useState } from "react";
import "./App.css";
import LinkDevice from "./Components/LinkDevice";
import Footer from "./Components/Footer";
import Navbar from "./Components/Navbar/Navbar";
import Settings from "./Components/Settings";
import CreatePetModal from "./Components/CreatePetModal";
import PetPage from "./Components/PetPage";

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
    console.error(auth.error);
    return <div>Oops... {auth.error.message}</div>;
  }

  if (auth.isAuthenticated) {
    return (
      <div style={{ backgroundColor: "#FFFFFF" }}>
        <div>
          <div>
            <Navbar />{" "}
            <LinkDevice />
          </div>
        </div>
        {/* <div>
          Hello USERNAME: {auth.user?.profile?.preferred_username || "User"}
        </div> */}
        <Footer />,
      </div>
    );
  }
  return [<Navbar />, <Footer />];
}

export default App;
