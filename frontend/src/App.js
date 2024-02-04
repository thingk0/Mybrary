import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";

import { useEffect } from "react";
import useStompStore from "./store/useStompStore";
import useUserStore from "./store/useUserStore";
import useNotificationStore from "./store/useNotificationStore";
import { Toaster } from "react-hot-toast";

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
