import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/member/Login";
import useUserStore from "../../store/useUserStore";
import useStompStore from "../../store/useStompStore";
import useNotificationStore from "../../store/useNotificationStore";
import styles from "./LoginForm.module.css";
import toast from "react-hot-toast";

function LoginForm() {
  /* 로그인하고 바로 stompClient 초기화. */
  const { connect } = useStompStore();
  const { setHasNewNotification } = useNotificationStore();
  /* 오류페이지 이동 */
  const navigate = useNavigate();

  // 유저상태 전역 관리를 위한 코드
  const { setUser } = useUserStore();

  /* 상태 */
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

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
    try {
      const res = await login(formData);
      if (res.status === "SUCCESS") {
        // useStore에 data안에 들어있는 기본 정보들을 저장해라
        localStorage.setItem("accessToken", res.data.token);
        localStorage.setItem("tokenTimestamp", Date.now());

        async function socketConnect() {
          try {
            await connect(res.data.memberInfo.email, setHasNewNotification);
          } catch (e) {
            //웹소켓 연결 실패
          } finally {
            setFormData({}); // 로그인이 성공했으므로, 무조건 setFormData는 초기화
          }
        }
        await socketConnect();

        await setUser({
          email: res.data.memberInfo.email,
          memberId: res.data.memberInfo.memberId,
          nickname: res.data.memberInfo.nickname,
          profileImageUrl: res.data.memberInfo.profileImageUrl,
        });

        navigate(`/mybrary/${res.data.memberInfo.memberId}`);
      } else {
        // 이메일, 비밀번호 불일치
        showToast("아이디와 비밀번호를 확인해주세요.");
      }
    } catch (e) {
      // 전송 오류 발생 시
      // 서버에러. 에러페이지로 이동
      navigate("/error");
    }
  };

  /* 알림 함수 */
  const showToast = (string) => {
    toast.error(`${string}`, {
      style: {
        border: "1px solid #713200",
        padding: "16px",
        color: "#713200",
        zIndex: "100",
      },
      iconTheme: {
        primary: "#713200",
        secondary: "#FFFAEE",
      },
      position: "top-center",
    });
  };

  return (
    <form>
      <div className={styles.inputContainer}>
        <label className={styles.label}>email</label>
        <input
          className={styles.input}
          type="text"
          name="email"
          placeholder="이메일을 입력하세요"
          value={formData.email}
          onChange={handleChange}
        />
      </div>
      <div className={styles.inputContainer}>
        <label className={styles.label}>비밀번호</label>
        <input
          className={styles.input}
          type="password"
          name="password"
          placeholder="비밀번호를 입력하세요"
          value={formData.password}
          onChange={handleChange}
        />
      </div>
      <div className={styles.button} onClick={() => handleSubmit()}>
        로그인
      </div>
    </form>
  );
}

export default LoginForm;
