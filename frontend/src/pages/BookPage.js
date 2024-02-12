import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import React, { useState, useRef, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getBookList, getCategoryList } from "../api/category/Category";
import useBookStore from "../store/useBookStore";
import s from "classnames";

export default function BookPage() {
  const { categoryid } = useParams();
  const carouselRef = useRef(null);
  const navigate = useNavigate();
  const [bookList, setBookList] = useState([]);
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);
  const selectedBook = bookList[selectedBookIndex];
  const setBook = useBookStore((state) => state.setBook);

  const handleSelectBook = (index) => {
    setSelectedBookIndex(index);
  };

  const [animate, setAnimate] = useState(false);
  const handleBookClick = () => {
    setBook(selectedBook);
    setAnimate(true);

    // 애니메이션 시간 후에 페이지 이동
    setTimeout(() => {
      navigate(`/book/${selectedBook.bookId}`);
    }, 600); // 300ms는 애니메이션 시간과 일치해야 합니다.
  };

  // const handleWheel = useCallback(
  //   (event) => {
  //     const scrollAmount = event.deltaY;

  //     // 스크롤 방향에 따라 선택된 책의 인덱스를 변경
  //     if (scrollAmount > 0) {
  //       // 아래로 스크롤
  //       setSelectedBookIndex((prevIndex) =>
  //         prevIndex < bookList.length - 1 ? prevIndex + 1 : prevIndex
  //       );
  //     } else if (scrollAmount < 0) {
  //       // 위로 스크롤
  //       setSelectedBookIndex((prevIndex) =>
  //         prevIndex > 0 ? prevIndex - 1 : prevIndex
  //       );
  //     }
  //   },
  //   [bookList.length]
  // );

  // useEffect(() => {
  //   // 캐러셀 DOM 요소에 이벤트 리스너 추가
  //   const carouselElement = carouselRef.current;
  //   if (carouselElement) {
  //     carouselElement.addEventListener("wheel", handleWheel);
  //   }
  //   // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
  //   return () => {
  //     if (carouselElement) {
  //       carouselElement.removeEventListener("wheel", handleWheel);
  //     }
  //   };
  // }, [handleWheel]); // 'handleWheel'을 의존성 배열에 추가

  useEffect(() => {
    async function fetchbookshelfData() {
      try {
        console.log(categoryid);
        const response = await getBookList(categoryid);
        console.log(response.data);

        setBookList(response.data);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchbookshelfData();
  }, []);
  return (
    <>
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate("../")}>
            &lt; 뒤로가기
          </div>
        </div>

        <div className={title.title}>
          <div
            className={title.left_title}
            onClick={() => navigate("../../rollingpaper")}
          >
            &lt; 롤링페이퍼
          </div>
          <div className={title.main_title}>카테고리이름</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../../bookshelf")}
          >
            {" "}
            책장 &gt;
          </div>
        </div>

        <div className={styles.header}>
          <div className={styles.책추가}>책추가</div>
          <div className={styles.options}>옵션</div>
        </div>

        <div className={styles.flex}>
          <div className={styles.middle}>
            <div className={styles.캐러셀}>
              {bookList.map((book, index) => {
                // 선택된 아이템
                if (index === selectedBookIndex) {
                  return (
                    <div
                      key={book.bookId}
                      className={s(
                        styles.캐러셀아이템,
                        styles.selected,
                        styles[`color${book.coverColorCode}`]
                      )}
                      onClick={() => handleSelectBook(index)}
                      style={{ backgroundColor: book.coverColorCode }}
                    >
                      <div className={styles.item}>
                        <div className={styles.writerflex}>
                          <div className={styles.bookTitle}>
                            {book.coverTitle}
                          </div>
                          <div className={styles.flex}>
                            <div
                              className={styles.writerImage}
                              style={{
                                background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.writer.imageUrl}")`,
                                backgroundRepeat: "no-repeat",
                                backgroundPosition: "center",
                                backgroundSize: "cover",
                              }}
                            ></div>
                            <div className={styles.writer}>
                              {book.writer.nickname}
                            </div>
                          </div>
                        </div>
                        <div
                          className={styles.bookCoverImage}
                          style={{
                            background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.imageUrl}")`,
                            backgroundRepeat: "no-repeat",
                            backgroundPosition: "center",
                            backgroundSize: "cover",
                          }}
                        ></div>
                      </div>
                    </div>
                  );
                } else {
                  // 선택되지 않은 아이템
                  return (
                    <div
                      key={book.bookId}
                      className={s(
                        styles.캐러셀아이템,
                        styles[`color${book.coverColorCode}`]
                      )}
                      onClick={() => handleSelectBook(index)}
                      style={{ backgroundColor: book.coverColorCode }}
                    >
                      <div className={styles.item}>
                        <div>{book.coverTitle}</div>
                        <div className={styles.flex}>
                          <div
                            className={styles.writerImage2}
                            style={{
                              background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.writer.imageUrl}")`,
                              backgroundRepeat: "no-repeat",
                              backgroundPosition: "center",
                              backgroundSize: "cover",
                            }}
                          ></div>
                          <div>{book.writer.nickname}</div>
                        </div>
                      </div>
                    </div>
                  );
                }
              })}
            </div>
          </div>

          <div>
            <div
              className={`${styles.main_left} ${
                animate ? styles["선택한책-animate"] : ""
              }`}
              onClick={handleBookClick}
            >
              {selectedBook && (
                <>
                  <div
                    className={s(
                      styles.cover,
                      styles[`color${selectedBook.coverColorCode}`]
                    )}
                  >
                    <img
                      className={s(
                        styles.book,
                        styles[`layImg${selectedBook.coverLayout}`]
                      )}
                      src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${selectedBook.imageUrl}`}
                      alt={`Cover of ${selectedBook.coverTitle}`}
                    />
                    <div
                      className={s(
                        styles.text,
                        styles[`layText${selectedBook.coverLayout}`]
                      )}
                    >
                      {selectedBook.coverTitle}
                    </div>
                  </div>
                  <div
                    className={s(
                      styles.cover2,
                      styles[`backcolor${selectedBook.coverColorCode}`]
                    )}
                  ></div>
                </>
              )}
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
