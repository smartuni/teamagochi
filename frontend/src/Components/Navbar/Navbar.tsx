import React, { useState } from "react";
import { BrowserRouter as Router, Link, Route, Routes } from "react-router-dom";
import Inventory from "../Inventory.tsx";
import Settings from "../Settings.tsx";
import Friends from "../Friends.tsx";
import PetPage from "../PetPage.tsx";
import LinkDevice from "../LinkDevice.tsx";
import { useAuth } from "react-oidc-context";
import "./navbar.css";

const Navbar = () => {
  const auth = useAuth();
  const [activePage, setActivePage] = useState("Pet Page");

  const loginButtonStyles = {
    color: "#fff",
    backgroundColor: "#006400",
    fontWeight: "bold",
  };

  const navbarItemStyles = {
    marginRight: "20px",
  };

  const handlePageClick = (page) => {
    setActivePage(page);
  };

  return (
    <div>
      <Router>
        <nav className="navbar navbar-expand-lg navbar-dark bg-success">
          <img
            src={"./android-chrome-192x192.png"}
            alt="Logo"
            style={{ width: "30px", height: "30px", marginLeft: "5px" }}
          />
          <a className="navbar-brand px-2" href="#">
            TEAMAGOCHI
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div
            className="collapse navbar-collapse flex-row-reverse"
            id="navbarSupportedContent"
            style={navbarItemStyles}
          >
            <ul className="navbar-nav">
              {auth.isAuthenticated ? (
                <>
                      
                  <li
                    
                  >
                    <Link
                      className="nav-link"
                      to="/PetPage"
                      style={navbarItemStyles}
                      onClick={() => handlePageClick("Pet Page")}
                    >
                      Pet Page
                    </Link>
                  </li>
                  <li
                    className={`nav-item ${
                      activePage === "Inventory" ? "active" : ""
                    }`}
                  >
                    <Link
                      className="nav-link"
                      to="/inventory"
                      style={navbarItemStyles}
                      onClick={() => handlePageClick("Inventory")}
                    >
                      Inventory
                    </Link>
                  </li>
                  <li
                    className={`nav-item ${
                      activePage === "Friends" ? "active" : ""
                    }`}
                  >
                    <Link
                      className="nav-link"
                      to="/Friends"
                      style={navbarItemStyles}
                      onClick={() => handlePageClick("Friends")}
                    >
                      Friends
                    </Link>
                  </li>
                  <li
                    className={`nav-item ${
                      activePage === "Settings" ? "active" : ""
                    }`}
                  >
                    <Link
                      className="nav-link"
                      to="/Settings"
                      style={navbarItemStyles}
                      onClick={() => handlePageClick("Settings")}
                    >
                      Settings
                    </Link>
                  </li>
                  <li className="navbar-item">
                    <button
                      className="nav-link"
                      onClick={() => auth.removeUser()}
                      style={loginButtonStyles}
                    >
                      Log out
                    </button>
                  </li>
                </>
              ) : (
                <li className="navbar-item">
                  <a
                    className="nav-link"
                    href="#"
                    onClick={() => auth.signinRedirect()}
                    style={loginButtonStyles}
                  >
                    Log in
                  </a>
                </li>
              )}
            </ul>
          </div>
        </nav>
        <Routes>
          <Route path="/inventory" element={<Inventory />} />
          <Route path="/Settings" element={<Settings />} />
          <Route path="/Friends" element={<Friends />} />
          <Route path="/PetPage" element={<PetPage />} />
        </Routes>
      </Router>
    </div>
  );
};
export default Navbar;
