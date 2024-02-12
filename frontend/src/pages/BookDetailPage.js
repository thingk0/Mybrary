import React, { useEffect, useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import styles from "./style/BookDetailPage.module.css";
import s from "classnames";
import FeedModal from "../components/feed/FeedModal";
import ContentItem from "../components/feed/ContentItem";
import useUserStore from "../store/useUserStore";
import useBookStore from "../store/useBookStore";
import { getBook } from "../api/book/Book";

export default function BookDetailPage() {
  const user = useUserStore((state) => state.user);
  const book = useBookStore((state) => state.book);

  const bookRef = useRef();
  const [curPage, setCurPage] = useState();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const onPrev = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
    console.log("pageIndex", pageIndex);
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipPrev("bottom");
    } else {
      bookRef.current.pageFlip().turnToPrevPage();
    }
  };
  const onNext = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
    console.log("pageIndex", pageIndex);
    // 마지막 페이지에 도달했을 때
    if (pageIndex === 7) return;
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipNext();
    } else {
      bookRef.current.pageFlip().turnToNextPage();
    }
  };
  const onFlip = (e) => {
    const curPage = e.data;
    setCurPage(curPage);
    console.log("flip", e);
  };

  const [pages, setPages] = useState([]);
  useEffect(() => {
    async function getbook() {
      const pagelist = await getBook(book.bookId);
      setPages(pagelist.data.paperList);
      console.log(pagelist.data.paperList);
    }
    console.log(book);
    getbook();
  }, []);

  return (
    <>
      <div className={s(styles.bookContainer)}>
        <div className={styles.header}>
          <div className={styles.headerLeft}>뒤로가기</div>
          <div className={styles.headerMain}>책 이름</div>
          <div
            onClick={() => {
              setIsModalOpen(true);
            }}
            className={styles.headerRight}
          >
            옵션
            <FeedModal
              width="150px"
              setIsModalOpen={setIsModalOpen}
              isModalOpen={isModalOpen}
              right="-5px"
              top="10px"
            >
              <div className={styles.option}>
                {}
                <div>내 책장에 담기</div>
                <div>책 카테고리 수정</div>
                <div>책 표지 수정</div>
                <div>책 삭제</div>
                <div>책 구독취소</div>
              </div>
            </FeedModal>
          </div>
        </div>
        <HTMLFlipBook
          ref={bookRef}
          width={600}
          height={800}
          onFlip={onFlip}
          maxShadowOpacity={0.2}
          clickEventForward={true}
          useMouseEvents={false}
        >
          {pages.map((page, index) => (
            <div className={styles.page} key={index}>
              <ContentItem paper={page} />
            </div>
          ))}
        </HTMLFlipBook>
        <div>
          <button onClick={() => onPrev("Y")}>이전 페이지</button>
          <div>
            <div>{curPage}</div>
            <div>/전체페이지</div>
          </div>
          <button onClick={() => onNext("Y")}>다음 페이지</button>
        </div>
      </div>
    </>
  );
}
