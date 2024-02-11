import { useEffect, useState } from "react";
import Container from "../components/frame/Container";
//아직 안써서 주석처리
import styles from "./style/SettingPage.module.css";
import 설정옆이미지 from "../assets/설정옆이미지.png";
//현재유저를 가져오기위해
import useUserStore from "../store/useUserStore";
//현재유저를통해 정보를가져오기위해
import { getMyMybrary } from "../api/mybrary/Mybrary";
import { checkNickName } from "../api/member/SignUp";
import { updateProfile } from "../api/member/Account";
import toast from "react-hot-toast";
import axios from "axios";
import { uplodaImage } from "../api/image/Image";
import { useNavigate } from "react-router-dom";
export default function SettingPage() {
  const user = useUserStore((state) => state.user);

  const [testuser, setTestuser] = useState({
    data: {},
  });

  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");
  const [intro, setIntro] = useState("");
  const [profileimg, setProfileimg] = useState("");
  const [profilepublic, setProfilepublic] = useState(true);
  const [notifyenable, setNotifyenable] = useState(true);

  const [formErrors, setFormErrors] = useState({});
  const [isNickNameChecked, setIsNickNameChecked] = useState(false); // 중복검사를 해야만 회원가입 가능

  const [formData, setFormData] = useState({
    member: {
      memberId: "",
      profileImageId: "",
      nickname: "",
      intro: "",
      profilePublic: true,
      notifyEnable: true,
    },
  });

  /* 유효성검사 정규표현식 */
  const regex = {
    password: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*\d).{8,20}$/,
    nickname: /^[a-zA-Z0-9_]{3,15}$/,
  };
  const navigate = useNavigate();
  const navigateToErrorPage = () => {
    navigate("/error");
  };
  const showToast = (string) => {
    toast.success(`${string}`, {
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
  const handleNickNameChange = (e) => {
    setIsNickNameChecked(false);
    setNickname(e.target.value);
  };
  //
  const handleCheckNickName = async (e, nname) => {
    e.preventDefault();
    //닉네임 중복검사는 몇번이든 할 수 있다. 유효성 검증을 하고, 응답에 따라 상태를 바꾼다.

    setFormErrors((prevFormErrors) => {
      const { nickname, ...rest } = prevFormErrors;
      return rest;
    });

    if (!nname) {
      setTimeout(
        () =>
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            nickname: "닉네임을 입력해주세요",
          })),
        5
      );
    } else if (!regex.nickname.test(nname)) {
      setTimeout(
        () =>
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            nickname:
              "닉네임은 영어, 숫자, 언더바만 사용하여 3~15자 입력해야 합니다",
          })),
        5
      );
    } else {
      try {
        const data = await checkNickName(nname);
        /* 중복이 아닐 경우 */

        if (data.message === "사용 가능한 닉네임입니다") {
          setFormErrors((prevFormErrors) => {
            const { nickname, ...rest } = prevFormErrors;
            return rest;
          });
          setIsNickNameChecked(true);

          showToast("사용 가능한 닉네임입니다");
        } else if (data.message === "중복된 닉네임입니다") {
          /* 중복 닉네임인 경우 */
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            nickname: "중복된 닉네임입니다",
          }));
          showToast("중복된 닉네임입니다"); // 중복 닉네임일 경우 토스트 표시 추가
        } else {
          showToast("사용할 수 없는 닉네임입니다");
        }
      } catch (e) {
        console.log(e);
        navigateToErrorPage();
      }
    }
  };
  const [password, setPassword] = useState("1234");
  const [checkpassword, setCheckpassword] = useState("1234");
  //
  useEffect(() => {
    async function fetchData() {
      try {
        const response = await getMyMybrary();
        console.log(response);
        setTestuser(response);
        setName(response.data.name);
        setNickname(response.data.nickname);
        setIntro(response.data.intro);
        setProfileimg(response.data.profileImageUrl);
        setProfilepublic(response.data.profilePublic);
        setNotifyenable(response.data.notifyEnable);
      } catch (error) {
        console.error("데이터를 가져오는데 실패하였다", error);
      }
    }
    fetchData();
  }, []);
  return (
    <>
      <Container>
        <div className={styles.main}>
          <div className={styles.설정}>
            <span>설정</span>
          </div>
          <div className={styles.미드첫째}>
            <div className={styles.설정과이미지}>
              <span>계정설정</span>
              <img className={styles.설정옆이미지} src={설정옆이미지} alt="" />
            </div>
            <div className={styles.설정친구들}>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>닉네임변경</span>
                </div>
                <div className={styles.중간크기조정}>
                  <input
                    type="text"
                    id="search"
                    placeholder="닉네임을입력하세요"
                    value={nickname}
                    className={styles.input}
                    onChange={handleNickNameChange}
                  />
                  <button onClick={(e) => handleCheckNickName(e, nickname)}>
                    중복체크
                  </button>
                </div>
                {isNickNameChecked && (
                  <button className={styles.확인버튼}>확인</button>
                )}
              </div>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>한줄소개</span>
                </div>
                <div className={styles.중간크기조정}>
                  <input
                    type="text"
                    id="search"
                    placeholder={"한줄소개를 적어주세요"}
                    value={intro}
                    className={styles.input2}
                    onChange={(e) => setIntro(e.target.value)}
                  />
                </div>
                <button className={styles.확인버튼}>확인</button>
              </div>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>이름변경</span>
                </div>
                <div className={styles.중간크기조정}>
                  <input
                    type="text"
                    id="search"
                    placeholder={name}
                    value={name}
                    className={styles.input}
                    onChange={(e) => setName(e.target.value)}
                  />
                </div>
                <button className={styles.확인버튼}>확인</button>
              </div>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>비밀번호변경</span>
                </div>
                <div className={styles.중간크기조정}>
                  <input
                    type="password"
                    id="search"
                    placeholder={password}
                    value={password}
                    className={styles.input}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  <input
                    type="password"
                    id="search"
                    placeholder={password}
                    value={checkpassword}
                    className={styles.input}
                    onChange={(e) => setCheckpassword(e.target.value)}
                  />
                </div>
                <button className={styles.확인버튼}>확인</button>
              </div>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>계정공개</span>
                </div>
                <div className={styles.공개비공개버튼}>
                  <button
                    onClick={() => setProfilepublic(true)}
                    className={profilepublic ? styles.버튼1 : styles.버튼2}
                  >
                    공개
                  </button>
                  <button
                    onClick={() => setProfilepublic(false)}
                    className={profilepublic ? styles.버튼2 : styles.버튼1}
                  >
                    비공개
                  </button>
                </div>
              </div>
            </div>
            <div className={styles.설정과이미지}>
              <span>알림설정</span>
              <img className={styles.설정옆이미지} src={설정옆이미지} alt="" />
            </div>
            <div className={styles.설정친구들}>
              <div className={styles.설정한줄크기}>
                <div className={styles.고정크기}>
                  <span>알림허용</span>
                </div>
                <div className={styles.공개비공개버튼}>
                  <button
                    onClick={() => setNotifyenable(true)}
                    className={notifyenable ? styles.버튼1 : styles.버튼2}
                  >
                    허용
                  </button>
                  <button
                    onClick={() => setNotifyenable(false)}
                    className={notifyenable ? styles.버튼2 : styles.버튼1}
                  >
                    비허용
                  </button>
                </div>
              </div>
            </div>
          </div>
          <button>회원탈퇴</button>
          <button>완료</button>
        </div>
      </Container>
    </>
  );
}
