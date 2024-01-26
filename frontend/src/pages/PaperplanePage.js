import Container from "../components/frame/Container";
import styles from "./style/PaperplanePage.module.css";
import 예시이미지2 from "../assets/예시이미지2.png";
import 이미지예시 from "../assets/이미지예시.png";
import 종이비행기 from "../assets/종이비행기.png";
import { useState } from "react";

export default function PaperplanePage() {
  // const [chatRoomList, setChatRoomList] = useState([]);
  const [chatRoomId, setChatRoomId] = useState();

  // const chatRoomId = "room123"; // 채팅방 ID
  const chatRoomList = [
    {
      chatRoomId: "room1",
      member: {
        memberId: "member1",
        name: "John Doe",
        nickname: "Johnny",
        memberImageUrl: "https://example.com/path/to/image1.jpg",
      },
      recentMessage: "안녕하세요, 어제 보내신 메시지 잘 받았습니다.",
      messagesCount: 2,
    },
    {
      chatRoomId: "room2",
      member: {
        memberId: "member2",
        name: "Jane Smith",
        nickname: "Janey",
        memberImageUrl: "https://example.com/path/to/image2.jpg",
      },
      recentMessage: "프로젝트 관련 회의 언제 하면 좋을까요?",
      messagesCount: 5,
    },
    {
      chatRoomId: "room3",
      member: {
        memberId: "member3",
        name: "Alice Johnson",
        nickname: "Alice",
        memberImageUrl: "https://example.com/path/to/image3.jpg",
      },
      recentMessage: "내일 저녁에 시간 어때요?",
      messagesCount: 1,
    },
    // ... 더 많은 채팅방 데이터를 추가할 수 있습니다
  ];

  const charMessageList = [
    {
      messageId: "msg1",
      sender: {
        senderId: "내 아이디",
        senderNickname: "내 닉네임",
        senderImageUrl: "내 프로필 이미지 URL",
      },
      message: "안녕하세요!", // 메시지 내용
      thread: null, // 스레드 정보가 없는 경우
      isRead: true,
      time: "2024-01-23 10:00:00",
    },
    {
      messageId: "msg2",
      sender: {
        senderId: "상대방 아이디",
        senderNickname: "상대방 닉네임",
        senderImageUrl: "상대방 프로필 이미지 URL",
      },
      message: "안녕하세요! 반갑습니다.", // 메시지 내용
      thread: null, // 스레드 정보가 없는 경우
      isRead: false,
      time: "2024-01-23 10:05:00",
    },
    {
      messageId: "msg2",
      sender: {
        senderId: "상대방 아이디",
        senderNickname: "상대방 닉네임",
        senderImageUrl: "상대방 프로필 이미지 URL",
      },
      message:
        "안녕하2ssssssssssssssss\nsssssssssssssssssssssssssssssssssssssssss ssssssssssssssssssssssssss\nssss", // 메시지 내용
      thread: null, // 스레드 정보가 없는 경우
      isRead: false,
      time: "2024-01-23 10:05:00",
    },
    {
      messageId: "msg3",
      sender: {
        senderId: "내 아이디",
        senderNickname: "내 닉네임",
        senderImageUrl: "내 프로필 이미지 URL",
      },
      message: null, // 메시지 내용이 없는 경우
      thread: {
        threadId: "thread1",
        paperId: "paper123",
        imageUrl: "페이퍼 대표 이미지 썸네일 URL",
        memberId: "작성자 아이디",
        nickname: "cwnsgh",
        memberImageUrl: "작성자 프로필 이미지 URL",
      }, // 스레드 정보
      isRead: true,
      time: "2024-01-23 10:10:00",
    },
    // 다른 메시지들...
  ];

  return (
    <>
      <Container backgroundColor={"#FFFAFA"}>
        <div className={styles.main}>
          <div className={styles.pipibox}>
            <div className={styles.member}>
              <div className={styles.pipi}>Paper Plane</div>
              <div className={styles.search}>
                <>
                  <form action="/search" method="get">
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
              <div className={styles.users}>
                {chatRoomList.length > 0 ? (
                  chatRoomList.map((list) => (
                    <div
                      className={styles.user}
                      key={list.chatRoomId} // Make sure each child in a list has a unique "key" prop.
                      onClick={() => setChatRoomId(list.chatRoomId)} // Set chatRoomId to the id of the clicked chat room
                    >
                      <div className={styles.유저이미지}>
                        {/* 여기에 실제 이미지 URL을 사용하거나, 상대방 프로필 이미지를 배경으로 설정 */}
                        <img
                          src={list.member.memberImageUrl}
                          alt="프로필 이미지"
                        />
                      </div>
                      <div className={styles.유저정보}>
                        <div>{list.member.nickname}</div>
                        <div>{list.recentMessage}</div>
                      </div>
                      <div className={styles.유저점점점}>. . .</div>
                    </div>
                  ))
                ) : (
                  // 리스트가 비어 있는 경우 표시할 메시지나 요소
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
              {chatRoomId != null ? (
                <div>
                  <div className={styles.header}>
                    {/* 상대방 프로필 이미지와 닉네임 */}
                    <div className={styles.이미지닉네임}>
                      <div>프로필이미지</div>
                      <div>닉네임</div>
                    </div>
                    <button className={styles.마이브러리가기}>
                      마이브러리 가기
                    </button>
                  </div>
                  {/* 채팅 메시지 내용 */}
                  <div className={styles.middle}>
                    <div className={styles.textmain}>
                      {charMessageList.map((message) => (
                        <div
                          key={message.messageId}
                          className={
                            message.sender.senderId === "내 아이디" // 발신자 ID 비교로 내 메시지와 상대방 메시지 구별
                              ? styles.내가보낸메시지
                              : styles.상대가보낸메시지
                          }
                        >
                          {/* 메시지 내용 혹은 스레드 내용 렌더링 */}
                          {message.message ? (
                            <div className={styles.메세지}>
                              <div className={styles.메세지최대길이}>
                                {message.message}
                              </div>
                            </div>
                          ) : (
                            <div className={styles.쓰레드}>
                              {/* 스레드 내용 렌더링 */}
                              <div className={styles.쓰레드헤더}>
                                <img
                                  className={styles.유저이미지2}
                                  src={이미지예시}
                                />
                                <div>
                                  {message.thread.nickname} 님이 작성한 페이퍼
                                </div>
                              </div>
                              <img
                                className={styles.예시이미지2}
                                src={예시이미지2}
                              />
                            </div>
                          )}
                        </div>
                      ))}
                    </div>
                    {/* 메시지 입력창 */}
                    <div className={styles.sendbox}>
                      <span className={styles.예제}>메세지를 보내세요</span>
                      <button className={styles.비행기}>비행기</button>
                    </div>
                  </div>
                </div>
              ) : (
                <div className={styles.채팅방이없어요}>
                  <div>Paper Plane</div>
                  <img className={styles.종이비행기이미지} src={종이비행기} />
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
