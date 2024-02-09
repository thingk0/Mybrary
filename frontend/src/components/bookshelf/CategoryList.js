//카테고리리스트가 보이는 컴포넌트입니다.
//카테고리 순서변경을 담당합니다.

import styles from "./CategoryList.module.css";
import { useEffect, useState } from "react";
import Sortable from "sortablejs";
import { deleteCategory, updateCategory } from "../../api/category/Category";

export default function CategoryList({
  categoryList,
  setCategoryList,
  bookShelfId,
}) {
  useEffect(() => {
    const categorySortable = new Sortable(document.getElementById("list"), {
      swapThreshold: 0.7,
      animation: 150,
      onEnd: function async(evt) {
        const newCategoryList = Array.from(evt.from.children).map(
          (item, index) => {
            const categoryId = item.dataset.categoryId;
            const category = categoryList.find(
              (cat) => cat.categoryId === +categoryId
            );

            const updatedCategory = {
              ...category,
              seq: index + 1,
            };
            return updatedCategory;
          }
        );
        updateCategory({
          bookShelfId: bookShelfId,
          categoryList: newCategoryList,
        });
        setCategoryList(newCategoryList);
      },
    });

    return () => {
      categorySortable.destroy();
    };
  }, [setCategoryList, categoryList]);

  const [editingCategoryId, setEditingCategoryId] = useState(null);
  const [updatedCategoryName, setUpdatedCategoryName] = useState("");

  const handleEditCategory = (categoryId) => {
    setEditingCategoryId(categoryId);
    setUpdatedCategoryName(
      categoryList.find((cat) => cat.categoryId === categoryId).name
    );
  };

  const handleUpdateCategory = (categoryId) => {
    const updatedCategoryList = categoryList.map((category) => {
      if (category.categoryId === categoryId) {
        return { ...category, name: updatedCategoryName };
      }
      return category;
    });

    updateCategory({
      bookShelfId: bookShelfId,
      categoryList: updatedCategoryList,
    });
    setCategoryList(updatedCategoryList);
    setEditingCategoryId(null);
  };

  const handleDeleteCategory = (categoryId) => {
    const updatedCategoryList = categoryList.filter(
      (category) => category.categoryId !== categoryId
    );
    deleteCategory(categoryId);
    setCategoryList(updatedCategoryList);
  };

  return (
    <div id="list" className={styles.categoryList}>
      {categoryList.map((category) => (
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
                <div className={styles.categoryItem}>{category.name}</div>
                <div onClick={() => handleEditCategory(category.categoryId)}>
                  수정
                </div>
                {category.bookCount === 0 && (
                  <div
                    onClick={() => handleDeleteCategory(category.categoryId)}
                  >
                    X
                  </div>
                )}
              </>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}
