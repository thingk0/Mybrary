import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";
import axios from "axios";
import { useEffect } from "react";
import useStompStore from "./store/useStompStore";
import useUserStore from "./store/useUserStore";
import useNotificationStore from "./store/useNotificationStore";
import { Toaster } from "react-hot-toast";
import { isTokenExpired, renewToken } from "./api/common/Token";

axios.interceptors.request.use(
  async (config) => {
    let accessToken = localStorage.getItem("accessToken");

    // accessToken이 없는 경우, 인증 헤더를 추가하지 않고 요청을 계속 진행
    // ex) 로그인 전 요청. 회원가입의 경우 jwt인증을 하지 않으므로 정상 요청
    // 사용자가 로컬스토리지의 엑세스 토큰을 삭제한 경우, 헤더에 엑세스 토큰이 같이 전송되지 않으므로 오류페이지로 이동 (권한없음 에러)
    if (!accessToken) {
      return config;
    }

    // 토큰이 만료되었는지 확인
    if (isTokenExpired(accessToken)) {
      // 재요청 보내

      console.log("이전: " + accessToken);
      accessToken = await renewToken(accessToken);
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("tokenTimestamp", Date.now());
    }

    config.headers["Authorization"] = `Bearer ${accessToken}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default function App() {
  const { connect } = useStompStore();
  const { setNewNotification } = useNotificationStore();
  const email = useUserStore((state) => state.user?.email);

  useEffect(() => {
    async function socketConnect() {
      try {
        if (email) {
          await connect(email, setNewNotification);
        }
      } catch (e) {
        //웹소켓 연결 실패
        console.log(e);
      }
    }
    socketConnect();
  }, []);

  return (
    <>
      <Nav />
      <div>
        <Outlet />
      </div>
      <Toaster position="bottom-right" reverseOrder={false} />
    </>
  );
}
