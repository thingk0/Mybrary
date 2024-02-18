import Container from "../components/frame/Container";
import styles from "./style/BookPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getBookList, getCategoryList } from "../api/category/Category";
import useBookStore from "../store/useBookStore";
import useUserStore from "../store/useUserStore";
import s from "classnames";
import three from "../assets/three.png";
import Modal from "../components/common/Modal";
import BigModal from "../components/common/BigModal";
import { deleteBook } from "../api/book/Book";
import BookCreateOfCategory from "../components/common/BookCreateOfCategory";
import useUrlStore from "../store/useUrlStore";
import BookUpdate from "../components/common/BookUpdate";

export default function BookPage() {
  const { userid, bookShelfId, categoryid } = useParams();
  const memberId = useUserStore((state) => state.user.memberId);
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [editModal, setEditModal] = useState(false);
  const [createModal, setCreateModal] = useState(false);
  const [bookList, setBookList] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [selectCategory, setSelectCategory] = useState("");
  const [selectedBookIndex, setSelectedBookIndex] = useState(0);
  const setBook = useBookStore((state) => state.setBook);

  const handleSelectBook = (index) => {
    setSelectedBookIndex(index);
    setSelectedBook(bookList[index]); // 첫 번째 책 선택
  };

  const sampleLocation = useLocation();
  const setUrl = useUrlStore((state) => state.setUrl);
  const handleBookClick = async () => {
    setUrl({ url: sampleLocation.pathname });
    await setBook(selectedBook);
    navigate(`/book/${selectedBook.bookId}`);
  };
  const handleCategory = (id) => {
    setOpen(false);
    navigate(`../${id}`);
  };
  const [selectedBook, setSelectedBook] = useState(null);

  useEffect(() => {
    if (bookList.length > 0) {
      setSelectedBook(bookList[0]); // 첫 번째 책 선택
    }
  }, [bookList]);

  useEffect(() => {
    async function fetchbookshelfData() {
      try {
        const response = await getBookList(categoryid);
        const response2 = await getCategoryList(bookShelfId);
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
  }, [categoryid, bookShelfId]);

  const handleDelete = async () => {
    try {
      await deleteBook(selectedBook.bookId);
      const updatedBookList = bookList.filter(
        (book) => book.bookId !== selectedBook.bookId
      );
      setBookList(updatedBookList);
      setSelectedBookIndex(0); // 첫 번째 책 선택
      setBook(updatedBookList[0]); // 선택된 책 업데이트
      setDeleteModal(false);
    } catch (error) {
      console.error("책 삭제 중 오류 발생:", error);
    }
  };
  return (
    <>
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate(-1)}>
            &lt; 뒤로가기
          </div>
        </div>

        <div className={title.title}>
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
        </div>

        {bookList.length ? (
          <div className={styles.flex}>
            <div className={styles.middle}>
              {+userid === memberId && (
                <div
                  className={styles.책추가}
                  onClick={() => setCreateModal(true)}
                >
                  책추가
                </div>
              )}
              <div className={styles.캐러셀}>
                {bookList?.map((book, index) => {
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
                                  background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.writer.imageUrl}")no-repeat center/cover`,
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
                              background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.imageUrl}")no-repeat center/cover`,
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
                                background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.writer.imageUrl}")no-repeat center/cover`,
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
              {selectedBook?.writer.memberId === memberId && (
                <div className={styles.options}>
                  <Modal
                    width="200px"
                    top="0px"
                    right="0px"
                    title={"수정 및 삭제"}
                    open={open2}
                    setOpen={setOpen2}
                  >
                    <div>
                      <div
                        className={styles.option}
                        onClick={() => setEditModal(true)}
                      >
                        <span className={styles.texts}>
                          {selectedBook?.coverTitle}
                        </span>
                        책 수정
                      </div>
                      {/* <div className={styles.option}>
                        <span className={styles.texts}>
                          {selectedBook?.coverTitle}
                        </span>
                        책 카테고리 변경
                      </div> */}
                      <div
                        className={styles.option}
                        onClick={() => setDeleteModal(true)}
                      >
                        <span className={styles.texts}>
                          {selectedBook?.coverTitle}
                        </span>
                        <span className={styles.delete}>책 삭제</span>
                      </div>
                    </div>
                  </Modal>
                </div>
              )}
              <div
                className={styles.main_left}
                onClick={() => handleBookClick()}
              >
                <div
                  className={s(
                    styles.cover,
                    styles[`color${selectedBook?.coverColorCode}`]
                  )}
                >
                  <div
                    className={s(
                      styles.img,
                      styles[`layImg${selectedBook?.coverLayout}`]
                    )}
                    style={{
                      background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${selectedBook?.imageUrl}")no-repeat center/cover`,
                    }}
                  ></div>
                  <div
                    className={s(
                      styles.text,
                      styles[`layText${selectedBook?.coverLayout}`]
                    )}
                  >
                    {selectedBook?.coverTitle}
                  </div>
                </div>
                <div
                  className={s(
                    styles.cover2,
                    styles[`backcolor${selectedBook?.coverColorCode}`]
                  )}
                ></div>
              </div>
            </div>
          </div>
        ) : (
          <div className={styles.noneBook}>
            <div className={styles.noneTitle}>
              카테고리에 책이 하나도 없습니다.
            </div>
            <div
              className={styles.noneButton}
              onClick={() => setCreateModal(true)}
            >
              책 추가하기
            </div>
          </div>
        )}
      </Container>
      <BigModal
        modalIsOpen={deleteModal}
        setModalIsOpen={setDeleteModal}
        width="400px"
        height="160px"
      >
        <div className={styles.deleteTitle}>
          {selectedBook?.coverTitle} 책 을 삭제 하시겠습니까?
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
        modalIsOpen={editModal}
        setModalIsOpen={setEditModal}
        width="1200px"
        height="800px"
        background="var(--main4)"
      >
        <BookUpdate
          categoryid={categoryid}
          book={selectedBook}
          booklist={categoryList}
          setModalIsOpen={setEditModal}
          setList={setBookList}
        />
      </BigModal>
      <BigModal
        modalIsOpen={createModal}
        setModalIsOpen={setCreateModal}
        width="1200px"
        height="800px"
        background="var(--main4)"
      >
        <BookCreateOfCategory
          booklist={categoryList}
          setModalIsOpen={setCreateModal}
          setList={setBookList}
        />
      </BigModal>
    </>
  );
}
