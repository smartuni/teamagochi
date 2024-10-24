import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { useState } from "react";

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

    const handlePageClick = (page: string) => {
        setActivePage(page);
    };

    return (
        <div>
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
                        <li className={`nav-item ${activePage === "Pet Page" ? "active" : ""}`}>
                            <Link
                                className="nav-link"
                                to="/PetPage"
                                style={navbarItemStyles}
                                onClick={() => handlePageClick("Pet Page")}
                            >
                            Pet Page
                            </Link>
                        </li>
                        <li className={`nav-item ${activePage === "Settings" ? "active" : ""}`}>
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
                            <Link
                                className="nav-link"
                                onClick={() => auth.removeUser()}
                                to="/"
                                style={loginButtonStyles}
                            >
                            Log out
                            </Link>
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
        </div>
    );
};

export default Navbar;
