import axios from "axios";

/* 회원 정보(프로필) 수정 */
export async function updateProfile(object) {
  try {
    const response = await axios.put(`/api/v1/member/profile`, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 비밀번호 재설정(로그인 후) */
export async function updatePassword(object) {
  try {
    const response = await axios.put(`/api/v1/member/password-update`, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 비밀번호 찾기 */
export async function findPassword(object) {
  try {
    const response = await axios.put(`/api/v1/member/password-update`, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 계정 탈퇴 */
export async function deleteAccount(object) {
  try {
    const response = await axios.delete("/api/v1/member/secession", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
