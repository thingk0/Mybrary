import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080";

// 일반 회원가입

export async function signup(user) {
  try {
    const response = await axios.post("/api/v1/member", user);

    return response.data;
  } catch (error) {
    throw error;
  }
}

// 이메일 인증 요청
export async function verifyEmail(email) {
  try {
    const response = await axios.post("/api/v1/member/email/verification", {
      email: email,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

// 인증번호 검사
export async function verifyCode(email, code) {
  try {
    const response = await axios.post("/api/v1/member/email/verify", {
      email: email,
      authNum: code,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}
// 닉네임 중복 검사
export async function checkNickName(nickname) {
  try {
    const response = await axios.get(
      `/api/v1/member/nickname/${nickname}/exists`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
}
