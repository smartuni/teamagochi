import { useEffect, useState } from "react";
import { ApiClient, useClient } from "./useClient";
import { components } from "./backendApi";

type Pet = components["schemas"]["PetDTO"];

class PetApi {
  private apiClient?: ApiClient;

  constructor(apiClient: ApiClient) {
    this.apiClient = apiClient;
  }
  
  public setApiClient(apiClient: ApiClient) {
    this.apiClient = apiClient;
  }

  public async getPets(): Promise<Pet[]> {
    let result: Pet[] = [];

    try {
      const { data } = await this.withClient().GET("/api/v1/pets/self");
      if (data !== undefined) {
        result = data;
      }
    } catch (error: unknown) {
      this.printErrorMessage(error);
    }

    return result;
  }

  public async getPetById(id: number): Promise<Pet|undefined> {
    const {data, error} = await this.withClient().GET(
      "/api/v1/pets/self/{petId}",
      {params: {path: { petId: id }}}
    );

    // TODO
    console.log(data);
    console.log(error);

    return data;
  }

  public async createPet(pet: Pet) {
    const {data, error} = await this.withClient().POST(
      "/api/v1/pets/self",
      {
        body: pet,
      }
    )

    // TODO
    console.log(data);
    console.log(error);

    return data;
  }

  public async removePet(id: number) {
    const {data, error} = await this.withClient().DELETE(
      "/api/v1/pets/{petId}",
      {
        params: {
          path: { petId: id }
        }
      }
    )

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
      console.warn("Error when fetching pets");
    }
  }
}

function usePetApi() {
  const apiClient = useClient();
  const [petApi, setPetApi] = useState<PetApi | undefined>();

  useEffect(() => {
    if (!apiClient) return;
    setPetApi(new PetApi(apiClient))
  }, [apiClient]);

  return petApi;
}

export {
  type Pet,
  usePetApi,
}
