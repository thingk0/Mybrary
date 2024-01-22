import Container from "../components/frame/Container";
import styles from "./style/FeedPage.module.css";
export default function FeedPage() {
  return (
    <>
      <Container>
        <div className={styles.body}>
          <div className={styles.feed}>
            <div className={styles.feedmain}>
              <div className={styles.userhead}>
                <div className={styles.headimage}>이미지</div>
                <div className={styles.headmid}>
                  <div>이름</div>
                  <div>작성날짜</div>
                </div>
                <div className={styles.팔로우}>팔로우</div>
              </div>
              <div className={styles.좋댓}></div>
              <div className={styles.레이아웃}>
                <div className={styles.태그}></div>
              </div>
            </div>
            <div className={styles.back1}></div>
            <div className={styles.back2}></div>
          </div>
        </div>
      </Container>
    </>
  );
}
