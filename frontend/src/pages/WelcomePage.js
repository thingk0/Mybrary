import { useNavigate } from "react-router-dom";
import styles from "./style/WelcomePage.module.css";

export default function WelcomePage() {
  const navigate = useNavigate();

  const handleStart = () => {
    navigate("mybrary/userid");
  };

  return (
    <>
      <div className={styles.container}>
        <div>
          <div onClick={() => handleStart()}>입장하기</div>
        </div>
      </div>
    </>
  );
}
