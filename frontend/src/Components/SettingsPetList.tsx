import { useState } from "react";
import { Pet } from "../lib/api/usePetApi";
import { Device } from "../lib/api/useDeviceApi";
import { Link } from "react-router-dom";

interface Props {
    currentDevice: Device;
    pets: Pet[];
    selectCallback: (id: number) => void;
    removeCallback: (id: number) => void;
}

const SettingsPetList = (props: Props) => {
    const { currentDevice, pets, selectCallback, removeCallback } = props;
    const [selected, setSelected] = useState<number | undefined>(undefined);

    if (currentDevice !== undefined && currentDevice.petId !== selected) {
        setSelected(currentDevice.petId);
    }

    return (
    <>
    <ul className="list-group" style={{ fontSize: "1.2rem" }}>
        {pets.length === 0 && (
            <li className="list-group-item d-flex justify-content-between align-items-center py-3">
                No pets available.  <Link to="/PetPage">Create a pet</Link>
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
                        disabled={currentDevice === undefined || selected === pet.id}
                    >
                        {selected === pet.id ? 'Selected' : 'Select'}
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
    </ul>
    </>
    );
};

export default SettingsPetList;
