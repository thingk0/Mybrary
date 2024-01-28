import React, { useState, useEffect } from "react";
import Container from "../components/frame/Container";
import styles from "./style/BookshelfPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import s from "classnames";
import CategoryEditModal from "../components/bookshelf/CategoryEditModal";
import { useNavigate } from "react-router-dom";

export default function BookshelfPage() {
  const navigate = useNavigate();
  const [categoryList, setCategoryList] = useState([]);
  // 책장 ID 상태 (필요한 경우에 사용)
  // const [bookShelfId, setBookShelfId] = useState(null);

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

  //책들 랜더링 하는 부분
  function BookAlbum({ category }) {
    const colors = [
      "#946087",
      "#BA667E",
      "#F5998F",
      "#455371",
      "#57423F",
      "#7E994F",
      "#2F4858",
    ];

    const bookCount = category ? category.bookCount : 0;
    return (
      <>
        {Array.from({
          length: bookCount < 10 ? bookCount : 10,
        }).map((_, idx) => {
          const height = Math.random() * 50 + 90;
          const color = colors[Math.floor(Math.random() * colors.length)];
          return (
            <div
              key={idx}
              className={styles.album}
              style={{
                height: `${height}px`,
                backgroundColor: color,
              }}
            ></div>
          );
        })}
        {bookCount - 10 > 0 && <div>+ {bookCount - 10}</div>}
      </>
    );
  }

  //책장을 랜더링하는 부분
  function Bookshelf({ category, categoryName, sort }) {
    return (
      <div
        className={styles.boxs}
        style={{
          flex: `${
            category && category.categoryId !== "empty"
              ? category.bookCount > 10
                ? 10
                : category.bookCount < 4
                ? 4
                : category.bookCount
              : 2
          }`,
        }}
      >
        <div className={styles.box}>
          {category && category.categoryId !== "empty" && (
            <>
              <div className={s(categoryName, styles.categoryName)}>
                {category.categoryName}
              </div>
              <div className={s(sort, styles.books)}>
                <div className={styles.categoryNameOnHover}>
                  {category.bookCount}권의 "{category.categoryName}"책 보러가기
                </div>
                <BookAlbum category={category} />
              </div>
            </>
          )}
        </div>
      </div>
    );
  }

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
            onClick={() => navigate("../threads")}
          >
            &lt; 게시물
          </div>
          <div className={title.main_title}>cwnsgh's bookshelf</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../rollingpaper")}
          >
            {" "}
            롤링페이퍼 &gt;
          </div>
        </div>
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
