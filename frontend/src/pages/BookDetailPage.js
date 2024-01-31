import React, { useState, useEffect } from "react";
import Container from "../components/frame/Container";
import styles from "./style/BookDetailPage.module.css";

export default function BookDetailPage() {
  const [animate, setAnimate] = useState(false);
  const totalPages = 10;
  const [currentPage, setCurrentPage] = useState(0);

  const nextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 2);
    }
  };

  const prevPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 2);
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
                <div className={styles.leftpage} onClick={prevPage}>
                  머임? {currentPage}
                </div>
              )}
              {currentPage < totalPages - 1 && (
                <div className={styles.rightpage} onClick={nextPage}>
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
