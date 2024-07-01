import { useAuth } from "react-oidc-context";
import "./App.css";
import Footer from "./Components/Footer";
import Navbar from "./Components/Navbar/Navbar";

function App() {
  const auth = useAuth();

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
      <div style={{ backgroundColor: "#FFFFFF" }}>
        <div>
          <div>
            <Navbar client={client} />
          </div>
        </div>
        <Footer />
      </div>
    );
  }
  return [<Navbar />, <Footer />];
}

export default App;
