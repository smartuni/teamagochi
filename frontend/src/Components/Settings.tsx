import { useState } from 'react';
import profile_pic1 from '../Misc/8-bit-dog.png';
function Settings(){
return (
   
    <div className='d-flex justify-content-center pt-4'>
    <div className="card align-items-center">
        <img src={profile_pic1} className="card-img-top py-3" alt="profile picture" style={{ width: "200px", height: "200px" }} />
        <h5 className="card-title text-center">Profile</h5>
        <div className="card-body">
            This is some text within a card body.
        </div>
    </div>
    </div>



);
}
export default Settings;
