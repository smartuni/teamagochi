import { Route, Routes } from 'react-router-dom';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import NotFound from './routes/NotFound';
import Dashboard from './routes/Dashboard';
import AuthDebug from './routes/AuthDebug';
import Navbar from './Navbar';

const AppRoutes = {
  Home: '/',
  AuthDebug: '/auth/debug',
  NotFound: '*',
};

const App: React.FC = () => {
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
