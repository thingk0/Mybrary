import styles from "./BookCreate.module.css";
import three from "../../assets/three.png";
import s from "classnames";
import { useState } from "react";
import Modal from "./Modal";
import { getBookList } from "../../api/category/Category";
import { updateBook } from "../../api/book/Book";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export default function BookUpdate({
  categoryid,
  book,
  booklist,
  setModalIsOpen,
  setList,
}) {
  const navigate = useNavigate();
  const layouts = [1, 2, 3, 4, 5, 6];
  const colors = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
  const categorys = booklist.map((category) => {
    return { categoryId: category.categoryId, name: category.name };
  });
  const [open, setOpen] = useState(false);

  const [value, setValue] = useState({
    title: book.coverTitle,
    coverLayout: book.coverLayout,
    coverColorCode: book.coverColorCode,
    categoryId: categoryid,
    imageUrl: book.imageUrl,
  });
  const handleChange = (name, value) => {
    setValue((prevValue) => ({
      ...prevValue,
      [name]: value,
    }));
  };
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    handleChange(name, value);
  };

  const [title, setTitle] = useState("선택되지 않음");
  const handleCategory = (category) => {
    handleChange("categoryId", category.categoryId);
    setTitle(category.name);
    setT(true);
    setOpen(false);
  };
  const addNewBookToCategory = async (categoryId) => {
    const updatedBookList = await getBookList(categoryId);
    setList(updatedBookList.data);
  };

  const handleSubmit = async () => {
    await updateBook({
      bookId: book.bookId,
      title: value.title,
      coverLayout: value.coverLayout,
      coverColorCode: value.coverColorCode,
      beforeCategoryId: categoryid,
      afterCategoryId: value.categoryId,
    });
    addNewBookToCategory(value.categoryId);
    setModalIsOpen(false);
    navigate(`../${value.categoryId}`);
  };
  const [t, setT] = useState(false);
  const nonecate = () => {
    toast.error("책의 카테고리를 선택해주세요", {
      position: "top-center",
    });
  };

  return (
    <div className={styles.container}>
      <div className={styles.title}>새로운 책 만들기</div>
      <div className={styles.main}>
        <div className={styles.main_left}>
          <div
            className={s(styles.cover, styles[`color${value.coverColorCode}`])}
          >
            <div
              className={s(styles.book, styles[`layImg${value.coverLayout}`])}
              style={{
                background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${value.imageUrl}")no-repeat center/cover`,
              }}
            ></div>
            <textarea
              placeholder="표지명을 작성하세요"
              name="title"
              value={value.title}
              onChange={handleInputChange}
              className={s(styles.text, styles[`layText${value.coverLayout}`])}
            ></textarea>
          </div>
          <div
            className={s(
              styles.cover2,
              styles[`backcolor${value.coverColorCode}`]
            )}
          ></div>
        </div>
        <div className={styles.main_right}>
          <div className={styles.subtitle}>책 커버 레이아웃</div>
          <div className={styles.layouts}>
            {layouts.map((lay) => (
              <div
                key={lay}
                className={s(
                  styles.layout,
                  value.coverLayout === lay && styles.select
                )}
                onClick={() => handleChange("coverLayout", lay)}
              >
                <div className={styles[`layText${lay}`]}>글</div>
                <div className={styles[`layImg${lay}`]}>사진</div>
              </div>
            ))}
          </div>
          <div className={styles.subtitle}>책 커버 색상</div>
          <div className={styles.colors}>
            {colors.map((c) => (
              <div
                key={c}
                className={s(
                  styles.color,
                  styles[`color${c}`],
                  value.coverColorCode === c && styles.select
                )}
                onClick={() => handleChange("coverColorCode", c)}
              ></div>
            ))}
          </div>
          <div className={styles.categorys}>
            <div className={styles.categorytitle}>책 카테고리 선택</div>
            <div className={styles.category}>
              <div className={styles.categoryName}>
                <Modal
                  height={"300px"}
                  width={"200px"}
                  title={title}
                  bottom={"40px"}
                  left={"-15px"}
                  open={open}
                  setOpen={setOpen}
                  header={"카테고리 선택"}
                >
                  <div className={styles.nameContainer}>
                    {categorys.map((category) => (
                      <div
                        className={s(
                          value.categoryId === category.categoryId
                            ? styles.selectName
                            : styles.name
                        )}
                        onClick={() => handleCategory(category)}
                      >
                        {category.name}
                      </div>
                    ))}
                  </div>
                </Modal>
              </div>
              <img className={styles.categoryImg} src={three} alt="" />
            </div>
          </div>
        </div>
      </div>
      <div
        className={s(styles.bookCreate)}
        onClick={() => (t ? handleSubmit() : nonecate())}
      >
        수정
      </div>
    </div>
  );
}
