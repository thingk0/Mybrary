import styles from "./Loading.module.css";
import animationData from "../../assets/lottie/loading.json";
import React from "react";
import Container from "../frame/Container.js";
import LottieAnimation from "./LottieAnimation.js";
export default function Loading() {
  return (
    <>
      <Container>
        <div className={styles.modalContainer}>
          <div className={styles.modal}>
            <LottieAnimation animationPath={animationData} />
          </div>
        </div>
      </Container>
    </>
  );
}
