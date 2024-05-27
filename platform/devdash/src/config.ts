import { UserManager, WebStorageStateStore } from 'oidc-client-ts';
import { ThemeOptions } from '@mui/material/styles';
import { Page, Service } from './types';

export const userManager = new UserManager({
  authority: import.meta.env.VITE_AUTHORITY,
  client_id: import.meta.env.VITE_CLIENT_ID,
  redirect_uri: `${window.location.origin}${window.location.pathname}`,
  post_logout_redirect_uri: window.location.origin,
  userStore: new WebStorageStateStore({ store: window.sessionStorage }),
  monitorSession: true // Allows cross tab login/logout detection
});

export const onSigninCallback = () => {
  window.history.replaceState({}, document.title, window.location.pathname);
};

export const mainMenu: Page[] = [
  {slug: '/', title: 'Dashboard'}
];

export const services: Service[] = [
  {
    name: 'Tea:shan',
    description: 'Eclipse Leshan Server demo including a Bootstrap Server.',
    links: [
      {title: 'Server App', url: '/leshan'},
      {title: 'Bootstrap Server App', url: '/leshan/bs'}
    ],
  },
  {
    name: 'Identity Provider',
    description: 'Keycloak realms for the Teamagochi universe.',
    links: [
      {title: 'Teamagochi Realm', url: '/kc/admin/teamagochi/console/'},
      {title: 'Admin Realm', url: '/kc/admin/master/console/'}
    ],
  },
  {
    name: 'Mailbox',
    description: 'Local testing mailserver powered by Mailpit.',
    links: [{title: 'Mailpit', url: '/mailbox'}],
  },
];

export const themeOptions: ThemeOptions = {
  palette: {
    mode: 'light',
    primary: {
      main: '#5D4EBC',
    },
    secondary: {
      main: '#f26c73',
    },
  },
};