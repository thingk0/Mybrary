//로그인요청
export async function login(user) {
  /*
  try {
    const response = await fetch("/api/v1/member/login", {
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
  }*/
  const data = {
    status: "SUCCESS",
    email: user.email,
  };
  return data;
}
