import React, { useState, useEffect } from "react";
import styles from "./OneThread.module.css";
import s from "classnames";
import FeedContent from "./FeedContent";
import BookSelect2 from "./BookSelect2";
import BookCreate from "../common/BookCreate";
import BigModal from "../common/BigModal";
import Comment from "./Comment";
import { getMYBooks } from "../../api/book/Book";
import { getThread } from "../../api/thread/Thread";

export default function OneThread({ threadId, setThreadModal }) {
  const [list, setList] = useState([]);
  const [scrapModal, setScrapModal] = useState(false);

  const [comment, setComment] = useState(false);
  const [commentId, setCommentId] = useState(0);
  const [zIndex, setZIndex] = useState(-1);

  // 나의 북리스트 가져오기
  const [papers, setPapers] = useState([]);
  const [booklist, setBookList] = useState([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const handleOpenBookList = async (paperList) => {
    setPapers(paperList);
    const booklists = await getMYBooks();
    setBookList(booklists.data);
    setScrapModal(true);
  };

  useEffect(() => {
    async function fetchMainFeedData() {
      try {
        const response = await getThread(threadId);

        setList([response.data]);
      } catch (error) {
        console.error("데이터불러오기실패");
      }
    }
    fetchMainFeedData();
  }, [threadId]);

  const incrementCommentCount = (paperId) => {
    setList((currentList) =>
      currentList.map((thread) => ({
        ...thread,
        paperList: thread.paperList.map((paper) => {
          if (paper.id === paperId) {
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
            return { ...paper, commentCount: paper.commentCount - 1 };
          }
          return paper;
        }),
      }))
    );
  };
  const incrementScrapCount = (paperId) => {
    setList((currentList) =>
      currentList.map((thread) => ({
        ...thread,
        paperList: thread.paperList.map((paper) => {
          if (paper.id === paperId) {
            // 해당 페이퍼의 스크립카운트 1 증가
            return { ...paper, scrapCount: paper.scrapCount + 1 };
          }
          return paper;
        }),
      }))
    );
  };

  return (
    <>
      <div onClick={() => setThreadModal(false)} className={styles.close}>
        닫기
      </div>
      <div className={styles.flex}>
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
                comment ? styles.content : styles.content2
              )}
              style={{ zIndex: `-${index}` }}
            >
              {/* 하나의 쓰레드에 해당 */}
              <FeedContent
                setThreadModal={setThreadModal}
                thread={thread}
                list={list}
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
            setComment={setComment}
            updateCommentCount={incrementCommentCount}
            updateCommentCount2={decreaseCommentCount}
          />
        </div>
      </div>

      <BigModal
        modalIsOpen={scrapModal}
        setModalIsOpen={setScrapModal}
        width="800px"
        height="600px"
      >
        <BookSelect2
          setModalIsOpen={setModalIsOpen}
          setModalIsOpen2={setScrapModal}
          incrementScrapCount={incrementScrapCount}
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
