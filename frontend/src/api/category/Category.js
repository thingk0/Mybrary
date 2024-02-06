import axios from "axios";

/* 카테고리 조회 */
export async function getCategoryList(id) {
  const params = {
    bookshelfId: id,
  };
  try {
    const response = await axios.get("/api/v1/category", { params });
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 수정 */
export async function updateCategory(object) {
  try {
    const response = await axios.put("/api/v1/category", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 생성 */
export async function createCategory(object) {
  try {
    const response = await axios.put("/api/v1/category", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 기반 책 조회 */
export async function getBookList(categoryid) {
  try {
    const response = await axios.get(`/api/v1/category/${categoryid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 카테고리 삭제 */
export async function deleteCategory(categoryid) {
  try {
    const response = await axios.delete(`/api/v1/category/${categoryid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
