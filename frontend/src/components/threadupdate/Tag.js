import { useState } from "react";
import styles from "./Tag.module.css";

export default function Tag({ children, papers, setPapers, currentPage }) {
  const [addtext, setAddtext] = useState("");
  // const [addfriend, setAddfriend] = useState("");

  const handleSubmitTag = (e) => {
    e.preventDefault();
    setPapers((prevPapers) => {
      const updatedPapers = [...prevPapers];
      if (!updatedPapers[currentPage].tagList.includes(addtext)) {
        updatedPapers[currentPage].tagList.push(addtext);
      }
      return updatedPapers;
    });
    setAddtext("");
  };
  // const handleSubmitFriendTag = (e) => {
  //   e.preventDefault();
  //   setPapers((prevPapers) => {
  //     const updatedPapers = [...prevPapers];
  //     if (!updatedPapers[currentPage].mentionList.includes(addfriend)) {
  //       updatedPapers[currentPage].mentionList.push(addfriend);
  //     }
  //     return updatedPapers;
  //   });
  //   setAddfriend("");
  // };
  const handleRemoveTag = (indexToRemove) => {
    setPapers((prevPapers) => {
      return prevPapers.map((paper, index) => {
        if (index === currentPage) {
          return {
            ...paper,
            tagList: paper.tagList.filter(
              (_, index) => index !== indexToRemove
            ),
          };
        }
        return paper;
      });
    });
  };
  // const handleRemoveFriendTag = (indexToRemove) => {
  //   setPapers((prevPapers) => {
  //     return prevPapers.map((paper, index) => {
  //       if (index === currentPage) {
  //         return {
  //           ...paper,
  //           mentionIdList: paper.mentionList.filter(
  //             (_, index) => index !== indexToRemove
  //           ),
  //         };
  //       }
  //       return paper;
  //     });
  //   });
  // };

  return (
    <div className={styles.오른쪽크기조정}>
      <div className={styles.title}>태그추가</div>
      <div className={styles.tagInputContainer}>
        <form onSubmit={handleSubmitTag}>
          <input
            type="text"
            id="add"
            placeholder="태그추가"
            value={addtext}
            className={styles.tagInput}
            onChange={(e) => setAddtext(e.target.value)}
          />
          <button type="submit" className={styles.searchButton}>
            +
          </button>
        </form>
        <div className={styles.추가된태그들}>
          {papers[currentPage].tagList.map((tag, index) => (
            <div className={styles.친구태그} key={index}>
              <div># {tag}</div>
              <div
                className={styles.x버튼}
                onClick={() => handleRemoveTag(index)}
              >
                x
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className={styles.title}></div>
      <div className={styles.tagInputContainer}>
        {/* <form onSubmit={handleSubmitFriendTag}>
          <input
            type="text"
            id="add2"
            placeholder="태그추가"
            value={addfriend}
            className={styles.tagInput}
            onChange={(e) => setAddfriend(e.target.value)}
          />
          <button type="submit" className={styles.searchButton}>
            +
          </button>
        </form>
        <div className={styles.추가된태그들}>
          {papers[currentPage].mentionList.map((mention, index) => (
            <div className={styles.친구태그} key={index}>
              <div>@ {mention}</div>
              <div
                className={styles.x버튼}
                onClick={() => handleRemoveFriendTag(index)}
              >
                x
              </div>
            </div>
          ))}
        </div> */}
      </div>
      {children}
    </div>
  );
}
