import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
/* 나의 알림 조회 */
export async function getMessageList(id) {
  try {
    const response = await axios.get(BASE_URL + `notification/`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 전체 삭제 */
export async function deleteAllNotification() {
  try {
    const response = await axios.delete(BASE_URL + `notification/`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 단건 삭제 */
export async function deleteNotification(id) {
  try {
    const response = await axios.delete(BASE_URL + `notification/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
