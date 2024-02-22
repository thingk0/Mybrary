import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";

/* 스레드 검색 */
export async function searchContents(keyword) {
  const params = {
    keyword,
    page: 0,
    size: 100,
  };

  try {
    const response = await axios.get(BASE_URL + "search/contents", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}
/* 추천 검색어 조회 */ //
export async function keyword(keyword) {
  try {
    const response = await axios.get(BASE_URL + `search?keyword=${keyword}`);
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
