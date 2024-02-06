import axios from "axios";

/* 스레드 검색 */
export async function searchThread(keyword, pagingObject) {
  const params = {
    keyword,
    ...pagingObject,
  };

  try {
    const response = await axios.get("/api/v1/search/thread", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 인기 검색어 조회 */ //
export async function getPopularList() {
  try {
    const response = await axios.get("/api/v1/search/popular");
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 멘션에서 계정 검색 */
export async function getProfiles(pagingObject) {
  try {
    const response = await axios.get("/api/v1/search/mention", pagingObject);
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
    const response = await axios.get("/api/v1/search/book", { params });
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
    const response = await axios.get("/api/v1/search/account", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}
