// 로그인 중인지 검사
/* 실제 사용할 메서드
export async function checkLogin() {
  try {
    const response = await fetch("/api/v1/member/check", {
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
*/

// 테스트 코드
export function checkLogin() {
  const data = {
    status: "SUCCESS",
    email: "dmdkvj369@naver.com",
    memberId: "1",
    nickname: "jingu123",
  };
  return data;
}
