import Container from "../components/frame/Container";
import SharedPaper from "../components/paperplane/SharedPaper";
import styles from "./style/PaperplanePage.module.css";
import 종이비행기 from "../assets/종이비행기.png";
import { useEffect, useState, useRef } from "react";
import useStompStore from "../store/useStompStore";
import useUserStore from "../store/useUserStore";
import { getChatList } from "../api/chat/Chat.js";
import ChatProfile from "../components/paperplane/ChatProfile.js";
import Iconuser2 from "../assets/icon/Iconuser2.png";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import useNotificationStore from "../store/useNotificationStore.js";

export default function PaperplanePage() {
  /* 웹소켓: 채팅용 주소를 구독 */
  const user = useUserStore((state) => state.user);
  const [chatRoomList, setChatRoomList] = useState([]); // 채팅방 리스트
  const [chatMessageList, setChatMessageList] = useState([]); // 접속중인 채팅방 채팅 내역
  const [nowChatRoom, setNowChatRoom] = useState({}); // 현재 내가 보고있는 채팅방 정보
  const [stompClient, setStompClient] = useState(null);
  const { connect } = useStompStore();
  const { setNewNotification } = useNotificationStore();
  const chatContainerRef = useRef(null); // 채팅 컨테이너에 대한 ref 스크롤 아래로 관리하기 윟마

  useEffect(() => {
    // 채팅방 리스트들을 조회, 나에게 오는 메시지들을 받아볼 수 있도록 구독

    (async function asyncGetChatList() {
      const res = await getChatList();
      setChatRoomList(res.data.content);
    })();

    async function socketConnect() {
      await connect(user.email, setNewNotification);
    }

    // 해당 페이지가 unmount 될 때, 즉 해당 페이지를 떠날 때
    // 오직 알림만을 위한 소켓 연결을 시도
    return () => {
      socketConnect();
    };
  }, []);

  //채팅 방이 바뀔 때마다 해당 채팅 방으로 새로운 구독을 발행
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    const client = new Client({
      webSocketFactory: () => new SockJS("https://i10b207.p.ssafy.io/ws"),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    client.onConnect = function () {
      client.subscribe(`/sub/chatroom/1`, (message) => {
        console.log("receive check!! ");
        console.log(message);
      });
    };

    client.activate();
    setStompClient(client);

    const scrollToBottom = () => {
      if (chatContainerRef.current) {
        chatContainerRef.current.scrollTop =
          chatContainerRef.current.scrollHeight;
      }
    };

    scrollToBottom(); // 컴포넌트 마운트 시 실행
  }, []);

  useEffect(() => {
    // 이제 채팅방 메시지 조회 함수 여기서 불러와야 함
    console.log(nowChatRoom);
  }, [nowChatRoom]);

  useEffect(() => {
    // 채팅 메시지 컨테이너의 스크롤을 맨 아래로 이동
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  }, [chatMessageList]); // chatMessageList가 변경될 때마다 실행

  const sendMessage = () => {
    const messageObject = {
      chatRoomId: 1,
      senderId: user.memberId,
      message: "안녕하세요",
      threadId: 1,
    };

    console.log(stompClient);
    const destination = `/pub/chat/1/send`;
    const bodyData = JSON.stringify(messageObject);
    stompClient.publish({ destination, body: bodyData });
  };

  const handleSelectChatRoom = (chatRoom) => {
    setNowChatRoom(chatRoom);
  };

  return (
    <>
      <Container backgroundColor={"#FFFAFA"}>
        <div className={styles.main}>
          <div className={styles.pipibox}>
            <div className={styles.member}>
              <div className={styles.pipi}>Paper Plane</div>
              <div className={styles.search}>
                <>
                  <form action="/search" method="get" style={{ width: "100%" }}>
                    <label htmlFor="search"></label>
                    <div className={styles.searchContainer}>
                      <button type="submit" className={styles.searchButton}>
                        검색
                      </button>
                      <input
                        type="text"
                        id="search"
                        name="q"
                        placeholder=""
                        className={styles.searchInput}
                      />
                    </div>
                  </form>
                </>
              </div>
              <div className={styles.users} style={{ marginTop: "15px" }}>
                {chatRoomList.length > 0 ? (
                  <>
                    {chatRoomList.map((chatRoom) => (
                      <ChatProfile
                        key={chatRoom.chatRoomId}
                        isSelected={
                          chatRoom.chatRoomId === nowChatRoom?.chatRoomId
                        }
                        onClick={() => handleSelectChatRoom(chatRoom)}
                        otherMemberNickname={chatRoom.otherMemberNickname}
                        otherMemberProfileImageUrl={
                          chatRoom.otherMemberProfileImageUrl
                        }
                        lastMessage={chatRoom.lastMessage}
                        unreadMessageCount={chatRoom.unreadMessageCount}
                      />
                    ))}
                  </>
                ) : (
                  <div className={styles.emptyList}>
                    검색해서, 새로운채팅을 시작해보세요 !
                  </div>
                )}
              </div>
            </div>
            <div className={styles.gori}>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
            </div>
            <div className={styles.chat}>
              {/* 채팅 헤더 및 마이브러리 가기 버튼 */}
              {true ? (
                <div>
                  <div className={styles.header}>
                    {/* 상대방 프로필 이미지와 닉네임 */}
                    <div className={styles.이미지닉네임}>
                      <img
                        src={
                          nowChatRoom.otherMemberProfileImageUrl
                            ? `https://jingu.s3.ap-northeast-2.amazonaws.com/${nowChatRoom.otherMemberProfileImageUrl}`
                            : Iconuser2
                        } // 선택된 이미지 또는 기본 이미지
                        alt="프로필"
                        style={{
                          width: "23%",
                          objectFit: "cover",
                          borderRadius: "50%",
                        }}
                      />
                      <div>{nowChatRoom.otherMemberNickname}</div>
                    </div>
                    <button className={styles.마이브러리가기}>
                      마이브러리 가기
                    </button>
                  </div>
                  {/* 채팅 메시지 내용 */}
                  <div className={styles.middle}>
                    <div className={styles.textmain}>
                      <div
                        className={styles.chatContainer}
                        ref={chatContainerRef}
                      >
                        <div
                          className={`${styles.message} ${
                            true ? styles.sender : styles.receiver
                          }`}
                        >
                          ㅋㅋㅋㅋㅋㅋ 개웃겨 이거 봄? ㅋㅋㅋㅋㅋㅋ 개웃겨 이거
                          봄? ㅋㅋㅋㅋㅋㅋ 개웃겨 이거 봄? ㅋㅋㅋㅋㅋㅋ 개웃겨
                          이거 봄?
                        </div>
                        <div
                          className={`${styles.message} ${
                            false ? styles.sender : styles.receiver
                          }`}
                        >
                          안녕
                        </div>

                        <SharedPaper />
                        <SharedPaper />
                      </div>
                    </div>
                    {/* 메시지 입력창 */}
                    <div className={styles.sendbox}>
                      <textarea
                        placeholder="메시지를 보내세요"
                        rows="1" // 초기에 표시할 줄의 수
                        style={{
                          flexGrow: 1,
                          marginLeft: "10px",
                          marginRight: "10px",
                          padding: "10px",
                          borderRadius: "20px",
                          border: "none",
                          outline: "none",
                          backgroundColor: "#eee3dd", // 배경색을 디자인에 맞게 조정
                          color: "#57423f", // 글자 색상 조정
                          fontSize: "16px", // 글자 크기 조정
                          resize: "none", // 사용자가 크기 조절하지 못하도록 설정
                          overflow: "auto", // 내용이 넘칠 때 스크롤바 자동 생성
                        }}
                        onKeyDown={() => {
                          //엔터 누르면 전송, 쉬프트 + 엔터는 줄바꿈
                        }}
                      />

                      <img
                        className={styles.종이비행기이미지}
                        src={종이비행기}
                        style={{
                          width: "60px",
                          objectFit: "contain",
                          marginRight: "15px",
                          cursor: "pointer",
                        }}
                        alt="전송버튼"
                        onClick={sendMessage}
                      ></img>
                    </div>
                  </div>
                </div>
              ) : (
                <div className={styles.채팅방이없어요}>
                  <div>Paper Plane</div>
                  <img
                    className={styles.종이비행기이미지}
                    src={종이비행기}
                    alt=""
                  />
                  <div>팔로우하고있는 사람에게 종이비행기를 날려보세요.</div>
                </div>
              )}
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
