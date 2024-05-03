import { useAuth } from 'react-oidc-context';
import * as jose from 'jose';
import { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import styled from '@emotion/styled';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardHeader from '@mui/material/CardHeader';
import IconButton from '@mui/material/IconButton';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import Collapse from '@mui/material/Collapse';
import Stack from '@mui/material/Stack';

const BrokenWord = styled.p`
  word-break: break-all;
`;

const TokenTabs: React.FC = () => {
  const auth = useAuth();
  const [openAccessToken, setOpenAccessToken] = useState(false);
  const [currentAccessTokenTabIndex, setCurrentAccessTokenTabIndex] = useState(1);
  const [openIdToken, setOpenIdToken] = useState(false);
  const [currentIdTokenTabIndex, setCurrentIdTokenTabIndex] = useState(1);

  const decodeToken = (token: string) => JSON.stringify(jose.decodeJwt(token), null, 2);

  const handleAccessTokenTabChange = (_event: unknown, tabIndex: number) => {
    setCurrentAccessTokenTabIndex(tabIndex);
  };

  const handleIdTokenTabChange = (_event: unknown, tabIndex: number) => {
    setCurrentIdTokenTabIndex(tabIndex);
  };

  if (!auth.isAuthenticated) {
    return (
      <Card sx={{marginBottom: '2em'}}>
        <Box component="div" sx={{ backgroundColor: '#efefef' }}>
          <CardContent>
            <Typography variant="h5" component="div">
              Token
            </Typography>
          </CardContent>
        </Box>
        <CardContent>
          <p>
            This panel displays the <i>Access Token</i> and the <i>Identity Token</i> after login.<br />
            You can use on of the following existing users.
          </p>
          <Stack direction={{ xs: 'column', lg: 'row' }} spacing={{ xs: 2, md: 4, lg: 8 }}>
            <Box py={{ lg: '2em'}}>
              <Typography fontWeight='bold'>Teamagochi Realm</Typography>
              <pre>
                username: testuser01<br/>
                password: password<br/>
                comment: a enduser of the product
              </pre>
              <pre>
                username: testmanager01<br/>
                password: password<br/>
                comment: a realm manager
              </pre>
            </Box>
            <Box py={{ lg: '2em'}}>
              <Typography fontWeight='bold'>Admin Realm</Typography>
              <pre>
                username: kcadmin<br/>
                password: kcadminpw<br/>
              </pre>
              <p>You probably don't need to login into the admin realm. Yet, no one will stop you.</p>
            </Box>
          </Stack>
        </CardContent>
      </Card>
    )
  }

  return (
    <>
      <Card sx={{marginBottom: '1em'}}>
        <Box component="div" sx={{ backgroundColor: '#efefef' }}>
          <CardHeader
            sx={{ '&:hover': {cursor: 'pointer' }}}
            onClick={() => setOpenAccessToken(!openAccessToken)}
            aria-label="expand"
            action={
              <IconButton size="small">
                  {openAccessToken ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
              </IconButton>
            }
            title={
              <Typography variant="h5" component="div">
                Access Token
              </Typography>
            }
          />
        </Box>
        <Collapse in={openAccessToken} timeout="auto" unmountOnExit>
          <CardContent>
            <Tabs id="my-id" value={currentAccessTokenTabIndex} onChange={handleAccessTokenTabChange}>
              <Tab label="Decoded" />
              <Tab label="Encoded" />
            </Tabs>
            <Box component="div" sx={{ backgroundColor: '#efefef40' }}>
              {/* TAB 1 Contents */}
              {currentAccessTokenTabIndex === 0 && (
                <Box component="div" sx={{ px: 3, py: 1 }}>
                  <pre>
                    {decodeToken(auth.user!.access_token)}
                  </pre>
                </Box>
              )}
              {/* TAB 2 Contents */}
              {currentAccessTokenTabIndex === 1 && (
                <Box sx={{ p: 3 }}>
                  <BrokenWord>
                    {auth.user?.access_token}
                  </BrokenWord>
                </Box>
              )}
            </Box>
          </CardContent>
        </Collapse>
      </Card>
      <Card sx={{marginBottom: '2em'}}>
        <Box component="div" sx={{ backgroundColor: '#efefef' }}>
          <CardHeader
            sx={{ '&:hover': {cursor: 'pointer' }}}
            onClick={() => setOpenIdToken(!openIdToken)}
            aria-label="expand"
            action={
              <IconButton size="small">
                  {openIdToken ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
              </IconButton>
            }
            title={
              <Typography variant="h5" component="div">
                ID Token
              </Typography>
            }
          />
        </Box>
        <Collapse in={openIdToken} timeout="auto" unmountOnExit>
          <CardContent>
            <Tabs value={currentIdTokenTabIndex} onChange={handleIdTokenTabChange}>
              <Tab label="Decoded" />
              <Tab label="Encoded" />
            </Tabs>
            <Box component="div" sx={{ backgroundColor: '#efefef40' }}>
              {/* TAB 1 Contents */}
              {currentIdTokenTabIndex === 0 && (
                <Box sx={{ px: 3, py: 1 }}>
                  <pre>
                    {decodeToken(auth.user!.id_token!)}
                  </pre>
                </Box>
              )}
              {/* TAB 2 Contents */}
              {currentIdTokenTabIndex === 1 && (
                <Box sx={{ p: 3 }}>
                  <BrokenWord>
                    {auth.user?.id_token}
                  </BrokenWord>
                </Box>
              )}
            </Box>
          </CardContent>
        </Collapse>
      </Card>
    </>
  );
};

export default TokenTabs;
