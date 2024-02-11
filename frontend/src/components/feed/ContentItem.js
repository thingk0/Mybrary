import styles from "./ContentItem.module.css";
import s from "classnames";

export default function ContentItem({ paper }) {
  const createMarkup = (html) => {
    return { __html: html };
  };
  const handleWheelInsideContent = (event) => {
    const { deltaY } = event;
    const isScrollableContent =
      event.currentTarget.scrollHeight > event.currentTarget.clientHeight;

    if (isScrollableContent) {
      const isAtTop = event.currentTarget.scrollTop === 0;
      const isAtBottom =
        event.currentTarget.scrollTop + event.currentTarget.clientHeight >=
        event.currentTarget.scrollHeight;

      // 휠을 위로 움직이거나 아래로 움직일 때, 스크롤이 맨 위 또는 맨 아래에 도달했을 때만 이벤트 차단
      if ((deltaY < 0 && isAtTop) || (deltaY > 0 && isAtBottom)) {
        event.preventDefault();
        event.stopPropagation();
      }
    }
  };
  return (
    <div className={styles.position} onWheel={handleWheelInsideContent}>
      <div
        className={s(styles[`text1_${paper.layoutType}`])}
        onWheel={handleWheelInsideContent}
        dangerouslySetInnerHTML={createMarkup(paper.content1)}
      ></div>
      <div
        className={s(styles[`text2_${paper.layoutType}`])}
        onWheel={handleWheelInsideContent}
        dangerouslySetInnerHTML={createMarkup(paper.content2)}
      ></div>
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
