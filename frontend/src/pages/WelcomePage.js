import { useNavigate } from "react-router-dom";
import styles from "./style/WelcomePage.module.css";
import line1 from "../assets/line1.png";
import line2 from "../assets/line2.png";
import line3 from "../assets/line3.png";
import top from "../assets/top.png";
import left from "../assets/left.png";
import right from "../assets/right.png";

export default function WelcomePage() {
  const navigate = useNavigate();

  const handleStart = () => {
    navigate("/join");
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.문구}>
          <div>
            <img src={line1} alt="" width="3px" className={styles.line1} />
          </div>
          <div className={styles.title}>MYBRARY</div>
          <div className={styles.text}>
            당신의 추억이 한 페이지가 될 수 있도록
          </div>
          <div>
            <img src={line3} alt="" width="3px" />
          </div>
          <div onClick={() => handleStart()} className={styles.button}>
            입장하기
          </div>
          <div>
            <img src={line3} alt="" width="3px" />
          </div>
        </div>
        <div className={styles.zero}>
          <img src={top} alt="" className={styles.top} />
          <img src={left} alt="" className={styles.left} />
          <img src={right} alt="" className={styles.right} />
          <div className={styles.cover1}></div>
          <div className={styles.cover2}></div>
        </div>
      </div>
    </>
  );
}
