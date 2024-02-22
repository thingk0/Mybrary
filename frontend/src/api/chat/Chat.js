import axios from "axios";
const BASE_URL = "https://i10b207.p.ssafy.io/api/v1/";

/* 채팅방 내 메시지 리스트 조회 */
export async function getMessageList(roomid) {
  try {
    const response = await axios.get(BASE_URL + `chat/${roomid}/message`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 자신의 채팅방 리스트 조회 */
export async function getChatList() {
  try {
    const response = await axios.get(BASE_URL + "chat");
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 회원정보에서 채팅 시작 */
export async function getFirstChat(memberId) {
  try {
    const response = await axios.get(BASE_URL + `chat/message`, {
      params: { memberId },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

// /* 채팅 메시지 보내기 */
// export async function sendMessage(roomid, object) {
//   try {
//     const response = await axios.post(
//       BASE_URL + `chat/${roomid}/message`,
//       object
//     );
//     return response.data;
//   } catch (error) {
//     throw error;
//   }
// }

/* 채팅방 나가기 */
export async function deleteChatRoom(roomid) {
  try {
    const response = await axios.delete(BASE_URL + `chat/${roomid}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
