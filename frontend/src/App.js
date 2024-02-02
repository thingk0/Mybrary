import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";

import { useEffect } from "react";
import useStompStore from "./store/useStompStore";
//import { checkLogin } from "./api/member/CheckLogin";
import { useNavigate } from "react-router-dom";
import useUserStore from "./store/useUserStore";
import useNotificationStore from "./store/useNotificationStore";
import { Toaster } from "react-hot-toast";

export default function App() {
  const { connect, disconnect } = useStompStore();
  const { setNewNotification } = useNotificationStore();
  const navigate = useNavigate();
  const email = useUserStore((state) => state.user.email);

  useEffect(() => {
    async function socketConnect() {
      try {
        await connect(email, setNewNotification);
      } catch (e) {
        console.log("?");
        navigate("/join");
        // 통신장애
        navigate("/error");
      }
    }

    socketConnect();
  }, [connect, disconnect, navigate, email, setNewNotification]);

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
