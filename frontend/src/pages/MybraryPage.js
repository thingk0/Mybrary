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
import useMybraryStore from "../store/useMybraryStore";
import {
  getMyMybrary,
  getMybrary,
  updateMybrary,
} from "../api/mybrary/Mybrary";
import 기본액자 from "../assets/예시이미지2.png";
import gomimg from "../assets/icon/Iconuser2.png";
import BigModal from "../components/common/BigModal";
import Loading from "../components/common/Loading";
import FollowList from "../components/mybrary/FollowList";
import FollowerList from "../components/mybrary/FollowerList";
import FileInput from "../components/common/FileInput";
import { uplodaImage } from "../api/image/Image";
import { deleteFollow, follow, followCancel } from "../api/member/Follow";
import { getFirstChat } from "../api/chat/Chat";
import useNotificationStore from "../store/useNotificationStore";
import useMyStore from "../store/useMyStore";
import useNavStore from "../store/useNavStore";

export default function MybraryPage() {
  const { setNotifyEnable } = useNotificationStore();
  // 유저 관련
  const navigate = useNavigate();
  const Params = useParams();
  const nowuser = Params.userid;
  const user = useUserStore((state) => state.user);
  const setMybrary = useMybraryStore((state) => state.setMybrary);
  const setMy = useMyStore((state) => state.setMy);
  const setNav = useNavStore((state) => state.setNav);

  // 각각의 색상옵션들
  const color = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13];
  const easelImgs = [easel1, easel2, easel3, easel4, easel5, easel6];
  const tableImgs = [table1, table2, table3, table4, table5, table6];
  const bookshelfImgs = [shelf1, shelf2, shelf3, shelf4, shelf5, shelf6];
  const [bgColor, setBgColor] = useState(1);
  const [esColor, setEsColor] = useState(easel1);
  const [tbColor, setTbColor] = useState(table1);
  const [bsColor, setBsColor] = useState(shelf1);
  const [frameimgurl, setFrameimgurl] = useState(기본액자);
  const [userimg, setUserimg] = useState("null");

  // 상테 체크
  const [isLoading, setIsLoading] = useState(true); // 로딩 상태
  const [edit, setEdit] = useState(false); //수정 상태
  const [checkme, setCheckme] = useState(false); //로그인한 회원인지 체크

  // 마이브러리의 유저 정보
  const [userInfo, setUserInfo] = useState({});
  const [followStatus, setFollowStatus] = useState("나예요");

  // 호버했을때 나오는 글씨
  const [show, setShow] = useState({
    bookshelf: false,
    table: false,
    easel: false,
    frame: false,
    door: false,
    postbox: false,
  });
  const handleShow = (name, value) => {
    setShow((prevValue) => ({
      ...prevValue,
      [name]: value,
    }));
  };

  const [showListType, setShowListType] = useState(null); // 리스트 타입을 관리하는 상태
  const handleShowList = (type) => {
    setShowListType(showListType === type ? null : type); // 같은 버튼을 다시 클릭하면 리스트를 닫습니다.
  };

  // 수정시에 필요한 상태
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [value, setValue] = useState({});
  const handleChangeValue = (name, value) => {
    setValue((prevValue) => ({
      ...prevValue,
      [name]: value,
    }));
  };
  const handleEdit = () => {
    setEdit(true);
    setValue({
      frameImageId: -1,
      frameImage: null,
      backgroundColor: userInfo.backgroundColor,
      deskColor: userInfo.deskColor,
      bookshelfColor: userInfo.bookshelfColor,
      easelColor: userInfo.easelColor,
    });
  };

  const handleImg = () => {
    setModalIsOpen(false);
    const nextPreview = URL.createObjectURL(value.frameImage);
    setFrameimgurl(nextPreview);
  };

  //완료버튼을 눌렀을때 실행하는 함수
  const handleSelect = async () => {
    if (value.frameImage !== null) {
      const formData = new FormData();
      formData.append("images", value.frameImage);
      const frameImageId = await uplodaImage(formData);

      const updateData = {
        mybraryId: userInfo.mybraryId,
        frameImageId: frameImageId.imageIds[0],
        backgroundColor: value.backgroundColor,
        deskColor: value.deskColor,
        bookshelfColor: value.bookshelfColor,
        easelColor: value.easelColor,
      };

      try {
        // updateMybrary 함수를 호출하여 데이터 업데이트
        await updateMybrary(updateData);

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
    } else {
      // 요청 객체 생성
      const updateData = {
        mybraryId: userInfo.mybraryId,
        frameImageId: userInfo.frameImageId,
        backgroundColor: value.backgroundColor,
        deskColor: value.deskColor,
        bookshelfColor: value.bookshelfColor,
        easelColor: value.easelColor,
      };
      console.log(updateData);
      try {
        // updateMybrary 함수를 호출하여 데이터 업데이트
        await updateMybrary(updateData);

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
    }

    setEdit(false);
  };

  // 팔로워 수 업데이트 함수
  const updateFollowerCount = (newCount) => {
    setUserInfo((prevState) => ({
      ...prevState,
      followerCount: newCount,
    }));
  };
  // 팔로잉 수 업데이트 함수
  const updateFollowingCount = (newCount) => {
    setUserInfo((prevState) => ({
      ...prevState,
      followingCount: newCount,
    }));
  };

  useEffect(() => {
    async function fetchMybraryData() {
      setShowListType(null);
      try {
        const memberId = user.memberId;
        if (memberId === +nowuser) {
          const response = await getMyMybrary();
          // console.log(response.data);
          setNotifyEnable(response.data.notifyEnable);
          setCheckme(true);
          await setMy(response.data);
          await setMybrary(response.data);
          setUserInfo(response.data);
          setBgColor(response.data.backgroundColor);
          setEsColor(easelImgs[response.data.easelColor - 1]);
          setTbColor(tableImgs[response.data.deskColor - 1]);
          setBsColor(bookshelfImgs[response.data.bookshelfColor - 1]);
          if (response.data.frameImageUrl != null) {
            setFrameimgurl(
              `https://jingu.s3.ap-northeast-2.amazonaws.com/${response.data.frameImageUrl}`
            );
          }
          if (response.data.profileImageUrl != null) {
            setUserimg(
              `https://jingu.s3.ap-northeast-2.amazonaws.com/${response.data.profileImageUrl}`
            );
          }
          setIsLoading(false);
          await setNav(0);
        } else {
          const response = await getMybrary(nowuser);
          setMybrary(response.data);
          console.log(response.data);
          setCheckme(false);
          setFollowStatus(response.data.followStatus);
          setUserInfo(response.data);
          setBgColor(response.data.backgroundColor);
          setEsColor(easelImgs[response.data.easelColor - 1]);
          setTbColor(tableImgs[response.data.deskColor - 1]);
          setBsColor(bookshelfImgs[response.data.bookshelfColor - 1]);
          if (response.data.frameImageUrl != null) {
            setFrameimgurl(
              `https://jingu.s3.ap-northeast-2.amazonaws.com/${response.data.frameImageUrl}`
            );
          }
          if (response.data.profileImageUrl != null) {
            setUserimg(
              `https://jingu.s3.ap-northeast-2.amazonaws.com/${response.data.profileImageUrl}`
            );
          }
          setIsLoading(false);
        }
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
        setIsLoading(false);
      }
    }

    fetchMybraryData();
  }, [nowuser]);

  if (isLoading) {
    return <Loading></Loading>; // 로딩 중일 때 보여줄 컴포넌트 또는 메시지
  }

  //색을 고르는 컴포넌트
  function ColorSelector({ color, setColor, Colors, name }) {
    return (
      <>
        {Colors.map((colornum, index) => (
          <div
            key={index}
            className={s(styles.color, styles[`color${index + 1}`])}
            onClick={() => {
              setColor(colornum);
              handleChangeValue(name, index + 1);
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
            onClick={() => {
              handleChangeValue("backgroundColor", index + 1);
              setColor(colornum);
            }}
          >
            {color === colornum && <div className={styles.select}></div>}
          </div>
        ))}
      </>
    );
  }

  async function handlePostboxClick() {
    if (user.memberId === nowuser) {
      navigate("/paperplane");
    } else {
      await getFirstChat(nowuser);
      navigate(`/paperplane?chatuserid=${nowuser}`);
    }
  }

  // 팔로우하는 함수
  const handleFollow = async () => {
    await follow(nowuser);
    if (userInfo.profilePublic) {
      setFollowStatus(3);
      setUserInfo((prev) => ({
        ...prev,
        followerCount: userInfo.followerCount + 1,
      }));
      toast.success("팔로우 완료 !", {
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
    } else {
      setFollowStatus(2);
    }
  };
  // 팔로우 요청 취소하는 함수
  const handleCancelFollow = async () => {
    await followCancel(nowuser);
    setFollowStatus(1);
  };
  // 팔로우 취소하는 함수
  const handleUnFollow = async () => {
    await deleteFollow(nowuser);
    setFollowStatus(1);
    setUserInfo((prev) => ({
      ...prev,
      followerCount: userInfo.followerCount - 1,
    }));
    toast.success("팔로우 취소되었습니다.", {
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
  };

  return (
    <>
      <div className={s(`${styles.bg} ${styles[`bg${color[bgColor - 1]}`]}`)}>
        <div className={styles.center}>
          <>
            <img
              src={bsColor}
              alt=""
              className={s(styles.bookshelf, !edit && styles.img)}
              onClick={() => !edit && navigate(`${userInfo.bookShelfId}`)}
              onMouseEnter={() => handleShow("bookshelf", true)}
              onMouseLeave={() => handleShow("bookshelf", false)}
            />
            <>
              {show.bookshelf && !edit && (
                <span className={styles.책장가자}>
                  {userInfo.nickname}님의 책장
                </span>
              )}
            </>
            <img
              src={tbColor}
              alt=""
              className={s(styles.table, !edit && styles.img)}
              onClick={() => !edit && navigate("threads")}
              onMouseEnter={() => handleShow("table", true)}
              onMouseLeave={() => handleShow("table", false)}
            />
            {show.table && !edit && (
              <span className={styles.테이블가자}>
                {userInfo.nickname}님의 쓰레드
              </span>
            )}
            <img
              src={esColor}
              alt=""
              className={s(styles.easel, !edit && styles.img)}
              onClick={() =>
                !edit && navigate(`rollingpaper/${userInfo.rollingPaperId}`)
              }
              onMouseEnter={() => handleShow("easel", true)}
              onMouseLeave={() => handleShow("easel", false)}
            />
            {show.easel && !edit && (
              <span className={styles.이젤가자}>
                {userInfo.nickname}님의 롤링페이퍼
              </span>
            )}
            <div className={s(styles.frame, !edit && styles.img)}>
              <img src={frameimgurl} alt="" className={styles.trapezoid} />
              <img
                src={frame}
                alt=""
                className={styles.frameimg}
                onMouseEnter={() => handleShow("frame", true)}
                onMouseLeave={() => handleShow("frame", false)}
              />
            </div>
            {show.frame && !edit && (
              <span className={styles.액자가자}>
                {userInfo.nickname}님의 액자
              </span>
            )}

            <img
              src={door}
              alt=""
              className={s(styles.door, !edit && styles.img)}
              onClick={() => !edit && navigate("/feed")}
              onMouseEnter={() => handleShow("door", true)}
              onMouseLeave={() => handleShow("door", false)}
            />
            {show.door && !edit && (
              <span className={styles.문가자}>피드페이지로 가기</span>
            )}
            <img
              src={postbox}
              alt=""
              className={s(styles.postbox, !edit && styles.img)}
              onClick={() => !edit && handlePostboxClick()}
              onMouseEnter={() => handleShow("postbox", true)}
              onMouseLeave={() => handleShow("postbox", false)}
            />
            {show.postbox && !edit && (
              <span className={styles.알림가자}>메시지가기!</span>
            )}
          </>
          {edit && (
            <div>
              <div
                onClick={() => setModalIsOpen(true)}
                className={s(styles.edit, styles.이젤이미지변경)}
              >
                액자 이미지 변경하기
              </div>
              <div className={s(styles.edit, styles.easelColor)}>
                <div className={styles.colorTitle}>이젤색</div>
                <ColorSelector
                  color={esColor}
                  setColor={setEsColor}
                  Colors={easelImgs}
                  name={"easelColor"}
                />
              </div>
              <div className={s(styles.edit, styles.tableColor)}>
                <div className={styles.colorTitle}>테이블색</div>
                <ColorSelector
                  color={tbColor}
                  setColor={setTbColor}
                  Colors={tableImgs}
                  name={"deskColor"}
                />
              </div>
              <div className={s(styles.edit, styles.bookshelfColor)}>
                <div className={styles.colorTitle}>책장색</div>
                <ColorSelector
                  color={bsColor}
                  setColor={setBsColor}
                  Colors={bookshelfImgs}
                  name={"bookshelfColor"}
                />
              </div>
            </div>
          )}
        </div>
        {!userInfo.profilePublic && !checkme && (
          <div className={styles.unPublic}>
            <div className={styles.unPublicModal}>
              <div>앗!!</div>
              <div>비공개 계정입니다.</div>
            </div>
          </div>
        )}
        <div className={styles.flex}>
          <div className={showListType ? styles.showList : styles.hideList}>
            {showListType === "follower" && (
              <FollowerList
                updateFollowerCount={updateFollowerCount}
                updateFollowingCount={updateFollowingCount}
                setShowList={() => setShowListType(null)}
                me={user.memberId}
                nowuser={nowuser}
              />
            )}
            {showListType === "following" && (
              <FollowList
                updateFollowingCount={updateFollowingCount}
                setShowList={() => setShowListType(null)}
                me={user.memberId}
                nowuser={nowuser}
              />
            )}
          </div>
          <div
            className={s(
              edit ? styles.active : showListType ? styles.dd : styles.container
            )}
          >
            <div className={styles.profileContainer}>
              <div className={styles.profile}>
                <div className={`${styles.프로필박스2}`}>
                  {userimg != "null" ? (
                    <div
                      className={styles.프로필이미지곰}
                      style={{
                        background: `url(${userimg})no-repeat center/cover`,
                      }}
                    ></div>
                  ) : (
                    <div
                      className={styles.프로필이미지곰}
                      style={{
                        background: `url(${gomimg})no-repeat center/cover`,
                      }}
                    ></div>
                  )}
                </div>
                <div className={styles.프로필박스4}>
                  <div className={styles.닉네임폰트}>{userInfo.nickname}</div>
                  <div>{userInfo.name}</div>
                </div>
                <div className={styles.프로필박스}>
                  <div>{userInfo.bookCount}</div>
                  <div>책</div>
                </div>
                <div className={styles.프로필박스}>
                  <div>{userInfo.threadCount}</div>
                  <div>스레드</div>
                </div>
                <div
                  className={styles.프로필박스3}
                  onClick={() => handleShowList("follower")}
                >
                  <div>{userInfo.followerCount}</div>
                  <div>팔로워</div>
                </div>
                <div
                  className={styles.프로필박스3}
                  onClick={() => handleShowList("following")}
                >
                  <div>{userInfo.followingCount}</div>
                  <div>팔로잉</div>
                </div>
              </div>
              <div className={styles.한줄소개}>
                {userInfo.intro ? userInfo.intro : "아직 한줄소개가 없습니다."}
              </div>
            </div>
            <div>
              {checkme ? (
                <div className={styles.editButton} onClick={() => handleEdit()}>
                  방 꾸미기
                </div>
              ) : (
                <div
                  className={styles.editButton}
                  onClick={() =>
                    followStatus === 1
                      ? handleFollow()
                      : followStatus === 2
                      ? handleCancelFollow()
                      : handleUnFollow()
                  }
                >
                  {followStatus === 1
                    ? "팔로우"
                    : followStatus === 2
                    ? "팔로우요청취소"
                    : "팔로잉중입니다"}
                </div>
              )}
            </div>
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
      <BigModal
        modalIsOpen={modalIsOpen}
        setModalIsOpen={setModalIsOpen}
        width="400px"
        height="500px"
      >
        <div className={styles.inputContainer}>
          <div className={styles.inputTitle}>액자 이미지를 선택하세요</div>

          <FileInput
            className={styles.changeImg}
            name="frameImage"
            value={value.frameImage}
            onChange={handleChangeValue}
            initialPreview={frameimgurl}
          />
        </div>
        <div
          className={styles.inputButton}
          onClick={() =>
            value.frameImage ? handleImg() : setModalIsOpen(false)
          }
        >
          확인
        </div>
      </BigModal>
    </>
  );
}
