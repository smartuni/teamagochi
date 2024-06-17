import React from "react";
import { useAuth } from "react-oidc-context";
import { useState } from 'react'
import './App.css'
import LinkDevice from './Components/LinkDevice'
import Footer from './Components/Footer';
import Navbar from './Components/Navbar/Navbar'
import Landing from './Components/Landing'

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
      <div style={{ backgroundColor: '#F5F5DC'}}>
        <div>
        <div><Navbar /> </div>
        <h1 >WELCOME TO TEAMAGOCHI GANG</h1>
          <h1 className='font-extrabold text-9xl' >
             <a href="https://www.youtube.com/watch?v=sf0PJsknZiM">CARTI </a>
          </h1>
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />   
          <img src={"./android-chrome-384x384.png"} alt="Logo" style={{ width: '384px', height: '384px', marginLeft: '5px' }} />  
          

          

        </div>
        <div className="card">
          <button onClick={() => setCount((count) => count + 1)}>
            count is {count}[]
          </button>
          <button onClick={() => void auth.removeUser()}>Log out</button>
        </div>
        <p className="read-the-docs">
          Hello
        </p>
        <div>Hello {auth.user?.profile.sub}</div>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <Landing/>
    </div>
  ) 
}

export default App;