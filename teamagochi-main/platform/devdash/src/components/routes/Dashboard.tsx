import Grid from "@mui/material/Grid";
import { services } from "../../config";
import ServiceCard from "../ServiceCard";
import TokenTabs from "../TokenTabs";

const Dashboard: React.FC = () => {
  return (
    <>
      <h1>Dashboard</h1>
      <Grid container spacing={2} columns={{ xs: 4, sm: 12 }}>
        <Grid item xs={6}>
          <Grid container spacing={2} columns={2}>
            {services.map((service, index) => (
              <Grid item xs={2} sm={2} md={2} key={index}>            
                <ServiceCard service={service} />
              </Grid>
            ))}
          </Grid>
        </Grid>
        <Grid item xs={6}>
          <TokenTabs />
        </Grid>
      </Grid>
    </>
  )
};

export default Dashboard;
