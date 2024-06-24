import { useEffect, useState } from "react";
import petImage from "../assets/pet_frog.png";
import { ProgressBar } from "react-progressbar-fancy";
import type { paths } from "../../src/web-backend-api";
import { useAuth } from "react-oidc-context";
import CreatePetModal from "./CreatePetModal";

// https://github.com/RavinRau/react-progressbar-fancy?tab=readme-ov-file

const PetPage = (client) => {
  const [petData, setPetData] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const { response, error } = await client.GET("/api/v1/devices/self", {});
    if (error != null) {
      setPetData(await client.GET(`/api/v1/pets/self/${response.petId}`));
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
        <div className="h2 px-3 py-4">( Pet name )</div>
        <div>
          <div className="h5 px-3">HP</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"red"}
            score={100}
          />
        </div>
        <div>
          <div className="h5 px-3">Happiness</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"purple"}
            score={50}
          />
        </div>
        <div>
          <div className="h5 px-3">XP</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={80}
          />
        </div>
      </div>
    </div>
  );
};

export default PetPage;
