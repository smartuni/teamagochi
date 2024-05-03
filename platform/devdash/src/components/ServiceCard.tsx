import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import { Service } from '../types';
import Typography from '@mui/material/Typography';
import OpenInNewIcon from '@mui/icons-material/OpenInNew';
import Box from '@mui/material/Box';

interface ServiceCardProps {
  service: Service;
}

const ServiceCard: React.FC<ServiceCardProps> = (props) => {
  const { service } = props;

  return (
    <Card sx={{height: '100%'}}>
      <CardContent>
        <Typography variant="h5" component="div">
          {service.name}
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
          {service.description}
        </Typography>
        {service.body && <Typography variant="body2" dangerouslySetInnerHTML={{__html: service.body}}></Typography>}
      </CardContent>
      <CardActions>
       {service.links.map((service) => (
          <Button key={service.url} variant="outlined" size="small" target="_blank" href={service.url}>
            {service.title}
            <Box component='div' display='flex' marginLeft='.3em'>
              <OpenInNewIcon sx={{ fontSize: 16 }} />
            </Box>
          </Button>
        ))}
      </CardActions>
    </Card>
  );
};

export default ServiceCard;