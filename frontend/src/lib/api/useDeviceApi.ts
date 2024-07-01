import { useEffect, useState } from "react";
import { ApiClient, useClient } from "./useClient";
import { components } from "./backendApi";

type Device = components["schemas"]["DeviceDTO"];

class DeviceApi {
  private apiClient?: ApiClient;

  constructor(apiClient: ApiClient) {
    this.apiClient = apiClient;
  }
  
  public setApiClient(apiClient: ApiClient) {
    this.apiClient = apiClient;
  }

  public async getDevices(): Promise<Device[]> {
    const {data, error} = await this.withClient().GET("/api/v1/devices/self");

    // TODO
    console.log(data);
    console.log(error);

    return data == undefined ? [] : data;
  }

  private withClient() {
    if (!this.apiClient) {
      throw new Error("Client must be set.");
    }

    return this.apiClient;
  }
}

function useDeviceApi() {
  const apiClient = useClient();
  const [deviceApi, setDeviceApi] = useState<DeviceApi | undefined>();

  useEffect(() => {
    if (!apiClient) return;
    setDeviceApi(new DeviceApi(apiClient))
  }, [apiClient]);

  return deviceApi;
}

export {
  type Device,
  useDeviceApi,
}
