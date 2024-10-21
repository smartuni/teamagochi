import { useEffect, useState } from "react";
import petImage from "../Misc/frog.png";
import petImageGray from "../Misc/frog-gray.png";
import { useDeviceApi } from "../lib/api/useDeviceApi";
import { Pet, usePetApi } from "../lib/api/usePetApi";
import PetDetails from "./PetDetails";
import Loading from "./Loading";
import { Link } from "react-router-dom";

// https://github.com/RavinRau/react-progressbar-fancy?tab=readme-ov-file

const PetPage = () => {
  const [loading, setLoading] = useState(true);
  const [pet, setPet] = useState<Pet | undefined>();
  const deviceApi = useDeviceApi();
  const petApi = usePetApi();

  useEffect(() => {
    if (!deviceApi || !petApi) return;

    const fetchData = async () => {
      const devices = await deviceApi.getDevices();
      const defaultDevice = devices[0];

      if (defaultDevice && defaultDevice.petId) {
        const pet = await petApi.getPetById(defaultDevice.petId);
        setPet(pet);
      }

      setLoading(false);
    };

    const interval = setInterval(() => {
      fetchData()
        .catch(err => console.error(err));
    }, 1500); // Reload ever 1.5s

    return () => clearInterval(interval);
  }, [deviceApi, petApi]);

  if (loading) {
    return (
      <div className="align-content-center flex-grow-1 align-self-center">
        <Loading />
      </div>
    )
  }

  return (
    <div className="container-lg">
      <div className="d-flex flex-column p-4">
        {pet != undefined ? (
          <PetDetails pet={pet} petImageSrc={petImage} deadPetImageSrc={petImageGray} />
        ) : (
          <div className="d-flex flex-row justify-content-center align-items-center w-full p-5">
            <div className="col-2">
              <img
                className="img-fluid"
                src={petImageGray}
                alt="Image showing a gray pet"
                style={{opacity: "0.5"}}
              />
            </div>
            <div>
              <h4>Your journey is about to begin!</h4>
              <Link to="/Settings">Create a pet or connect your device.</Link>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default PetPage;
