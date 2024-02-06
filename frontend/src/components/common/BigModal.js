import React, { useState } from "react";
import Modal from "react-modal";
import styles from "./BigModal.module.css";

export default function BigModal({
  children,
  modalIsOpen,
  setModalIsOpen,
  width = "600px",
  height = "400px",
}) {
  return (
    <div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="모달"
        style={{ content: { width: width, height: height } }}
        className={styles.modal} // 커스텀 모달 클래스 적용
        overlayClassName={styles.modalOverlay} // 커스텀 오버레이 클래스 적용
      >
        {children}
      </Modal>
    </div>
  );
}
