import React, { useState, useMemo, useRef, useEffect } from "react";
import "react-quill/dist/quill.snow.css";
// import React, { useState, useEffect } from "react";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { EditorState, convertToRaw } from "draft-js";
// convertToRaw로 변환시켜준 원시 JS 구조를 HTML로 변환.
import draftToHtml from "draftjs-to-html";

import styles from "./style/ThreadCreatePage.module.css";
import Container from "../components/frame/Container";
import 레이아웃1 from "../assets/레이아웃1.png";
import 레이아웃2 from "../assets/레이아웃2.png";
import 레이아웃3 from "../assets/레이아웃3.png";
import 레이아웃4 from "../assets/레이아웃4.png";
import 레이아웃5 from "../assets/진구형.jpg";
import 레이아웃6 from "../assets/혜선누나.jpg";

export default function ThreadCreatePage() {
  const saveContent = () => {
    // EditorState에서 RawDraftContentState를 추출
    const rawContentState = convertToRaw(editorState.getCurrentContent());
    const rawContentState2 = convertToRaw(editorState2.getCurrentContent());

    // RawDraftContentState를 HTML로 변환
    const htmlContent = draftToHtml(rawContentState);
    const htmlContent2 = draftToHtml(rawContentState2);

    console.log(htmlContent);
    console.log(htmlContent2);
    // 이제 htmlContent를 백엔드 API에 전송하여 저장

    // 예: axios.post('/api/saveContent', { content: htmlContent });
  };
  const htmlContent = `<p></p>
  <img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA5MTJfNDgg%2FMDAxNjk0NDg2OTUzMTUx.C-qTANszcS7JMhPhAKw8AObpciMkphr_qBDq3tcgM4Eg.SnSjMpF7PpK9_jhoCdo9i0DcVwZtc1biuFdI0guzYgwg.JPEG.my_ebbuny%2F6.4_%25281_-_14%2529.jpg&type=a340" alt="undefined" style="height: 150px;width: 150px"/>
  <p>오늘 저녁은 뭐먹을까 너무 고민이되네요~</p>
  <p></p>`;
  // <div dangerouslySetInnerHTML={{ __html: htmlContent }} />

  const [showColorPicker, setShowColorPicker] = useState(false);
  const [currentColor, setCurrentColor] = useState("#000000");
  const toggleColorPicker = () => {
    setShowColorPicker(!showColorPicker);
  };

  const changeColor = (color) => {
    setCurrentColor(color.hex);
    const newEditorState = RichUtils.toggleInlineStyle(
      editorState,
      "COLOR-" + color.hex
    );
    setEditorState(newEditorState);
  };

  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const [toolbarZIndex, setToolbarZIndex] = useState(1); // 초기 z-index 값 설정

  const [editorState2, setEditorState2] = useState(EditorState.createEmpty());
  const [toolbarZIndex2, setToolbarZIndex2] = useState(1); // 초기 z-index 값 설정

  const [imageSrc, setImageSrc] = useState(null);
  const [imageStyle, setImageStyle] = useState({});
  const [editorStyle, setEditorStyle] = useState({});

  const onEditorStateChange = (newEditorState) => {
    setEditorState(newEditorState);
  };
  const onEditorStateChange2 = (newEditorState) => {
    setEditorState2(newEditorState);
  };
  const handleFocus1 = () => {
    setToolbarZIndex(4); // 에디터가 포커스되면 z-index를 4로 설정
  };
  const handleFocus2 = () => {
    setToolbarZIndex2(4); // 에디터가 포커스되면 z-index를 4로 설정
  };

  const handleBlur = () => {
    setToolbarZIndex(1); // 에디터 포커스 해제 시 z-index를 원래 값으로 복원
    setToolbarZIndex2(1); // 에디터 포커스 해제 시 z-index를 원래 값으로 복원
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file && file.type.match("image.*")) {
      const reader = new FileReader();

      reader.onload = (readerEvent) => {
        setImageSrc(readerEvent.target.result);
      };

      reader.readAsDataURL(file);
    }
  };

  const handlePositionChange = (
    imageTop,
    imageRight,
    editorTop,
    editorRight
  ) => {
    setImageStyle({
      position: "absolute",
      top: `${imageTop}px`,
      right: `${imageRight}px`,
    });
    setEditorStyle({
      position: "absolute",
      top: `${editorTop}px`,
      right: `${editorRight}px`,
    });
  };

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

  const rayout = [
    레이아웃1,
    레이아웃2,
    레이아웃3,
    레이아웃4,
    레이아웃5,
    레이아웃6,
  ];
  return (
    <>
      <Container>
        <div className={styles.메인}>
          <div className={styles.페이지}>
            <div className={styles.페이지사이즈조정}>
              <span className={styles.페이지작성}>페이지 작성</span>
            </div>
          </div>
          <div className={styles.페이지만들기}>
            <div className={styles.페이지만들기헤더}>
              <div className={styles.헤더사이즈조정}>
                <div className={styles.버튼들}>
                  <button>1</button>
                  <button>+</button>
                </div>
                <div>
                  <button className={styles.페이지제거버튼}>페이지제거</button>
                </div>
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
                <div className={styles.미드왼쪽사이즈조정}>
                  <div className={styles.왼쪽헤더}>
                    <div className={styles.왼쪽헤더사이즈조정}>
                      <span className={styles.레이아웃글자}>레이아웃</span>
                      <div className={styles.레이아웃버튼}>
                        <button
                          onClick={() =>
                            handlePositionChange(400, 400, 250, 260)
                          }
                        >
                          1
                        </button>
                        <button onClick={() => handlePositionChange(600, 300)}>
                          2
                        </button>
                        <button onClick={() => handlePositionChange(400, 300)}>
                          3
                        </button>
                        <button onClick={() => handlePositionChange(700, 100)}>
                          4
                        </button>
                      </div>
                    </div>
                  </div>
                  <div className={styles.왼쪽미드}>
                    <div className={styles.왼쪽미드레이아웃}>
                      {rayout.map((레이아웃, index) => (
                        <div key={index}>
                          <img
                            className={styles.레이아웃이미지}
                            src={레이아웃}
                            alt={`레이아웃 ${index + 1}`}
                          />
                        </div>
                      ))}
                    </div>
                  </div>
                  <button className={styles.다음버튼} onClick={handleNextClick}>
                    다음
                  </button>
                </div>
              </div>
              <div className={styles.미드중앙}>
                <div className={styles.미드중앙사이즈조정}>
                  <div className={styles.positionWrapper}>
                    <div className={styles.wrapperClass}>
                      <Editor
                        wrapperClassName={styles.wrapperClass}
                        editorClassName={styles.editorClass}
                        toolbarClassName={styles.toolbarClass}
                        toolbarStyle={{ zIndex: toolbarZIndex }}
                        toolbar={{
                          list: { inDropdown: true },
                          textAlign: { inDropdown: true },
                        }}
                        placeholder="내용을 작성해주세요."
                        localization={{
                          locale: "ko",
                        }}
                        editorStyle={editorStyle} // 에디터 스타일 적용
                        editorState={editorState}
                        onEditorStateChange={onEditorStateChange}
                        onFocus={handleFocus1} // 에디터 포커스 이벤트 핸들러
                        onBlur={handleBlur}
                      />
                    </div>
                    <div className={styles.wrapperClass2}>
                      <Editor
                        wrapperClassName={styles.wrapperClass2}
                        editorClassName={styles.editorClass2}
                        toolbarClassName={styles.toolbarClass}
                        toolbarStyle={{ zIndex: toolbarZIndex2 }}
                        toolbar={{
                          list: { inDropdown: true },
                          textAlign: { inDropdown: true },
                        }}
                        placeholder="내용을 작성해주세요."
                        localization={{
                          locale: "ko",
                        }}
                        editorStyle={editorStyle} // 에디터 스타일 적용
                        editorState={editorState2}
                        onEditorStateChange={onEditorStateChange2}
                        onFocus={handleFocus2} // 에디터 포커스 이벤트 핸들러
                        onBlur={handleBlur}
                      />
                    </div>
                  </div>
                  <div className={styles.이미지업로드부분}>
                    <input
                      className={styles.이미지업로드버튼}
                      type="file"
                      onChange={handleImageChange}
                    />
                    {imageSrc && (
                      <img
                        className={styles.업로드이미지}
                        src={imageSrc}
                        alt="뭐지"
                        style={imageStyle} // 인라인 스타일 적용
                      />
                    )}
                  </div>
                </div>
              </div>
              <div className={styles.미드오른쪽}>
                <div className={styles.오른쪽크기조정}>
                  <div className={styles.태그추가부분}>
                    <span className={styles.태그span}>태그추가</span>
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
                    <span className={styles.태그span}>친구태그</span>
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
            </div>
            <div className={styles.페이지만들기푸터}>
              <div className={styles.푸터}>
                <div className={styles.푸터인포}>
                  {/* <span>책선택</span>
                  <span>쓰레드를 끼워넣을 책을 선택하세요</span> */}
                </div>
                <button onClick={() => saveContent()}>선택되지않음</button>
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
              <div dangerouslySetInnerHTML={{ __html: htmlContent }} />
              <button className={styles.버튼게시}>게시</button>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
