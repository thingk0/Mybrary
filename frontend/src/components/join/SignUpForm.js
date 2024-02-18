import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SignUpForm.module.css";
import s from "classnames";
import toast from "react-hot-toast";
import Iconuser2 from "../../assets/icon/Iconuser2.png";
import 예시이미지2 from "../../assets/예시이미지2.png";
import 기본책표지 from "../../assets/기본책표지.png";

import {
  checkNickName,
  signup,
  verifyCode,
  verifyEmail,
} from "../../api/member/SignUp";
import { uplodaImage } from "../../api/image/Image";

function SignUpForm({ setPageremote }) {
  /* 에러페이지 이동 */
  const navigate = useNavigate();
  const navigateToErrorPage = () => {
    navigate("/error");
  };

  /* 유효성검사 정규표현식 */
  const regex = {
    email: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
    password: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*\d).{8,20}$/,
    name: /^[가-힣]{2,5}$/,
    nickname: /^[a-zA-Z0-9_]{3,10}$/,
  };

  /* 알림 함수 */
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

  /* useState 상태 */
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    passwordConfirm: "",
    name: "",
    nickname: "",
  });

  const [formErrors, setFormErrors] = useState({});
  const [isEmailVerifying, setIsEmailVerifying] = useState(false); // 이메일 인증 중인 상태
  const [isEmailVerified, setIsEmailVerified] = useState(false); // 이메일 인증 완료 (해당 조건을 만족해야만, 가입 가능. 인증 하고 나면 인증 버튼이 사라진다)
  const [isNickNameChecked, setIsNickNameChecked] = useState(false); // 중복검사를 해야만 회원가입 가능
  const [code, setCode] = useState(""); // 이메일인증코드

  /* 값을 입력함과 동시에 form 데이터 동시에 갱신 */
  function handleChange(e) {
    setFormData((prevFormData) => ({
      ...prevFormData,
      [e.target.name]: e.target.value,
    }));
  }

  /* 닉네임은 입력하면 닉네임 중복 처리가 초기화되므로 따로 처리 */
  const handleNickNameChange = (e) => {
    setIsNickNameChecked(false);
    handleChange(e);
  };

  /* 인증코드는 상태로 따로 관리  */
  const handleCodeChange = (e) => {
    setCode(e.target.value);
  };

  /* 이메일 인증 버튼 클릭 */
  const handleVerifyEmail = async (e, mail) => {
    e.preventDefault();

    // 일단 유효성에 대한 검증을 먼저 한 후에, 유효성을 만족하면 검증 요청을 보냄. 응답에 따라 상태를 바꾼다.
    setFormErrors((prevFormErrors) => {
      const { email, ...rest } = prevFormErrors;
      return rest;
    });

    if (!mail) {
      setTimeout(
        () =>
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            email: "이메일을 입력해주세요",
          })),
        5
      );
    } else if (!regex.email.test(mail)) {
      setTimeout(
        () =>
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            email: "유효한 이메일을 입력해주세요",
          })),
        5
      );
    } else {
      // 유효성은 검증 됐으므로, 메일 전송.
      // 이메일 전송 코드

      showToast("인증 코드가 전송되었습니다."); // 실제 백엔드 검사 전 미리 출력. (사용자 경험 향상)
      setIsEmailVerifying(true); // 이메일 인증 중인 상태
      // 이메일 관련 오류메시지 해제
      setFormErrors((prevFormErrors) => {
        const { email, ...rest } = prevFormErrors;
        return rest;
      });

      try {
        const data = await verifyEmail(formData.email);
        // 데이터는 에러객체이거나, response.data의 값임
        if (data.status === "SUCCESS") {
        } else {
          // 백엔드에서의 유효성 검사에서 막힘.
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            email: "유효한 이메일을 입력해주세요",
          }));
        }
      } catch (e) {
        // 서버통신에러. 에러페이지로 이동
        navigateToErrorPage();
      }
    }
  };

  const [ok, setOk] = useState(false);
  const handleVerifyCode = async (e) => {
    e.preventDefault();
    setFormErrors((prevFormErrors) => {
      const { code, ...rest } = prevFormErrors;
      return rest;
    });
    try {
      const data = await verifyCode(formData.email, code);
      // 일치하면
      if (data.status === "SUCCESS") {
        setFormErrors((prevFormErrors) => {
          const { code, email, ...rest } = prevFormErrors;
          return rest;
        });

        showToast("이메일 인증이 완료 되었습니다");

        setCode("");

        setIsEmailVerifying(false);
        setIsEmailVerified(true);
        setOk(true);
      } else {
        setFormErrors((prevFormErrors) => ({
          ...prevFormErrors,
          code: "인증번호가 일치하지 않거나, 인증 시간이 만료되었습니다",
        }));
      }
    } catch (e) {
      navigateToErrorPage();
    }
  };

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
              "닉네임은 영어, 숫자, 언더바만 사용하여 3~10자 입력해야 합니다",
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
        } else {
          /* 중복 닉네임인 경우 */
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            nickname: "중복된 닉네임입니다",
          }));
        }
      } catch (e) {
        navigateToErrorPage();
      }
    }
  };

  // 폼 검사
  const handleSubmit = async (e) => {
    const errors = validate(formData);
    if (Object.keys(errors).length === 0) {
      // 여기에 폼 제출 로직을 추가하세요.
      // 여기서 회원가입 post 요청을 보내면 된다. 이 요청은 api 폴더에 있기 때문에 import 해온 것을 쓰면 된다.
      try {
        // 여기서 이미지 업로드
        const res1 = await fetch(Iconuser2);
        const blob1 = await res1.blob();

        const res2 = await fetch(예시이미지2);
        const blob2 = await res2.blob();

        const res3 = await fetch(기본책표지);
        const blob3 = await res3.blob();

        const images = new FormData();
        images.append("images", blob1, "Iconuser2.png");
        images.append("images", blob2, "예시이미지2.png");
        images.append("images", blob3, "기본책표지.png");

        const res = await uplodaImage(images);
        console.log(res);

        const data = await signup({
          ...formData,
          profileImageId: res.imageIds[0],
          frameImageId: res.imageIds[1],
          bookCoverImageId: res.imageIds[2],
        });
        console.log(data);

        if (data.status === "SUCCESS") {
          showToast("회원가입이 완료되었습니다.");
          setPageremote((prev) => !prev);
          setFormData("");
        } else {
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            email: "이미 가입된 이메일 입니다.",
          }));
          setIsEmailVerifying(false);
          setIsEmailVerified(false);
        }
      } catch (e) {
        // 에러 페이지로 이동 또는 signup 메서드 반환값의 message 를 출력 (백엔드에서 보내주는 에러 미시지)
        //signup 에서 에러 처리 돼있으므로 생략함.
      }
    } else {
      setFormErrors({});
      setTimeout(() => setFormErrors(errors), 5);
    }
  };

  const validate = () => {
    const errors = {};

    if (!formData.email) {
      errors.email = "이메일을 입력해주세요";
    } else if (!regex.email.test(formData.email)) {
      errors.email = "유효한 이메일을 입력해주세요";
    } else if (!isEmailVerified) {
      errors.email = "이메일 인증을 완료해주세요";
    }

    if (!formData.password) {
      errors.password = "비밀번호를 입력해주세요";
    } else if (!regex.password.test(formData.password)) {
      errors.password =
        "비밀번호는 8자 이상, 15자 이하의 숫자, 소문자, 대문자, 특수문자를 모두 포함해야 합니다";
    }

    if (formData.password !== formData.passwordConfirm) {
      errors.passwordConfirm = "비밀번호가 일치하지 않습니다";
    }

    if (!formData.name) {
      errors.name = "이름을 입력해주세요";
    } else if (!regex.name.test(formData.name)) {
      errors.name = "유효한 한글 이름을 입력해주세요";
    }

    if (!formData.nickname) {
      errors.nickname = "닉네임을 입력해주세요";
    } else if (!regex.nickname.test(formData.nickname)) {
      errors.nickname =
        "닉네임은 영어, 숫자, 언더바만 사용하여 3~10자 입력해야 합니다";
    } else if (!isNickNameChecked) {
      errors.nickname = "닉네임 중복 검사를 완료해주세요";
    }

    return errors;
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className={styles.inputContainer}>
        <label className={styles.label}>email</label>
        <input
          className={s(styles.input, formErrors.email && styles.shakeAnimation)}
          type="email"
          name="email"
          value={formData.email}
          placeholder="이메일을 입력해주세요"
          onChange={handleChange}
          disabled={isEmailVerifying || isEmailVerified}
        />
        {formErrors.email && (
          <div className={styles.error}>{formErrors.email}</div>
        )}
        {!isEmailVerified && (
          <div
            className={styles.btn}
            onClick={(e) => handleVerifyEmail(e, formData.email)}
            disabled={isEmailVerified}
          >
            이메일 인증
          </div>
        )}
      </div>

      {isEmailVerifying && (
        <div className={styles.inputContainer}>
          <label className={styles.label}>인증번호입력:</label>

          <input
            className={s(
              styles.input,
              formErrors.code && styles.shakeAnimation
            )}
            type="text"
            name="code"
            placeholder="인증번호를 입력하세요"
            value={code}
            onChange={handleCodeChange}
          />
          {formErrors.code && (
            <div className={styles.error}>{formErrors.code}</div>
          )}
          <div className={styles.btn} onClick={(e) => handleVerifyCode(e)}>
            인증번호 확인
          </div>
        </div>
      )}

      {ok && (
        <>
          <div className={styles.inputContainer}>
            <label className={styles.label}>비밀번호</label>
            <input
              className={s(
                styles.input,
                formErrors.password && styles.shakeAnimation
              )}
              type="password"
              name="password"
              placeholder="비밀번호를 입력하세요"
              value={formData.password}
              onChange={handleChange}
            />
            {formErrors.password && (
              <div className={styles.error}>{formErrors.password}</div>
            )}
          </div>

          <div className={styles.inputContainer}>
            <label className={styles.label}>비밀번호 확인</label>
            <input
              className={s(
                styles.input,
                formErrors.passwordConfirm && styles.shakeAnimation
              )}
              type="password"
              name="passwordConfirm"
              placeholder="비밀번호 확인"
              value={formData.passwordConfirm}
              onChange={handleChange}
            />
            {formErrors.passwordConfirm && (
              <div className={styles.error}>{formErrors.passwordConfirm}</div>
            )}
          </div>

          <div className={styles.inputContainer}>
            <label className={styles.label}>이름</label>
            <input
              className={s(
                styles.input,
                formErrors.name && styles.shakeAnimation
              )}
              type="text"
              name="name"
              placeholder="이름을 입력하세요"
              value={formData.name}
              onChange={handleChange}
            />
            {formErrors.name && (
              <div className={styles.error}>{formErrors.name}</div>
            )}
          </div>

          <div className={styles.inputContainer}>
            <label className={styles.label}>닉네임</label>
            <input
              className={s(
                styles.input,
                formErrors.nickname && styles.shakeAnimation
              )}
              type="text"
              name="nickname"
              placeholder="닉네임을 입력하세요"
              value={formData.nickname}
              onChange={handleNickNameChange}
            />
            <div
              className={styles.btn}
              onClick={(e) => handleCheckNickName(e, formData.nickname)}
            >
              닉네임 중복 검사
            </div>
            {formErrors.nickname && (
              <div className={styles.error}>{formErrors.nickname}</div>
            )}
          </div>
          <div className={styles.button} onClick={() => handleSubmit()}>
            회원가입
          </div>
        </>
      )}
    </form>
  );
}

export default SignUpForm;
