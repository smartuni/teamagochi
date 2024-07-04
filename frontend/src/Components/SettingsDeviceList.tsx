import { Device } from "../lib/api/useDeviceApi";

interface Props {
    devices: Device[];
    removeCallback: (id: number) => void;
}

const SettingsDeviceList = (props: Props) => {
    const { devices, removeCallback } = props;

    return (
    <>
    <ul className="list-group" style={{ fontSize: "1.2rem" }}>
        {devices.map((device) => (
            <li
                key={device.id}
                className="list-group-item d-flex justify-content-between align-items-center py-3"
            >
                {device.name}
                <div>
                    <button
                        className="btn btn-danger btn-lg mx-1"
                        onClick={() => removeCallback(device.id!)}
                    >
                        Remove
                    </button>
                </div>
            </li>
        ))}
    </ul>
    </>
    );
};

export default SettingsDeviceList;
