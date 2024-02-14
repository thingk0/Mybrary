import React, { useState, useEffect } from "react";
import styles from "./style/BookshelfPage.module.css";
import Container from "../components/frame/Container";
import CategoryEditModal from "../components/bookshelf/CategoryEditModal";
import BookshelfHeader from "../components/bookshelf/BookshelfHeader";
import Bookshelf from "../components/bookshelf/Bookshelf";
import { getCategoryList } from "../api/category/Category.js";

import { useParams } from "react-router-dom";

export default function BookshelfPage() {
  const [categoryList, setCategoryList] = useState([]);
  const { bookShelfId } = useParams();
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
  }, []); // bookshelfId를 의존성 배열에 추가

  return (
    <>
      <Container>
        <BookshelfHeader />
        <div>
          <div className={styles.middle}>
            <div>책갈피목록</div>
            <CategoryEditModal
              bookShelfId={bookShelfId}
              categoryList={categoryList}
              setCategoryList={setCategoryList}
              content="카테고리수정"
            />
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
