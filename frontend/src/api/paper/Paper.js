import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
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
