//모달창을 당담합니다.
import styles from "./Modal.module.css";
import { useRef, useState } from "react";
import useOnClickOutside from "./useOnClickOutside";

export default function Modal({
  children,
  width = "300px",
  height,
  title,
  right,
  left,
  top,
  bottom,
}) {
  const ref = useRef();
  const [isModalOpen, setIsModalOpen] = useState(false);
  useOnClickOutside(ref, () => setIsModalOpen(false));

  return (
    <div className={styles.modalContainer}>
      <div onClick={() => setIsModalOpen(true)}>{title}</div>
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
          <div
            className={styles.modalClose}
            onClick={() => setIsModalOpen(false)}
          >
            닫기
          </div>
          {children}
        </div>
      )}
    </div>
  );
}
