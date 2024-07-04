import { SetStateAction, forwardRef, useImperativeHandle, useState } from "react";
import Popup from "reactjs-popup";
import "reactjs-popup/dist/index.css";
import profile_land from "../Misc/8-bit-dog-nobg.png";
import profile_air from "../Misc/duck_8bit.png";
import profile_water from "../Misc/frog.png";
import { usePetApi } from "../lib/api/usePetApi";

export type OpenState = {
  setOpen: (visible: boolean) => void;
};

type Props = {
    onClose: () => Promise<void>;
};

const CreatePetModal = forwardRef<OpenState, Props>((props, ref) => {
    const {onClose} = props;
  
    const [open, setOpen] = useState(false);
    const [petName, setPetName] = useState("");
    const [petType, setPetType] = useState("Water");
    const petApi = usePetApi();

    useImperativeHandle(ref, () => ({
      setOpen(visible: boolean) {
        setOpen(visible);
      }
    }));

    const handleClose = () => {
        setPetName("");
        setPetType("Water");
        setOpen(false);
        onClose();
    };

    const handleInputPetName = (e: {
        target: { value: SetStateAction<string> };
    }) => {
        setPetName(e.target.value);
    };

    const handleInputPetType = (e: {
        target: { value: SetStateAction<string> };
    }) => {
        setPetType(e.target.value);
    };

    const handleSubmit = async (e: { preventDefault: () => void }) => {
        e.preventDefault(); // Prevent form from reloading the page

        if (petName.trim() === "" || petName.trim() === "DEAD" || petType === "") {
            return;
        }

        if (petApi === undefined) {
          // Initialization error
            return;
        }

        try {
            await petApi.createPet({
                name: petName,
                type: 1,
                state: undefined,
            })
        } catch (error) {
            console.log(error)
            return;
        }

        // Close the popup after submission
        setOpen(false);
    };

    return (
        <>
        <Popup
            open={open}
            onClose={handleClose}
            overlayStyle={{ background: "rgba(0, 0, 0, 0.5)" }}
            contentStyle={{
                borderRadius: "10px",
                border: "2px solid grey",
                width: "50%",
            }}
        >
            <div style={{ padding: "20px", position: "relative" }}>
                <button
                    type="button"
                    className="btn-close"
                    aria-label="Close"
                    style={{ position: "absolute", top: "10px", right: "10px" }}
                    onClick={handleClose}
                ></button>

                <form
                    className="row g-0 needs-validation justify-content-center"
                    onSubmit={handleSubmit}
                >
                    <figure className="text-center">
                        <p className="lead">
                            <strong>
                                Enter your pet's name below and choose it's type:
                                {/* <span>You cannot name your pet "DEAD"</span> */}
                            </strong>
                        </p>
                    </figure>

                    {petType === "Land" && (
                        <div
                            className="card align-items-center m-3"
                            style={{ width: "40%" }}
                        >
                            <img
                                src={profile_land}
                                className="card-img-top py-3"
                                style={{ width: "80%", height: "auto" }}
                            />
                        </div>
                    )}

                    {petType == "Air" && (
                        <div
                            className="card align-items-center m-3"
                            style={{ width: "40%" }}
                        >
                            <img
                                src={profile_air}
                                className="card-img-top py-3"
                                alt="profile picture"
                                style={{ width: "80%", height: "auto" }}
                            />
                        </div>
                    )}

                    {petType == "Water" && (
                        <div
                            className="card align-items-center m-3"
                            style={{ width: "40%" }}
                        >
                            <img
                                src={profile_water}
                                className="card-img-top py-3"
                                alt="profile picture"
                                style={{ width: "80%", height: "auto" }}
                            />
                        </div>
                    )}

                    <div className="input-group  mb-1">
                        <span className="input-group-text">@</span>
                        <label htmlFor="inputLink" className="visually-hidden">
                            Pet Name
                        </label>
                        <input
                            type="text"
                            className="form-control"
                            id="inputPetName"
                            placeholder="Pet Name"
                            required
                            value={petName}
                            onChange={handleInputPetName}
                        />
                        <div className="invalid-feedback">ok</div>
                    </div>

                    <div
                        className="btn-group btn-group-toggle pt-2"
                        data-toggle="buttons"
                    >
                        <input
                            type="radio"
                            className="btn-check"
                            name="options"
                            id="option1"
                            value="Land"
                            autoComplete="off"
                            checked={petType === "Land"}
                            onChange={handleInputPetType}
                            disabled
                        ></input>
                        <label className="btn btn-secondary" htmlFor="option1">
                            Land
                        </label>

                        <input
                            type="radio"
                            className="btn-check"
                            name="options"
                            id="option2"
                            value="Air"
                            autoComplete="off"
                            checked={petType === "Air"}
                            onChange={handleInputPetType}
                            disabled
                        ></input>
                        <label className="btn btn-secondary" htmlFor="option2">
                            Air
                        </label>

                        <input
                            type="radio"
                            className="btn-check"
                            name="options"
                            id="option3"
                            value="Water"
                            autoComplete="off"
                            checked={petType === "Water"}
                            onChange={handleInputPetType}
                        ></input>
                        <label className="btn btn-secondary" htmlFor="option3">
                            Water
                        </label>
                    </div>

                    <button type="submit" className="btn btn-success mt-2 mb-2">
                        Create
                    </button>
                </form>
            </div>
        </Popup>
        </>
    );
});

export default CreatePetModal;
