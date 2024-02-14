import Container from "../components/frame/Container";
import LoginForm from "../components/join/LoginForm";
import SignUpForm from "../components/join/SignUpForm";
import styles from "./style/JoinPage.module.css";
import 로고 from "../assets/MYBRARY.png";
import { useState } from "react";
export default function JoinPage() {
  const [pageremote, setPageremote] = useState(false);

  const togglePage = () => {
    setPageremote((prev) => !prev);
  };
  return (
    <>
      <Container>
        <div className={styles.midright}>
          <div className={styles.midrightmain}>
            {pageremote && <SignUpForm setPageremote={setPageremote} />}
            {!pageremote && (
              <>
                <div className={styles.rightheader}>
                  <div>당신의 추억이 한페이지가 될 수 있도록</div>
                  <img className={styles.로고이미지} src={로고} alt="" />
                </div>
                <LoginForm />
              </>
            )}

            <div className={styles.토글} onClick={togglePage}>
              {pageremote ? (
                <>
                  <div className={styles.로그인회원가입}>
                    이미 회원이신가요 ?
                    <div className={styles.로그인하기}>로그인하기</div>
                  </div>
                </>
              ) : (
                <>
                  <div className={styles.로그인회원가입}>
                    아직 회원이 아니신가요 ?{" "}
                    <div className={styles.회원가입하기}>회원가입하기</div>
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
