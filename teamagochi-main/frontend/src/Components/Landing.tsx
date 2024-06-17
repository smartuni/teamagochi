// import React, { useState } from "react";

// const Landing = () =>{
    
//     return (
//         <div>landing nn  testing</div>
//     )
// }

// export default Landing

import React from 'react';
import './Landing.css';
import sample from './logos/sample_video.mp4';

const Landing = () => {
  return (
    <div className="homepage">

      <main className="main-content">
        <section className="hero">
          <h1>Welcome to Teamagachi</h1>
          <p>Revolutionize your team management with virtual pets!</p>
          <button className="cta-button">Get Started</button>
        </section>
        <div className='flex justify-center'>

        <section className="about" id="about">
          <h2 className='text-blue-300'>About Teamagachi</h2>
          <p>
            Teamagachi is an innovative app designed to improve team dynamics
            and productivity by integrating virtual pets into your team
            management process. Keep your team engaged and motivated while
            managing tasks efficiently.
          </p>
        </section>
        <video className='videoTag' autoPlay loop muted height="100%" id="background_video">
          <source src={sample} type='video/mp4' />
        </video>
        </div>
        <section className="features" id="features">
          <h2>Features</h2>
          <ul>
            <li>Task Management</li>
            <li>Virtual Pet Rewards</li>
            <li>Team Collaboration</li>
            <li>Progress Tracking</li>
            <li>Customizable Avatars</li>
          </ul>
        </section>
        <section className="contact" id="contact">
          <h2>Contact Us</h2>
          <p>Have questions? Reach out to us at <a href="mailto:support@teamagachi.com">support@teamagachi.com</a></p>
        </section>
      </main>
      {/* <footer className="footer">
        <p>&copy; 2024 Teamagachi. All rights reserved.</p>
      </footer> */}
    </div>
  );
};

export default Landing;
