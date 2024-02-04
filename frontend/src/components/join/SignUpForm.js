import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  checkNickName,
  signup,
  verifyCode,
  verifyEmail,
} from "../../api/member/SignUp";
//import useStompStore from "../../store/useStompStore";

function SignUpForm() {
  // const client = useStompStore((state) => state.stompClient);
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
    nickname: /^[a-zA-Z0-9_]{3,15}$/,
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
  const [isEmailVerifying, setIsEmailVerifying] = useState(false);
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [isNickNameChecked, setIsNickNameChecked] = useState(false);
  const [code, setCode] = useState(""); // 이메일인증코드
  const [isSuccess, setIsSuccess] = useState(false);

  /* 값을 입력함과 동시에 form 데이터 동시에 갱신 */
  function handleChange(e) {
    setFormData((prevFormData) => ({
      ...prevFormData,
      [e.target.name]: e.target.value,
    }));
  }

  /* 닉네임은 입력하면 닉네임 중복 처리가 초기화되므로 따로 처리 */
  const handleNickNameChange = (e) => {
    // setIsNickNameChecked(false);
    handleChange(e);
  };

  /* 인증코드는 상태로 따로 관리  */
  const handleCodeChange = (e) => {
    setCode(e.target.value);
  };

  /* 이메일 인증 버튼 클릭 */
  const handleVerifyEmail = async (e, mail) => {
    e.preventDefault();
    //console.log(client);

    // 일단 유효성에 대한 검증을 먼저 한 후에, 유효성을 만족하면 검증 요청을 보냄. 응답에 따라 상태를 바꾼다.
    if (!mail) {
      setFormErrors((prevFormErrors) => ({
        ...prevFormErrors,
        email: "이메일을 입력해주세요",
      }));
    } else if (!regex.email.test(mail)) {
      setFormErrors((prevFormErrors) => ({
        ...prevFormErrors,
        email: "유효한 이메일을 입력해주세요",
      }));
    } else {
      // 유효성은 검증 됐으므로, 메일 전송.
      // 이메일 전송 코드
      try {
        const data = await verifyEmail(formData.email);
        // 데이터는 에러객체이거나, response.data의 값임
        if (data.status === "SUCCESS") {
          setIsEmailVerifying(true);
          setFormErrors((prevFormErrors) => {
            const { email, ...rest } = prevFormErrors;
            return rest;
          });
        } else {
          // 백엔드에서의 유효성 검사에서 막힘.
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            email: "유효한 이메일을 입력해주세요",
          }));
        }
      } catch (e) {
        console.log(e);
        navigateToErrorPage();
        // 서버에러. 에러페이지로 이동
      }
    }
  };

  const handleVerifyCode = async (e) => {
    e.preventDefault();

    try {
      const data = await verifyCode(formData.email, code);
      // 일치하면
      if (data.status === "SUCCESS") {
        setFormErrors((prevFormErrors) => {
          const { code, email, ...rest } = prevFormErrors;
          return rest;
        });
        setIsEmailVerifying(false);
        setIsEmailVerified(true);
      } else {
        setFormErrors((prevFormErrors) => ({
          ...prevFormErrors,
          code: "인증번호가 일치하지 않습니다.",
        }));
      }
    } catch (e) {
      console.log(e);
      navigateToErrorPage();
    }
  };

  const handleCheckNickName = async (e, nname) => {
    e.preventDefault();
    //닉네임 중복검사는 몇번이든 할 수 있다. 유효성 검증을 하고, 응답에 따라 상태를 바꾼다.
    if (!nname) {
      setFormErrors((prevFormErrors) => ({
        ...prevFormErrors,
        nickname: "닉네임을 입력해주세요.",
      }));
    } else if (!regex.nickname.test(nname)) {
      setFormErrors((prevFormErrors) => ({
        ...prevFormErrors,
        nickname: "유효한 닉네임을 입력해주세요",
      }));
    } else {
      try {
        const data = await checkNickName(nname);
        /* 중복이 아닐 경우 */

        if (data.status === "SUCCESS") {
          setFormErrors((prevFormErrors) => {
            const { nickname, ...rest } = prevFormErrors;
            return rest;
          });
          setIsNickNameChecked(true);
        } else {
          /* 중복 닉네임인 경우 */
          setFormErrors((prevFormErrors) => ({
            ...prevFormErrors,
            nickname: "중복된 닉네임입니다.",
          }));
        }
      } catch (e) {
        console.log(e);
        navigateToErrorPage();
      }
    }
  };

  // 폼 검사
  const handleSubmit = (e) => {
    e.preventDefault(); // 원래 form 안에서 button 이 가지는 기본 기능을 막음
    const errors = validate(formData);
    if (Object.keys(errors).length === 0) {
      console.log("Form submitted:", formData);
      // 여기에 폼 제출 로직을 추가하세요.
      // 여기서 회원가입 post 요청을 보내면 된다. 이 요청은 api 폴더에 있기 때문에 import 해온 것을 쓰면 된다.
      try {
        signup(formData);
        setIsSuccess(true);
      } catch (e) {
        // 에러 페이지로 이동 또는 signup 메서드 반환값의 message 를 출력 (백엔드에서 보내주는 에러 미시지)
        //signup 에서 에러 처리 돼있으므로 생략함.
      }
    } else {
      setFormErrors(errors);
    }
  };

  const validate = () => {
    const errors = {};

    if (!formData.email) {
      errors.email = "이메일을 입력해주세요.";
    } else if (!regex.email.test(formData.email)) {
      errors.email = "유효한 이메일 주소를 입력해주세요.";
    } else if (!isEmailVerified) {
      errors.email = "이메일 인증을 완료해주세요.";
    }

    if (!formData.password) {
      errors.password = "비밀번호를 입력해주세요.";
    } else if (!regex.password.test(formData.password)) {
      errors.password =
        "비밀번호는 8자 이상, 15자 이하의 숫자, 소문자, 대문자, 특수문자를 모두 포함해야 합니다.";
    }

    if (formData.password !== formData.passwordConfirm) {
      errors.passwordConfirm = "비밀번호가 일치하지 않습니다.";
    }

    if (!formData.name) {
      errors.name = "이름을 입력해주세요.";
    } else if (!regex.name.test(formData.name)) {
      errors.name = "유효한 한글 이름을 입력해주세요.";
    }

    if (!formData.nickname) {
      errors.nickname = "닉네임을 입력해주세요.";
    } else if (!regex.nickname.test(formData.nickname)) {
      errors.nickname =
        "닉네임은 영어, 숫자, 언더바만 사용하여 3~15자 입력해야 합니다.";
    }
    // else if (!isNickNameChecked) {
    //   errors.nickname = "닉네임 중복 검사를 완료해주세요.";
    // }

    return errors;
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <div>
          <label>이메일:</label>
          <input
            type="text"
            name="email"
            value={formData.email}
            onChange={handleChange}
            disabled={isEmailVerifying || isEmailVerified}
          />
          {!isEmailVerified && (
            <button onClick={(e) => handleVerifyEmail(e, formData.email)}>
              이메일 인증
            </button>
          )}
          {formErrors.email && <p>{formErrors.email}</p>}
        </div>
        {isEmailVerifying && (
          <div>
            <label>인증번호입력:</label>
            <input
              type="text"
              name="code"
              value={code}
              onChange={handleCodeChange}
            ></input>
            <button onClick={(e) => handleVerifyCode(e)}>인증번호 확인</button>
          </div>
        )}
        {formErrors.code && <p>{formErrors.code}</p>}

        <div>
          <label>비밀번호:</label>
          <input
            type="text"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {formErrors.password && <p>{formErrors.password}</p>}
        </div>

        <div>
          <label>비밀번호 확인:</label>
          <input
            type="text"
            name="passwordConfirm"
            value={formData.passwordConfirm}
            onChange={handleChange}
          />
          {formErrors.passwordConfirm && <p>{formErrors.passwordConfirm}</p>}
        </div>

        <div>
          <label>이름:</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          {formErrors.name && <p>{formErrors.name}</p>}
        </div>

        <div>
          <label>닉네임:</label>
          <input
            type="text"
            name="nickname"
            value={formData.nickname}
            onChange={handleNickNameChange}
          />

          <button onClick={(e) => handleCheckNickName(e, formData.nickname)}>
            닉네임 중복 검사
          </button>

          {formErrors.nickname && <p>{formErrors.nickname}</p>}
        </div>

        <button type="submit">가입하기</button>
      </form>
      {isSuccess && <h1>회원가입성공</h1>}
    </>
  );
}

export default SignUpForm;
