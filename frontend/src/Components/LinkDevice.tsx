import { useState } from 'react'

function LinkDevice(){
    return (
        <>
        <div style={{ padding: '20px', border: '2px solid grey', borderRadius: '10px' }}>
        <form className="row g-0">
            <figure className='text-center'>
                <p className='lead'>
                  <strong>  Enter your device ID below: </strong></p>
            </figure>
            <div className="input-group col mb-1">
                <span className="input-group-text">#</span>
                <label htmlFor="inputLink" className="visually-hidden">Device ID</label>
                <input type="text" className="form-control" id="inputDeviceId" placeholder="Device ID"></input>
            </div>
            <button type='submit' className='btn btn-success mt-2 mb-2'>
                Link device
            </button>
        </form>
        </div>
        </>
      )
}

export default LinkDevice;
