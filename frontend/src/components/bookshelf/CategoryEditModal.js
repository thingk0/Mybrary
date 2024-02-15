//카테고리 수정을 클릭했을때 보이는 모달창을 당담합니다.

//아직하지 않은것
//카테고리이름 중복검사
//다자인

import styles from "./CategoryEditModal.module.css";
import { useState } from "react";
import CategoryList from "./CategoryList";
import Modal from "../common/Modal";
import { createCategory } from "../../api/category/Category";

export default function CategoryEditModal({
  categoryList,
  content,
  setCategoryList,
  bookShelfId,
}) {
  const [newCategoryName, setNewCategoryName] = useState("");
  const [open, setOpen] = useState(false);

  const handleAddCategory = async () => {
    const category = {
      bookShelfId: bookShelfId,
      name: newCategoryName,
    };
    const id = await createCategory(category);
    const newCategory = {
      categoryId: id,
      name: newCategoryName,
      seq: categoryList.length + 1,
      bookCount: 0,
    };

    setCategoryList([...categoryList, newCategory]);
    setNewCategoryName("");
  };

  return (
    <Modal
      title={content}
      width="290px"
      open={open}
      setOpen={setOpen}
      top={"30px"}
      right={"0px"}
      header={content}
    >
      <div className={styles.main}>
        <CategoryList
          bookShelfId={bookShelfId}
          categoryList={categoryList}
          setCategoryList={setCategoryList}
        />
        <div className={styles.푸터입니다}>
          <input
            type="text"
            placeholder="새로운 카테고리 추가"
            value={newCategoryName}
            onChange={(e) => setNewCategoryName(e.target.value)}
            className={styles.인풋창입니다}
          />
          <button
            className={styles.추가버튼입니다}
            onClick={() => handleAddCategory()}
          >
            추가
          </button>
        </div>
      </div>
    </Modal>
  );
}
