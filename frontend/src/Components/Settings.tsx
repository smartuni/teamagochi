import React, { useState } from 'react';
import profile_pic1 from '../Misc/8-bit-dog-nobg.png';
import 'bootstrap/dist/css/bootstrap.min.css';
import LinkDevice from "../Components/LinkDevice";

interface SettingsProps {
    username: string;
}

function Settings(
    { username }: SettingsProps
) {
    const [items, setItems] = useState(['Device 1', 'Device 2', 'Device 3']);
    const [showLinkDevice, setShowLinkDevice] = useState<boolean>(true);
    const [selectedIndex, setSelectedIndex] = useState<number | null>(null);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [itemToRemove, setItemToRemove] = useState<number | null>(null);

    const handleRemoveItem = (index: number) => {
        setShowModal(true);
        setItemToRemove(index);
    };

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

    const resetItem = (index: number) => {
        setItems(items.map((item, i) => (i === index ? `Item ${index + 1}` : item)));
    };

    const selectItem = (index: number) => {
        setSelectedIndex(index);
    };

    return (
        <div className='d-flex justify-content-center pt-4 pb-2'>
            <div className="card align-items-center text-bg-light" style={{ width: "500px" }}>
                <img src={profile_pic1} className="card-img-top py-3" alt="profile picture" style={{ width: "100%", height: "auto" }} />
                <h2 className="card-title text-center"><kbd className='bg-success text-white'>PROFILE</kbd></h2>
                <div className="card-body" style={{ width: "100%" }}>
                    <div className="input-group mb-4" style={{ height: "60px" }}>
                        <div className="input-group-prepend">
                            <div className="input-group-text" style={{ fontSize: "1.5rem", height: "60px", display: 'flex', alignItems: 'center' }}>@</div>
                        </div>
                        <input type="text" className="form-control text-black" placeholder={username} disabled style={{ fontSize: "1.5rem", height: "60px" }}></input>
                    </div>
                    <div className='d-flex justify-content-center pt-2'>
                        <button type="button" className="btn btn-success btn-block w-100" style={{ fontSize: "1.5rem", height: "60px" }} onClick={() => setShowLinkDevice(false)} >Link Device</button>
                    </div>
                    <div className='pt-4' style={{ width: "100%" }}>
                        <ul className="list-group" style={{ fontSize: "1.2rem" }}>
                            {items.map((item, index) => (
                                <li key={index} className="list-group-item d-flex justify-content-between align-items-center py-3">
                                    {item}
                                    <div>
                                        <button
                                            className="btn btn-primary btn-lg mx-1"
                                            onClick={() => selectItem(index)}
                                            disabled={selectedIndex === index}
                                        >
                                            {selectedIndex === index ? 'Selected' : 'Select'}
                                        </button>
                                        <button className="btn btn-danger btn-lg mx-1" onClick={() => handleRemoveItem(index)}>Remove</button>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
            {showLinkDevice === false && (
                <LinkDevice onClose={() => setShowLinkDevice(true)} />
            )}

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
                                <button type="button" className="btn btn-secondary" onClick={cancelRemoveItem}>No</button>
                                <button type="button" className="btn btn-danger" onClick={confirmRemoveItem}>Yes</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Settings;
