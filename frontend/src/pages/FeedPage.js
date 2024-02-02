import React, { useState, useEffect, useCallback } from "react";
import Container from "../components/frame/Container";
import styles from "./style/FeedPage.module.css";
import s from "classnames";
import { useNavigate } from "react-router-dom";
import Comment from "../components/feed/Comment";
import icon_comment from "../assets/icon/icon_comment.png";

export default function FeedPage() {
  const [activeIndex, setActiveIndex] = useState(1);
  const [list, setList] = useState([10, 9, 8, 7, 6, 5, 4, 3, 2, 1]);
  const [comment, setComment] = useState(false);
  const [commentId, setCommentId] = useState(0);
  const navigate = useNavigate();

  // 스로틀링을 위한 상태
  const [isThrottled, setIsThrottled] = useState(false);

  // useCallback 내에서 함수 정의
  const handlePrevClick = useCallback(() => {
    setComment(false);
    if (activeIndex > 1) {
      setActiveIndex(activeIndex - 1);
    }
  }, [activeIndex]);

  // useCallback 내에서 함수 정의
  const handleNextClick = useCallback(() => {
    setActiveIndex(activeIndex + 1);
    setComment(false);
    if (activeIndex === list.length - 3 && list.length - 4 < activeIndex) {
      const newList = Array.from(
        { length: 10 },
        (_, index) => list[0] + index + 1
      ).reverse();
      setList([...newList, ...list]);
    }
  }, [activeIndex, list]);

  // useCallback으로 함수 래핑
  const handleWheelThrottled = useCallback(
    (event) => {
      if (!isThrottled) {
        if (event.deltaY < 0) {
          handlePrevClick();
        } else if (event.deltaY > 0) {
          handleNextClick();
        }
        setIsThrottled(true);
        setTimeout(() => setIsThrottled(false), 500); // 0.5초 동안 다음 이벤트 차단
      }
    },
    [isThrottled, handlePrevClick, handleNextClick]
  );

  useEffect(() => {
    window.addEventListener("wheel", handleWheelThrottled);
    return () => {
      window.removeEventListener("wheel", handleWheelThrottled);
    };
  }, [activeIndex, list, handleWheelThrottled]); // 의존성 배열 업데이트

  const openComment = (index) => {
    setComment(true);
    setCommentId(index);
  };

  function FeedContent({ index, content }) {
    return (
      <div className={styles.cont}>
        <div>{index}번 게시물이빈당</div>
        <img src={icon_comment} alt="" onClick={() => openComment(index)} />
      </div>
    );
  }

  return (
    <>
      <Container>
        <div className={styles.feedContainer}>
          <div>
            <div
              className={s(
                styles.StackCarousel_contents,
                comment && styles.active
              )}
            >
              {list.map((index, content) => (
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
                  <FeedContent index={index} content={content} />
                </div>
              ))}
            </div>
          </div>
          <div
            className={s(
              styles.ll,
              comment ? styles.commentContainer : styles.hide
            )}
          >
            <Comment commentId={commentId} />
          </div>
        </div>
        <div
          className={styles.create}
          onClick={() => navigate("/threadCreate")}
        >
          플러스버턴
        </div>
      </Container>
    </>
  );
}
