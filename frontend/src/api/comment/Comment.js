import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";
/* 페이퍼 댓글 조회 */
export async function getBookList(paperid) {
  const params = {
    paperId: paperid,
  };

  try {
    const response = await axios.get(BASE_URL + "comment", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 댓글 생성 */
export async function createCommnet(object) {
  try {
    const response = await axios.post(BASE_URL + "comment", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 댓글 삭제 */
export async function deleteCommnet(commentid) {
  try {
    const response = await axios.delete(BASE_URL + `comment/${commentid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
