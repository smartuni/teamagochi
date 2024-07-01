import { useEffect, useState } from "react";
import petImage from "../Misc/frog.png";
import CreatePetModal from "./CreatePetModal";
import { Device, useDeviceApi } from "../lib/api/useDeviceApi";
import { Pet, usePetApi } from "../lib/api/usePetApi";
import PetDetails from "./PetDetails";

// https://github.com/RavinRau/react-progressbar-fancy?tab=readme-ov-file

const PetPage = () => {
  const [devices, setDevices] = useState<Device[]>([]);
  const [pet, setPet] = useState<Pet | undefined>();
  const deviceApi = useDeviceApi();
  const petApi = usePetApi();

  useEffect(() => {
    if (!deviceApi || !petApi) return;

    const fetchData = async () => {
      const devices = await deviceApi.getDevices();
      const defaultDevice = devices[0];

      setDevices(devices);

      if (defaultDevice && defaultDevice.petId) {
        const pet = await petApi.getPetById(defaultDevice.petId);
        setPet(pet);
      }
    };

    fetchData();
  }, [deviceApi, petApi]);

  console.log("loaded-devices", devices);

  return (
    <div className="container-fluid row p-5">
      {pet != undefined ? (
        <PetDetails pet={pet} petImageSrc={petImage} />
      ) : (
        <CreatePetModal />
      )}
    </div>
  );
};

export default PetPage;
