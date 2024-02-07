import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";

export async function doLogout() {
  try {
    const response = await axios.post(BASE_URL + "member/logout");
    localStorage.clear();
    return response.data;
  } catch (error) {
    throw error;
  }
}
