import React from "react";
import "./pet.css";
import petImage from "../../assets/pet_frog.png";
import petImage2 from "../../assets/petyboi_carti.png";
import { ProgressBar } from "react-progressbar-fancy";

const Pet = () => {
  return (
    <div className="pet_container">
      <div className="pet_image">
        <img src={petImage} alt="pet name" />
      </div>
      <div className="pet_status_container">
        <div className="pet_status_contents pet_status_name">(Pet name)</div>
        <div className="pet_status_contents pet_status_HP">
          <ProgressBar
            // className="space"
            label={"HP"}
            progressColor={"red"}
            score={25}
          />
        </div>
        <div className="pet_status_contents pet_status_Happiness">
          <ProgressBar
            className="space"
            label={"Happiness"}
            progressColor={"purple"}
            score={50}
          />
        </div>
        <div className="pet_status_contents pet_status_XP">
          <ProgressBar
            className="space"
            label={"XP"}
            progressColor={"green"}
            score={80}
          />
        </div>
      </div>
    </div>
  );
};

export default Pet;
