import { SetStateAction, useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

function LinkDevice() {
    const [open, setOpen] = useState(false);
    const [deviceKey, setDeviceKey] = useState("");
    const [deviceName, setDeviceName] = useState("");

    const handleInputChangeKey = (e: { target: { value: SetStateAction<string>; }; }) => {
        setDeviceKey(e.target.value);
    };    

    const handleInputChangeDName = (e: {target: { value: SetStateAction<string>; }; }) => {
        setDeviceName(e.target.value);
    }

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault(); // Prevent form from reloading the page
        if (deviceKey.trim() === "" || deviceName.trim() === "") {
            return;
        }
        // TODO: Save the device ID or perform any action with the input value
        console.log("Device ID:", deviceKey," Device Name:", deviceName);
        setOpen(false); // Close the popup after submission
    };

    return (
        <>
            <div className='container-fluid d-flex justify-content-center align-items-center' style={{ height: '100vh' }}>
                <button className="btn btn-primary" onClick={() => setOpen(true)}>Link Tomogachi</button>
            </div>
            <Popup
                open={open}
                onClose={() => setOpen(false)}
                overlayStyle={{ background: 'rgba(0, 0, 0, 0.5)' }}
                contentStyle={{ borderRadius: '10px', border: '2px solid grey', width: 'fit-content' }}
            > 
                <div style={{ padding: '20px', position: 'relative' }}>
                    <button
                        type="button"
                        className="btn-close"
                        aria-label="Close"
                        style={{ position: 'absolute', top: '10px', right: '10px' }}
                        onClick={() => setOpen(false)}
                    ></button>
                    <form className="row g-0 needs-validation" onSubmit={handleSubmit}>
                        <figure className='text-center'>
                            <p className='lead'>
                                <strong>Enter your device Key Registration and Name below:</strong>
                            </p>
                        </figure>

                        <div className="input-group  mb-1">
                            <span className="input-group-text">#</span>
                            <label htmlFor="inputLink" className="visually-hidden">Device Key Registration</label>
                            <input type="text" className="form-control" id="inputDeviceId" 
                            placeholder="Device Key Registration" required value={deviceKey} onChange={handleInputChangeKey}/>
                            <div className='invalid-feedback'>ok</div>
                        </div>

                        <div className="input-group mb-1 py-2">
                            <span className="input-group-text">@</span>
                            <label htmlFor="inputLink" className="visually-hidden">Device Name</label>
                            <input type="text" className="form-control" id="inputDeviceName" 
                            placeholder="Device Name" required value={deviceName} onChange={handleInputChangeDName}/>
                            <div className='invalid-feedback'>ok</div>
                        </div>

                        <button type='submit' className='btn btn-success mt-2 mb-2'>
                            Link device
                        </button>
                    </form>
                </div>
            </Popup>
        </>
    );
}

export default LinkDevice;
