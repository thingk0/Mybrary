import axios from "axios";

export async function doLogout() {
  try {
    const response = await axios.post("/api/v1/member/logout");

    return response.data;
  } catch (error) {
    throw error;
  }
}
