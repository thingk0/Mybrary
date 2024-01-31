import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ConnectWebSocket, login } from "../../api/member/Login";

function LoginForm() {
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
        console.log("로그인성공");
        // 로그인 성공시 해야 할 것
        // 채팅, 알람 웹소켓 연결하기 (알람 비허용 여부는 백엔드에서 처리)
        // ConnectWebSocket(formData.email);
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
