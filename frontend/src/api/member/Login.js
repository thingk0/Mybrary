import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";

//로그인요청
export async function login(user) {
  try {
    const response = await fetch(BASE_URL + "member/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

/* 소셜 로그인 */
export async function socialLogin(object) {
  try {
    const response = await axios.post(BASE_URL + "member/login/social", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
