//카테고리 수정을 클릭했을때 보이는 모달창을 당담합니다.

//아직하지 않은것
//카테고리이름 중복검사
//다자인

import styles from "./CategoryEditModal.module.css";
import { useState } from "react";
import CategoryList from "./CategoryList";
import Modal from "../common/Modal";

export default function CategoryEditModal({
  categoryList,
  content,
  setCategoryList,
}) {
  const [newCategoryName, setNewCategoryName] = useState(""); // 새로운 카테고리 이름 상태 추가

  const handleAddCategory = () => {
    // 새로운 카테고리 객체 생성
    const newCategory = {
      categoryId: (Math.random() * 1000).toString(), //이 값은 나중에 요청하고 나서 받아올 것임.
      categoryName: newCategoryName,
      categorySeq: categoryList.length + 1,
      bookCount: 0,
    };

    // 기존 카테고리 리스트에 새로운 카테고리 추가
    setCategoryList([...categoryList, newCategory]);

    // 인풋창 초기화
    setNewCategoryName("");
  };

  return (
    <Modal title={content} width="260px">
      <div className={styles.flex}>
        <div>카테고리수정</div>
        <div>삭제</div>
      </div>

      <CategoryList
        categoryList={categoryList}
        setCategoryList={setCategoryList}
      />
      <input
        type="text"
        placeholder="새로운 카테고리 추가"
        value={newCategoryName}
        onChange={(e) => setNewCategoryName(e.target.value)}
      />

      <button onClick={handleAddCategory}>추가</button>
    </Modal>
  );
}
