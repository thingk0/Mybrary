import axios from "axios";

// 아마 수정 해야 될수도?
/* 이미지 업로드 */
export async function uplodaImage(object) {
  try {
    const response = await axios.post("/api/v1/image", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
