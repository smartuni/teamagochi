import { useState } from 'react';
import profile_pic1 from '../Misc/8-bit-dog.png';
function Settings(){
return (
   
    <div className='d-flex justify-content-center pt-4'>
    <div className="card align-items-center text-bg-light">
        <img src={profile_pic1} className="card-img-top py-3" alt="profile picture" style={{ width: "200px", height: "200px" }} />
            <h5 className="card-title text-center"><kbd className='bg-success text-white'>Profile</kbd></h5>
                <div className="card-body">                     
                    <div className="input-group mb-2 mr-sm-2">
                        <div className="input-group-prepend">
                            <div className="input-group-text">@</div>
                        </div>
                        <input type="text" className="form-control" placeholder="Pedro" disabled></input>
                    </div>
                    <div className='d-flex justify-content-center pt-2'>
                        <button type="button" className="btn btn-outline-danger btn-block w-100" >Logout</button>
                    </div>
                </div>
            
            </div>
    </div>



);
}
export default Settings;
