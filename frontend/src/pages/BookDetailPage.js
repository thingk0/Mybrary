import React, { useEffect, useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import styles from "./style/BookDetailPage.module.css";
import s from "classnames";
import FeedModal from "../components/feed/FeedModal";
import useUserStore from "../store/useUserStore";
import useBookStore from "../store/useBookStore";
import { getBook } from "../api/book/Book";
import ContentItem2 from "../components/book/ContentItem2";
import useUrlStore from "../store/useUrlStore";
import { useNavigate } from "react-router-dom";

export default function BookDetailPage() {
  const navigate = useNavigate();
  const userId = useUserStore((state) => state.user.memberId);
  const writerId2 = useBookStore((state) => state.book?.memberId);
  const writerId = useBookStore((state) => state.book?.writer?.memberId);
  const book = useBookStore((state) => state.book);
  const url = useUrlStore((state) => state.url.url);

  const bookRef = useRef();
  const [curPage, setCurPage] = useState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [listModal1, setListModal1] = useState(false);
  const [listModal2, setListModal2] = useState(false);

  const onPrev = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipPrev("bottom");
    } else {
      bookRef.current.pageFlip().turnToPrevPage();
    }
  };
  const onNext = (hasFlip = "N") => {
    const pageIndex = bookRef.current.pageFlip().getCurrentPageIndex();
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
  };

  const [pages, setPages] = useState([]);
  useEffect(() => {
    async function getbook() {
      const pagelist = await getBook(book.bookId);
      setPages(pagelist.data.paperList ? pagelist.data.paperList : []);
    }
    getbook();
  }, []);

  return (
    <>
      <div className={s(styles.bookContainer)}>
        <div className={styles.header}>
          <div className={styles.headerLeft} onClick={() => navigate(url)}>
            뒤로가기
          </div>
          <div className={styles.headerMain}>
            {book.coverTitle || book.bookTitle}
          </div>
          <div
            onClick={() => {
              setIsModalOpen(true);
            }}
            className={styles.headerRight}
          >
            옵션
          </div>
        </div>
        <FeedModal
          width="150px"
          setIsModalOpen={setIsModalOpen}
          isModalOpen={isModalOpen}
          right="25px"
          top="-20px"
        >
          <div className={styles.option}>
            {(writerId || writerId2) !== userId && <div>내 책장에 담기</div>}
            {(writerId || writerId2) === userId && <div>책 삭제</div>}
            {(writerId || writerId2) !== userId && <div>책 구독취소</div>}
          </div>
        </FeedModal>
        {pages.length ? (
          <>
            <div
              className={s(
                styles.margin,
                styles[`color${book.coverColorCode}`]
              )}
            >
              <div className={styles.cover}>
                <HTMLFlipBook
                  ref={bookRef}
                  width={566}
                  height={750}
                  onFlip={onFlip}
                  maxShadowOpacity={0.2}
                  useMouseEvents={false}
                  showCover={false}
                >
                  {pages.map((page, index) => (
                    <div
                      className={index % 2 !== 0 ? styles.page1 : styles.page2}
                      key={index}
                    >
                      <div className={styles.flex}>
                        <div className={styles.writer}>
                          <div
                            className={styles.writerImage}
                            style={{
                              background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${page.writer.imageUrl}")no-repeat center/cover`,
                            }}
                          ></div>
                          <div>{page.writer.nickname}님의 페이퍼</div>
                        </div>
                        {index % 2 !== 0 ? (
                          <div>
                            <div
                              onClick={() => {
                                setListModal1(true);
                              }}
                              className={styles.headerRight}
                            >
                              {".  .  ."}
                            </div>
                            <FeedModal
                              width="150px"
                              setIsModalOpen={setListModal1}
                              isModalOpen={listModal1}
                              right="-5px"
                              top="10px"
                            >
                              <div className={styles.option}>
                                <div>해당 스레드 보러가기</div>
                                {(writerId || writerId2) === userId && (
                                  <div>책에서 페이퍼 제거</div>
                                )}
                                {page.writer.memberId === userId && (
                                  <>
                                    <div>페이퍼 수정</div>
                                    <div>페이퍼 삭제</div>
                                  </>
                                )}
                              </div>
                            </FeedModal>
                          </div>
                        ) : (
                          <div>
                            <div
                              onClick={() => {
                                setListModal2(true);
                              }}
                              className={styles.headerRight}
                            >
                              {".  .  ."}
                            </div>
                            <FeedModal
                              width="150px"
                              setIsModalOpen={setListModal2}
                              isModalOpen={listModal2}
                              right="-5px"
                              top="10px"
                            >
                              <div className={styles.option}>
                                <div>해당 스레드 보러가기</div>
                                {(writerId || writerId2) === userId && (
                                  <div>책에서 페이퍼 제거</div>
                                )}
                                {page.writer.memberId === userId && (
                                  <>
                                    <div>페이퍼 수정</div>
                                    <div>페이퍼 삭제</div>
                                  </>
                                )}
                              </div>
                            </FeedModal>
                          </div>
                        )}
                      </div>
                      <div className={styles.main_content}>
                        <ContentItem2 paper={page} />
                      </div>
                    </div>
                  ))}
                </HTMLFlipBook>
              </div>
            </div>
            <div className={styles.flex2}>
              <div onClick={() => onPrev("Y")} className={styles.button}>
                이전 페이지
              </div>
              <div className={styles.flex}>
                <div>{curPage ? curPage + 1 : 1}</div>
                <div>/{pages.length}</div>
              </div>
              <div onClick={() => onNext("Y")} className={styles.button}>
                다음 페이지
              </div>
            </div>
          </>
        ) : (
          <div className={styles.none}> 책에 페이지가 하나도 없습니다</div>
        )}
      </div>
    </>
  );
}
