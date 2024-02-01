import BookAlbum from "./BookAlbum";
import styles from "./Bookshelf.module.css";
import s from "classnames";

//책장을 랜더링하는 부분
export default function Bookshelf({ category, categoryName, sort }) {
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
