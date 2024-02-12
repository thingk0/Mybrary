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
  setOpen,
  open,
  header,
}) {
  const ref = useRef();
  const [isModalOpen, setIsModalOpen] = useState(false);
  useOnClickOutside(ref, () => setIsModalOpen(false));

  return (
    <div className={styles.modalContainer}>
      <div
        className={styles.title}
        onClick={() => {
          setIsModalOpen(true);
          setOpen(true);
        }}
      >
        {title}
      </div>
      {isModalOpen && open && (
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
