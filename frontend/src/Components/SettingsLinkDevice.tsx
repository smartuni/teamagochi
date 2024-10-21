import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import { Device, useDeviceApi } from "../lib/api/useDeviceApi";
import 'reactjs-popup/dist/index.css';

interface LinkDeviceProps {
    onClose: () => void; // onClose prop should be a function that closes the popup
    onSubmit: (device: Device) => void;
}

function LinkDevice({ onClose, onSubmit }: LinkDeviceProps) {
    const [deviceKey, setDeviceKey] = useState("");
    const [deviceName, setDeviceName] = useState("");
    const [error, setError] = useState("");
    const deviceApi = useDeviceApi();

    const handleInputChangeKey = (e: React.ChangeEvent<HTMLInputElement>) => {
        setDeviceKey(e.target.value);
    };

    const handleInputChangeDName = (e: React.ChangeEvent<HTMLInputElement>) => {
        setDeviceName(e.target.value);
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // Prevent form from reloading the page

        const registrationCode = deviceKey.trim();
        const name = deviceName.trim();

        if (registrationCode === "" || name === "") {
            // Invalid form data
            setError("An error occured, please try again!")
            return;
        }

        if (deviceApi === undefined) {
            // Initialization error
            setError("An error occured, please try again!")
            return;
        }

        let newDevice;
        try {
            newDevice = await deviceApi.registerDevice(deviceKey, deviceName, "FROG");
           } catch (error) {
            // Request error
            setError("An error occured, please try again!")
            return;
        }

        if (newDevice === undefined) {
            // Invalid registration code
            setError("An error occured, please try again!")
            return;
        }

        onSubmit(newDevice);

        // Close the popup after submission
        onClose();

        console.log("Added device", newDevice);
        console.log(error)
    };

    return (
        <Popup
            open={true} // Always open initially based on your original logic
            onClose={onClose} // Pass the onClose function directly to Popup
            overlayStyle={{ background: 'rgba(0, 0, 0, 0.5)' }}
            contentStyle={{ borderRadius: '10px', border: '2px solid grey', width: 'fit-content' }}
        >
            <div style={{ padding: '20px', position: 'relative' }}>
                <button
                    type="button"
                    className="btn-close"
                    aria-label="Close"
                    style={{ position: 'absolute', top: '10px', right: '10px' }}
                    onClick={onClose} // Also close on clicking the close button
                />

                <form className="row g-0 needs-validation" onSubmit={handleSubmit}>
                    <figure className='text-center'>
                        <p className='lead'>
                            <strong>Register device</strong>
                        </p>
                    </figure>

                    {error !== "" &&
                        <div className="alert alert-danger" role="alert">
                            {error}
                        </div>
                    }

                    <div className="input-group mb-2">
                        <span className="input-group-text">#</span>
                        <label htmlFor="inputLink" className="visually-hidden">Device Registration Code</label>
                        <input
                            type="text"
                            className="form-control"
                            id="inputDeviceId"
                            placeholder="Device Registration Code"
                            required
                            value={deviceKey}
                            onChange={handleInputChangeKey}
                        />
                        <div className='invalid-feedback'>ok</div>
                    </div>

                    <div className="input-group my-2">
                        <span className="input-group-text">@</span>
                        <label htmlFor="inputLink" className="visually-hidden">Device Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="inputDeviceName"
                            placeholder="Device Name"
                            required
                            value={deviceName}
                            onChange={handleInputChangeDName}
                        />
                        <div className='invalid-feedback'>ok</div>
                    </div>

                    <div className="input-group my-2">
                        <span className="input-group-text">🐸</span>
                        <label htmlFor="inputLink" className="visually-hidden">Device Type</label>
                        <select
                            className="form-select"
                            id="inputDeviceType"
                            required
                            disabled
                            value="FROG"
                        >
                            <option>Frog</option>
                        </select>
                        <div className='invalid-feedback'>ok</div>
                    </div>

                    <button type='submit' className='btn btn-success mt-2 mb-2'>
                        Link device
                    </button>
                </form>
            </div>
        </Popup>
    );
}

export default LinkDevice;
