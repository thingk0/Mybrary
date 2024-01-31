// import * as Stomp from "stompjs";
// import { StompClientContext } from "./StompClientContext";
// import { useStompClient } from "../../store/StompClientContext";
// import SockJS from "sockjs-client";
// import { Client } from "@stomp/stompjs";

//로그인요청
export async function login(user) {
  try {
    const response = await fetch("/api/v1/member/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

// //웹소켓 연결
// export async function ConnectWebSocket(email) {
//   // const { setStompClient } = useStompClient();

//   const socket = new SockJS("http://localhost:8080/ws");
//   const client = new Client({
//     brokerURL: "ws://localhost:8080/ws", // brokerURL이 http 일경우 ws를 https일 경우 wss를 붙여서 사용
//     webSocketFactory: () => socket, // 웹소켓 인스턴스를 반환하는 팩토리 함수, sockJS를 사용할 경우 팩토리 함수를 지정해야 함.
//   });

//   client.onConnect = () => {
//     console.log("Connected!");

//     /* 구독 설정. 여러 주소를 구독할 수 있다. */
//     // 1. 내가 어떤 주소를 구독할 지
//     // 2. 내가 구독한 주소로부터의 리턴 값을 어떻게 처리할 지
//     client.current.subscribe("/sub/chat/" + email, (message) => {
//       // 알림아이콘 빨갛게 처리하고, toast ui 띄워주기
//       //onMessageReceived(message);
//     });

//     client.current.subscribe("/sub/alarm/" + email, (message) => {
//       // 채팅에 안읽은 메시지 처리하기
//     });
//   };

//   client.activate(); // 설정을 마친 후 활성화
//   // setStompClient(client);
// }
