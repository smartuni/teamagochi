import { useAuth } from "react-oidc-context";
import Footer from "./Components/Footer";
import Navbar from "./Components/Navbar";
import LandingPage from "./Components/LandingPage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./lib/ProtectedRoute";
import Settings from "./Components/Settings";
import PetPage from "./Components/PetPage";
import NotFound from "./Components/NotFound";
import { ToastContainer, toast } from "react-toastify";

import "./App.css";

function App() {
    const auth = useAuth();

    switch (auth.activeNavigator) {
        case "signinSilent":
            return;
        case "signoutRedirect":
            return;
    }

    if (auth.isLoading) {
        toast("Logging in...");
        return;
        // return <div>Loading...</div>;
    }

    if (auth.error) {
        console.error(auth.error);
        toast(auth.error.message);
        return;
        // return <div>Oops... {auth.error.message}</div>;
    }

    if (!auth.isAuthenticated) {
        return (
            <>
            <div style={{height: "100vh"}}>
                <Navbar />
                <LandingPage />
            </div>
            <div>
                <Footer />
            </div>
            </>
        );
    }

    return (
        <>
        <BrowserRouter basename="/">
            <Navbar />
            <div className="main-content" style={{ backgroundColor: "#FFFFFF" }}>
                <Routes>
                    <Route
                        path="/"
                        element={<LandingPage />}
                    />
                    <Route
                        path="/PetPage"
                        element={
                            <ProtectedRoute>
                                <PetPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/Settings"
                        element={
                        <ProtectedRoute>
                            <Settings
                                username={auth.user?.profile?.preferred_username || "User"}
                            />
                        </ProtectedRoute>
                        }
                    />

                    <Route
                        path="*"
                        element={
                            <ProtectedRoute>
                                <NotFound />
                            </ProtectedRoute>
                        }
                    />
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
        <ToastContainer />
        </>
    );
}

export default App;
