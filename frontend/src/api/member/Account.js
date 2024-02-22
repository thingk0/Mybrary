import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";
/* 회원 정보(프로필) 수정 */
export async function updateProfile(object) {
  try {
    const response = await axios.put(BASE_URL + `member/profile`, object);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 비밀번호 재설정(로그인 후) */
export async function updatePassword(object) {
  try {
    const response = await axios.put(
      BASE_URL + `member/password-update`,
      object
    );
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 비밀번호 찾기 */
export async function findPassword(object) {
  try {
    const response = await axios.put(
      BASE_URL + `member/password-update`,
      object
    );
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 계정 탈퇴 */
export async function deleteAccount(object) {
  try {
    const response = await axios.delete(BASE_URL + "member/secession", object);
    return response.data;
  } catch (error) {
    throw error;
  }
}
