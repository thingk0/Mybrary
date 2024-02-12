import styles from "./ContentItem.module.css";
import s from "classnames";

export default function ContentItem({ paper }) {
  return (
    <div className={styles.position}>
      <div className={s(styles[`text1_${paper.layoutType}`])}></div>
      <div className={s(styles[`text2_${paper.layoutType}`])}></div>
      <div
        className={styles[`img1_${paper.layoutType}`]}
        style={{
          background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${paper.imageUrl1}")`,
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          backgroundSize: "cover",
        }}
      >
        {paper.thumbnail1Url}
      </div>
      <div
        className={styles[`img2_${paper.layoutType}`]}
        style={{
          background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${paper.imageUrl2}")`,
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          backgroundSize: "cover",
        }}
      >
        {paper.thumbnail2Url}
      </div>
    </div>
  );
}
