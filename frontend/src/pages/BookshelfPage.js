import React, { useState, useEffect } from "react";
import styles from "./style/BookshelfPage.module.css";
import Container from "../components/frame/Container";
import CategoryEditModal from "../components/bookshelf/CategoryEditModal";
import BookshelfHeader from "../components/bookshelf/BookshelfHeader";
import Bookshelf from "../components/bookshelf/Bookshelf";
import { getCategoryList } from "../api/category/Category.js";
import useMyStore from "../store/useMyStore";
import { useParams } from "react-router-dom";
import useMybraryStore from "../store/useMybraryStore.js";

export default function BookshelfPage() {
  const [categoryList, setCategoryList] = useState([]);
  const { userid, bookShelfId } = useParams();
  const user = useMyStore((state) => state.my);
  const mybrary = useMybraryStore((state) => state.mybrary);
  const [checkme, setCheckme] = useState(false); //로그인한 회원인지 체크

  useEffect(() => {
    async function fetchbookshelfData() {
      try {
        const response = await getCategoryList(
          user.bookShelfId === mybrary.bookShelfId
            ? user.bookShelfId
            : mybrary.bookShelfId
        );

        const newCategoryList = [...response.data];
        const sortedCategoryList = newCategoryList.sort(
          (a, b) => a.seq - b.seq
        );
        setCategoryList(sortedCategoryList);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    if (+userid === user.memberId) {
      setCheckme(true);
    }

    fetchbookshelfData();
  }, [user, mybrary, userid]); // bookshelfId를 의존성 배열에 추가

  return (
    <>
      <Container>
        <BookshelfHeader />
        <div>
          <div className={styles.middle}>
            <div></div>
            {checkme && (
              <CategoryEditModal
                bookShelfId={bookShelfId}
                categoryList={categoryList}
                setCategoryList={setCategoryList}
                content="카테고리수정"
              />
            )}
          </div>
        </div>
        <div className={styles.bookshelf_container}>
          {Array.from({ length: Math.ceil(categoryList.length / 2) }).map(
            (_, rowIndex) => {
              const index = rowIndex * 2; // 0, 2, 4, 6, ...

              return (
                <div key={rowIndex} className={styles.bookshelf_line}>
                  <Bookshelf
                    category={categoryList[index]}
                    categoryName={styles.leftCategoryName}
                    sort={styles.leftBooks}
                  />
                  <Bookshelf
                    category={categoryList[index + 1]}
                    categoryName={styles.rightCategoryName}
                    sort={styles.rightBooks}
                  />
                </div>
              );
            }
          )}
        </div>
      </Container>
    </>
  );
}
