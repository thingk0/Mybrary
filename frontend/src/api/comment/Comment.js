import axios from "axios";

/* 페이퍼 댓글 조회 */
export async function getBookList(paperid) {
  const params = {
    paperId: paperid,
  };

  try {
    const response = await axios.get("/api/vi/comment", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 댓글 생성 */
export async function createCommnet(object) {
  try {
    const response = await axios.post("/api/vi/comment", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 댓글 삭제 */
export async function deleteCommnet(commentid) {
  try {
    const response = await axios.delete(`/api/vi/comment/${commentid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
