import { useEffect, useRef, useState } from "react";
import profile_pic1 from '../Misc/defaultUserPic.png';
import 'bootstrap/dist/css/bootstrap.min.css';
import LinkDevice from "./SettingsLinkDevice";
import { Pet, usePetApi } from "../lib/api/usePetApi";
import { Device, useDeviceApi } from "../lib/api/useDeviceApi";
import SettingsDeviceList from "./SettingsDeviceList";
import SettingsPetList from "./SettingsPetList";
import Loading from "./Loading";
import CreatePetModal, { OpenState } from "./CreatePetModal";

interface SettingsProps {
    username: string;
}

function Settings({ username }: SettingsProps) {
    const [pets, setPets] = useState<Pet[]>([]);
    const [devices, setDevices] = useState<Device[]>([]);
    const [items, setItems] = useState(['Pet 1', 'Pet 2', 'Pet 3']);
    const [showLinkDevice, setShowLinkDevice] = useState<boolean>(true);
    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [itemToRemove, setItemToRemove] = useState<number | null>(null);

    const [loading, setLoading] = useState(true);
    const createPetModalOpenState = useRef<OpenState>(null);
    const petApi = usePetApi();
    const deviceApi = useDeviceApi();

    useEffect(() => {
        if ( !petApi || ! deviceApi ) return;

        const fetchData = async () => {
          const pets = await petApi.getPets();
          setPets(pets);

          const devices = await deviceApi.getDevices();
          setDevices(devices);

          setLoading(false);
        }

        fetchData();
    }, [petApi, deviceApi]);

    const handleRemoveDevice = async (deviceId: number) => {
        if ( ! deviceApi ) return;

        await deviceApi.deleteDevice(deviceId);

        const updated = devices.filter(device => device.id != deviceId);
        setDevices(updated);
    };

    const handleOpenCreatePetModal = async (open: boolean) => {
      if ( ! petApi ) return;

      createPetModalOpenState.current?.setOpen(open)
    }

    const handleCloseCreatePetModal = async () => {
      if ( ! petApi ) return;

      const pets = await petApi.getPets();
      setPets(pets);
    }

    const handleRemovePet = async (petId: number) => {
        if ( ! petApi ) return;

        await petApi.removePet(petId);

        const updated = pets.filter(pet => pet.id != petId);
        setPets(updated);
    }

    const handleSelectPet = async (petId: number) => {
        if ( ! deviceApi ||  ! devices[0] ) return;

        const currentDevice = devices[0];
        currentDevice.petId = petId;

        await deviceApi.updateDevice(currentDevice);

        const updatedDevices = devices.filter(device => device.id !== currentDevice.id);
        setDevices([...updatedDevices, currentDevice]);
    }

    const confirmRemoveItem = () => {
        if (itemToRemove !== null) {
            setItems(items.filter((_, i) => i !== itemToRemove));
            if (selectedIndex === itemToRemove) {
                setSelectedIndex(null);
            }
            setItemToRemove(null);
        }
        setShowModal(false);
    };

    const cancelRemoveItem = () => {
        setShowModal(false);
        setItemToRemove(null);
    };

    console.log({
        devices: devices,
        pets: pets,
    })

    if (loading) {
      return (
        <div className="align-content-center flex-grow-1 align-self-center">
          <Loading />
        </div>
      )
    }

    return (
        <div className='d-flex justify-content-center pt-4 pb-2 mb-12'>
            <div className="card align-items-center text-bg-light" style={{ width: "500px" }}>
                <img src={profile_pic1} className="card-img-top py-3" alt="profile picture" style={{ width: "50%", height: "auto" }} />
                <h2 className="card-title text-center"><kbd className='bg-success text-white'>PROFILE</kbd></h2>
                <div className="card-body" style={{ width: "100%" }}>
                    <div className="input-group mb-4" style={{ height: "60px" }}>
                        <div className="input-group-prepend">
                            <div className="input-group-text" style={{ fontSize: "1.5rem", height: "60px", display: 'flex', alignItems: 'center' }}>@</div>
                        </div>
                        <input type="text" className="form-control text-black" placeholder={username} disabled style={{ fontSize: "1.5rem", height: "60px" }}></input>
                    </div>
                    <div className='pt-4 mb-4' style={{ width: "100%" }}>
                        <h3>Device</h3>
                        {devices.length == 0 ?
                            (
                                <button
                                    type="button"
                                    className="btn btn-success btn-block w-100 my-2"
                                    style={{ fontSize: "1.5rem", height: "60px" }}
                                    onClick={() => setShowLinkDevice(false)}
                                >
                                    Link Device
                                </button>
                            ) : (
                                <SettingsDeviceList
                                    devices={devices}
                                    removeCallback={handleRemoveDevice}
                                />
                            )
                        }
                    </div>
                    <div className='pt-4' style={{ width: "100%" }}>
                        <h3>Pets</h3>
                        <SettingsPetList
                            pets={pets}
                            currentDevice={devices[0]}
                            createCallback={handleOpenCreatePetModal}
                            selectCallback={handleSelectPet}
                            removeCallback={handleRemovePet}
                        />
                    </div>
                </div>
            </div>

            {showLinkDevice === false && (
                <LinkDevice
                    onClose={() => setShowLinkDevice(true)}
                    onSubmit={(device) => setDevices([...devices, device])}
                />
            )}

            <CreatePetModal onClose={handleCloseCreatePetModal} ref={createPetModalOpenState} />

            {showModal && (
                <div className="modal show d-block" tabIndex={-1} role="dialog">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Confirm Removal</h5>
                            </div>
                            <div className="modal-body">
                                <p>Are you sure you want to remove this pet?</p>
                            </div>
                            <div className="modal-footer">
                                <button
                                    type="button"
                                    className="btn btn-secondary"
                                    onClick={cancelRemoveItem}
                                >No</button>
                                <button
                                    type="button"
                                    className="btn btn-danger"
                                    onClick={confirmRemoveItem}
                                >Yes</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Settings;
