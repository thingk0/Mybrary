import styles from "./Header.module.css";
import s from "classnames";

export default function Header({
  papers,
  setPapers,
  currentPage,
  setCurrentPage,
}) {
  const removePaper = (pageIndex) => {
    const updatedPapers = papers.filter((_, index) => index !== pageIndex);
    setPapers(updatedPapers);

    if (currentPage === pageIndex || currentPage === updatedPapers.length - 1) {
      setCurrentPage(Math.max(0, currentPage - 1));
    } else {
      setCurrentPage(Math.max(0, currentPage));
    }
  };
  const changePage = (pageIndex) => {
    setCurrentPage(pageIndex);
  };

  return (
    <div className={styles.header_container}>
      <div className={styles.title}>스레드 수정</div>
      <div className={styles.buttons}>
        {papers.map((_, index) => (
          <div
            className={s(
              styles.page,
              currentPage === index && styles.currentPage
            )}
            key={index}
            onClick={() => changePage(index)}
          >
            페이퍼 {index + 1}
          </div>
        ))}
        {papers.length > 1 && (
          <div
            className={styles.deletePage}
            onClick={() => removePaper(currentPage)}
          >
            현재 <br />
            페이퍼 <br />
            삭제
          </div>
        )}
      </div>
    </div>
  );
}
