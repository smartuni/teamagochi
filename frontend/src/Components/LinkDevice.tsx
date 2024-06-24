import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';


interface LinkDeviceProps {
  onClose: () => void; // onClose prop should be a function that closes the popup
}

function LinkDevice({ onClose }: LinkDeviceProps) {
  const [deviceKey, setDeviceKey] = useState("");
  const [deviceName, setDeviceName] = useState("");

  const handleInputChangeKey = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDeviceKey(e.target.value);
  };    

  const handleInputChangeDName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDeviceName(e.target.value);
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // Prevent form from reloading the page
    if (deviceKey.trim() === "") {
      return;
    }
    // TODO: Save the device ID or perform any action with the input value
    (async () => {
        
      })();
    console.log("Device ID:", deviceKey, " Device Name:", deviceName);
    onClose(); // Close the popup after submission
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
            <input 
              type="text" 
              className="form-control" 
              id="inputDeviceId" 
              placeholder="Device Key Registration" 
              required 
              value={deviceKey} 
              onChange={handleInputChangeKey}
            />
            <div className='invalid-feedback'>ok</div>
          </div>

          <div className="input-group mb-1 py-2">
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

          <button type='submit' className='btn btn-success mt-2 mb-2'>
            Link device
          </button>
        </form>
      </div>
    </Popup>
  );
}

export default LinkDevice;
