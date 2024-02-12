import React, { useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import styles from "./style/BookDetailPage.module.css";
import Modal from "../components/common/Modal";
import s from "classnames";
import FeedModal from "../components/feed/FeedModal";

export default function BookDetailPage() {
  // 페이지를 구성하는 컨텐츠를 생성합니다. 여기서는 예시로 간단한 텍스트를 사용합니다.
  const bookRef = useRef();
  const [curPage, setCurPage] = useState();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleClick = (e) => {
    e.preventDefault(); // 이벤트의 기본 동작을 막음
  };

  const onPrev = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
    console.log("pageIndex", pageIndex);
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipPrev("bottom");
    } else {
      bookRef.current.pageFlip().turnToPrevPage();
    }
  };

  const onNext = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
    console.log("pageIndex", pageIndex);
    // 마지막 페이지에 도달했을 때
    if (pageIndex === 7) return;
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipNext();
    } else {
      bookRef.current.pageFlip().turnToNextPage();
    }
  };

  // 플립이 끝난 후
  const onFlip = (e) => {
    const curPage = e.data;
    setCurPage(curPage);
    console.log("flip", e);
  };

  const pages = [
    <div className={styles.demoPage}>
      <button>sfdda</button>
    </div>,
    <div className={styles.demoPage}>페이지 2</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    <div className={styles.demoPage}>페이지 3</div>,
    <div className={styles.demoPage}>페이지 4</div>,
    // 추가 페이지를 여기에 삽입
  ];

  return (
    <>
      <div className={s(styles.bookContainer)}>
        <div className={styles.header}>
          <div className={styles.headerLeft}>내 책장에 담기</div>
          <div>책 이름</div>
          <div
            onClick={() => {
              setIsModalOpen(true);
            }}
            className={styles.headerLeft}
          >
            옵션
            <FeedModal
              width="200px"
              setIsModalOpen={setIsModalOpen}
              isModalOpen={isModalOpen}
              right="-5px"
              top="10px"
              header={"옵션"}
            >
              <div>책 카테고리 수정</div>
              <div>책 표지 수정</div>
              <div>책 삭제</div>
              <div>책 구독취소</div>
            </FeedModal>
          </div>
        </div>
        <HTMLFlipBook
          onClick={() => handleClick()}
          ref={bookRef}
          width={600}
          height={800}
          onFlip={onFlip}
          maxShadowOpacity={0.2}
          clickEventForward={true}
          useMouseEvents={false}
        >
          {pages.map((page, index) => (
            <div className={styles.page} key={index} onClick={handleClick}>
              {page}
            </div>
          ))}
        </HTMLFlipBook>
        <button onClick={() => onPrev("Y")}>이전 페이지</button>
        <button onClick={() => onNext("Y")}>다음 페이지</button>
        <div>
          {curPage === 7 && "마지막 페이지입니다."}
          {curPage === 0 && "첫 페이지입니다."}
        </div>
      </div>
    </>
  );
}
