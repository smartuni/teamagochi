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

const BrokenWord = styled.p`
  word-break: break-all;
`;

const TokenTabs: React.FC = () => {
  const auth = useAuth();
  const [currentAccessTokenTabIndex, setCurrentAccessTokenTabIndex] = useState(1);
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
          This panel displays the "Access Token" and the "Identity Token" after login.
        </CardContent>
      </Card>
    )
  }

  return (
    <>
      <Card sx={{marginBottom: '2em'}}>
        <Box component="div" sx={{ backgroundColor: '#efefef' }}>
          <CardContent>
            <Typography variant="h5" component="div">
              Access Token
            </Typography>
          </CardContent>
        </Box>
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
      </Card>
      <Card sx={{marginBottom: '2em'}}>
        <Box component="div" sx={{ backgroundColor: '#efefef' }}>
          <CardContent>
            <Typography variant="h5" component="div">
              ID Token
            </Typography>
          </CardContent>
        </Box>
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
      </Card>
    </>
  );
};

export default TokenTabs;
