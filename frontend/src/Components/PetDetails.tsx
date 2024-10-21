import { Pet } from "../lib/api/usePetApi";
import PetDetailsProgressBar from "./PetDetailsProgressBar";

const PetDetails = (props: { pet: Pet; petImageSrc: string; deadPetImageSrc: string }) => {
  const { pet, petImageSrc, deadPetImageSrc } = props;

  if (!pet.state) return;
  const petState = pet.state;
  const petIsAlive = petState.health! > 0;
  const petImage = petIsAlive ? petImageSrc : deadPetImageSrc;

  return (
    <div className="d-flex flex-row">
      <div className="col-5">
        <img
          className="img-fluid"
          src={petImage}
          alt="Image showing the pet"
        />
      </div>
      <div className="col-6">
        <div className="h2 px-3 py-4">{pet.name}{petIsAlive || " died... 👻"}</div>
        <PetDetailsProgressBar
          labelName="Health"
          value={petState.health}
          color="green"
        />
        <PetDetailsProgressBar
          labelName="XP"
          value={petState.xp}
          color="purple"
        />
        <PetDetailsProgressBar
          labelName="Happiness"
          value={petState.happiness}
          color="red"
        />
        <PetDetailsProgressBar
          labelName="Wellbeing"
          value={petState.wellbeing}
          color="red"
        />
        <PetDetailsProgressBar
          labelName="Hunger"
          value={petState.hunger}
          color="red"
        />
        <PetDetailsProgressBar
          labelName="Cleanliness"
          value={petState.cleanliness}
          color="red"
        />
        <PetDetailsProgressBar
          labelName="Fun"
          value={petState.fun}
          color="red"
        />
      </div>
    </div>
  );
};

export default PetDetails;
