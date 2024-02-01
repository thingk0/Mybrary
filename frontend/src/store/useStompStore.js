import { create } from "zustand";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs"; // Stomp.Client로도 사용 가능

const useStompStore = create((set) => ({
  stompClient: null,
  setStompClient: (client) => set({ stompClient: client }),
  connect: (email) => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws", // brokerURL이 http 일경우 ws를 https일 경우 wss를 붙여서 사용
      webSocketFactory: () => socket, // 웹소켓 인스턴스를 반환하는 팩토리 함수, sockJS를 사용할 경우 팩토리 함수를 지정해야 함.
    });

    client.onConnect = () => {
      console.log("Connected!");
      set({ stompClient: client });

      /* 구독 설정. 여러 주소를 구독할 수 있다. */
      // 1. 내가 어떤 주소를 구독할 지
      // 2. 내가 구독한 주소로부터의 리턴 값을 어떻게 처리할 지
      client.current.subscribe(`/sub/alarm/${email}`, (message) => {
        // 반환값을 처리할 내용
        // 일단 빨간 아이콘을 띄워야 한다. <- 해당 변수는 전역으로 관리. 모든 페이지에서 떠야함. (정확하게는 nav바)
        // 알림메시지를 라이브러리로 띄워야 한다. // 그래서 알림메시지도 전역으로 관리하는게 좋겠다. (일회성이므로 리스트로 관리하는게 아니라, 문자열로 관리)
      });
    };
    client.activate();
  },
  disconnect: () => {
    set((state) => {
      if (state.stompClient) {
        state.stompClient.disconnect();
        return { stompClient: null };
      }
    });
  },
}));

export default useStompStore;
