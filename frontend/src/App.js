import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";

// import { useEffect } from "react";
// import useStompStore from "./store/useStompStore";
// import { checkLogin } from "./api/member/CheckLogin";
// import { useNavigate } from "react-router-dom";
// import useUserStore from "./store/useUserStore";
// import useNotificationStore from "./store/useNotificationStore";
import { Toaster } from "react-hot-toast";

export default function App() {
  // const { connect, disconnect } = useStompStore();
  // const { setNewNotification } = useNotificationStore();
  // const navigate = useNavigate();
  // const email = useUserStore((state) => state.user.email);

  // useEffect(() => {
  //   // 로그인 중인지 확인하는 요청 보내고, 로그인 상태가 아니라면 return
  //   try {
  //     const data = checkLogin();
  //     if (data.status === "SUCCESS") {
  //       // 컴포넌트 마운트 시 연결
  //       connect(email, setNewNotification);
  //     } else return;
  //   } catch (e) {
  //     console.log(e);
  //     // 통신장애
  //     navigate("/error");
  //   }

  //   // 컴포넌트 언마운트 시 연결 해제
  //   return () => {
  //     disconnect();
  //   };
  // }, [connect, disconnect, navigate, email, setNewNotification]);

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
