import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";
export async function share(object) {
  try {
    const response = await axios.post(BASE_URL + "paper/share", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function scrap(object) {
  try {
    const response = await axios.post(BASE_URL + "paper/share", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
