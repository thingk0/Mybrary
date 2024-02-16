import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";

export async function doLogout(setUser) {
  try {
    //await axios.post(BASE_URL + "member/logout");
    await setUser(null);
  } catch (error) {
    throw error;
  }
}
