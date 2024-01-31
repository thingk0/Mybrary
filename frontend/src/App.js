import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";

import { useEffect } from "react";
import useStompStore from "./stompStore";
import { checkLogin } from "./api/member/CheckLogin";
import { useNavigate } from "react-router-dom";

export default function App() {
  const { connect, disconnect } = useStompStore();
  const navigate = useNavigate();

  useEffect(() => {
    // 로그인 중인지 확인하는 요청 보내고, 로그인 상태가 아니라면 return
    try {
      const data = checkLogin();
      if (data.status === "FALSE") {
        return;
      }
    } catch (e) {
      // 통신장애
      navigate("/error");
    }

    // 컴포넌트 마운트 시 연결
    connect();

    // 컴포넌트 언마운트 시 연결 해제
    return () => {
      disconnect();
    };
  }, [connect, disconnect, navigate]);

  return (
    <>
      <Nav />
      <div>
        <Outlet />
      </div>
    </>
  );
}
