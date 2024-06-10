import { SetStateAction, useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import profile_pic1 from '../Misc/8-bit-dog-nobg.png';

const CreatePetModal = () => {
    const [open, setOpen] = useState(false);
    const [petName, setPetName] = useState("");
    const [petType, setPetType] = useState("");

    const handleClose = () => {
        setPetName("");
        setPetType("");
        setOpen(false);
    };

    const handleInputPetName = (e: { target: { value: SetStateAction<string>; }; }) => {
        setPetName(e.target.value);
    };

    const handleInputPetType = (e: { target: { value: SetStateAction<string>; }; }) => {
        setPetType(e.target.value);
    }; 

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault(); // Prevent form from reloading the page
        if (petName.trim() === "" || petType === "") {
            return;
        }
        // TODO: Save the device ID or perform any action with the input value
        console.log("Pet Name:", petName," Pet Type:", petType);
        setOpen(false); // Close the popup after submission
    };

  return (
        <>
            <div className='container-fluid d-flex justify-content-center align-items-center' style={{ height: '100vh' }}>
                <button className="btn btn-success" onClick={() => setOpen(true)}>Create Tomogachi</button>
            </div>
            <Popup
                open={open}
                onClose={handleClose}
                overlayStyle={{ background: 'rgba(0, 0, 0, 0.5)' }}
                contentStyle={{ borderRadius: '10px', border: '2px solid grey', width: 'fit-content' }}
            > 
                <div style={{ padding: '20px', position: 'relative' }}>
                <button
                        type="button"
                        className="btn-close"
                        aria-label="Close"
                        style={{ position: 'absolute', top: '10px', right: '10px' }}
                        onClick={handleClose}
                ></button>

                    <form className="row g-0 needs-validation justify-content-center" onSubmit={handleSubmit}>
                        <figure className='text-center'>
                            <p className='lead'>
                                <strong>Enter your Pet Name below and choose it's Type:</strong>
                            </p>
                        </figure>

                        {petType === "Land" &&
                            <div className="card align-items-center m-3" style={{ width: "30%" }}>
                                <img src={profile_pic1} className="card-img-top py-3" alt="profile picture" style={{ width: "100%", height: "auto" }} />
                            </div>
                        }

                        <div className="input-group  mb-1">
                            <span className="input-group-text">@</span>
                            <label htmlFor="inputLink" className="visually-hidden">Pet Name</label>
                            <input type="text" className="form-control" id="inputPetName" 
                            placeholder="Pet Name" required value={petName} onChange={handleInputPetName}/>
                            <div className='invalid-feedback'>ok</div>
                        </div>
                        
                        {/*<div className="container-fluid d-flex justify-content-center">
                            <button className="btn btn-dark px-5 m-2" onClick={() => setPetType("Land")}>Land</button>
                            <button className="btn btn-secondary px-5 m-2" onClick={() => setPetType("Air")}>Air</button>
                            <button className="btn btn-primary px-5 m-2" onClick={() => setPetType("Water")}>Water</button>
                        </div>*/}

                        {/*<div className="btn-group btn-group-toggle" data-toggle="buttons">
                           <label className="btn btn-dark active">
                                <input type="radio" name="Land" id="Land" checked>Land</input>
                            </label>
                            <label className="btn btn-secondary">
                                <input type="radio" name="Air" id="Air">Air</input>
                            </label>
                            <label className="btn btn-primary">
                                <input type="radio" name="Water" id="Water">Water</input>
                            </label>
                        </div> */}

                        <div className="select-group mb-1 py-2">
                            <select className="form-select" aria-label="Choose Pet Type" onChange={handleInputPetType}>
                                <option value="">Choose Pet Type</option>
                                <option value="Land">Land</option>
                                <option value="Air">Air</option>
                                <option value="Water">Water</option>
                            </select>
                            <div className="invalid-feedback">ok</div>
                        </div>

                        <button type='submit' className='btn btn-success mt-2 mb-2'>
                            Create
                        </button>
                    </form>
                </div>
            </Popup>
        </>
  );
};

export default CreatePetModal;
