import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/member/Login";
import { useQueryClient } from "react-query";

function LoginForm() {
  const queryClient = useQueryClient();

  /* 오류페이지 이동 */
  const navigate = useNavigate();
  const navigateToErrorPage = () => {
    navigate("/error");
  };

  /* 상태 */
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [isLoginFail, setIsLoginFail] = useState(false);

  /* 메서드 */
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "email" && value.length > 50) {
      return;
    }

    if (name === "password" && value.length > 20) {
      return;
    }

    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // 로그인 요청 보내기
      const data = await login(formData);
      if (data.status === "SUCCESS") {
        // 로그인 성공시 해야 할 것
        const token = data.token;
        queryClient.setQueryData("token", token); // JWT 저장
        // 채팅, 알람 웹소켓 연결하기
        // 해당 유저가 알림을 허용했는지 요청을 통해 혹인하고 알람 웹소켓 연결

        // 채팅 웹소켓 연결하기
      } else {
        // 이메일, 비밀번호 불일치
        setIsLoginFail(true);
      }
    } catch (e) {
      // 전송 오류 발생 시
      // 서버에러. 에러페이지로 이동
      console.log(e);
      navigateToErrorPage();
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <div>
          <label>이메일:</label>
          <input
            type="text"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>비밀번호:</label>
          <input
            type="text"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        <button type="submit">로그인</button>
      </form>
      {isLoginFail && <h1>아이디와 비밀번호를 확인해주세요.</h1>}
    </>
  );
}

export default LoginForm;
