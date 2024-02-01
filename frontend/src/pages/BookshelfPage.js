import React, { useState, useEffect } from "react";
import styles from "./style/BookshelfPage.module.css";
import Container from "../components/frame/Container";
import CategoryEditModal from "../components/bookshelf/CategoryEditModal";
import BookshelfHeader from "../components/bookshelf/BookshelfHeader";
import Bookshelf from "../components/bookshelf/Bookshelf";
// import { useNavigate } from "react-router-dom";

export default function BookshelfPage() {
  const [categoryList, setCategoryList] = useState([]);

  useEffect(() => {
    const fetchedData = {
      bookShelfId: "123",
      categoryList: [
        { categoryId: "1", categoryName: "문학", categorySeq: 1, bookCount: 1 },
        {
          categoryId: "2",
          categoryName: "역사",
          categorySeq: 2,
          bookCount: 10,
        },
        { categoryId: "3", categoryName: "과학", categorySeq: 3, bookCount: 8 },
        {
          categoryId: "4",
          categoryName: "예술",
          categorySeq: 4,
          bookCount: 12,
        },
        {
          categoryId: "5",
          categoryName: "여행",
          categorySeq: 5,
          bookCount: 11,
        },
        { categoryId: "6", categoryName: "요리", categorySeq: 6, bookCount: 2 },
        {
          categoryId: "7",
          categoryName: "만화",
          categorySeq: 7,
          bookCount: 12,
        },
        {
          categoryId: "8",
          categoryName: "연애",
          categorySeq: 8,
          bookCount: 16,
        },
        { categoryId: "9", categoryName: "사랑", categorySeq: 9, bookCount: 5 },
      ],
    };

    //책의 개수가 홀수일 때 한칸을 채워주도록
    if (fetchedData.categoryList.length % 2 !== 0) {
      fetchedData.categoryList.push({
        categoryId: "empty",
        // categorySeq: fetchedData.categoryList.length + 1,
      });
    }

    const sortedCategoryList = fetchedData.categoryList.sort(
      (a, b) => a.categorySeq - b.categorySeq
    );
    // setBookShelfId(fetchedData.bookShelfId);
    setCategoryList(sortedCategoryList);
  }, []);

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
