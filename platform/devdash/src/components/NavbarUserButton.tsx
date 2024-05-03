import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useAuth } from 'react-oidc-context';
import { Link } from 'react-router-dom';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Box } from '@mui/material';
import { useSnackbar } from 'notistack';

export default function BasicMenu() {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const auth = useAuth();
  const { enqueueSnackbar } = useSnackbar()

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const getUsername = () => auth.user?.profile.preferred_username || undefined;

  const handleLogin = async () => {
    handleClose();
    await auth.signinRedirect();
  };

  const handleLogout = async () => {
    handleClose();
    enqueueSnackbar('Logging out...');
    // auth.removeUser();
    // auth.signoutRedirect();
    await auth.signoutSilent();
    if (auth.error) {
      enqueueSnackbar('Logout failed!', { variant: 'error', persist: true });
    } else {
      enqueueSnackbar('Logout successful.');
    }
  };

  if (!auth.isAuthenticated) {
    return <Button onClick={handleLogin} color="inherit">Login</Button>;
  }

  return (
    <div>
      <Button
        id="nav-user-button"
        color="inherit"
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
      >
        <Box component='div' display='flex' marginRight='.6em'>
          <AccountCircleIcon />
        </Box>
        {getUsername()}
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'nav-user-button',
        }}
      >
        <MenuItem component={Link} to={'/auth/debug'} onClick={handleClose}>Show Userdata</MenuItem>
        <MenuItem onClick={handleLogout}>Logout</MenuItem>
      </Menu>
    </div>
  );
}