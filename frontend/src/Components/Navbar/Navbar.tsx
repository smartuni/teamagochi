import React, { useState } from "react";
import { useAuth } from "react-oidc-context";
import './navbar.css';

const Navbar = () =>{
    const auth = useAuth();
    const [activePage, setActivePage] = useState("Pet Page");

    const loginButtonStyles = {
        color: '#fff',             // White text color
        backgroundColor: '#006400', 
        fontWeight: 'bold',        // Bold font weight
        // Add any other custom styles as needed
    };

    const navbarItemStyles = {
        marginRight: '20px', // Adjust the margin between navbar items
    };

    const handlePageClick = (page) => {
        setActivePage(page);
    };

    return (
        <div>    
            <nav className="navbar navbar-expand-lg navbar-dark bg-success">
            <img src={"./android-chrome-192x192.png"} alt="Logo" style={{ width: '30px', height: '30px', marginLeft: '5px' }} />        
            <a className="navbar-brand" href="#"> TEAMAGOCHI</a>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse flex-row-reverse" id="navbarSupportedContent" style={navbarItemStyles}>
                <ul className="navbar-nav ">
                <li className={`nav-item ${activePage === 'Pet Page' ? 'active' : ''}`}>
                    <a className="nav-link" href="#" style={navbarItemStyles} onClick={() => handlePageClick('Pet Page')}>Pet Page</a>
                </li>
                <li className={`nav-item ${activePage === 'Inventory' ? 'active' : ''}`}>
                    <a className="nav-link" href="#" style={navbarItemStyles} onClick={() => handlePageClick('Inventory')}>Inventory</a>
                </li>
                <li className={`nav-item ${activePage === 'Friends' ? 'active' : ''}`}>
                    <a className="nav-link" href="#" style={navbarItemStyles} onClick={() => handlePageClick('Friends')}>Friends</a>
                </li>
                <li className={`nav-item ${activePage === 'Settings' ? 'active' : ''}`}>
                    <a className="nav-link" href="#" style={navbarItemStyles} onClick={() => handlePageClick('Settings')}>Settings</a>
                </li>
                {/* <li className="nav-item dropdown" style={navbarItemStyles}>
                    <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={navbarItemStyles}>
                    Settings
                    </a>
                    <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a className="dropdown-item" href="#">Action</a>
                    <a className="dropdown-item" href="#">Another action</a>
                    <div className="dropdown-divider"></div>
                    <a className="dropdown-item" href="#">Something else here</a>
                    </div>
                </li> */}
                {auth.isAuthenticated ? (
                <li className="navbar-item">
                    <button className="nav-link" onClick={() => auth.removeUser()} style={loginButtonStyles}>Log out</button>
                </li>
                ) : (
                <li className="navbar-item">
                    <a className="nav-link" href="#" onClick={() => auth.signinRedirect()} style={loginButtonStyles}>Log in</a>
                </li>
                )}
                
                </ul>
            </div>
            </nav>
        </div>
    )
}

export default Navbar
