import { User } from "oidc-client-ts"

function getUser() {
    const oidcStorage = localStorage.getItem(`oidc.user:<your authority>:<your client id>`)
    if (!oidcStorage) {
        return null;
    }

    return User.fromStorageString(oidcStorage);
}

export const getPosts = createAsyncThunk(
    "store/getPosts",
    async () => {
        const user = getUser();
        const token = user?.access_token;
        return fetch("https://api.example.com/posts", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
    },
    // ...
)