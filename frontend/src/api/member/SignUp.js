import { json } from "react-router-dom";

// 일반 회원가입
export async function signup(user) {
  try {
    const response = await fetch("/api/v1/member", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

// 이메일 인증 요청
export async function verifyEmail(emailString) {
  try {
    const jsonEmail = JSON.stringify({
      email: emailString,
    });
    const response = await fetch("/api/v1/member/email/verification", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonEmail,
    });
    console.log(response);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

// 인증번호 검사
export async function verifyCode(email, code) {
  try {
    const jsonData = JSON.stringify({
      email: email,
      authNum: code,
    });

    console.log(jsonData);

    const response = await fetch("/api/v1/member/email/verify", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonData,
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

// 닉네임 중복 검사
export async function checkNickName(nickname) {
  try {
    const response = await fetch("/api/v1/member/nickname", {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();

    return data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
}
