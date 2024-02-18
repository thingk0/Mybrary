import { create } from "zustand";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs"; // Stomp.Client로도 사용 가능
import toast from "react-hot-toast";

const msgAlarm = (nickname) => {
  toast(`${nickname}님으로부터 메시지 도착`, {
    icon: "📩",
  });
};

const showAlarm = (alarmObj) => {
  const type = alarmObj.notifyType;
  const nickname = alarmObj.sender.nickname;
  const bookname = alarmObj.bookName;
  let msg = "";
  let iconMsg = "🔔";

  if (type === 13) {
    // 현재 경로 확인
    const currentPath = window.location.pathname;

    // /paperplane 경로 확인 (쿼리 스트링 제외)
    const isChatPage = currentPath.startsWith("/paperplane");
    if (isChatPage) return;

    // 채팅 페이지가 아닐 때만 알람 표시
    msgAlarm(nickname);

    return;
  }

  switch (type) {
    case 1:
      msg = "팔로우를 요청했습니다";
      iconMsg = "🤝";
      break;
    case 2:
      msg = "회원님을 팔로우했습니다";
      iconMsg = "🤝";
      break;
    case 3:
      msg = "회원님의 페이퍼에 댓글을 남겼습니다";
      iconMsg = "💬";
      break;
    case 4:
      msg = "회원님의 댓글에 답글을 남겼습니다";
      iconMsg = "💬";
      break;
    case 5:
      msg = "회원님의 페이퍼에 댓글을 남겼습니다";
      iconMsg = "💬";
      break;
    case 9:
      msg = `회원님의 책 ${bookname}을 구독했습니다`;
      iconMsg = "📚";
      break;
    case 10:
      msg = "회원님의 스레드를 스크랩했습니다";
      iconMsg = "🔗";
      break;
    case 11:
      msg = "회원님을 페이퍼에서 언급했습니다";
      iconMsg = "📢";
      break;
    case 12:
      msg = "회원님의 페이퍼를 좋아합니다";
      iconMsg = "💕";
      break;
    default:
      msg = "종을 흔들었습니다";
  }

  toast(`${nickname}님이 ${msg}`, {
    icon: iconMsg,
  });
};

const useStompStore = create((set) => ({
  stompClient: null,
  setStompClient: (client) => set({ stompClient: client }),

  connect: (email, setHasNewNotification) => {
    const token = localStorage.getItem("accessToken");
    const client = new Client({
      webSocketFactory: () => new SockJS("https://i10b207.p.ssafy.io/ws"),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      reconnectDelay: 5000,
    });

    client.onConnect = function () {
      console.log("재연결");
      client.subscribe(`/sub/notification/${email}`, (msg) => {
        setHasNewNotification(true);
        const alarmObj = JSON.parse(msg.body);
        showAlarm(alarmObj);
      });

      set({ stompClient: client });
    };

    client.activate();
  },

  disconnect: () => {
    if (useStompStore.getState().stompClient) {
      useStompStore
        .getState()
        .stompClient.deactivate()
        .then(() => {
          console.log("웹소켓 연결이 종료되었습니다.");
          set({ stompClient: null });
        })
        .catch((error) => {
          console.error("웹소켓 연결 종료 중 오류 발생:", error);
        });
    }
  },
}));

export default useStompStore;
