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
          <div>마이브러리에 오신것을 환영합니다</div>

          <div onClick={() => handleStart()}>입장하기</div>
        </div>
      </div>
    </>
  );
}
