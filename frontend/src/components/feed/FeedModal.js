//모달창을 당담합니다.
import styles from "./FeedModal.module.css";
import { useEffect, useRef, useState } from "react";
import useOnClickOutside from "../common/useOnClickOutside";
import { getPaperinBook } from "../../api/book/Book";
import s from "classnames";

export default function FeedModal({
  isModalOpen,
  setIsModalOpen,
  children,
  width = "300px",
  height,
  right,
  left,
  top,
  bottom,
  header,
  paperId,
}) {
  const [booklist, setBooklist] = useState([]);

  useEffect(() => {
    console.log("으아아아아");
    async function fetchData() {
      try {
        const response = await getPaperinBook(paperId);
        console.log(response);
        setBooklist(response.data);
      } catch (error) {
        console.log("데이터를 가져오는데 실패함");
      }
    }
    if (paperId) fetchData(); // paperId가 존재할 때만 fetchData 함수를 호출합니다.
  }, [paperId]);

  const ref = useRef();
  useOnClickOutside(ref, () => setIsModalOpen(false));

  return (
    <div className={styles.modalContainer}>
      {isModalOpen && (
        <div
          ref={ref}
          className={styles.modal}
          style={{
            width: width,
            height: height,
            top: top,
            bottom: bottom,
            left: left,
            right: right,
          }}
        >
          <div className={styles.flex}>
            <div className={styles.헤더}>{header}</div>
            <div
              className={styles.modalClose}
              onClick={() => setIsModalOpen(false)}
            >
              닫기
            </div>
          </div>
          <div className={styles.책모음}>
            {booklist.map((book, index) => (
              <>
                <div className={styles.책한권}>
                  <div>
                    <span className={styles.푸터}>
                      <img
                        className={styles.유저이미지}
                        src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${book.profileImageUrl}`}
                      />{" "}
                      {book.bookTitle}
                    </span>
                  </div>
                </div>
              </>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
