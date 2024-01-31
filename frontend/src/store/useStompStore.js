import create from "zustand";
import * as Stomp from "stompjs";

const useStompStore = create((set) => ({
  stompClient: null,
  setStompClient: (client) => set({ stompClient: client }),
  connect: () => {
    const client = Stomp.over(new WebSocket("ws://your-websocket-server"));
    client.connect({}, () => {
      set({ stompClient: client });
    });
  },
  disconnect: () => {
    const client = get().stompClient;
    if (client) {
      client.disconnect();
      set({ stompClient: null });
    }
  },
}));

export default useStompStore;
