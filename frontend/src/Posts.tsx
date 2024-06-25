// import React from "react";
// import { useAuth } from "react-oidc-context";

// const Posts = () => {
//     const auth = useAuth();
//     const [posts, setPosts] = React.useState(Array);

//     React.useEffect(() => {
//         (async () => {
//             try {
//                 const token = auth.user?.access_token;
//                 const response = await fetch("https://api.example.com/posts", {
//                     headers: {
//                         Authorization: `Bearer ${token}`,
//                     },
//                 });
//                 setPosts(await response.json());
//             } catch (e) {
//                 console.error(e);
//             }
//         })();
//     }, [auth]);

//     if (!posts.length) {
//         return <div>Loading...</div>;
//     }

//     return (
//         <ul>
//         {posts.map((post, index) => {
//             return <li key={index}>{post}</li>;
//         })}
//         </ul>
//     );
// };

// export default Posts;
