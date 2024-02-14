import Container from "../components/frame/Container";
import styles from "./style/ErrorPage.module.css";
import React from "react";
import { Link } from "react-router-dom";
import animationData from "../assets/lottie/image.json";
import LottieAnimation from "../components/common/LottieAnimation.js";
export default function ErrorPage() {
  return (
    <>
      <Container>
        <div className={styles.main}>
          <div className={styles.문구모음}>
            <span className={styles.앗}>앗!!!</span>
            <span className={styles.잘못된}> 잘못된 접근입니다 ! </span>
            <span>다시확인해주세요</span>
            <Link to="/join">
              <button className={styles.로그인페이지이동}>
                로그인페이지로 이동하기
              </button>
            </Link>
          </div>
          <div className={styles.이미지공간}>
            <LottieAnimation animationPath={animationData} />
          </div>
        </div>
      </Container>
    </>
  );
}
