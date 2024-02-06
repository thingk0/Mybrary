import axios from "axios";

/* 책 수정 */
export async function updateBook(object) {
  try {
    const response = await axios.put("/api/v1/book", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 생성 */
export async function createBook(object) {
  try {
    const response = await axios.post("/api/v1/book", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 구독 */
export async function subscribeBook(object) {
  try {
    const response = await axios.post("/api/v1/book/subscription", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 북마크 생성 */
export async function createBookmark(object) {
  try {
    const response = await axios.post("/api/v1/book/bookmark", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 정보 조회 */
export async function getBook(id) {
  try {
    const response = await axios.get(`/api/v1/book/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 삭제 */
export async function deleteBook(id) {
  try {
    const response = await axios.get(`/api/v1/book/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 책 목록 조회 */
export async function getMYBooks() {
  try {
    const response = await axios.get(`/api/v1/book/my`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 구독 삭제 */
export async function unsubsribeBook(id) {
  try {
    const response = await axios.delete(`/api/v1/book/unsubscription/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
