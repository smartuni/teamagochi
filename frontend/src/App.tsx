import React from "react";
//import { useAuth } from "react-oidc-context";
import { useState } from 'react'
import './App.css'
import LinkDevice from './Components/LinkDevice'
import Footer from './Components/Footer';
import Navbar from "./Components/Navbar/Navbar";
import Settings from "./Components/Settings";

function App() {
  /*const auth = useAuth();
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
      
      <div style={{ backgroundColor: '#F5F5DC'}}>
        <div>
        <div><Navbar /> </div>
{/*         
        <h1 >WELCOME TO TEAMAGOCHI GANG</h1>
          <h1 className='font-extrabold text-9xl' >
             <a href="https://www.youtube.com/watch?v=sf0PJsknZiM">CARTI </a>
          </h1>
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   */}
        </div>
        
        <div>Hello {auth.user?.profile?.preferred_username || 'User'}</div>
      </div>
    );
  }

  return <button onClick={() => void auth.signinRedirect()}>Log in</button>; */
  return[
    <Navbar/>,
    <Settings/>,
    <Footer/>,
  ];
}

export default App;
