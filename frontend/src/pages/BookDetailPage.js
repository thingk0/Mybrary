import React, { useState, useEffect } from "react";
import Container from "../components/frame/Container";
import styles from "./style/BookDetailPage.module.css";

export default function BookDetailPage() {
  const [animate, setAnimate] = useState(false);
  const totalPages = 10;
  const [currentPage, setCurrentPage] = useState(0);

  const [flipping, setFlipping] = useState(false); // 페이지가 접히는 상태 관리

  const nextPage = () => {
    if (!flipping && currentPage < totalPages - 1) {
      setFlipping(true);
      setTimeout(() => {
        setCurrentPage(currentPage + 2);
        setFlipping(false);
      }, 600); // 전체 애니메이션 시간에 맞춰서 페이지 변경
    }
  };

  const prevPage = () => {
    if (!flipping && currentPage > 0) {
      setFlipping(true);
      setTimeout(() => {
        setCurrentPage(currentPage - 2);
        setFlipping(false);
      }, 600); // 전체 애니메이션 시간에 맞춰서 페이지 변경
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      setAnimate(true);
    }, 100);
    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      <Container>
        <div>
          <div>뒤로가기</div>
        </div>
        <div className={styles.body}>
          <div className={styles.middle}>
            <div className={`${styles.book} ${animate ? styles.animate : ""}`}>
              {currentPage > 0 && (
                <div
                  className={`${styles.leftpage} ${
                    flipping ? styles.flipLeft : ""
                  }`}
                  onClick={prevPage}
                >
                  머임? {currentPage}
                </div>
              )}
              {currentPage < totalPages - 1 && (
                <div
                  className={`${styles.rightpage} ${
                    flipping ? styles.flipRight : ""
                  }`}
                  onClick={nextPage}
                >
                  Page {currentPage + 1}
                </div>
              )}
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
