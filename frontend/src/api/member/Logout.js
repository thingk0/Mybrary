//로그아웃요청
export async function doLogout() {
  try {
    const response = await fetch("/api/v1/member/logout", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });

    console.log(response.ok);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
  } catch (error) {
    console.log(error);
    throw error;
  }
}
