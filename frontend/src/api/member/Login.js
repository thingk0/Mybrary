import axios from "axios";

const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
//로그인요청
export async function login(user) {
  try {
    localStorage.clear();

    const response = await axios.post(BASE_URL + "member/login", user);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 소셜 로그인 */
// export async function socialLogin(object) {
//   try {
//     const response = await axios.post(BASE_URL + "member/login/social", object);
//     return response.data;
//   } catch (error) {
//     throw error;
//   }
// }
