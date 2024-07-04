import { useEffect, useState } from "react";
import petImage from "../Misc/frog.png";
import CreatePetModal from "./CreatePetModal";
import { useDeviceApi } from "../lib/api/useDeviceApi";
import { Pet, usePetApi } from "../lib/api/usePetApi";
import PetDetails from "./PetDetails";
import Loading from "./Loading";

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

    fetchData();
  }, [deviceApi, petApi]);

  if (loading) {
    return (
      <div className="d-flex justify-content-center min-h-100">
        <Loading />
      </div>
    )
  }

  return (
    <div className="container-lg">
      <div className="d-flex flex-row p-4">
        {pet != undefined ? (
          <PetDetails pet={pet} petImageSrc={petImage} />
        ) : (
          <CreatePetModal />
        )}
      </div>
    </div>
  );
};

export default PetPage;
