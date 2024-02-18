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
        className={styles.modal}
        overlayClassName={styles.modalOverlay}
      >
        {children}
      </Modal>
    </div>
  );
}
