import Container from "../components/frame/Container";
import LoginForm from "../components/join/LoginForm";
import SignUpForm from "../components/join/SignUpForm";
import styles from "./style/JoinPage.module.css";
import 책읽는곰 from "../assets/곰탱이.png";
import 카카오 from "../assets/icon/카카오.png";
import 네이버 from "../assets/icon/네이버.png";
import 구글 from "../assets/icon/구글.png";
import 로고 from "../assets/MYBRARY.png";
import { useState } from "react";
// 안써서 주석처리
// import styles from "./style/JoinPage.module.css";
export default function JoinPage() {
  const [pageremote, setPageremote] = useState(false);

  const togglePage = () => {
    setPageremote((prev) => !prev);
  };
  return (
    <>
      <Container height="89vh">
        <div className={styles.joinmain}>
          <div className={styles.backpage1}>
            <div className={styles.backpage2}>
              <div className={styles.backpage3}>
                <div className={styles.midleft}>
                  <img className={styles.책읽는곰} src={책읽는곰} alt="" />
                </div>
                <div className={styles.midmid}>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                  <div className={styles.고리}></div>
                </div>
                <div className={styles.midright}>
                  <div className={styles.midrightmain}>
                    {pageremote && <SignUpForm />}
                    {!pageremote && (
                      <>
                        <div className={styles.rightheader}>
                          <div>당신의 추억이 한페이지가 될 수 있도록</div>
                          <img
                            className={styles.로고이미지}
                            src={로고}
                            alt=""
                          />
                        </div>
                        <LoginForm />
                      </>
                    )}

                    <span className={styles.토글} onClick={togglePage}>
                      {pageremote ? (
                        <>
                          <div className={styles.로그인회원가입}>
                            이미 회원이신가요 ?
                            <span className={styles.로그인하기}>
                              로그인하기
                            </span>
                          </div>
                          <div className={styles.footer}>
                            <span>SNS Sign Up</span>
                            <div className={styles.소셜모음}>
                              <div className={styles.쇼셜}>
                                <img src={네이버} alt="" />
                                <span>네이버</span>
                              </div>
                              <div className={styles.쇼셜}>
                                <img src={카카오} alt="" />
                                <span>카카오</span>
                              </div>
                              <div className={styles.쇼셜}>
                                <img src={구글} alt="" />
                                <span>구글</span>
                              </div>
                            </div>
                          </div>
                        </>
                      ) : (
                        <>
                          <div className={styles.로그인회원가입}>
                            아직 회원이 아니신가요 ?{" "}
                            <span className={styles.회원가입하기}>
                              회원가입하기
                            </span>
                          </div>
                          <div className={styles.footer}>
                            <span>SNS Login</span>
                            <div className={styles.소셜모음}>
                              <div className={styles.쇼셜}>
                                <img src={네이버} alt="" />
                                <span>네이버</span>
                              </div>
                              <div className={styles.쇼셜}>
                                <img src={카카오} alt="" />
                                <span>카카오</span>
                              </div>
                              <div className={styles.쇼셜}>
                                <img src={구글} alt="" />
                                <span>구글</span>
                              </div>
                            </div>
                          </div>
                        </>
                      )}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
