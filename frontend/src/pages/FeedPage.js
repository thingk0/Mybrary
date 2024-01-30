import React, { useState } from "react";
import Container from "../components/frame/Container";
import styles from "./style/FeedPage.module.css";
import s from "classnames";

export default function FeedPage() {
  const [activeIndex, setActiveIndex] = useState(1);
  const [list, setList] = useState([10, 9, 8, 7, 6, 5, 4, 3, 2, 1]);

  const handleNextClick = () => {
    if (activeIndex > 1) {
      setActiveIndex(activeIndex - 1);
    }
  };

  const handlePrevClick = () => {
    setActiveIndex(activeIndex + 1);
    if (activeIndex === list.length - 3 && list.length - 4 < activeIndex) {
      const newList = Array.from(
        { length: 10 },
        (_, index) => list[0] + index + 1
      ).reverse();
      setList([...newList, ...list]);
    }
  };

  return (
    <>
      <Container>
        <div className={styles.StackCarousel_contents}>
          {list.map((index) => (
            <div
              key={index}
              className={s(
                styles.StackCarousel_content,
                {
                  [styles.StackCarousel_above]: activeIndex > index,
                  [styles.StackCarousel_active]: activeIndex === index,
                  [styles.StackCarousel_second]: activeIndex === index - 1,
                  [styles.StackCarousel_third]: activeIndex === index - 2,
                },
                activeIndex < index - 2 ? "" : null
              )}
            >
              <div className={styles.cont}>{index}번 게시물이빈당</div>
            </div>
          ))}
        </div>
        <button onClick={handleNextClick}>이전</button>
        <button onClick={handlePrevClick}>다음</button>
      </Container>
    </>
  );
}
