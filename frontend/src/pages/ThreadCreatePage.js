import React, { useState } from "react";
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

const initialPaper = () => ({
  editorState: EditorState.createEmpty(),
  editorState2: EditorState.createEmpty(),
  imageData: null,
});
export default function ThreadCreatePage() {
  //
  //
  // const initialPaper = {
  //   layoutType: 0,
  //   content1: "",
  //   content2: "",
  //   image1: { name: "", originName: "", url: "", thumbnailUrl: "", format: "", size: "" },
  //   image2: { name: "", originName: "", url: "", thumbnailUrl: "", format: "", size: "" },
  //   tagList: [],
  //   mentionIdList: []
  // };

  //
  //
  const [papers, setPapers] = useState([initialPaper()]);
  const [currentPage, setCurrentPage] = useState(0);

  const addPaper = () => {
    setPapers([...papers, initialPaper()]);
  };

  const removePaper = (pageIndex) => {
    const updatedPapers = papers.filter((_, index) => index !== pageIndex);
    setPapers(updatedPapers);

    // 페이지 삭제 후 현재 페이지 인덱스 업데이트
    if (currentPage === pageIndex || currentPage >= updatedPapers.length) {
      setCurrentPage(Math.max(0, currentPage));
    }
  };

  const changePage = (pageIndex) => {
    setCurrentPage(pageIndex);
  };

  const onEditorStateChange = (editorState) => {
    const updatedPapers = [...papers];
    updatedPapers[currentPage].editorState = editorState;
    setPapers(updatedPapers);
  };
  const onEditorStateChange2 = (editorState2) => {
    const updatedPapers = [...papers];
    updatedPapers[currentPage].editorState2 = editorState2;
    setPapers(updatedPapers);
  };
  //
  //

  // const saveContent = () => {
  //   // EditorState에서 RawDraftContentState를 추출
  //   const rawContentState = convertToRaw(editorState.getCurrentContent());
  //   const rawContentState2 = convertToRaw(editorState2.getCurrentContent());

  //   // RawDraftContentState를 HTML로 변환
  //   const htmlContent = draftToHtml(rawContentState);
  //   const htmlContent2 = draftToHtml(rawContentState2);

  //   console.log(htmlContent);
  //   console.log(htmlContent2);
  //   // 이제 htmlContent를 백엔드 API에 전송하여 저장
  //   // 예: axios.post('/api/saveContent', { content: htmlContent });
  // };

  const saveContent = () => {
    const contentData = papers.map((paper) =>
      draftToHtml(convertToRaw(paper.editorState.getCurrentContent()))
    );
    const contentData2 = papers.map((paper) =>
      draftToHtml(convertToRaw(paper.editorState2.getCurrentContent()))
    );
    console.log(contentData);
    console.log(contentData2);
    // 백엔드에 contentData 전송 로직
  };

  const htmlContent = `<p style="text-align:center;">안녕하세요 반갑습니다<br>저는<span style="font-size: 24px;"> 서만</span>기입니다</p>
   <p>👻    <span style="color: rgb(26,188,156);background-color: rgb(84,172,210);font-size: 24px;">😛</span></p>
  <p><a href="http://www.instagram.com/mangmangi_98" target="_blank"><span style="color: rgb(235,107,86);font-size: 24px;">서만</span><span style="font-size: 24px;">기인</span><span style="background-color: rgb(147,101,184);font-size: 24px;">스타</span></a><span style="font-size: 24px;"> </span></p>
   <p style="text-align:center;"></p>
   <p style="text-align:right;">우아하하하하</p>`;

  // <div dangerouslySetInnerHTML={{ __html: htmlContent }} />

  const [toolbarZIndex, setToolbarZIndex] = useState(1); // 초기 z-index 값 설정
  const [toolbarZIndex2, setToolbarZIndex2] = useState(1); // 초기 z-index 값 설정

  const [imageSrc, setImageSrc] = useState(null);
  const [imageStyle, setImageStyle] = useState({});
  const [editorStyle, setEditorStyle] = useState({});

  // const onEditorStateChange = (newEditorState) => {
  //   setEditorState(newEditorState);
  // };
  // const onEditorStateChange2 = (newEditorState) => {
  //   setEditorState2(newEditorState);
  // };
  const handleFocus1 = () => {
    setToolbarZIndex(4);
  };
  const handleFocus2 = () => {
    setToolbarZIndex2(4);
  };

  const handleBlur = () => {
    setToolbarZIndex(1);
    setToolbarZIndex2(1);
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
                {/* <div className={styles.버튼들}>
                  <button>1</button>
                  <button>+</button>
                </div>
                <div>
                  <button className={styles.페이지제거버튼}>페이지제거</button>
                </div> */}

                <div>
                  {papers.map((_, index) => (
                    <button key={index} onClick={() => changePage(index)}>
                      페이지 {index + 1}
                    </button>
                  ))}
                  <button onClick={addPaper}>+</button>
                  {papers.length > 1 && (
                    <button onClick={() => removePaper(currentPage)}>
                      현재 페이지 삭제
                    </button>
                  )}
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
                        editorState={papers[currentPage].editorState}
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
                        editorState={papers[currentPage].editorState2}
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
                  <span>책선택</span>
                  <span>쓰레드를 끼워넣을 책을 선택하세요</span>
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
