import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/member/Login";
import useUserStore from "../../store/useUserStore";
import useStompStore from "../../store/useStompStore";
import useNotificationStore from "../../store/useNotificationStore";
import styles from "./LoginForm.module.css";
import Loading from "../common/Loading";

function LoginForm() {
  /* 로그인하고 바로 stompClient 초기화. */
  const { connect } = useStompStore();
  const { setNewNotification } = useNotificationStore();
  /* 오류페이지 이동 */
  const navigate = useNavigate();
  const navigateToErrorPage = () => {
    navigate("/error");
  };
  const [isLoading, setIsLoading] = useState(false);

  // 유저상태 전역 관리를 위한 코드
  const setUser = useUserStore((state) => state.setUser);

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
    setIsLoading(true);
    try {
      // 로그인 요청 보내기

      const data = await login(formData);
      console.log(data);
      if (data.status === "SUCCESS") {
        // useStore에 data안에 들어있는 기본 정보들을 저장해라
        localStorage.setItem("accessToken", data.data.token);
        localStorage.setItem("tokenTimestamp", Date.now());
        await setUser({
          email: formData.email,
          memberId: data.data.memberInfo.memberId,
          nickname: data.data.memberInfo.nickname,
          bookshelfId: 0,
        });

        async function socketConnect() {
          try {
            if (formData.email) {
              await connect(formData.email, setNewNotification);
            }
          } catch (e) {
            //웹소켓 연결 실패
            console.log(e);
          }
        }
        await socketConnect();
        console.log(data.data.memberInfo.memberId);
        setIsLoading(false);
        navigate(`/mybrary/${data.data.memberInfo.memberId}`);
        // navigate(`/mybrary/userid`);
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
      <div className={styles.로그인메인}>
        <div className={styles.로그인사이즈조정}>
          <form onSubmit={handleSubmit}>
            <div>
              <label>email</label>
              <div className={styles.각각의폼디브}>
                <input
                  className={styles.인풋창}
                  type="text"
                  name="email"
                  placeholder="이메일을 입력하세요"
                  value={formData.email}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div>
              <label>비밀번호</label>
              <div className={styles.각각의폼디브}>
                <input
                  className={styles.인풋창}
                  type="text"
                  name="password"
                  placeholder="비밀번호를 입력하세요"
                  value={formData.password}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className={styles.버튼디브}>
              <button className={styles.로그인버튼} type="submit">
                로그인
              </button>
            </div>
            {isLoginFail && (
              <span className={styles.에러메시지}>
                아이디와 비밀번호를 확인해주세요.
              </span>
            )}
          </form>
        </div>
      </div>
      {/* <> */}
      {/* {isLoading && <Loading />} */}
      {/* 로그인 폼 및 기타 컴포넌트 */}
      {/* </> */}
    </>
  );
}

export default LoginForm;
