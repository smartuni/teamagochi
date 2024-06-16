import React from "react";
import petImage from "../assets/pet_frog.png";
import { ProgressBar } from "react-progressbar-fancy";

// https://github.com/RavinRau/react-progressbar-fancy?tab=readme-ov-file

const Pet = () => {
  return (
    <div className="container-fluid row p-5">
      <div className="col-6">
        <img className="img-fluid" src={petImage} alt="pet name" />
      </div>
      <div className="col-6">
        <div className="h2 px-3 py-4">( Pet name )</div>
        <div>
          <div className="h5 px-3">HP</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"red"}
            score={100}
          />
        </div>
        <div>
          <div className="h5 px-3">Hapiness</div>
          <ProgressBar
            className="px-1 pb-3"
            hideText={true}
            progressColor={"purple"}
            score={50}
          />
        </div>
        <div>
          <div className="h5 px-3">XP</div>
          <ProgressBar
            className="px-1"
            hideText={true}
            progressColor={"green"}
            score={80}
          />
        </div>
      </div>
    </div>
  );
};

export default Pet;
