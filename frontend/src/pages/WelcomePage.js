import { useNavigate } from "react-router-dom";
import styles from "./style/WelcomePage.module.css";

export default function WelcomePage() {
  const navigate = useNavigate();

  const handleStart = () => {
    navigate("/join");
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.문구}>
          <div className={styles.title}>MYBRATY</div>
          <div className={styles.text}>
            당신의 추억이 한 페이지가 될 수 있도록
          </div>

          <div onClick={() => handleStart()} className={styles.button}>
            입장하기
          </div>
        </div>
      </div>
    </>
  );
}
