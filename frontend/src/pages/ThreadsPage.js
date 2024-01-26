import Container from "../components/frame/Container";
import React, { useState, useEffect } from "react";
import styles from "./style/ThreadsPage.module.css";
import bearImage from "../assets/예시이미지2.png";
import heart from "../assets/heart.png";
import msg from "../assets/msg.png";
import clip from "../assets/clip.png";
import human from "../assets/이미지예시.png";
import 오른쪽 from "../assets/오른쪽.png";
import 왼쪽 from "../assets/왼쪽.png";
export default function ThreadsPage() {
  const [groupedData, setGroupedData] = useState(new Map());

  const [hoverStyle, setHoverStyle] = useState({});

  // 마우스 위치에 따라 카드가 움직이는 함수
  const handleMouseMove = (event, threadId) => {
    const x = event.nativeEvent.offsetX;
    const y = event.nativeEvent.offsetY;
    const rotateY = ((-1 / 5) * x + 20) / 3;
    const rotateX = ((4 / 30) * y - 20) / 5;

    setHoverStyle({
      ...hoverStyle,
      [threadId]: {
        overlay: {
          backgroundPosition: `${x / 5 + y / 5 + 5}%`,
        },
        thread: {
          transform: `perspective(350px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`,
        },
      },
    });
  };

  // onMouseOut 이벤트 핸들러
  // 마우스가 요소를 벗어날 때 스타일을 초기화.
  const handleMouseOut = (threadId) => {
    setHoverStyle({
      ...hoverStyle,
      [threadId]: {
        overlay: {
          filter: "opacity(1)",
        },
        thread: {
          transform: "perspective(350px) rotateY(0deg) rotateX(0deg)",
        },
      },
    });
  };

  useEffect(() => {
    // 목데이터 생성
    const threadList = [
      {
        threadId: 1,
        imageUrl: bearImage,
        likeCount: 10,
        commentCount: 5,
        scrapCount: 3,
        createdDate: "2023-08-01T12:00:00.000Z",
      },
      {
        threadId: 2,
        imageUrl: bearImage,
        likeCount: 20,
        commentCount: 2,
        scrapCount: 8,
        createdDate: "2023-06-15T12:00:00.000Z",
      },
      {
        threadId: 3,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-02-01T12:00:00.000Z",
      },
      {
        threadId: 4,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2020-02-01T12:00:00.000Z",
      },
      {
        threadId: 5,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 6,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 7,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 8,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 9,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 10,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-01-01T12:00:00.000Z",
      },
      {
        threadId: 11,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2023-08-01T12:00:00.000Z",
      },
      {
        threadId: 12,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2022-02-01T12:00:00.000Z",
      },
      {
        threadId: 13,
        imageUrl: bearImage,
        likeCount: 5,
        commentCount: 1,
        scrapCount: 2,
        createdDate: "2021-01-01T12:00:00.000Z",
      },
    ];

    const grouped = new Map();

    threadList.forEach((thread) => {
      const date = new Date(thread.createdDate);
      const yearMonth = `${date.getFullYear()}년 ${String(
        date.getMonth() + 1
      ).padStart(2, "0")}월`; // 월을 2자리 숫자로 표시

      if (!grouped.has(yearMonth)) {
        grouped.set(yearMonth, []);
      }

      grouped.get(yearMonth).push(thread);
    });

    setGroupedData(grouped);
  }, []);
  return (
    <>
      {/* - 스레드 리스트(threadList)
[
  - 스레드ID(threadId)
  - 스레드대표이미지주소(imageUrl)         
  - 좋아요수(likeCount)
  - 댓글수(commentCount)
  - 스크랩수(scrapCount)
  - 생성날짜(createdDate)
] */}
      <Container>
        <div>
          <div className={styles.뒤로가기}>&lt; 뒤로가기</div>
        </div>
        <div className={styles.body}>
          <div className={styles.threadpage}>
            <div className={styles.bodyhead}>
              <div className={styles.롤링페이퍼}>&lt; 롤링페이퍼</div>
              <div className={styles.모든게시물}>cwnsgh's papers</div>
              <div className={styles.책장}> 책장 &gt;</div>
            </div>
          </div>
        </div>
        <div className={styles.center}>
          <div className={styles.mainbook}>
            {/* 그룹화된 데이터 렌더링 */}
            {[...groupedData.keys()].map((yearMonth) => (
              <div key={yearMonth} className={styles.년도별}>
                <div className={styles.책}>
                  <img className={styles.날짜막대기} src={왼쪽} />
                  <span className={styles.fontsize2}>{yearMonth}</span>
                  <img className={styles.날짜막대기} src={오른쪽} />
                </div>{" "}
                {/* 년-월 표시 */}
                <div className={styles.년도별스레드}>
                  {groupedData.get(yearMonth).map((thread) => (
                    <div
                      key={thread.threadId}
                      className={styles.thread}
                      onMouseMove={(e) => handleMouseMove(e, thread.threadId)}
                      onMouseOut={() => handleMouseOut(thread.threadId)}
                      style={hoverStyle[thread.threadId]?.thread}
                    >
                      <div
                        className={styles.overlay}
                        style={hoverStyle[thread.threadId]?.overlay}
                      ></div>
                      <div className={styles.좋댓스}>
                        <div className={styles.작성자}>
                          <img className={styles.userimage} src={human} />
                          <span className={styles.작성자폰트}>cwnsgh </span>
                        </div>
                        <div className={styles.나머지좋댓스}>
                          <div className={styles.infobox}>
                            <img
                              className={styles.userimage}
                              src={heart}
                              alt=""
                            />
                            <div className={styles.fontsize1}>
                              {" "}
                              {thread.likeCount}
                            </div>
                          </div>
                          <div className={styles.infobox}>
                            <img
                              className={styles.userimage}
                              src={msg}
                              alt=""
                            />
                            <div className={styles.fontsize1}>
                              {" "}
                              {thread.commentCount}
                            </div>
                          </div>
                          <div className={styles.infobox}>
                            <img
                              className={styles.userimage}
                              src={clip}
                              alt=""
                            />
                            <div className={styles.fontsize1}>
                              {" "}
                              {thread.scrapCount}
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className={styles.main이미지}>
                        <img
                          className={styles.스레드이미지}
                          src={thread.imageUrl}
                          alt={`스레드 ${thread.threadId}`}
                        />
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        </div>
      </Container>
    </>
  );
}
