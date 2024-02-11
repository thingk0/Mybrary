import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
/* 책 수정 */
export async function updateBook(object) {
  try {
    const response = await axios.put(BASE_URL + "book", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 생성 */
export async function createBook(object) {
  try {
    const response = await axios.post(BASE_URL + "book", object);
    return response.data.data;
  } catch (error) {
    throw error;
  }
}

/* 책 구독 */
export async function subscribeBook(object) {
  try {
    const response = await axios.post(BASE_URL + "book/subscription", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 북마크 생성 */
export async function createBookmark(object) {
  try {
    const response = await axios.post(BASE_URL + "book/bookmark", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 정보 조회 */
export async function getBook(id) {
  try {
    const response = await axios.get(BASE_URL + `book/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 삭제 */
export async function deleteBook(id) {
  try {
    const response = await axios.get(BASE_URL + `book/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 책 목록 조회 */
export async function getMYBooks() {
  try {
    const response = await axios.get(BASE_URL + `book/my`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 구독 삭제 */
export async function unsubsribeBook(id) {
  try {
    const response = await axios.delete(BASE_URL + `book/unsubscription/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
