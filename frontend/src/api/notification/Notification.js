import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/notification";

/* 나의 알림 조회 */
export async function getNotificationList() {
  try {
    const response = await axios.get(BASE_URL);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 전체 삭제 */
export async function deleteAllNotification() {
  try {
    const response = await axios.delete(BASE_URL);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 알림 단건 삭제 */
export async function deleteNotification(id) {
  try {
    const response = await axios.delete(`BASE_URL/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 팔로우요청 거절 */
export async function refuseFollow(id) {
  try {
    const response = await axios.delete(`BASE_URL/${id}/refuse`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 팔로우요청 수락 */
export async function acceptFollow(id) {
  try {
    const response = await axios.delete(`BASE_URL/${id}/accept`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
