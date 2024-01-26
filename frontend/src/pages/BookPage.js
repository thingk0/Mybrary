import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
export default function BookPage() {
  return (
    <>
      <Container backgroundColor="#fff9f6">
        <div>
          <div>뒤로가기</div>
        </div>
        <div className={styles.body}>
          <div className={styles.bookpage}>
            <div className={styles.head}>
              <div>게시물</div>
              <div className={styles.카테고리}>카테고리</div>
              <div className={styles.롤링페이퍼}>롤링페이퍼</div>
            </div>
            <div className={styles.middle}>
              <div className={styles.책추가}>책추가</div>
              <div className={styles.책검색}>책검색</div>
              <div className={styles.캐러셀}></div>
            </div>
            <div className={styles.mainbook}>
              <div className={styles.책갈피로이동}>책갈피로이동</div>
              <div className={styles.옵션}>옵션</div>
              <div>책</div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
