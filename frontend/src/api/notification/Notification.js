import axios from "axios";

/* 나의 알림 조회 */
export async function getMessageList(id) {
  try {
    const response = await axios.get(`/api/v1/notification/`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 전체 삭제 */
export async function deleteAllNotification() {
  try {
    const response = await axios.delete(`/api/v1/notification/`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 단건 삭제 */
export async function deleteNotification(id) {
  try {
    const response = await axios.delete(`/api/v1/notification/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
