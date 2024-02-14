import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";

export async function getRollingPaper(id) {
  try {
    const response = await axios.get(BASE_URL + `rollingpaper/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function saveRollingPaper(object) {
  try {
    const response = await axios.post(BASE_URL + `rollingpaper`, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
