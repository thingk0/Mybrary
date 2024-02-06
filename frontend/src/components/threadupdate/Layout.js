import lo from "./Layouts.module.css";
import styles from "./Layout.module.css";
import s from "classnames";

export default function Layout({
  papers,
  setPapers,
  currentPage,
  layouts,
  children,
}) {
  const persent = ["", "9:16", "3:4", "1:1", "4:3", "16:9"];
  const handleSelect = (los) => {
    setPapers((prevPapers) => {
      const updatedPapers = [...prevPapers];
      updatedPapers[currentPage].layoutType = los;
      return updatedPapers;
    });
  };

  return (
    <div className={styles.미드왼쪽사이즈조정}>
      <div className={styles.왼쪽헤더}>
        <span className={styles.레이아웃글자}>레이아웃</span>
        <div className={styles.레이아웃버튼}>
          <button>ALL</button>
          <button>1CUT</button>
          <button>2CUT</button>
        </div>
      </div>
      <div className={styles.왼쪽미드레이아웃}>
        {layouts.map((los, index) => (
          <div
            key={index}
            className={s(
              lo.position,
              papers[currentPage].layoutType === los && lo.select
            )}
            onClick={() => handleSelect(los)}
          >
            <div className={lo[`text1_${los}`]}>text</div>
            <div className={lo[`text2_${los}`]}>text</div>
            <div className={lo[`img1_${los}`]}>
              {persent[Math.floor((los % 1000) / 100)]}
            </div>
            <div className={lo[`img2_${los}`]}>
              {persent[Math.floor((los % 100) / 10)]}
            </div>
          </div>
        ))}
      </div>
      <div>{children}</div>
    </div>
  );
}
