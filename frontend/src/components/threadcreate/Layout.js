import lo from "./Layouts.module.css";
import styles from "./Layout.module.css";
import s from "classnames";
import { useState } from "react";

export default function Layout({
  papers,
  setPapers,
  currentPage,
  layouts,
  children,
}) {
  const [filter, setFilter] = useState("1CUT"); // 필터 상태 추가

  const persent = ["", "9:16", "3:4", "1:1", "4:3", "16:9"];

  const handleFilter = (filterType) => {
    setFilter(filterType);
    if (filterType === "1CUT") {
      papers[currentPage].imageId2 = null;
    }
  };

  const handleSelect = (los) => {
    setPapers((prevPapers) => {
      const updatedPapers = [...prevPapers];
      updatedPapers[currentPage].layoutType = los;
      return updatedPapers;
    });
  };

  const filteredLayouts = layouts.filter((los) => {
    if (filter === "1CUT" && Math.floor(los / 1000) === 1) return true;
    if (filter === "2CUT" && Math.floor(los / 1000) === 2) return true;
    return false;
  });

  return (
    <div className={styles.미드왼쪽사이즈조정}>
      <div className={styles.왼쪽헤더}>
        <span className={styles.레이아웃글자}>레이아웃</span>
        <div className={styles.레이아웃버튼}>
          <div
            className={s(
              styles.cutButton,
              filter === "1CUT" && styles.selectCutButton
            )}
            onClick={() => handleFilter("1CUT")}
          >
            1CUT
          </div>
          <div
            className={s(
              styles.cutButton,
              filter === "2CUT" && styles.selectCutButton
            )}
            onClick={() => handleFilter("2CUT")}
          >
            2CUT
          </div>
        </div>
      </div>
      <div className={styles.왼쪽미드레이아웃}>
        {filteredLayouts.map((los, index) => (
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
