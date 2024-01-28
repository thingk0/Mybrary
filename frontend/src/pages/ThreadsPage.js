import Container from "../components/frame/Container";
import React, { useState, useEffect } from "react";
import styles from "./style/ThreadsPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import bearImage from "../assets/예시이미지2.png";
import 오른쪽 from "../assets/오른쪽.png";
import 왼쪽 from "../assets/왼쪽.png";
import Thread from "../components/common/Thread";
import { useNavigate } from "react-router-dom";

export default function ThreadsPage() {
  const navigate = useNavigate();
  const [groupedData, setGroupedData] = useState(new Map());

  useEffect(() => {
    // 목데이터 생성
    /* 
  - 스레드 리스트(threadList)
  [
  - 스레드ID(threadId)
  - 스레드대표이미지주소(imageUrl)         
  - 좋아요수(likeCount)
  - 댓글수(commentCount)
  - 스크랩수(scrapCount)
  - 생성날짜(createdDate)
  ] 
  */
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
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate("../")}>
            &lt; 뒤로가기
          </div>
        </div>
        <div className={title.title}>
          <div
            className={title.left_title}
            onClick={() => navigate("../rollingpaper")}
          >
            &lt; 롤링페이퍼
          </div>
          <div className={title.main_title}>cwnsgh's thread</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../bookshelf")}
          >
            {" "}
            책장 &gt;
          </div>
        </div>
        <div className={styles.mainbook}>
          {/* 그룹화된 데이터 렌더링 */}
          {[...groupedData.keys()].map((yearMonth) => (
            <div key={yearMonth} className={styles.년도별}>
              <div className={styles.책}>
                <img className={styles.날짜막대기} src={왼쪽} alt="" />
                <span className={styles.fontsize2}>{yearMonth}</span>
                <img className={styles.날짜막대기} src={오른쪽} alt="" />
              </div>{" "}
              {/* 년-월 표시 */}
              <div className={styles.년도별스레드}>
                {groupedData.get(yearMonth).map((thread) => (
                  <Thread thread={thread} user="cwnsgh" />
                ))}
              </div>
            </div>
          ))}
        </div>
      </Container>
    </>
  );
}
