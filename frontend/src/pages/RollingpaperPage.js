import Container from "../components/frame/Container";
import s from "classnames";
import styles from "./style/RollingpaperPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import { useNavigate } from "react-router-dom";
export default function RollingpaperPage() {
  const navigate = useNavigate();
  return (
    <>
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate("../")}>
            &lt; 뒤로가기
          </div>
        </div>
        <div className={title.title}>
          <div
            className={title.left_title}
            onClick={() => navigate("../bookshelf")}
          >
            &lt; 책장
          </div>
          <div className={title.main_title}>cwnsgh's rollingpaper</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../threads")}
          >
            {" "}
            게시물 &gt;
          </div>
        </div>

        <div className={styles.초기화}>
          <span>롤링페이퍼초기화</span>
        </div>
        <div className={styles.main}>
          <div className={styles.롤링페이퍼}></div>
          <div className={styles.색변경}>
            <div className={s(styles.color1, styles.color)}></div>
            <div className={s(styles.color2, styles.color)}></div>
            <div className={s(styles.color3, styles.color)}></div>
            <div className={s(styles.color4, styles.color)}></div>
            <div className={s(styles.color5, styles.color)}></div>
            <div className={s(styles.color6, styles.color)}></div>
            <div className={s(styles.color7, styles.color)}></div>
            <div className={s(styles.color8, styles.color)}></div>
            <div className={s(styles.color9, styles.color)}></div>
            <div className={s(styles.color10, styles.color)}></div>
            <div className={s(styles.color11, styles.color)}></div>
            <div className={s(styles.color12, styles.color)}></div>
          </div>
          <div className={styles.저장}>
            <button>저장</button>
          </div>
        </div>
      </Container>
    </>
  );
}
