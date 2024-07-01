import { Pet } from "../lib/api/usePetApi";
import PetDetailsProgressBar from "./PetDetailsProgressBar";

const PetDetails = (props: {pet: Pet, petImageSrc: string}) => {
  const {pet, petImageSrc} = props;

  if (!pet.state) return;
  const petState = pet.state;

  return (
    <>
      <div className="col-6">
        <img className="img-fluid" src={petImageSrc} alt="Image showing the pet" />
      </div>
      <div className="col-6">
        <div className="h2 px-3 py-4">{pet.name}</div>
        <PetDetailsProgressBar value={petState.xp} color="green" />
        <PetDetailsProgressBar value={petState.happiness} color="purple" />
        <PetDetailsProgressBar value={petState.health} color="red" />
      </div>
    </>
  );
}

export default PetDetails;
