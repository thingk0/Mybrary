import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import React, { useState, useRef, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getCategoryList } from "../api/category/Category";

export default function BookPage() {
  const { bookShelfId, categoryid } = useParams();
  const carouselRef = useRef(null);
  const navigate = useNavigate();
  const [bookList, setBookList] = useState([]);
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);
  const selectedBook = bookList[selectedBookIndex];
  const [categoryList, setCategoryList] = useState([]);

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
    setBookList([
      {
        bookId: "1",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "여행기록을담다",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "2",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 2",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "3",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 3",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "4",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 4",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "5",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 5",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "6",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 6",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "7",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "8",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 6",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "9",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "10",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 6",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "11",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "12",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 6",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "13",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "14",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "15",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "16",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 6",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "17",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "18",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "19",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "20",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "21",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "22",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      {
        bookId: "23",
        bookCoverImageUrl: "https://via.placeholder.com/150",
        bookTitle: "Book 7",
        bookCoverColor: "#FFA07A",
        owner: "Owner 1",
      },
      // ... 더 많은 책 객체 추가
    ]);
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
        const response = await getCategoryList(bookShelfId);
        const newCategoryList = [...response.data];
        const sortedCategoryList = newCategoryList.sort(
          (a, b) => a.seq - b.seq
        );
        setCategoryList(sortedCategoryList);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchbookshelfData();
  });
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
                    style={{ backgroundColor: book.bookCoverColor }}
                  >
                    <img
                      className={styles.bookCoverImage}
                      src={book.bookCoverImageUrl}
                      alt={`Cover of ${book.bookTitle}`}
                    />
                    <div className={styles.bookTitle}>{book.bookTitle}</div>
                  </div>
                );
              } else {
                // 선택되지 않은 아이템
                return (
                  <div
                    key={book.bookId}
                    className={styles.캐러셀아이템}
                    onClick={() => handleSelectBook(index)}
                    style={{ backgroundColor: book.bookCoverColor }}
                  >
                    <div className={styles.bookTitle2}>{book.bookTitle}</div>
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
                src={selectedBook.bookCoverImageUrl}
                alt={`Cover of ${selectedBook.bookTitle}`}
                className={styles.selectedBookImage}
              />
              <div className={styles.selectedBookTitle}>
                {selectedBook.bookTitle}
              </div>
            </>
          )}
        </div>
      </Container>
    </>
  );
}
