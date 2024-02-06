import axios from "axios";

/* 나의 마이브러리 조회 */
export async function getMyMybrary() {
  try {
    const response = await axios.get("/api/v1/mybrary");
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 마이브러리 수정 */
export async function updateMybrary(object) {
  try {
    const response = await axios.put("/api/v1/mybrary", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 타인의 마이브러리 조회 */
export async function getMybrary(id) {
  try {
    const response = await axios.get(`/api/v1/mybrary/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
