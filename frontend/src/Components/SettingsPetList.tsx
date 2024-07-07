import { MouseEvent, useState } from "react";
import { Pet } from "../lib/api/usePetApi";
import { Device } from "../lib/api/useDeviceApi";

interface Props {
    currentDevice: Device;
    pets: Pet[];
    createCallback: (open: boolean) => void;
    selectCallback: (id: number) => void;
    removeCallback: (id: number) => void;
}

const SettingsPetList = (props: Props) => {
    const {currentDevice, pets, createCallback, selectCallback, removeCallback} = props;
    const [selected, setSelected] = useState<number | undefined>(undefined);

    if (currentDevice !== undefined && currentDevice.petId !== selected) {
        setSelected(currentDevice.petId);
    }

    const handleCreateButton = (event: MouseEvent<HTMLButtonElement | HTMLAnchorElement>) => {
      event.preventDefault();
      createCallback(true);
    }

    const petIsSelectable = (): boolean => {
      //return currentDevice !== undefined && (selected === null || selected === undefined || selected !== petId);
      return currentDevice !== undefined;
    }

    return (
    <>
    <ul className="list-group" style={{ fontSize: "1.2rem" }}>
        {pets.length === 0 && (
            <li className="list-group-item d-flex justify-content-between align-items-center py-3">
                No pets available.
            </li>
        )} 
        {pets.map((pet) => (
            <li key={pet.id} className="list-group-item d-flex justify-content-between align-items-center py-3">
                {pet.name}
                <div>
                    <button
                        className="btn btn-primary btn-lg mx-1"
                        onClick={() => {
                            if (!pet.id) return;
                            selectCallback(pet.id);
                            setSelected(pet.id);
                        }}
                        disabled={!petIsSelectable()}
                    >
                        {selected === pet.id ? 'Return' : 'Select'}
                    </button>
                    <button
                        className="btn btn-danger btn-lg mx-1"
                        onClick={() => {
                            if (!pet.id) return;
                            removeCallback(pet.id);
                        }}
                        disabled={currentDevice?.petId !== undefined && pet.id == currentDevice.petId}
                    >
                        Remove
                    </button>
                </div>
            </li>
        ))}
        <li className="d-flex justify-content-between align-items-center p-3">
          <a href="#" onClick={handleCreateButton}>
            Create pet
          </a>
        </li>
    </ul>
    </>
    );
};

export default SettingsPetList;
