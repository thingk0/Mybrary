import styles from "./style/MybraryPage.module.css";
import bookshelf1 from "../assets/bookshelf_01.png";
import bookshelf2 from "../assets/bookshelf_02.png";
import bookshelf3 from "../assets/bookshelf_03.png";
import bookshelf4 from "../assets/bookshelf_04.png";
import bookshelf5 from "../assets/bookshelf_05.png";
import bookshelf6 from "../assets/bookshelf_06.png";
import table1 from "../assets/table_01.png";
import table2 from "../assets/table_02.png";
import table3 from "../assets/table_03.png";
import table4 from "../assets/table_04.png";
import table5 from "../assets/table_05.png";
import table6 from "../assets/table_06.png";
import easel1 from "../assets/easel_01.png";
import easel2 from "../assets/easel_02.png";
import easel3 from "../assets/easel_03.png";
import easel4 from "../assets/easel_04.png";
import easel5 from "../assets/easel_05.png";
import easel6 from "../assets/easel_06.png";
import frame from "../assets/frame.png";
import door from "../assets/door.png";
import postbox from "../assets/postbox.png";
import s from "classnames";
import { useState } from "react";
import toast, { Toaster } from "react-hot-toast";

export default function MybraryPage() {
  const [edit, setEdit] = useState(false);
  const [bgColor, setBgColor] = useState("01");
  const [esColor, setEsColor] = useState(easel1);
  const [tbColor, setTbColor] = useState(table1);
  const [bsColor, setBsColor] = useState(bookshelf1);

  const color = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
    "13",
  ];

  //완료버튼을 눌렀을때 실행하는 함수
  const handleSelect = () => {
    setEdit(false);
    toast.success("변경이 완료 되었습니다.", {
      style: {
        border: "1px solid #713200",
        padding: "16px",
        color: "#713200",
        zIndex: "100",
      },
      iconTheme: {
        primary: "#713200",
        secondary: "#FFFAEE",
      },
    });
  };

  return (
    <>
      <div className={`${styles.bg} ${styles[`bg${bgColor}`]}`}>
        <div className={styles.center}>
          <img src={bsColor} className={styles.bookshelf} />
          <img src={tbColor} className={styles.table} />
          <img src={esColor} className={styles.easel} />
          <img src={frame} className={styles.frame} />
          <img src={door} className={styles.door} />
          <img src={postbox} className={styles.postbox} />
          {edit && (
            <div>
              <div className={s(styles.edit, styles.easelColor)}>
                <div className={styles.colorTitle}>이젤색</div>
                <div
                  className={s(styles.color, styles.easel1)}
                  onClick={() => setEsColor(easel1)}
                ></div>
                <div
                  className={s(styles.color, styles.easel2)}
                  onClick={() => setEsColor(easel2)}
                ></div>
                <div
                  className={s(styles.color, styles.easel3)}
                  onClick={() => setEsColor(easel3)}
                ></div>
                <div
                  className={s(styles.color, styles.easel4)}
                  onClick={() => setEsColor(easel4)}
                ></div>
                <div
                  className={s(styles.color, styles.easel5)}
                  onClick={() => setEsColor(easel5)}
                ></div>
                <div
                  className={s(styles.color, styles.easel6)}
                  onClick={() => setEsColor(easel6)}
                ></div>
              </div>
              <div className={s(styles.edit, styles.tableColor)}>
                <div className={styles.colorTitle}>테이블색</div>
                <div
                  className={s(styles.color, styles.table1)}
                  onClick={() => setTbColor(table1)}
                ></div>
                <div
                  className={s(styles.color, styles.table2)}
                  onClick={() => setTbColor(table2)}
                ></div>
                <div
                  className={s(styles.color, styles.table3)}
                  onClick={() => setTbColor(table3)}
                ></div>
                <div
                  className={s(styles.color, styles.table4)}
                  onClick={() => setTbColor(table4)}
                ></div>
                <div
                  className={s(styles.color, styles.table5)}
                  onClick={() => setTbColor(table5)}
                ></div>
                <div
                  className={s(styles.color, styles.table6)}
                  onClick={() => setTbColor(table6)}
                ></div>
              </div>
              <div className={s(styles.edit, styles.bookshelfColor)}>
                <div className={styles.colorTitle}>책장색</div>
                <div
                  className={s(styles.color, styles.bookshelf1)}
                  onClick={() => setBsColor(bookshelf1)}
                >
                  {bsColor === bookshelf1 && (
                    <div className={styles.select}></div>
                  )}
                </div>
                <div
                  className={s(styles.color, styles.bookshelf2)}
                  onClick={() => setBsColor(bookshelf2)}
                >
                  {bsColor === bookshelf2 && (
                    <div className={styles.select}></div>
                  )}
                </div>
                <div
                  className={s(styles.color, styles.bookshelf3)}
                  onClick={() => setBsColor(bookshelf3)}
                >
                  {bsColor === bookshelf3 && (
                    <div className={styles.select}></div>
                  )}
                </div>
                <div
                  className={s(styles.color, styles.bookshelf4)}
                  onClick={() => setBsColor(bookshelf4)}
                >
                  {bsColor === bookshelf4 && (
                    <div className={styles.select}></div>
                  )}
                </div>
                <div
                  className={s(styles.color, styles.bookshelf5)}
                  onClick={() => setBsColor(bookshelf5)}
                >
                  {bsColor === bookshelf5 && (
                    <div className={styles.select}></div>
                  )}
                </div>
                <div
                  className={s(styles.color, styles.bookshelf6)}
                  onClick={() => setBsColor(bookshelf6)}
                >
                  {bsColor === bookshelf6 && (
                    <div className={styles.select}></div>
                  )}
                </div>
              </div>
            </div>
          )}
        </div>
        <div className={s(edit ? styles.active : styles.container)}>
          <div className={styles.profileContainer}>
            <div className={styles.profile}>
              <div>이미지</div>
              <div>
                <div>닉네임</div>
                <div>이름</div>
              </div>
              <div>
                <div>300</div>
                <div>앨범</div>
              </div>
              <div>
                <div>300</div>
                <div>게시글</div>
              </div>
              <div>
                <div>300</div>
                <div>팔로워</div>
              </div>
              <div>
                <div>300</div>
                <div>팔로우</div>
              </div>
            </div>
            <div>한줄소개가 들어가는 부분이빈다.</div>
          </div>
          <div>
            <div className={styles.editButton} onClick={() => setEdit(true)}>
              방 꾸미기
            </div>
            {/* <div className={styles.editButton}>방 꾸미기</div> */}
          </div>
        </div>
        {edit && (
          <div>
            <div className={s(styles.edit, styles.backGroundColor)}>
              <div>마이브러리 홈 꾸미기</div>

              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[0])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[1])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[2])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[3])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[4])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[5])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[6])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[7])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[8])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[9])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[10])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[11])}
              ></div>
              <div
                className={s(styles.color, styles.easel1)}
                onClick={() => setBgColor(color[12])}
              ></div>
            </div>
            <div
              className={s(styles.edit, styles.ok)}
              onClick={() => handleSelect()}
            >
              완료
            </div>
          </div>
        )}
      </div>
      <Toaster position="top-center" reverseOrder={false} />
    </>
  );
}
