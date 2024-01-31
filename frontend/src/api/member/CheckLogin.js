// 로그인 중인지 검사
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
