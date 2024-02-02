import React from "react";
import HTMLFlipBook from "react-pageflip";
import Container from "../components/frame/Container";
import styles from "./style/BookDetailPage.module.css";
import s from "classnames";

export default function BookDetailPage() {
  // 페이지를 구성하는 컨텐츠를 생성합니다. 여기서는 예시로 간단한 텍스트를 사용합니다.
  const pages = [
    <div className={styles.demoPage}>Page 1</div>,
    <div className={styles.demoPage}>Page 2</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    <div className={styles.demoPage}>Page 3</div>,
    <div className={styles.demoPage}>Page 4</div>,
    // 추가 페이지를 여기에 삽입
  ];

  return (
    <Container>
      <div className={s(styles.bookContainer)}>
        <HTMLFlipBook
          width={400}
          height={500}
          showCover={true}
          maxShadowOpacity={0.2}
        >
          {pages.map((page, index) => (
            <div className={styles.page} key={index}>
              {page}
            </div>
          ))}
        </HTMLFlipBook>
      </div>
    </Container>
  );
}
