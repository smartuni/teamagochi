import { Route, Routes } from 'react-router-dom';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import NotFound from './routes/NotFound';
import Dashboard from './routes/Dashboard';
import AuthDebug from './routes/AuthDebug';
import Navbar from './Navbar';
import { enqueueSnackbar } from 'notistack';
import { useAuth } from 'react-oidc-context';
import { useEffect } from 'react';

const AppRoutes = {
  Home: '/',
  AuthDebug: '/auth/debug',
  NotFound: '*',
};

const App: React.FC = () => {
  const auth = useAuth();

  useEffect(() => {
    if (auth.activeNavigator === "signinSilent") {
      enqueueSnackbar('Logging in...');
    } else if (auth.activeNavigator === "signoutRedirect") {
      enqueueSnackbar('Logging out...');
    } else if (auth.error) {
      console.log(auth.error.message);
      enqueueSnackbar('Login failed!', { variant: 'error' });
    }
  }, [auth]);
  
  return (
    <>
    <CssBaseline />
    <Navbar />
    <Container maxWidth="xl" sx={{marginBottom: '4em'}}>
      <Routes>
        <Route path={AppRoutes.Home}>
          <Route index element={<Dashboard />} />
          <Route path={AppRoutes.AuthDebug} element={<AuthDebug />} />
          <Route path={AppRoutes.NotFound} element={<NotFound />} />
        </Route>
      </Routes>
    </Container>
    </>
  );
};

export default App;
