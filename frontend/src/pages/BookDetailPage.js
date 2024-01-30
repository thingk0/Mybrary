import React, { useEffect, useState } from "react";
import Container from "../components/frame/Container";
import styles from "./style/BookDetailPage.module.css";

export default function BookDetailPage() {
  const [animate, setAnimate] = useState(false);

  useEffect(() => {
    // 마운트 후 약간의 지연을 두고 애니메이션 상태를 변경
    const timer = setTimeout(() => {
      setAnimate(true);
    }, 100); // 100ms 지연

    // 컴포넌트가 언마운트될 때 타이머 정리
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
            <div
              className={`${styles.book} ${
                animate ? styles["book-animate"] : ""
              }`}
            ></div>
          </div>
        </div>
      </Container>
    </>
  );
}
