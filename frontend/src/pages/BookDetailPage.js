import React, { useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import Container from "../components/frame/Container";
import styles from "./style/BookDetailPage.module.css";
import s from "classnames";

export default function BookDetailPage() {
  // 페이지를 구성하는 컨텐츠를 생성합니다. 여기서는 예시로 간단한 텍스트를 사용합니다.
  const bookRef = useRef();
  const [curPage, setCurPage] = useState();

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

  const onChangeState = (instance) => {
    // console.log("instance", instance);
  };

  const pages = [
    <div className={styles.demoPage}>페이지 1</div>,
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
    <div className={s(styles.bookContainer)}>
      <button onClick={() => onPrev("Y")}>이전 페이지</button>
      <HTMLFlipBook
        ref={bookRef}
        width={600}
        height={800}
        showCover={true}
        maxShadowOpacity={0.2}
        autoSize={true}
        usePortrait={true}
        mobileScrollSupport={true}
        flippingTime={1000}
        clickEventForward={false}
        onFlip={onFlip}
        useMouseEvents={true}
        onChangeState={onChangeState}
        onUpdate={(e) => {
          console.log(e);
        }}
        swipeDistance={30}
        drawShadow={true}
        isClickFlip={false}
        // isClickFlip={false}
        // clickEventForward={false}
      >
        {pages.map((page, index) => (
          <div className={styles.page} key={index}>
            {page}
          </div>
        ))}
      </HTMLFlipBook>
      <button onClick={() => onNext("Y")}>다음 페이지</button>
      <div>
        {curPage === 7 && "마지막 페이지입니다."}
        {curPage === 0 && "첫 페이지입니다."}
      </div>
    </div>
  );
}
