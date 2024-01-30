import React, { useState, useMemo, useRef, useEffect } from "react";
import ReactQuill, { Quill } from "react-quill";
import "react-quill/dist/quill.snow.css";
import ImageResize from "@looop/quill-image-resize-module-react";
import styles from "./style/ThreadCreatePage.module.css";
import Container from "../components/frame/Container";

export default function ThreadCreatePage() {
  const [addtext, setAddtext] = useState("");
  const [addfriend, setAddfriend] = useState("");
  const [sectionVisible, setSectionVisible] = useState("left-center"); // 상태 변수 추가

  // "다음" 버튼 핸들러
  const handleNextClick = () => {
    setSectionVisible("center-right");
  };

  // ".미드오른쪽" 섹션 클릭 핸들러
  const handleRightSectionClick = () => {
    setSectionVisible("left-center");
  };

  // Quill.register("modules/ImageResize", ImageResize);

  const [content1, setContent1] = useState(""); // 첫 번째 Quill 컴포넌트의 내용
  // const [content2, setContent2] = useState(""); // 두 번째 Quill 컴포넌트의 내용

  const quillRef1 = useRef(null); // 첫 번째 Quill 컴포넌트의 ref
  // const quillRef2 = useRef(null); // 두 번째 Quill 컴포넌트의 ref

  // Quill 모듈 설정
  const modules = useMemo(
    () => ({
      toolbar: {
        container: [
          ["bold", "italic", "underline", "strike"], // 원하는 툴바 버튼 설정
          [{ list: "ordered" }, { list: "bullet" }],
          [{ indent: "-1" }, { indent: "+1" }],
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }],
          [{ align: [] }],
          // ["image"],
        ],
      },
      // ImageResize: {
      //   parchment: Quill.import("parchment"),
      //   modules: ["Resize", "DisplaySize"],
      // },
      history: {
        delay: 500,
        maxStack: 100,
        userOnly: true,
      },
      // 다른 모듈 설정 추가
    }),
    []
  );

  // Quill 컴포넌트의 툴바 설정
  useEffect(() => {
    if (quillRef1.current) {
      quillRef1.current.getEditor().getModule("toolbar").update();
    }
  }, []);

  return (
    <>
      <Container>
        <div className={styles.메인}>
          <span>페이지 작성</span>
          <div className={styles.페이지만들기}>
            <div className={styles.페이지만들기헤더}>
              <div>
                <button>1</button>
                <button>+</button>
              </div>
              <div>
                <button>페이지제거</button>
              </div>
            </div>
            <div
              className={`${styles.페이지만들기미드} ${
                sectionVisible === "left-center"
                  ? styles.showLeftCenter
                  : styles.showCenterRight
              }`}
            >
              <div className={styles.미드왼쪽}>
                <div className={styles.왼쪽헤더}>
                  <span>레이아웃</span>
                  <div>
                    <button>1</button>
                    <button>2</button>
                    <button>3</button>
                    <button>4</button>
                  </div>
                </div>
                <div className={styles.왼쪽미드}>
                  <div>
                    <div>레이아웃1</div>
                    <div>레이아웃2</div>
                    <div>레이아웃3</div>
                    <div>레이아웃4</div>
                    <div>레이아웃5</div>
                    <div>레이아웃6</div>
                  </div>
                  <button onClick={handleNextClick}>다음</button>
                </div>
              </div>
              <div className={styles.미드중앙}>
                <div>선택된레이아웃</div>
              </div>
              <div className={styles.미드오른쪽}>
                <div className={styles.태그추가부분}>
                  <span>태그추가</span>
                  <form>
                    {/* <form onSubmit={handleSubmit}> */}
                    <label htmlFor="add"></label>
                    <div className={styles.searchContainer}>
                      <input
                        type="text"
                        id="add"
                        placeholder="태그추가"
                        value={addtext}
                        className={styles.searchInput}
                        onChange={(e) => setAddtext(e.target.value)}
                      />
                      <button type="submit" className={styles.searchButton}>
                        +
                      </button>
                    </div>
                  </form>
                  <div className={styles.추가된태그들}>추가된태그들</div>
                </div>
                <div className={styles.친구태그부분}>
                  <span>친구태그</span>
                  <form>
                    {/* <form onSubmit={handleSubmit}> */}
                    <label htmlFor="add2"></label>
                    <div className={styles.searchContainer}>
                      <input
                        type="text"
                        id="add2"
                        placeholder="태그추가"
                        value={addfriend}
                        className={styles.searchInput}
                        onChange={(e) => setAddfriend(e.target.value)}
                      />
                      <button type="submit" className={styles.searchButton}>
                        +
                      </button>
                    </div>
                  </form>
                  <div className={styles.추가된태그들}>추가된친구태그들</div>
                </div>
                <div className={styles.레아수정}>
                  <button onClick={handleRightSectionClick}>
                    {/* 오른쪽 섹션 내용 */}레이아웃수정
                  </button>
                </div>
              </div>
            </div>
            <div className={styles.페이지만들기푸터}>
              <div className={styles.푸터}>
                <div className={styles.푸터인포}>
                  <span>책선택</span>
                  <span>페이퍼를 끼워넣을 책을 선택하세요</span>
                </div>
                <button>선택되지않음</button>
              </div>
              <div className={styles.푸터}>
                <div className={styles.푸터인포}>
                  <span>공개설정</span>
                  <span>나만보기일 경우 남에게 보여지지 않습니다</span>
                </div>
                <div>
                  <button>전체</button>
                  <button>나만보기</button>
                </div>
              </div>
              <div className={styles.푸터}>
                <div className={styles.푸터인포}>
                  <span>스크랩허용</span>
                  <span>나만보기일 경우 스크랩허용을 할 수 없습니다</span>
                </div>
                <div>
                  <button>허용</button>
                  <button>허용되지않음</button>
                </div>
              </div>
            </div>
            <div className={styles.게시}>
              <button className={styles.버튼게시}>게시</button>
            </div>
          </div>
          <div>
            <ReactQuill
              ref={quillRef1}
              value={content1}
              onChange={setContent1}
              modules={modules}
            />
          </div>
        </div>
      </Container>
    </>
  );
}
