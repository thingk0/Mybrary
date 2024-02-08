import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";
/* 스레드 검색 */
export async function searchThread(keyword, pagingObject) {
  const params = {
    keyword,
    ...pagingObject,
  };

  try {
    const response = await axios.get(BASE_URL + "search/thread", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 인기 검색어 조회 */ //
export async function getPopularList() {
  try {
    const response = await axios.get(BASE_URL + "search/popular");
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 멘션에서 계정 검색 */
export async function getProfiles(pagingObject) {
  try {
    const response = await axios.get(BASE_URL + "search/mention", pagingObject);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 책 검색 */
export async function searchBook(keyword, pagingObject) {
  const params = {
    keyword,
    ...pagingObject,
  };

  try {
    const response = await axios.get(BASE_URL + "search/book", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 검색페이지에서 계정 검색 */
export async function searchAccount(keyword, pagingObject) {
  const params = {
    keyword,
    ...pagingObject,
  };

  try {
    const response = await axios.get(BASE_URL + "search/account", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}
