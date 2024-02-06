import BookAlbum from "./BookAlbum";
import styles from "./Bookshelf.module.css";
import s from "classnames";
import { useNavigate } from "react-router-dom";

//책장을 랜더링하는 부분
export default function Bookshelf({ category, categoryName, sort }) {
  const navigate = useNavigate();
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
              {category.name}
            </div>
            <div className={s(sort, styles.books)}>
              <div
                className={styles.categoryNameOnHover}
                onClick={() => navigate(`${category.categoryId}`)}
              >
                {category.bookCount}권의 "{category.name}"책 보러가기
              </div>
              <BookAlbum category={category} />
            </div>
          </>
        )}
      </div>
    </div>
  );
}
