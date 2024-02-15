import Modal from "react-modal";
import styles from "./BigModal.module.css";

export default function LoadModal({
  children,
  modalIsOpen,
  width = "600px",
  height = "400px",
  background = "#FFF9F699",
}) {
  return (
    <div>
      <Modal
        appElement={document.getElementById("root")}
        isOpen={modalIsOpen}
        contentLabel="모달"
        style={{
          content: { width: width, height: height, background: background },
        }}
        className={styles.modal} // 커스텀 모달 클래스 적용
        overlayClassName={styles.modalOverlay} // 커스텀 오버레이 클래스 적용
      >
        {children}
      </Modal>
    </div>
  );
}
