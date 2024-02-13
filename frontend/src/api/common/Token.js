import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";

export async function renewToken(accessToken) {
  try {
    const response = await fetch(BASE_URL + "member", {
      method: "PUT", // 요청 방식을 PUT으로 설정
      headers: {
        "Content-Type": "application/json", // 컨텐트 타입을 JSON으로 명시
      },
      body: accessToken, // 데이터를 JSON 문자열로 변환하여 전송
      credentials: "include",
    });

    if (!response.ok) {
      throw new Error("Network response was not ok"); // 네트워크 응답이 성공적이지 않은 경우 에러 발생
    }

    const data = await response.json(); // 응답 데이터를 JSON으로 파싱
    if (data.status === "SUCCESS") {
      //console.log("갱신성공");
      return data.data; // 갱신된 토큰 반환
    } else {
      throw new Error("Token renewal failed"); // 토큰 갱신 실패 시 에러 발생
    }
  } catch (e) {
    throw e; // 에러를 다시 던져서 호출한 곳에서 처리할 수 있게 함
  }
}

// export function isTokenExpired() {
//   const tokenTimestamp = localStorage.getItem("tokenTimestamp");
//   if (!tokenTimestamp) return true;

//   const now = Date.now();
//   const timeElapsed = (now - tokenTimestamp) / 1000; // 분 단위로 변환

//   // 14분이 지났으면 갱신 필요
//   return timeElapsed > 840;
// }
