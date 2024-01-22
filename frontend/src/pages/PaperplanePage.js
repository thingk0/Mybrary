import Container from "../components/frame/Container";
import styles from "./style/PaperplanePage.module.css";
export default function PaperplanePage() {
  return (
    <>
      <Container width={"100%"} height={"100%"} backgroundColor={"#FFFAFA"}>
        <div className={styles.main}>
          <div className={styles.pipibox}>
            <div className={styles.member}>
              <div className={styles.pipi}>PiPi</div>
              <div className={styles.search}>
                <>
                  <form action="/search" method="get">
                    <label htmlFor="search"></label>
                    <div className={styles.searchContainer}>
                      <button type="submit" className={styles.searchButton}>
                        검색
                      </button>
                      <input
                        type="text"
                        id="search"
                        name="q"
                        placeholder=""
                        className={styles.searchInput}
                      />
                    </div>
                  </form>
                </>
              </div>
              <div className={styles.users}>
                <div className={styles.user}>
                  <div className={styles.유저이미지}>이미지</div>
                  <div className={styles.유저정보}>
                    <div>닉네임</div>
                    <div>내용</div>
                  </div>
                  <div className={styles.유저점점점}>점점점</div>
                </div>
              </div>
            </div>
            <div className={styles.gori}>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
              <div className={styles.ring}></div>
            </div>
            <div className={styles.chat}>
              <div className={styles.header}>
                <div className={styles.이미지닉네임}>
                  <div>프로필이미지</div>
                  <div>닉네임</div>
                </div>
                <button className={styles.마이브러리가기}>
                  마이브러리 가기
                </button>
              </div>
              <div className={styles.middle}>
                <div className={styles.textmain}></div>
                <div className={styles.sendbox}>
                  <span className={styles.예제}>메세지를 보내세요</span>
                  <button className={styles.비행기}>비행기</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
