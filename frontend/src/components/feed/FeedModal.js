//모달창을 당담합니다.
import styles from "./FeedModal.module.css";
import { useRef } from "react";
import useOnClickOutside from "../common/useOnClickOutside";

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
}) {
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
            <div>{header}</div>
            <div
              className={styles.modalClose}
              onClick={() => setIsModalOpen(false)}
            >
              닫기
            </div>
          </div>
          {children}
        </div>
      )}
    </div>
  );
}
