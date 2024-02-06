import axios from "axios";

export async function share(object) {
  try {
    const response = await axios.post("/api/v1/paper/share", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function scrap(object) {
  try {
    const response = await axios.post("/api/v1/paper/share", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
