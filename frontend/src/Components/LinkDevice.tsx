import { useState } from 'react';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

function LinkDevice() {
    const [open, setOpen] = useState(false);

    return (
        <>
            <button className="btn btn-primary" onClick={() => setOpen(true)}>Link Tomogachi</button>
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
                    <form className="row g-0">
                        <figure className='text-center'>
                            <p className='lead'>
                                <strong>Enter your device ID below:</strong>
                            </p>
                        </figure>
                        <div className="input-group col mb-1">
                            <span className="input-group-text">#</span>
                            <label htmlFor="inputLink" className="visually-hidden">Device ID</label>
                            <input type="text" className="form-control" id="inputDeviceId" placeholder="Device ID" />
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
