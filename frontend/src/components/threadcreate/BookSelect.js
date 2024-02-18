import styles from "./BookSelect.module.css";
import s from "classnames";

export default function BookSelect({
  setModalIsOpen2,
  setModalIsOpen,
  setBookId,
  setBook,
  book,
  booklist,
  papers,
}) {
  const selectBook = (books) => {
    if (papers.length + books.paperCount <= 50) setBook(books);
  };
  const handleSelect = () => {
    if (book.title) {
      setBookId(book.bookId);
      setModalIsOpen2(false);
    }
  };
  return (
    <>
      <div className={styles.title}>
        <div className={styles.titleName}>
          {papers.length} 장의 페이퍼를 담을 책 선택
        </div>
        <div
          className={styles.createButton}
          onClick={() => setModalIsOpen(true)}
        >
          새로운 책 만들기
        </div>
      </div>
      <div className={styles.main}>
        {booklist.map((category) => (
          <div>
            <div className={styles.category} key={category.categoryId}>
              <div className={styles.categoryName}>{category.categoryName}</div>
              {/* <img className={styles.line} src={line} alt="" /> */}
            </div>
            {category.bookList.map((books) => (
              <div
                className={s(
                  papers.length + books.paperCount <= 50
                    ? styles.book
                    : styles.none,
                  books === book && styles.select
                )}
                key={books.bookId}
                onClick={() => selectBook(books)}
              >
                <div className={styles.bookName}>{books.title}</div>
                <div className={styles.paperCount}>{books.paperCount}/50</div>
              </div>
            ))}
          </div>
        ))}
      </div>
      <div
        className={book.title ? styles.selectButton : styles.noneSelect}
        onClick={() => handleSelect()}
      >
        {book.title ? `"${book.title}"책에 담기` : "책을 선택하세요"}
      </div>
    </>
  );
}
