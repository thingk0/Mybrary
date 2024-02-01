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
export async function verifyEmail(email) {
  try {
    const response = await fetch("api/v1/member/email/verification", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: email,
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

// 인증번호 검사
export async function verifyCode(code) {
  try {
    const response = await fetch("/api/v1/member/email/verify", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: code,
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
