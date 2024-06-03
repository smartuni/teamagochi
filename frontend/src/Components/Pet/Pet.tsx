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
        <div className="pet_status_name">(Pet name)</div>
        <div className="pet_status_HP">
          HP
          <ProgressBar
            // className="space"
            label={"My Progress Red"}
            progressColor={"red"}
            darkTheme
            score={25}
          />
        </div>
        <div className="pet_status_HP">
          Happiness
          <ProgressBar
            className="space"
            label={"My Progress Purple"}
            progressColor={"purple"}
            darkTheme
            score={50}
          />
        </div>
        <div className="pet_status_HP">
          XP
          <ProgressBar
            className="space"
            label={"My Progress Green"}
            progressColor={"green"}
            darkTheme
            score={80}
          />
        </div>
      </div>
    </div>
  );
};

export default Pet;
