import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
/* 나의 마이브러리 조회 */
export async function getMyMybrary() {
  try {
    const response = await axios.get(BASE_URL + "mybrary");
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 마이브러리 수정 */
export async function updateMybrary(object) {
  try {
    const response = await axios.put(BASE_URL + "mybrary", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 타인의 마이브러리 조회 */
export async function getMybrary(id) {
  try {
    const response = await axios.get(BASE_URL + `mybrary/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
