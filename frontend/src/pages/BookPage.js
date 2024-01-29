import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import React, { useState } from "react";
export default function BookPage() {
  const [bookList, setBookList] = useState([]);
  setBookList([
    {
      bookId: "1",
      bookCoverImageUrl: "https://via.placeholder.com/150",
      bookTitle: "Book 1",
      bookCoverColor: "#FFA07A",
      owner: "Owner 1",
    },
    {
      bookId: "2",
      bookCoverImageUrl: "https://via.placeholder.com/150",
      bookTitle: "Book 1",
      bookCoverColor: "#FFA07A",
      owner: "Owner 1",
    },
    {
      bookId: "3",
      bookCoverImageUrl: "https://via.placeholder.com/150",
      bookTitle: "Book 1",
      bookCoverColor: "#FFA07A",
      owner: "Owner 1",
    },
    {
      bookId: "4",
      bookCoverImageUrl: "https://via.placeholder.com/150",
      bookTitle: "Book 1",
      bookCoverColor: "#FFA07A",
      owner: "Owner 1",
    },
    // ... 더 많은 책 객체 추가
  ]);
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);

  const handleSelectBook = (index) => {
    setSelectedBookIndex(index);
  };

  // const handleWheel = (event) => {
  //   const container = event.currentTarget;
  //   const scrollAmount = event.deltaY; // deltaY는 수직 스크롤 양을 나타냅니다. 이 값을 수평 스크롤에 적용합니다.
  //   container.scrollLeft += scrollAmount; // 수평 스크롤 조정
  // };
  return (
    <>
      <Container backgroundColor="#fff9f6">
        <div>
          <div>뒤로가기</div>
        </div>
        <div className={styles.body}>
          <div className={styles.bookpage}>
            <div className={styles.head}>
              <div>게시물</div>
              <div className={styles.카테고리}>카테고리</div>
              <div className={styles.롤링페이퍼}>롤링페이퍼</div>
            </div>
            <div className={styles.middle}>
              <div className={styles.책추가}>책추가</div>
              <div className={styles.책검색}>책검색</div>
              <div className={styles.캐러셀}>
                {bookList.map((book, index) => (
                  <div
                    key={book.bookId}
                    className={`${styles.캐러셀아이템} ${
                      index === selectedBookIndex ? styles.selected : ""
                    }`}
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
                ))}
              </div>
            </div>
            <div className={styles.mainbook}>
              <div className={styles.책갈피로이동}>책갈피로이동</div>
              <div className={styles.옵션}>옵션</div>
              <div>책</div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
