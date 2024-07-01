import { useContext, useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { ApiClient, createConfiguredClient } from "./client";
import { ConfigContext } from "../ConfigContext";

function useClient() {
  const auth = useAuth();
  const [apiClient, setApiClient] = useState<ApiClient | undefined>();
  const config = useContext(ConfigContext);

  useEffect(() => {
    if (!auth.user || !auth.user.access_token || !config) return;

    const client = createConfiguredClient(config.api, auth.user.access_token);
    setApiClient(client)

  }, [auth.user, config]);

  return apiClient;
}

export {
  useClient,
  type ApiClient
}
