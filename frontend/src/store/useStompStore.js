import { create } from "zustand";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs"; // Stomp.Client로도 사용 가능

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

    client.onConnect = function (frame) {
      console.log("웹소켓 연결");
      console.log(frame);

      //client.subscribe(`/chat`);

      set({ stompClient: client });
    };

    client.activate();
  },
}));

export default useStompStore;
