import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/category";

/* 카테고리 조회 */
// const BASE_URL = "/api/v1/category";

export async function getCategoryList(id) {
  const params = {
    bookshelfId: id,
  };
  try {
    const response = await axios.get(BASE_URL, { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 수정 */
export async function updateCategory(object) {
  try {
    const response = await axios.put(BASE_URL, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 생성 */
export async function createCategory(newCategory) {
  try {
    const response = await axios.post(BASE_URL, newCategory);

    return response.data;
  } catch (e) {
    console.log(e);
  }
}

/* 카테고리 기반 책 조회 */
export async function getBookList(categoryid) {
  try {
    const response = await axios.get(BASE_URL + `/${categoryid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 삭제 */
// 썼음 카테고리리스트.js
export async function deleteCategory(categoryid) {
  try {
    const response = await axios.delete(BASE_URL + `/${categoryid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
