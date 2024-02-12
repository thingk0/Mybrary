import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import React, { useState, useRef, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getBookList, getCategoryList } from "../api/category/Category";
import useBookStore from "../store/useBookStore";
import s from "classnames";
import three from "../assets/three.png";
import Modal from "../components/common/Modal";

export default function BookPage() {
  const { bookShelfId, categoryid } = useParams();
  const carouselRef = useRef(null);
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);
  const [bookList, setBookList] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [selectCategory, setSelectCategory] = useState("");
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);
  const selectedBook = bookList[selectedBookIndex];
  const setBook = useBookStore((state) => state.setBook);

  const handleSelectBook = (index) => {
    setSelectedBookIndex(index);
  };

  const handleBookClick = () => {
    setBook(selectedBook);
    navigate(`/book/${selectedBook.bookId}`);
  };
  const handleCategory = (id) => {
    setOpen(false);
    navigate(`../${id}`);
  };

  useEffect(() => {
    async function fetchbookshelfData() {
      try {
        console.log(categoryid);
        const response = await getBookList(categoryid);
        const response2 = await getCategoryList(bookShelfId);
        console.log(response.data);
        console.log(response2.data);
        setBookList(response.data);
        setCategoryList(response2.data);
        const selectedCategory = response2.data.find(
          (category) => category.categoryId === +categoryid
        );
        setSelectCategory(selectedCategory.name);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchbookshelfData();
  }, [categoryid]);
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
          <div
            className={s(title.main_title, styles.fff)}
            onClick={() => setOpen(true)}
          >
            <Modal
              width={"300px"}
              title={selectCategory}
              top={"40px"}
              // left={"-55px"}
              open={open}
              setOpen={setOpen}
              header={"카테고리 선택"}
            >
              <div className={styles.nameContainer}>
                {categoryList.map((category) => (
                  <div
                    className={s(
                      selectCategory === category.name
                        ? styles.selectName
                        : styles.name
                    )}
                    onClick={() => handleCategory(category.categoryId)}
                  >
                    {category.name}
                  </div>
                ))}
              </div>
            </Modal>
            <img className={styles.categoryImg} src={three} alt="" />
          </div>

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
        </div>
        {bookList.length ? (
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

            <div className={styles.selectedBook}>
              <div className={styles.options}>
                <Modal
                  width="140px"
                  top="0px"
                  right="0px"
                  title={"옵션"}
                  open={open2}
                  setOpen={setOpen2}
                >
                  <div>
                    <div className={styles.option}>
                      <span className={styles.texts}>
                        {selectedBook.coverTitle}
                      </span>{" "}
                      책 수정
                    </div>
                    <div className={styles.option}>
                      <span className={styles.texts}>
                        {selectedBook.coverTitle}
                      </span>{" "}
                      책 삭제
                    </div>
                  </div>
                </Modal>
              </div>
              <div
                className={styles.main_left}
                onClick={() => handleBookClick()}
              >
                <>
                  <div
                    className={s(
                      styles.cover,
                      styles[`color${selectedBook.coverColorCode}`]
                    )}
                  >
                    <div
                      className={s(
                        styles.img,
                        styles[`layImg${selectedBook.coverLayout}`]
                      )}
                      style={{
                        background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${selectedBook.imageUrl}")`,
                        backgroundSize: "cover",
                        backgroundRepeat: "no-repeat",
                        backgroundPosition: "center",
                      }}
                    ></div>
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
              </div>
            </div>
          </div>
        ) : (
          <div className={styles.noneBook}>
            <div className={styles.noneTitle}>
              카테고리에 책이 하나도 없습니다.
            </div>
            <div className={styles.noneButton}>책 추가하기</div>
          </div>
        )}
      </Container>
    </>
  );
}
