import createClient, { type Middleware } from "openapi-fetch";
import type { paths } from "./backendApi";
import { AppConfig } from "../ConfigContext";

const throwOnError: Middleware = {
  async onResponse({ response }) {
    if (response.status >= 400) {
      const body = response.headers.get("content-type")?.includes("json")
        ? await response.clone().json()
        : await response.clone().text();
      throw new Error(body);
    }

    return undefined;
  },
};

type AuthMiddlewareFactory = (accessToken: string) => Middleware;

const createAuthMiddleware: AuthMiddlewareFactory = (accessToken) => {
  return {
    onRequest: async ({ request }) => {
      request.headers.set(
        "Authorization",
        `Bearer ${accessToken}`
      );
      return request;
    }
  }
}

type ApiClient = ReturnType<typeof createConfiguredClient>;

const createConfiguredClient = (config: AppConfig["api"], accessToken: string) => {
  const client = createClient<paths>({ baseUrl: config.baseUrl });
  client.use(throwOnError);
  client.use(createAuthMiddleware(accessToken));
  return client;
}


export {
  type ApiClient,
  createConfiguredClient,
}
