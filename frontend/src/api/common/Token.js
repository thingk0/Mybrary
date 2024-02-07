export async function renewToken(accessToken) {
  try {
    const response = await fetch("/api/v1/member", {
      method: "PUT", // 요청 방식
      headers: {
        "Content-Type": "application/json",
      },
      body: accessToken, // 데이터를 문자열로 변환하여 전송
    });

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const data = await response.json();
    if (data.status === "SUCCESS") {
      return data.data;
    } else {
      throw new Error();
    }
  } catch (e) {
    throw e;
  }
}

export function isTokenExpired() {
  const tokenTimestamp = localStorage.getItem("tokenTimestamp");
  if (!tokenTimestamp) return true;

  const now = Date.now();
  const timeElapsed = (now - tokenTimestamp) / 1000; // 분 단위로 변환

  // 14분이 지났으면 갱신 필요
  return timeElapsed > 840;
}
