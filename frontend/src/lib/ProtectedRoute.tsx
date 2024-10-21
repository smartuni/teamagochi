import { useAuth } from "react-oidc-context";
import { Navigate, useLocation } from "react-router-dom";

function ProtectedRoute({ children }: { children: JSX.Element }) {
  const auth = useAuth();
  const location = useLocation();

  if (!auth.isAuthenticated) {
    return <Navigate to="/" state={{ from: location }} />;
  }

  return children;
}

export default ProtectedRoute;
