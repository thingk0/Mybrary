import Container from "../components/frame/Container";
import LoginForm from "../components/join/LoginForm";
import SignUpForm from "../components/join/SignUpForm";
import styles from "./style/JoinPage.module.css";
import s from "classnames";
import { useState } from "react";
export default function JoinPage() {
  const [pageremote, setPageremote] = useState(true);

  return (
    <Container>
      <div className={s(styles.title, pageremote ? styles.left : styles.right)}>
        {pageremote ? "로그인" : "회원가입"}
      </div>

      <div className={styles.main}>
        <div
          className={s(
            styles.main_in,
            pageremote ? styles.leftMain : styles.rightMian
          )}
        >
          <div className={styles.main_left}>
            <LoginForm />
            <div className={styles.footer}>
              <div className={styles.footer_in}>
                <div>아직 회원이 아니신가요?</div>
                <div
                  className={styles.button}
                  onClick={() => setPageremote(!pageremote)}
                >
                  회원가입하기
                </div>
              </div>
            </div>
          </div>
          <div className={styles.main_img}>
            <div className={styles.logoImg}>MYBRARY</div>
            <div className={styles.logoText}>
              당신의 추억이 한페이지가 될 수 있도록
            </div>
          </div>

          <div className={styles.main_right}>
            <SignUpForm setPageremote={setPageremote} />
            <div className={styles.footer}>
              <div className={styles.footer_in}>
                <div>이미 회원이신가요?</div>
                <div
                  className={styles.button}
                  onClick={() => setPageremote(!pageremote)}
                >
                  로그인하기
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Container>
  );
}
