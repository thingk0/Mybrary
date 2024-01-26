//모달창을 당담합니다.

import s from "classnames";
import styles from "./Modal.module.css";
import { useRef, useState } from "react";
import useOnClickOutside from "./useOnClickOutside";

export default function Modal({ children, width = "300px", height, title }) {
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
          style={{ width: width, height: height }}
        >
          <div className="modal-top">
            <span className="modal-close" onClick={() => setIsModalOpen(false)}>
              X
            </span>
            {children}
          </div>
        </div>
      )}
    </div>
  );
}
