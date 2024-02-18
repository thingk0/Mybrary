import { Outlet, useLocation } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";
import axios from "axios";
import { useEffect } from "react";
import useStompStore from "./store/useStompStore";
import useUserStore from "./store/useUserStore";
import useNotificationStore from "./store/useNotificationStore";
import { Toaster } from "react-hot-toast";
// import { isTokenExpired, renewToken } from "./api/common/Token";
// import toast from "react-hot-toast";

axios.defaults.withCredentials = true;

axios.interceptors.request.use(
  async (config) => {
    let accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
      return config;
    }

    // if (isTokenExpired) {
    //   const res = renewToken(accessToken);
    //   localStorage.setItem("accessToken", res.data);
    //   localStorage.setItem("tokenTimestamp", Date.now());
    // }

    config.headers["Authorization"] = `Bearer ${accessToken}`;
    return config;
  },
  (error) => {
    const { disconnect } = useStompStore();
    localStorage.clear();
    disconnect();
    window.location.href = "/join";
    //return Promise.reject(error);
  }
);

export default function App() {
  const { stompClient, connect } = useStompStore();
  const { setHasNewNotification, notifyEnable } = useNotificationStore();
  const email = useUserStore((state) => state.user?.email);

  useEffect(() => {
    async function socketConnect() {
      try {
        if (email) {
          await connect(email, setHasNewNotification);
        }
      } catch (e) {
        //웹소켓 연결 실패
      }
    }
    // 있으면 재연결 하지마
    if (!stompClient) socketConnect();
  }, [stompClient, email, setHasNewNotification, connect]);

  const location = useLocation(); // 현재 위치 정보를 가져옵니다.

  // 현재 경로가 홈('/')이면 Nav를 숨깁니다.
  const showNav = location.pathname !== "/" && location.pathname !== "/join";

  return (
    <>
      {showNav && <Nav />}
      <div>
        <Outlet />
      </div>
      {notifyEnable && <Toaster position="bottom-right" reverseOrder={false} />}
    </>
  );
}
