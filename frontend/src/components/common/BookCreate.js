import styles from "./BookCreate.module.css";
import three from "../../assets/three.png";
import s from "classnames";
import FileInput from "../common/FileInput";
import { useState } from "react";
import Modal from "./Modal";
import { uplodaImage } from "../../api/image/Image";
import { createBook } from "../../api/book/Book";
import toast from "react-hot-toast";

export default function BookCreate({ setBookList, booklist, setModalIsOpen }) {
  const layouts = [1, 2, 3, 4, 5, 6];
  const colors = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
  const categorys = booklist.map((category) => {
    return { categoryId: category.categoryId, name: category.categoryName };
  });
  const [open, setOpen] = useState(false);

  const [value, setValue] = useState({
    title: "",
    coverImage: null,
    coverLayout: 1,
    coverColorCode: 1,
    categoryId: 0,
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
  const [t, setT] = useState(false);
  const handleCategory = (category) => {
    handleChange("categoryId", category.categoryId);
    setTitle(category.name);
    setT(true);
    setOpen(false);
  };
  const addNewBookToCategory = (categoryId, newBook) => {
    const updatedBookList = booklist.map((category) => {
      if (category.categoryId === categoryId) {
        return {
          ...category,
          bookList: [...category.bookList, newBook],
        };
      }
      return category;
    });
    setBookList(updatedBookList);
  };

  const noneImg = () => {
    toast.error("이미지를 추가해주세요", {
      position: "top-center",
    });
  };
  const nonecate = () => {
    toast.error("책의 카테고리를 선택해주세요", {
      position: "top-center",
    });
  };
  const handleSubmit = async () => {
    const formData = new FormData();
    formData.append("images", value.coverImage);
    const coverImageId = await uplodaImage(formData);
    const bookId = await createBook({
      title: value.title,
      coverImageId: coverImageId.imageIds[0],
      coverLayout: value.coverLayout,
      coverColorCode: value.coverColorCode,
      categoryId: value.categoryId,
    });
    const newBook = {
      bookId: bookId,
      title: value.title,
      paperCount: 0,
    };
    addNewBookToCategory(value.categoryId, newBook);
    setModalIsOpen(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.title}>새로운 책 만들기</div>
      <div className={styles.main}>
        <div className={styles.main_left}>
          <div
            className={s(styles.cover, styles[`color${value.coverColorCode}`])}
          >
            <FileInput
              className={s(styles.book, styles[`layImg${value.coverLayout}`])}
              name="coverImage"
              value={value.coverImage}
              onChange={handleChange}
            />
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
        onClick={() =>
          value.coverImage ? (t ? handleSubmit() : nonecate()) : noneImg()
        }
      >
        생성
      </div>
    </div>
  );
}
