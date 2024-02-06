import axios from "axios";

export async function doLogout() {
  try {
    const response = await axios.post("/api/v1/member/logout");
    localStorage.clear();
    return response.data;
  } catch (error) {
    throw error;
  }
}
