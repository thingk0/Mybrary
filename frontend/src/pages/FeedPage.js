import React, { useState, useEffect, useCallback } from "react";
import styles from "./style/FeedPage.module.css";
import s from "classnames";
import { useNavigate } from "react-router-dom";
import Comment from "../components/feed/Comment";
import FeedContent from "../components/feed/FeedContent";
import { getThreadList } from "../api/thread/Thread";
import BigModal from "../components/common/BigModal";
import { getMYBooks } from "../api/book/Book";
import BookSelect2 from "../components/feed/BookSelect2";
import BookCreate from "../components/common/BookCreate";

export default function FeedPage() {
  const [activeIndex, setActiveIndex] = useState(0);
  const [list, setList] = useState([]);
  const [page, setPage] = useState(0);
  const [scrapModal, setScrapModal] = useState(false);

  // console.log(threadList);
  const [comment, setComment] = useState(false);
  const [commentId, setCommentId] = useState(0);
  const [zIndex, setZIndex] = useState(-1);
  const navigate = useNavigate();

  // 스로틀링을 위한 상태
  const [isThrottled, setIsThrottled] = useState(false);

  // useCallback 내에서 함수 정의
  const handlePrevClick = useCallback(() => {
    setComment(false);
    setZIndex(-1);
    if (activeIndex > 0) {
      setActiveIndex(activeIndex - 1);
    }
  }, [activeIndex]);

  // useCallback 내에서 함수 정의
  const handleNextClick = useCallback(() => {
    setActiveIndex(activeIndex + 1);
    setComment(false);
    setZIndex(-1);
    if (activeIndex === list.length - 3 && list.length - 4 < activeIndex) {
      setPage(page + 1);
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

  // 나의 북리스트 가져오기
  const [papers, setPapers] = useState([]);
  const [booklist, setBookList] = useState([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const handleOpenBookList = async (paperList) => {
    setPapers(paperList);
    const booklists = await getMYBooks();
    console.log(booklists.data);
    setBookList(booklists.data);
    setScrapModal(true);
  };

  useEffect(() => {
    const stackCarouselContents = document.querySelector(
      `.${styles.StackCarousel_contents}`
    );

    if (stackCarouselContents) {
      stackCarouselContents.addEventListener("wheel", handleWheelThrottled);
    }

    return () => {
      if (stackCarouselContents) {
        stackCarouselContents.removeEventListener(
          "wheel",
          handleWheelThrottled
        );
      }
    };
  }, [handleWheelThrottled]);

  useEffect(() => {
    async function fetchMainFeedData() {
      try {
        const response = await getThreadList(page);
        // console.log(page);
        // setThreadList(response.data);

        setList([...list, ...response.data]);
        console.log(response);
      } catch (error) {
        console.error("데이터불러오기실패");
      }
    }
    fetchMainFeedData();
  }, [page]);

  const incrementCommentCount = (paperId) => {
    setList((currentList) =>
      currentList.map((thread) => ({
        ...thread,
        paperList: thread.paperList.map((paper) => {
          if (paper.id === paperId) {
            // 해당 페이퍼의 commentCount를 1 증가
            return { ...paper, commentCount: paper.commentCount + 1 };
          }
          return paper;
        }),
      }))
    );
  };
  const decreaseCommentCount = (paperId) => {
    setList((currentList) =>
      currentList.map((thread) => ({
        ...thread,
        paperList: thread.paperList.map((paper) => {
          if (paper.id === paperId) {
            // 해당 페이퍼의 commentCount를 1 증가
            return { ...paper, commentCount: paper.commentCount - 1 };
          }
          return paper;
        }),
      }))
    );
  };

  return (
    <>
      <div className={styles.feedContainer}>
        <div
          className={s(
            styles.StackCarousel_contents,
            comment && styles.StackCarousel_translate
          )}
        >
          {list.map((thread, index) => (
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
              style={{ zIndex: `-${index}` }}
            >
              {/* 하나의 쓰레드에 해당 */}
              <FeedContent
                index={index}
                thread={thread}
                setList={setList}
                setCommentId={setCommentId}
                setComment={setComment}
                setZIndex={setZIndex}
                setScrapModal={setScrapModal}
                setPapers={setPapers}
                handleOpenBookList={handleOpenBookList}
              />
            </div>
          ))}
        </div>

        <div
          className={s(
            styles.commentContainer,
            comment ? styles.commentActive : styles.commentHide
          )}
          style={{ zIndex: zIndex }}
        >
          <Comment
            commentId={commentId}
            updateCommentCount={incrementCommentCount}
            updateCommentCount2={decreaseCommentCount}
          />
        </div>
      </div>
      <div className={styles.create} onClick={() => navigate("/threadCreate")}>
        + 스레드 작성하러가기
      </div>
      {activeIndex >= list.length && (
        <div className={styles.noneFeed}>
          <div className={styles.noneText}>더이상 스레드가 없어요!</div>
          <div className={styles.createButton}>
            새로운 스레드를 작성해 보세요
          </div>
        </div>
      )}
      <BigModal
        modalIsOpen={scrapModal}
        setModalIsOpen={setScrapModal}
        width="800px"
        height="600px"
      >
        <BookSelect2
          setModalIsOpen={setModalIsOpen}
          setModalIsOpen2={setScrapModal}
          papers={papers}
          booklist={booklist}
        />
      </BigModal>
      <BigModal
        modalIsOpen={modalIsOpen}
        setModalIsOpen={setModalIsOpen}
        width="1200px"
        height="800px"
        background="var(--main4)"
      >
        <BookCreate
          setBookList={setBookList}
          booklist={booklist}
          setModalIsOpen={setModalIsOpen}
        />
      </BigModal>
    </>
  );
}
