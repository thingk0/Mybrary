import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import React, { useState, useRef, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getBookList, getCategoryList } from "../api/category/Category";

export default function BookPage() {
  const { categoryid } = useParams();
  const carouselRef = useRef(null);
  const navigate = useNavigate();
  const [bookList, setBookList] = useState([]);
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);
  const selectedBook = bookList[selectedBookIndex];

  const handleSelectBook = (index) => {
    setSelectedBookIndex(index);
  };

  const [animate, setAnimate] = useState(false);
  const handleBookClick = () => {
    setAnimate(true);

    // 애니메이션 시간 후에 페이지 이동
    setTimeout(() => {
      navigate(`${selectedBook.bookId}`);
    }, 600); // 300ms는 애니메이션 시간과 일치해야 합니다.
  };

  const handleWheel = useCallback(
    (event) => {
      const scrollAmount = event.deltaY;

      // 스크롤 방향에 따라 선택된 책의 인덱스를 변경
      if (scrollAmount > 0) {
        // 아래로 스크롤
        setSelectedBookIndex((prevIndex) =>
          prevIndex < bookList.length - 1 ? prevIndex + 1 : prevIndex
        );
      } else if (scrollAmount < 0) {
        // 위로 스크롤
        setSelectedBookIndex((prevIndex) =>
          prevIndex > 0 ? prevIndex - 1 : prevIndex
        );
      }
    },
    [bookList.length]
  );

  useEffect(() => {
    // 캐러셀 DOM 요소에 이벤트 리스너 추가
    const carouselElement = carouselRef.current;
    if (carouselElement) {
      carouselElement.addEventListener("wheel", handleWheel);
    }
    // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
    return () => {
      if (carouselElement) {
        carouselElement.removeEventListener("wheel", handleWheel);
      }
    };
  }, [handleWheel]); // 'handleWheel'을 의존성 배열에 추가

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
          <div className={title.main_title}>cwnsgh's thread</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../../bookshelf")}
          >
            {" "}
            책장 &gt;
          </div>
        </div>

        <div className={styles.middle}>
          <div className={styles.header}>
            <div className={styles.책추가}>책추가</div>
            <div className={styles.책검색}>책검색</div>
          </div>

          <div className={styles.캐러셀} ref={carouselRef}>
            {bookList.map((book, index) => {
              // 선택된 아이템
              if (index === selectedBookIndex) {
                return (
                  <div
                    key={book.bookId}
                    className={`${styles.캐러셀아이템} ${styles.selected}`}
                    onClick={() => handleSelectBook(index)}
                    style={{ backgroundColor: book.coverColorCode }}
                  >
                    <img
                      className={styles.bookCoverImage}
                      src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${book.imageUrl}`}
                      alt={`Cover of ${book.bookTitle}`}
                    />
                    <div className={styles.bookTitle}>{book.coverTitle}</div>
                  </div>
                );
              } else {
                // 선택되지 않은 아이템
                return (
                  <div
                    key={book.bookId}
                    className={styles.캐러셀아이템}
                    onClick={() => handleSelectBook(index)}
                    style={{ backgroundColor: book.coverColorCode }}
                  >
                    <div className={styles.bookTitle2}>{book.coverTitle}</div>
                  </div>
                );
              }
            })}
          </div>
        </div>

        <div className={styles.options}>
          <div>책갈피로이동</div>
          <div>옵션</div>
        </div>

        <div
          className={`${styles.선택한책} ${
            animate ? styles["선택한책-animate"] : ""
          }`}
          onClick={handleBookClick}
        >
          {selectedBook && (
            <>
              <img
                src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${selectedBook.imageUrl}`}
                alt={`Cover of ${selectedBook.coverTitle}`}
                className={styles.selectedBookImage}
              />
              <div className={styles.selectedBookTitle}>
                {selectedBook.coverTitle}
              </div>
            </>
          )}
        </div>
      </Container>
    </>
  );
}
