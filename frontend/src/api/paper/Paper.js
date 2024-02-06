import axios from "axios";

export async function share(paper) {
  try {
    const response = await axios.post("/api/v1/paper/share", paper);
    return response.data;
  } catch (error) {
    throw error;
  }
}
