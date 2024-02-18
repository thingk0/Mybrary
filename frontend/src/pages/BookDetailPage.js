import React, { useEffect, useRef, useState } from "react";
import HTMLFlipBook from "react-pageflip";
import styles from "./style/BookDetailPage.module.css";
import s from "classnames";
import useBookStore from "../store/useBookStore";
import {
  deleteBook,
  deletePaper,
  getBook,
  subscribeBook,
  unsubsribeBook,
} from "../api/book/Book";
import ContentItem2 from "../components/book/ContentItem2";
import useUrlStore from "../store/useUrlStore";
import { useNavigate } from "react-router-dom";
import BigModal from "../components/common/BigModal";
import { getCategoryList } from "../api/category/Category";
import useMyStore from "../store/useMyStore";
import OneThread from "../components/threads/OneThread";
import FeedModal2 from "../components/feed/FeedModal2";

export default function BookDetailPage() {
  const navigate = useNavigate();
  const book = useBookStore((state) => state.book);
  const url = useUrlStore((state) => state.url.url);

  const my = useMyStore((state) => state.my);
  const bookRef = useRef();
  const [curPage, setCurPage] = useState();
  const [listModal1, setListModal1] = useState(false);
  const [listModal2, setListModal2] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [subscribeModal, setSubscribeModal] = useState(false);
  const [unSubscribeModal, setUnSubscribeModal] = useState(false);

  const onPrev = (hasFlip = "N") => {
    if (hasFlip === "Y") {
      bookRef.current.pageFlip().flipPrev("bottom");
    } else {
      bookRef.current.pageFlip().turnToPrevPage();
    }
  };
  const onNext = (hasFlip = "N") => {
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
  const [bookinfo, setBookinfo] = useState([]);
  useEffect(() => {
    async function getbook() {
      const pagelist = await getBook(book.bookId);
      setPages(pagelist.data.paperList ? pagelist.data.paperList : []);
      setBookinfo(pagelist.data);
    }
    getbook();
  }, [book]);

  const handleDelete = async () => {
    try {
      await deleteBook(bookinfo.bookId);
      navigate(url);
    } catch (error) {
      console.error("책 삭제 중 오류 발생:", error);
    }
  };

  const [list, setList] = useState([]);
  const [cate, setCate] = useState(0);
  const handleSubscribe = async () => {
    try {
      await subscribeBook({
        bookId: bookinfo.bookId,
        categoryId: cate,
      });
      navigate(`/mybrary/${my.memberId}/${my.bookShelfId}/${cate}`);
    } catch (error) {
      console.error("책 구독 중 오류 발생:", error);
    }
  };

  const subscribe = async () => {
    try {
      const lists = await getCategoryList(my.bookShelfId);
      setList(lists.data);
    } catch (error) {
      console.error("카테고리 목록 불러오기 중 오류 발생:", error);
    }
    setSubscribeModal(true);
  };
  const handleUnSubscribe = async () => {
    try {
      await unsubsribeBook(bookinfo.bookId);
      setUnSubscribeModal(false);
      navigate(-1);
    } catch (error) {
      console.error("책 구독취소 중 오류 발생:", error);
    }
  };

  const [threadModal, setThreadModal] = useState(false);
  const [tId, setTId] = useState(0);
  const goTread = (threadId) => {
    setTId(threadId);
    setThreadModal(true);
  };

  const handleDeletePaper = async (paperId) => {
    try {
      await deletePaper(bookinfo.bookId, paperId);
      const updatedPages = pages.filter((page) => page.paperId !== paperId);
      setPages(updatedPages);
    } catch (error) {
      console.error("책 페이퍼 제거 중 오류 발생:", error);
    }
  };

  return (
    <>
      <div className={s(styles.bookContainer)}>
        <div className={styles.header}>
          <div className={styles.headerLeft} onClick={() => navigate(-1)}>
            뒤로가기
          </div>
          <div className={styles.headerMain}>
            {book.coverTitle || book.bookTitle}
          </div>
          <div className={styles.headerRight}>
            {bookinfo.owner && (
              <div className={styles.red} onClick={() => setDeleteModal(true)}>
                책 삭제
              </div>
            )}
            {!bookinfo.owner && !bookinfo.picked && (
              <div onClick={() => subscribe()}>내 책장에 담기</div>
            )}
            {!bookinfo.owner && bookinfo.picked && (
              <div onClick={() => setUnSubscribeModal(true)}>
                책 구독취소하기
              </div>
            )}
          </div>
        </div>
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
                            <FeedModal2
                              width="150px"
                              setIsModalOpen={setListModal1}
                              isModalOpen={listModal1}
                              right="-5px"
                              top="10px"
                            >
                              <div className={styles.option}>
                                <div onClick={() => goTread(page.threadId)}>
                                  해당 스레드 보러가기
                                </div>
                                {bookinfo.owner && (
                                  <div
                                    onClick={() =>
                                      handleDeletePaper(page.paperId)
                                    }
                                  >
                                    책에서 페이퍼 제거
                                  </div>
                                )}
                              </div>
                            </FeedModal2>
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
                            <FeedModal2
                              width="150px"
                              setIsModalOpen={setListModal2}
                              isModalOpen={listModal2}
                              right="-5px"
                              top="10px"
                            >
                              <div className={styles.option}>
                                <div onClick={() => goTread(page.threadId)}>
                                  해당 스레드 보러가기
                                </div>
                                {bookinfo.owner && (
                                  <div
                                    onClick={() =>
                                      handleDeletePaper(page.paperId)
                                    }
                                  >
                                    책에서 페이퍼 제거
                                  </div>
                                )}
                              </div>
                            </FeedModal2>
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
      <BigModal
        modalIsOpen={subscribeModal}
        setModalIsOpen={setSubscribeModal}
        width="800px"
        height="500px"
      >
        <div className={styles.deleteTitle}>
          {book.coverTitle || book.bookTitle} 책 을 내 책장에 담으시겠습니까?
        </div>
        <div className={styles.container}>
          <div className={styles.categoryHeader}>카테고리 선택</div>
          <div className={styles.categorys}>
            {list.map((category) => (
              <div
                key={category.categoryId}
                className={s(
                  styles.category,
                  category.categoryId === cate && styles.select
                )}
                onClick={() => setCate(category.categoryId)}
              >
                <div>{category.name}</div>
                <div>{category.bookCount}귄 있음</div>
              </div>
            ))}
          </div>
        </div>
        <div className={styles.fff}>
          <div className={styles.del} onClick={() => setSubscribeModal(false)}>
            취소
          </div>
          <div className={styles.can} onClick={() => handleSubscribe()}>
            담기
          </div>
        </div>
      </BigModal>
      <BigModal
        modalIsOpen={unSubscribeModal}
        setModalIsOpen={setUnSubscribeModal}
        width="400px"
        height="160px"
      >
        <div className={styles.deleteTitle}>
          {book.coverTitle || book.bookTitle} 책 을 내 책장에서 빼시겠습니까?
        </div>
        <div className={styles.fff}>
          <div
            className={styles.can}
            onClick={() => setUnSubscribeModal(false)}
          >
            취소
          </div>
          <div className={styles.del} onClick={() => handleUnSubscribe()}>
            빼기
          </div>
        </div>
      </BigModal>
      <BigModal
        modalIsOpen={deleteModal}
        setModalIsOpen={setDeleteModal}
        width="400px"
        height="160px"
      >
        <div className={styles.deleteTitle}>
          {book.coverTitle || book.bookTitle} 책 을 삭제 하시겠습니까?
        </div>
        <div className={styles.fff}>
          <div className={styles.can} onClick={() => setDeleteModal(false)}>
            취소
          </div>
          <div className={styles.del} onClick={() => handleDelete()}>
            삭제
          </div>
        </div>
      </BigModal>
      <BigModal
        modalIsOpen={threadModal}
        setModalIsOpen={setThreadModal}
        width="1300px"
        height="860px"
      >
        <OneThread threadId={tId} setThreadModal={setThreadModal} />
      </BigModal>
    </>
  );
}
