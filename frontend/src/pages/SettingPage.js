import { useState } from "react";
import Container from "../components/frame/Container";
//아직 안써서 주석처리
import styles from "./style/SettingPage.module.css";
import 설정옆이미지 from "../assets/설정옆이미지.png";
export default function SettingPage() {
  const [nickname, setNickname] = useState("cwnsgh");
  const [name, setName] = useState("최준호");
  const [password, setPassword] = useState("1234");
  const [checkpassword, setCheckpassword] = useState("1234");
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
                    onChange={(e) => setNickname(e.target.value)}
                  />
                  <button className={styles.중복체크}>중복체크</button>
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
                  <button className={styles.버튼1}>공개</button>
                  <button className={styles.버튼2}>비공개</button>
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
                  <button className={styles.버튼1}>허용</button>
                  <button className={styles.버튼2}>비허용</button>
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
