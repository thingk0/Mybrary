import styles from "./BookCreate.module.css";
import three from "../../assets/three.png";

export default function BookCreate() {
  return (
    <div className={styles.container}>
      <div className={styles.title}>새로운 책 만들기</div>
      <div className={styles.main}>
        <div className={styles.main_left}>
          <div className={styles.cover}>책커버부분</div>
          <div className={styles.cover2}></div>
        </div>
        <div className={styles.main_right}>
          <div className={styles.subtitle}>책 레이아웃</div>
          <div className={styles.layouts}>
            <div className={styles.layout}>레이아웃1</div>
            <div className={styles.layout}>레이아웃2</div>
            <div className={styles.layout}>레이아웃3</div>
            <div className={styles.layout}>레이아웃4</div>
            <div className={styles.layout}>레이아웃5</div>
            <div className={styles.layout}>레이아웃6</div>
          </div>
          <div className={styles.subtitle}>책 커버 색상</div>
          <div className={styles.colors}>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
            <div className={styles.color}></div>
          </div>
          <div className={styles.categorys}>
            <div className={styles.categorytitle}>카테고리선택</div>
            <div className={styles.category}>
              <div className={styles.categoryName}>일기</div>
              <img className={styles.categoryImg} src={three} alt="" />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.bookCreate}>생성</div>
    </div>
  );
}
