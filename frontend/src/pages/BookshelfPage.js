import React, { useState, useEffect } from "react";
import styles from "./style/BookshelfPage.module.css";
import Container from "../components/frame/Container";
import CategoryEditModal from "../components/bookshelf/CategoryEditModal";
import BookshelfHeader from "../components/bookshelf/BookshelfHeader";
import Bookshelf from "../components/bookshelf/Bookshelf";
import {
  getCategoryList,
  updateCategory,
  createCategory,
  getBookList,
  deleteCategory,
} from "../api/category/Category.js";

import { useParams, Params } from "react-router-dom";
// import { useNavigate } from "react-router-dom";

export default function BookshelfPage() {
  const [categoryList, setCategoryList] = useState([]);
  const [fetchedData, setFetchedData] = useState([]);
  const { bookShelfId } = useParams(); // URL에서 bookshelfId 추출
  useEffect(() => {
    async function fetchbookshelfData() {
      console.log(bookShelfId);
      try {
        // bookshelfId를 이용하여 서버에 요청
        const response = await getCategoryList(bookShelfId);
        setFetchedData(response);
        console.log(response);

        // 책장 카테고리 정렬 및 홀수 개수 처리
        const newCategoryList = [...response.data];
        if (newCategoryList.length % 2 !== 0) {
          newCategoryList.push({
            categoryId: "empty",
          });
        }
        const sortedCategoryList = newCategoryList.sort(
          (a, b) => a.seq - b.seq
        );
        setCategoryList(sortedCategoryList);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }

    fetchbookshelfData();
  }, [bookShelfId]); // bookshelfId를 의존성 배열에 추가

  return (
    <>
      <Container>
        <BookshelfHeader />
        <div>
          <div className={styles.middle}>
            <div>책갈피목록</div>
            <CategoryEditModal
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
