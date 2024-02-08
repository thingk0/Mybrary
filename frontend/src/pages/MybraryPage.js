import styles from "./style/MybraryPage.module.css";
import table1 from "../assets/table_01.png";
import table2 from "../assets/table_02.png";
import table3 from "../assets/table_03.png";
import table4 from "../assets/table_04.png";
import table5 from "../assets/table_05.png";
import table6 from "../assets/table_06.png";
import shelf1 from "../assets/bookshelf_01.png";
import shelf2 from "../assets/bookshelf_02.png";
import shelf3 from "../assets/bookshelf_03.png";
import shelf4 from "../assets/bookshelf_04.png";
import shelf5 from "../assets/bookshelf_05.png";
import shelf6 from "../assets/bookshelf_06.png";
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
import { useState, useEffect } from "react";
import toast from "react-hot-toast";
import { useNavigate, useParams } from "react-router-dom";
import useUserStore from "../store/useUserStore";
import useStompStore from "../store/useStompStore";
import 혜선누나 from "../assets/혜선누나.jpg";
import { getMyMybrary, updateMybrary } from "../api/mybrary/Mybrary";
import gomimg from "../assets/곰탱이.png";
import BigModal from "../components/common/BigModal";
import axios from "axios";

export default function MybraryPage() {
  const navigate = useNavigate();
  const Params = useParams();
  const nowuser = Params.userid;
  console.log(nowuser);
  const [edit, setEdit] = useState(false);
  const [bgColor, setBgColor] = useState("1");
  const [esColor, setEsColor] = useState(easel1);
  const [tbColor, setTbColor] = useState(table1);
  const [bsColor, setBsColor] = useState(shelf1);
  const user = useUserStore((state) => state.user);
  const client = useStompStore((state) => state.stompClient);
  const [tablenum, setTablenum] = useState("1");
  const [easelnum, setEaselnum] = useState("1");
  const [bookshelfnum, setBookshelfnum] = useState("1");
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [frameimgurl, setFrameimgurl] = useState();
  const [file, setFile] = useState();
  const [checkme, setCheckme] = useState(false);

  const [testuser, setTestuser] = useState({
    data: {
      member: {},
      bookCount: 0,
      threadCount: 0,
      followerCount: 0,
      followingCount: 0,
    },
  });

  useEffect(() => {
    async function fetchMybraryData() {
      try {
        const memberId = user.memberId;
        console.log(memberId);
        console.log(user.nickname);
        if (memberId == nowuser) {
          const response = await getMyMybrary();
          console.log("내라이브러리입니다");
          setCheckme(true);
          setTestuser(response);
          setBgColor(response.data.backgroundColor.toString());
          setEsColor(easelImgs[response.data.easelColor - 1]);
          setEaselnum(response.data.easelColor);
          setTbColor(tableImgs[response.data.deskColor - 1]);
          setTablenum(response.data.deskColor);
          setBsColor(bookshelfImgs[response.data.bookshelfColor - 1]);
          setBookshelfnum(response.data.bookshelfColor - 1);
          setFrameimgurl(response.data.frameImageUrl);
        } else {
          const response = await getMyMybrary(nowuser);
          console.log("상대방의라이브러리입니다");
          setTestuser(response);
          setBgColor(response.data.backgroundColor.toString());
          setEsColor(easelImgs[response.data.easelColor - 1]);
          setEaselnum(response.data.easelColor);
          setTbColor(tableImgs[response.data.deskColor - 1]);
          setTablenum(response.data.deskColor);
          setBsColor(bookshelfImgs[response.data.bookshelfColor - 1]);
          setBookshelfnum(response.data.bookshelfColor - 1);
          setFrameimgurl(response.data.frameImageUrl);
        }
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }

    fetchMybraryData();
  }, []);

  const color = [
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "10",
    "11",
    "12",
    "13",
  ];
  const easelImgs = [easel1, easel2, easel3, easel4, easel5, easel6];
  const tableImgs = [table1, table2, table3, table4, table5, table6];
  const bookshelfImgs = [shelf1, shelf2, shelf3, shelf4, shelf5, shelf6];

  //완료버튼을 눌렀을때 실행하는 함수
  const handleSelect = async () => {
    // 요청 객체 생성
    const updateData = {
      mybraryId: testuser.data.mybraryId, // 여기서는 예시로 `testuser.data.mybraryId`를 사용합니다.
      frameImage: {
        // frameImage에 필요한 데이터를 적절히 채워 넣으세요.
        name: "string",
        originName: "string",
        url: "string",
        thumbnailUrl: "string",
        format: "string",
        size: "string",
      },
      backgroundColor: parseInt(bgColor, 10),
      deskColor: parseInt(tablenum, 10),
      bookshelfColor: parseInt(bookshelfnum, 10),
      easelColor: parseInt(easelnum, 10),
    };

    try {
      // updateMybrary 함수를 호출하여 데이터 업데이트
      const response = await updateMybrary(updateData);
      console.log("업데이트 성공:", response);

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
        position: "top-center",
      });
    } catch (error) {
      console.error("업데이트 실패:", error);
      toast.error("변경 실패: " + error.message);
    }

    setEdit(false);
  };

  //색을 고르는 컴포넌트
  function ColorSelector({ color, setColor, Colors, setNum }) {
    return (
      <>
        {Colors.map((colornum, index) => (
          <div
            key={index}
            className={s(styles.color, styles[`color${index + 1}`])}
            onClick={() => {
              setColor(colornum);
              setNum(index + 1);
            }}
          >
            {color === colornum && <div className={styles.select}></div>}
          </div>
        ))}
      </>
    );
  }
  //배경색을 고르는 컴포넌트
  function BgColorSelector({ color, setColor, Colors }) {
    return (
      <>
        {Colors.map((colornum, index) => (
          <div
            key={index}
            className={s(styles.color, styles[`bgColor${index + 1}`])}
            onClick={() => setColor(colornum)}
          >
            {color === colornum && <div className={styles.select}></div>}
          </div>
        ))}
      </>
    );
  }
  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setFrameimgurl(e.target.result); // 이 부분에서 미리보기 URL을 설정
      };
      reader.readAsDataURL(file);
    }
  };
  const handleFileUpload = async () => {
    if (file) {
      // 파일 업로드 로직 구현
      // 예: 서버에 파일을 업로드하고, 응답으로 받은 이미지 URL을 저장
      const formData = new FormData();
      formData.append("image", file[0]);
      const result = await axios.post("/upload-single", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      console.log(result);
      // try {
      //   setFrameimgurl(file); // 업로드된 이미지 URL을 상태에 저장
      // } catch (error) {
      //   console.error("파일 업로드 중 오류 발생:", error);
      //   // 오류 처리 로직
      // }
    }
  };

  return (
    <>
      <div className={s(`${styles.bg} ${styles[`bg${bgColor}`]}`)}>
        <div className={styles.center}>
          <img
            src={bsColor}
            alt=""
            className={s(styles.bookshelf, !edit && styles.img)}
            onClick={() => !edit && navigate(`${testuser.data.bookShelfId}`)}
          />
          <img
            src={tbColor}
            alt=""
            className={s(styles.table, !edit && styles.img)}
            onClick={() => !edit && navigate("threads")}
          />
          <img
            src={esColor}
            alt=""
            className={s(styles.easel, !edit && styles.img)}
            onClick={() => !edit && navigate("rollingpaper")}
          />
          <div className={s(styles.frame, !edit && styles.img)}>
            <img
              src={testuser.data.frameImageUrl || 혜선누나}
              alt=""
              className={styles.trapezoid}
            />
            <img src={frame} alt="" className={styles.frameimg} />
          </div>

          <img
            src={door}
            alt=""
            className={s(styles.door, !edit && styles.img)}
            onClick={() => !edit && navigate("/feed")}
          />
          <img
            src={postbox}
            alt=""
            className={s(styles.postbox, !edit && styles.img)}
            onClick={() => !edit && navigate("/paperplane")}
          />
          {edit && (
            <div>
              <div
                onClick={() => setModalIsOpen(true)}
                className={s(styles.edit, styles.이젤이미지변경)}
              >
                이젤이미지변경하기
                <BigModal
                  modalIsOpen={modalIsOpen}
                  setModalIsOpen={setModalIsOpen}
                  width="800px"
                  height="600px"
                  background="var(--main4)"
                >
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      setModalIsOpen(false);
                    }}
                  >
                    x
                  </button>
                  <div>
                    <div>현재 이미지</div>
                    {frameimgurl && (
                      <img
                        className={styles.선택이미지}
                        src={frameimgurl}
                        alt="미리보기 이미지"
                      />
                    )}
                    {/* 파일 업로드 input */}
                    <input type="file" onChange={handleFileChange} />
                    <button onClick={() => handleFileUpload()}>저장</button>
                  </div>
                </BigModal>
              </div>
              <div className={s(styles.edit, styles.easelColor)}>
                <div className={styles.colorTitle}>이젤색</div>
                <ColorSelector
                  color={esColor}
                  setColor={setEsColor}
                  Colors={easelImgs}
                  setNum={setEaselnum}
                />
              </div>
              <div className={s(styles.edit, styles.tableColor)}>
                <div className={styles.colorTitle}>테이블색</div>
                <ColorSelector
                  color={tbColor}
                  setColor={setTbColor}
                  Colors={tableImgs}
                  setNum={setTablenum}
                />
              </div>
              <div className={s(styles.edit, styles.bookshelfColor)}>
                <div className={styles.colorTitle}>책장색</div>
                <ColorSelector
                  color={bsColor}
                  setColor={setBsColor}
                  Colors={bookshelfImgs}
                  setNum={setBookshelfnum}
                />
              </div>
            </div>
          )}
        </div>
        {/* <div className={styles.trapezoid}></div> */}
        <div className={s(edit ? styles.active : styles.container)}>
          <div className={styles.profileContainer}>
            <div className={styles.profile}>
              <div className={styles.프로필박스2}>
                <img
                  className={styles.프로필이미지곰}
                  src={testuser.data.url || gomimg}
                  alt="대체 이미지"
                />
              </div>
              <div className={styles.프로필박스}>
                <div>{testuser.data.member.nickname}</div>
                <div>{testuser.data.member.name}</div>
              </div>
              <div className={styles.프로필박스}>
                <div>{testuser.data.bookCount}</div>
                <div>앨범</div>
              </div>
              <div className={styles.프로필박스}>
                <div>{testuser.data.threadCount}</div>
                <div>게시글</div>
              </div>
              <div className={styles.프로필박스}>
                <div>{testuser.data.followerCount}</div>
                <div>팔로워</div>
              </div>
              <div className={styles.프로필박스}>
                <div>{testuser.data.followingCount}</div>
                <div>팔로우</div>
              </div>
            </div>
            <div className={styles.한줄소개}>{testuser.data.member.intro}</div>
          </div>
          <div>
            {checkme && (
              <div className={styles.editButton} onClick={() => setEdit(true)}>
                방 꾸미기
              </div>
            )}
            {/* <div className={styles.editButton}>방 꾸미기</div> */}
          </div>
        </div>
        {edit && (
          <div>
            <div className={s(styles.editbg, styles.backGroundColor)}>
              <div>마이브러리 방 꾸미기</div>
              <div className={styles.bgcont}>
                <div>배경색</div>
                <BgColorSelector
                  color={bgColor}
                  setColor={setBgColor}
                  Colors={color}
                />
              </div>
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
    </>
  );
}
