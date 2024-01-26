//카테고리리스트가 보이는 컴포넌트입니다.
//카테고리 순서변경을 담당합니다.

import styles from "./CategoryList.module.css";
import { useEffect, useState } from "react";
import Sortable from "sortablejs";

export default function CategoryList({ categoryList, setCategoryList }) {
  useEffect(() => {
    const categorySortable = new Sortable(document.getElementById("list"), {
      swapThreshold: 0.7,
      animation: 150,
      onEnd: function (evt) {
        const newCategoryList = Array.from(evt.from.children).map(
          (item, index) => {
            const categoryId = item.dataset.categoryId;
            const category = categoryList.find(
              (cat) => cat.categoryId === categoryId
            );
            const updatedCategory = {
              ...category,
              categorySeq: index + 1,
            };
            return updatedCategory;
          }
        );
        setCategoryList(newCategoryList);
      },
    });

    return () => {
      categorySortable.destroy();
    };
  }, [categoryList]);

  const [editingCategoryId, setEditingCategoryId] = useState(null);
  const [updatedCategoryName, setUpdatedCategoryName] = useState("");

  const handleEditCategory = (categoryId) => {
    setEditingCategoryId(categoryId);
    setUpdatedCategoryName(
      categoryList.find((cat) => cat.categoryId === categoryId).categoryName
    );
  };

  const handleUpdateCategory = (categoryId) => {
    const updatedCategoryList = categoryList.map((category) => {
      if (category.categoryId === categoryId) {
        return { ...category, categoryName: updatedCategoryName };
      }
      return category;
    });

    setCategoryList(updatedCategoryList);
    setEditingCategoryId(null);
  };

  const handleDeleteCategory = (categoryId) => {
    const updatedCategoryList = categoryList.filter(
      (category) => category.categoryId !== categoryId
    );

    setCategoryList(updatedCategoryList);
  };

  return (
    <div id="list" className={styles.categoryList}>
      {categoryList
        .filter((category) => category.categoryId !== "empty")
        .map((category) => (
          <div key={category.categoryId} data-category-id={category.categoryId}>
            <div className={styles.category}>
              {editingCategoryId === category.categoryId ? (
                <>
                  <input
                    type="text"
                    value={updatedCategoryName}
                    style={{ width: "40px" }}
                    onChange={(e) => setUpdatedCategoryName(e.target.value)}
                  />
                  <button
                    onClick={() => handleUpdateCategory(category.categoryId)}
                  >
                    완료
                  </button>
                </>
              ) : (
                <>
                  <div className={styles.categoryItem}>
                    {category.categoryName}
                  </div>
                  <div onClick={() => handleEditCategory(category.categoryId)}>
                    수정
                  </div>
                  <div
                    onClick={() => handleDeleteCategory(category.categoryId)}
                  >
                    X
                  </div>
                </>
              )}
            </div>
          </div>
        ))}
    </div>
  );
}
