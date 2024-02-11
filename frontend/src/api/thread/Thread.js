import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
/* 쓰레드 생성 */
export async function createThread(object) {
  try {
    const response = await axios.post(BASE_URL + "thread", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 쓰레드 수정 */
export async function updateThread(object) {
  try {
    const response = await axios.put(BASE_URL + "thread", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 쓰레드 단건 조회 */
export async function getThread(threadid) {
  try {
    const response = await axios.get(BASE_URL + `thread/${threadid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 쓰레드 삭제 */
export async function deleteThread(threadid) {
  try {
    const response = await axios.delete(BASE_URL + `thread/${threadid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 특정 회원의 쓰레드(나의게시물) 조회 */
export async function getDeskThread(memberid, pagingObject) {
  try {
    const response = await axios.get(BASE_URL + `thread/${memberid}/desk`, {
      pagingObject,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 메인피드 쓰레드 조회 */
export async function getThreadList(pagingObject) {
  try {
    const response = await axios.get(BASE_URL + `thread/home`, {
      pagingObject,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 쓰레드 조회 */
export async function getMyThreadList(pagingObject) {
  try {
    const response = await axios.get(BASE_URL + `thread/desk`, {
      pagingObject,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}
