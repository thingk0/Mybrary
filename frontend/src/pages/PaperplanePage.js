import Container from "../components/frame/Container";
import styles from "./style/PaperplanePage.module.css";
import 예시이미지2 from "../assets/예시이미지2.png";
import 이미지예시 from "../assets/이미지예시.png";
import 종이비행기 from "../assets/종이비행기.png";
import { useEffect, useState } from "react";
import useStompStore from "../store/useStompStore";
import useUserStore from "../store/useUserStore";
import { getChatList } from "../api/chat/Chat.js";
import gomimg from "../assets/곰탱이.png";
import ChatProfile from "../components/paperplane/ChatProfile.js";
import Iconuser2 from "../assets/icon/Iconuser2.png";

export default function PaperplanePage() {
  /* 웹소켓: 채팅용 주소를 구독 */
  const stompClient = useStompStore((state) => state.stompClient);
  const user = useUserStore((state) => state.user);
  const [chatRoomList, setChatRoomList] = useState([]); // 채팅방 리스트
  const [chatMessageList, setChatMessageList] = useState([]); // 접속중인 채팅방 채팅 내역
  const [chatRoomId, setChatRoomId] = useState(null); // 현재 내가 보고 있는 채팅방의 아이디

  useEffect(() => {
    // 채팅방 리스트들을 조회, 새로운 구독
  }, []);

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
                {true ? (
                  <>
                    <ChatProfile isSelected={true}></ChatProfile>
                    <ChatProfile isSelected={false}></ChatProfile>
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
                        src={Iconuser2} // 선택된 이미지 또는 기본 이미지
                        alt="프로필"
                        style={{
                          width: "23%",
                          objectFit: "cover",
                          borderRadius: "50%",
                        }}
                      />
                      <div>manmangi_98</div>
                    </div>
                    <button className={styles.마이브러리가기}>
                      마이브러리 가기
                    </button>
                  </div>
                  {/* 채팅 메시지 내용 */}
                  <div className={styles.middle}>
                    <div className={styles.textmain}>
                      <div className={styles.chatContainer}>
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
                        onKeyDown={(e) => {
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
                        }}
                        alt="전송버튼"
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
