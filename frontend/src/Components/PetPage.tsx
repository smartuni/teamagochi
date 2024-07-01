import { useEffect, useState } from "react";
import petImage from "../assets/pet_frog.png";
import { ProgressBar } from "react-progressbar-fancy";
import type { paths, components } from "../../src/web-backend-api";
import { useAuth } from "react-oidc-context";
import CreatePetModal from "./CreatePetModal";
import createClient, { type Middleware } from "openapi-fetch";
// https://github.com/RavinRau/react-progressbar-fancy?tab=readme-ov-file

type PetDTO = components["schemas"]["PetDTO"];

const PetPage = () => {
  const auth = useAuth();
  const authMiddleware: Middleware = {
    async onRequest({ request }) {
      // add Authorization header to every request
      request.headers.set("Authorization", `Bearer ${auth.user?.access_token}`);
      return request;
    },
  };

  const client = createClient<paths>({
    baseUrl: "http://localhost:4000/backend",
  });
  client.use(authMiddleware);

  const [petData, setPetData] = useState({} as PetDTO);
  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const { data, error } = await client.GET("/api/v1/devices/self", {});
    if (data && data[0].petId != undefined) {
      const { data: data2, error } = await client.GET(
        "/api/v1/pets/self/{petId}",
        {
          params: {
            path: { petId: +data[0].petId },
          },
        }
      );
      console.log(data2);
      if (data2) {
        setPetData(data2);
      }
    }
  };

  return (
    <div className="container-fluid row p-5">
      <div className="col-6">
        {petData != null ? (
          <img className="img-fluid" src={petImage} alt="pet name" />
        ) : (
          <div>
            <CreatePetModal />
          </div>
        )}
      </div>
      <div className="col-6">
        <div className="h2 px-3 py-4">{petData.name}</div>
        <div>
          <div className="h5 px-3">HP</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"red"}
            score={petData.state?.health == null ? 0 : petData.state.health}
          />
        </div>
        <div>
          <div className="h5 px-3">Well-being</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"purple"}
            score={
              petData.state?.wellbeing == null ? 0 : petData.state.wellbeing
            }
          />
        </div>
        <div>
          <div className="h5 px-3">XP</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={petData.state?.xp == null ? 0 : petData.state.xp}
          />
        </div>
        <div>
          <div className="h5 px-3">Cleanliness</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={
              petData.state?.cleanliness == null ? 0 : petData.state.cleanliness
            }
          />
        </div>
        <div>
          <div className="h5 px-3">Fun</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={petData.state?.fun == null ? 0 : petData.state.fun}
          />
        </div>
        <div>
          <div className="h5 px-3">Hunger</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={petData.state?.hunger == null ? 0 : petData.state.hunger}
          />
        </div>
        <div>
          <div className="h5 px-3">Happiness</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={
              petData.state?.happiness == null ? 0 : petData.state.happiness
            }
          />
        </div>
      </div>
    </div>
  );
};

export default PetPage;
