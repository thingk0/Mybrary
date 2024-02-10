import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
//로그인요청
export async function login(user) {
  try {
    const response = await fetch(BASE_URL + "member/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
      credentials: "include",
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    throw error;
  }
}
// //로그인요청
// export async function login(object) {
//   try {
//     const response = await axios.post(BASE_URL + "member/login", object);
//     return response.data;
//   } catch (error) {
//     throw error;
//   }
// }

/* 소셜 로그인 */
export async function socialLogin(object) {
  try {
    const response = await axios.post(BASE_URL + "member/login/social", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
