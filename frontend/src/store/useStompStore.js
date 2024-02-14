import { create } from "zustand";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs"; // Stomp.Client로도 사용 가능
import toast from "react-hot-toast";

const showAlarm = (alarmObj) => {
  const type = alarmObj.notifyType;
  const nickname = alarmObj.sender.nickname;
  const bookname = alarmObj.bookName;
  let msg = "";
  console.log(alarmObj);

  switch (type) {
    case 1:
      msg = "팔로우를 요청했습니다";
      break;
    case 2:
      msg = "회원님을 팔로우했습니다";
      break;
    case 3:
      msg = "회원님의 페이퍼에 댓글을 남겼습니다";
      break;
    case 4:
      msg = "회원님의 댓글에 답글을 남겼습니다";
      break;
    case 5:
      msg = "회원님의 페이퍼에 댓글을 남겼습니다";
      break;
    case 9:
      msg = `회원님의 책${bookname}을 구독했습니다`;
      break;
    case 10:
      msg = "회원님의 스레드를 스크랩했습니다";
      break;
    case 11:
      msg = "회원님을 페이퍼에서 언급했습니다";
      break;
    case 12:
      msg = "회원님의 페이퍼를 좋아합니다";
      break;
    default:
      msg = "새로운 알림이 도착했습니다";
  }

  toast(`${nickname}님이 ${msg}`, {
    icon: "🔔",
  });
};

const useStompStore = create((set) => ({
  stompClient: null,
  setStompClient: (client) => set({ stompClient: client }),

  connect: (email, setNewNotification) => {
    const token = localStorage.getItem("accessToken");
    const client = new Client({
      webSocketFactory: () => new SockJS("https://i10b207.p.ssafy.io/ws"),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    client.onConnect = function () {
      client.subscribe(`/sub/notification/${email}`, (msg) => {
        setNewNotification(true);
        const alarmObj = JSON.parse(msg.body);
        showAlarm(alarmObj);
      });

      set({ stompClient: client });
    };

    client.activate();
  },
}));

export default useStompStore;
