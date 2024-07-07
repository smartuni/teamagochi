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
    let result: Device[] = [];

    try {
      const { data } = await this.withClient().GET("/api/v1/devices/self");
      if (data !== undefined) {
        result = data;
      }
    } catch (error: unknown) {
      this.printErrorMessage(error);
    }
    
    return result;
  }

  public async updateDevice(device: Device) {
    const { data, error } = await this.withClient().PUT(
      "/api/v1/devices/self/{deviceId}", {
        params: {
          path: {
            deviceId: device.id!,
          }
        },
        body: device,
      }
    )

    // TODO
    console.log(data);
    console.log(error);

    return data;
  }

  public async deleteDevice(deviceId: number) {
    const { data, error } = await this.withClient()
      .DELETE("/api/v1/devices/self/{deviceId}", {
        params: {
          path: {
            deviceId: deviceId,
          }
        }
      });

    // TODO
    console.log(data);
    console.log(error);

    return data;
  }

  public async registerDevice(
    registrationCode: string,
    deviceName: string,
    deviceType: string)
  {
    const { data, error } = await this.withClient()
      .POST("/api/v1/devices/self/register/{registrationCode}", {
        params: {
          path: {
            registrationCode: registrationCode,
          }
        },
        body: {
          type: deviceType,
          name: deviceName
        }
      });

    // TODO
    console.log(data);
    console.log(error);

    return data;
  }

  private withClient() {
    if (!this.apiClient) {
      throw new Error("Client must be set.");
    }

    return this.apiClient;
  }

  private printErrorMessage(error: unknown) {
    if (error instanceof Error) {
      console.warn("Error when fetching devices");
    }
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
