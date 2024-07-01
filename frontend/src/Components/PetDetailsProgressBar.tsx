import { ProgressBar } from "react-progressbar-fancy";
import { IProgressBarProps } from "react-progressbar-fancy/build/ProgressBar/ProgressBar.types";

/**
 * See https://github.com/RavinRau/react-progressbar-fancy
 */
const PetDetailsProgressBar = (props: {
  labelName: string;
  value?: number;
  color: IProgressBarProps["progressColor"];
}) => {
  return (
    <>
      {props.value != undefined && (
        <div>
          <div className="h5 px-3">{props.labelName}</div>
          <ProgressBar
            className="px-1 py-2"
            hideText={true}
            progressColor={props.color}
            score={props.value}
          />
        </div>
      )}
    </>
  );
};

export default PetDetailsProgressBar;
