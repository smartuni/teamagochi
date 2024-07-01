import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import backgroundImage from "../Misc/compute-ea4c57a4.png";
import Navbar from "./Navbar/Navbar";
import { Link } from "react-router-dom";
import { useAuth } from "react-oidc-context";

const LandingPage = () => {
  const auth = useAuth();
  React.useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "auto";
    };
  }, []);

  return (
    <div>
      <div
        className="vh-100 vw-100 d-flex justify-content-center align-items-center"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        }}
      >
        <div className="text-white p-4 rounded text-center">
          <main role="main">
            <h1
              className="font-weight-bold"
              style={{
                fontSize: "4rem",
                textShadow: "2px 2px 4px rgba(0, 0, 0, 0.8)",
              }}
            >
              TEAMAGOCHI
            </h1>
            <p
              className="lead pt-4 pb-2"
              style={{
                fontSize: "1.75rem",
                textShadow: "1px 1px 2px rgba(0, 0, 0, 0.8)",
              }}
            >
              Teamagochi is more than just a toy, it's a pet.<br></br> Customize
              it the way you want it and complete fun challenges to earn
              rewards.<br></br>Take care of your pet and you will make it happy
              and thankful!
            </p>
            {/* <button type='button' className='btn btn-lg btn-success py-3' style={{ fontSize: '2rem' }}>Get started!</button> */}
            <a
              className="btn btn-lg btn-success py-3"
              href="#"
              onClick={() => auth.signinRedirect()}
            >
              Get started!
            </a>
          </main>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
