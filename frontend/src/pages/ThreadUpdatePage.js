import React, { useState, useRef, useEffect } from "react";
import { EditorState, ContentState, convertToRaw } from "draft-js";
import draftToHtml from "draftjs-to-html";
import htmlToDraft from "html-to-draftjs";
import { Editor } from "react-draft-wysiwyg";
import CreateModal from "../components/common/CreateModal";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

import styles from "./style/ThreadCreatePage.module.css";
import Container from "../components/frame/Container";
// 레이아웃 이미지 및 기타 자산들을 여기에 import하세요

import 레이아웃1 from "../assets/레이아웃1.png";
import 레이아웃2 from "../assets/레이아웃2.png";
import 레이아웃3 from "../assets/레이아웃3.png";
import 레이아웃4 from "../assets/레이아웃4.png";
import 레이아웃5 from "../assets/진구형.jpg";
import 레이아웃6 from "../assets/혜선누나.jpg";

const initialPaper = () => ({
  layoutType: null,
  editorState: EditorState.createEmpty(),
  editorState2: EditorState.createEmpty(),
  content1: null,
  content2: null,
  image1: null,
  image2: null,
  tagList: [],
  mentionIdList: [],
});

const htmlToEditorState = (html) => {
  const contentBlock = htmlToDraft(html);
  if (contentBlock) {
    const contentState = ContentState.createFromBlockArray(
      contentBlock.contentBlocks
    );
    return EditorState.createWithContent(contentState);
  }
  return EditorState.createEmpty();
};

export default function ThreadUpdatePage() {
  const [paperPublic, setPaperPublic] = useState(true);
  const [scarpEnable, setScarpEnable] = useState(true);
  const [bookId, setBookId] = useState(0); // 책 ID 상태 추가
  const inputRef = useRef(null);
  const [papers, setPapers] = useState([initialPaper()]);
  const [currentPage, setCurrentPage] = useState(0);
  const [toolbarZIndex, setToolbarZIndex] = useState(1); // 초기 z-index 값 설정
  const [toolbarZIndex2, setToolbarZIndex2] = useState(1); // 초기 z-index 값 설정

  const [imageSrc, setImageSrc] = useState(null);
  const [imageStyle, setImageStyle] = useState({});
  const [editorStyle, setEditorStyle] = useState({});
  useEffect(() => {
    // 백엔드에서 데이터를 가져오는 로직을 구현합니다.
    // 예시를 위해 목 데이터를 사용합니다.
    const fetchedData = {
      bookId: 1,
      paperList: [
        {
          content1: "<p>첫 번째 페이지의 첫 번째 텍스트 내용입니다.</p>",
          content2: "<p>첫 번째 페이지의 두 번째 텍스트 내용입니다.</p>",
          image1: null,
          image2: null,
          layoutType: null,
          tagList: ["태그1", "태그2"],
          mentionIdList: [101, 102],
        },
        {
          content1: "<p>두 번째 페이지의 첫 번째 텍스트 내용입니다.</p>",
          content2: "<p>두 번째 페이지의 두 번째 텍스트 내용입니다.</p>",
          image1: null,
          image2: null,
          layoutType: null,
          tagList: ["태그3", "태그4"],
          mentionIdList: [103, 104],
        },
        {
          content1: "<p>세 번째 페이지의 첫 번째 텍스트 내용입니다.</p>",
          content2: "<p>세 번째 페이지의 두 번째 텍스트 내용입니다.</p>",
          image1: null,
          image2: null,
          layoutType: null,
          tagList: ["태그5", "태그6"],
          mentionIdList: [105, 106],
        },
      ],
      paperPublic: true,
      scarpEnable: true,
    };

    setBookId(fetchedData.bookId);
    setPaperPublic(fetchedData.paperPublic);
    setScarpEnable(fetchedData.scarpEnable);

    const loadedPapers = fetchedData.paperList.map((paper) => ({
      ...initialPaper(),
      editorState: htmlToEditorState(paper.content1),
      editorState2: htmlToEditorState(paper.content2),
      tagList: paper.tagList, // 태그 리스트 저장
      mentionIdList: paper.mentionIdList, // 멘션 리스트 저장
      // ... 기타 필드 설정
    }));

    setPapers(loadedPapers);
  }, []);

  // saveContent 함수
  const saveContent = () => {
    const paperList = papers.map((paper) => {
      return {
        layoutType: paper.layoutType,
        content1: draftToHtml(
          convertToRaw(paper.editorState.getCurrentContent())
        ),
        content2: draftToHtml(
          convertToRaw(paper.editorState2.getCurrentContent())
        ),
        image1: paper.image1,
        image2: paper.image2,
        tagList: paper.tagList,
        mentionIdList: paper.mentionIdList,
      };
    });

    const payload = {
      bookId,
      paperList,
      paperPublic,
      scarpEnable,
    };

    console.log(payload);
    // 백엔드에 payload 전송 로직
    // 예: axios.post('/api/savePaper', payload);
  };

  const addPaper = () => {
    setPapers([...papers, initialPaper()]);
  };

  const rayout = [
    레이아웃1,
    레이아웃2,
    레이아웃3,
    레이아웃4,
    레이아웃5,
    레이아웃6,
  ];
  const removePaper = (pageIndex) => {
    const updatedPapers = papers.filter((_, index) => index !== pageIndex);
    setPapers(updatedPapers);
    setCurrentPage(
      Math.max(0, currentPage - (currentPage >= pageIndex ? 1 : 0))
    );
  };
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

  const handleImageChange = (e, isImage1) => {
    const file = e.target.files[0];
    if (file && file.type.match("image.*")) {
      const reader = new FileReader();
      reader.onload = (readerEvent) => {
        const imageSrc = readerEvent.target.result;
        setPapers((prevPapers) => {
          const updatedPapers = [...prevPapers];
          const imageKey = isImage1 ? "image1" : "image2";
          updatedPapers[currentPage][imageKey] = {
            name: file.name,
            url: imageSrc,
          };
          return updatedPapers;
        });
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmitTag = (e) => {
    e.preventDefault();
    setPapers((prevPapers) => {
      const updatedPapers = [...prevPapers];
      updatedPapers[currentPage].tagList.push(addtext);
      return updatedPapers;
    });
    setAddtext("");
  };

  const handleSubmitFriendTag = (e) => {
    e.preventDefault();
    setPapers((prevPapers) => {
      const updatedPapers = [...prevPapers];
      updatedPapers[currentPage].mentionIdList.push(addfriend);
      return updatedPapers;
    });
    setAddfriend("");
  };
  const handleRemoveTag = (indexToRemove) => {
    setPapers((prevPapers) => {
      return prevPapers.map((paper, index) => {
        if (index === currentPage) {
          return {
            ...paper,
            tagList: paper.tagList.filter(
              (_, index) => index !== indexToRemove
            ),
          };
        }
        return paper;
      });
    });
  };

  const handleRemoveFriendTag = (indexToRemove) => {
    setPapers((prevPapers) => {
      return prevPapers.map((paper, index) => {
        if (index === currentPage) {
          return {
            ...paper,
            mentionIdList: paper.mentionIdList.filter(
              (_, index) => index !== indexToRemove
            ),
          };
        }
        return paper;
      });
    });
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

  const handleNextClick = () => {
    setSectionVisible("center-right");
  };

  // ".미드오른쪽" 섹션 클릭 핸들러
  const handleRightSectionClick = () => {
    setSectionVisible("left-center");
  };
  return (
    <>
      <Container>
        <div className={styles.메인}>
          <div className={styles.페이지}>
            <div className={styles.페이지사이즈조정}>
              <span className={styles.페이지작성}>페이지 작성</span>
              <div className={styles.페이지만들기헤더}>
                <div className={styles.헤더사이즈조정}>
                  <div className={styles.페이지버튼모음}>
                    {papers.map((_, index) => (
                      <button
                        className={`${styles.페이지버튼} ${
                          currentPage === index ? styles.현재페이지버튼 : ""
                        }`}
                        key={index}
                        onClick={() => changePage(index)}
                      >
                        페이지 {index + 1}
                      </button>
                    ))}
                    <button className={styles.새페이지버튼} onClick={addPaper}>
                      새페이지 +
                    </button>
                    {papers.length > 1 && (
                      <button
                        className={styles.페이지삭제버튼}
                        onClick={() => removePaper(currentPage)}
                      >
                        현재 페이지 삭제
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className={styles.페이지만들기}>
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
                          1 cut
                        </button>
                        <button onClick={() => handlePositionChange(600, 300)}>
                          2 cut
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
                    <button
                      className={styles.이미지업로드버튼}
                      onClick={() => inputRef.current.click()}
                    >
                      +
                    </button>
                    <input
                      ref={inputRef}
                      type="file"
                      accept="image/*"
                      capture="camera"
                      style={{ display: "none" }}
                      onChange={(e) => handleImageChange(e, true)}
                    />
                    {papers[currentPage].image1 && (
                      <img
                        className={styles.업로드이미지}
                        src={papers[currentPage].image1.url}
                        alt="이미지1"
                        style={papers[currentPage].imagesrc1}
                      />
                    )}
                  </div>
                </div>
              </div>
              <div className={styles.미드오른쪽}>
                <div className={styles.오른쪽크기조정}>
                  <div className={styles.태그추가부분}>
                    <span className={styles.태그span}>태그추가</span>
                    <form onSubmit={handleSubmitTag}>
                      {/* <form onSubmit={handleSubmit}> */}
                      <label htmlFor="add"></label>
                      <div className={styles.searchContainer}>
                        <input
                          type="text"
                          id="add"
                          placeholder="태그추가"
                          value={addtext}
                          className={styles.searchInput1}
                          onChange={(e) => setAddtext(e.target.value)}
                        />
                        <button type="submit" className={styles.searchButton}>
                          +
                        </button>
                      </div>
                    </form>
                    <div className={styles.추가된태그들}>
                      {papers[currentPage].tagList.map((tag, index) => (
                        <div className={styles.글자태그} key={index}>
                          <span># {tag}</span>
                          <span
                            className={styles.x버튼}
                            onClick={() => handleRemoveTag(index)}
                          >
                            x
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>
                  <div className={styles.친구태그부분}>
                    <span className={styles.태그span}>친구태그</span>
                    <form onSubmit={handleSubmitFriendTag}>
                      {/* <form onSubmit={handleSubmit}> */}
                      <label htmlFor="add2"></label>
                      <div className={styles.searchContainer}>
                        <input
                          type="text"
                          id="add2"
                          placeholder="태그추가"
                          value={addfriend}
                          className={styles.searchInput2}
                          onChange={(e) => setAddfriend(e.target.value)}
                        />
                        <button type="submit" className={styles.searchButton}>
                          +
                        </button>
                      </div>
                    </form>
                    <div className={styles.추가된태그들}>
                      {papers[currentPage].mentionIdList.map(
                        (mention, index) => (
                          <div className={styles.친구태그} key={index}>
                            <span>@ {mention}</span>
                            <span
                              className={styles.x버튼}
                              onClick={() => handleRemoveFriendTag(index)}
                            >
                              x
                            </span>
                          </div>
                        )
                      )}
                    </div>
                  </div>
                  <div className={styles.레아수정}>
                    <button
                      className={styles.레이아웃수정버튼}
                      onClick={handleRightSectionClick}
                    >
                      {/* 오른쪽 섹션 내용 */}레이아웃수정
                    </button>
                    <CreateModal width="500px" height="250px" title="모달 제목">
                      <div className={styles.페이지만들기푸터}>
                        <div className={styles.푸터}>
                          <div className={styles.푸터인포}>
                            <span>책선택</span>
                            <span>쓰레드를 끼워넣을 책을 선택하세요</span>
                          </div>
                          <button onClick={() => saveContent()}>
                            선택되지않음
                          </button>
                        </div>
                        <div className={styles.푸터}>
                          <div className={styles.푸터인포}>
                            <span>공개설정</span>
                            <span>
                              나만보기일 경우 남에게 보여지지 않습니다
                            </span>
                            <button
                              onClick={() => setPaperPublic(!paperPublic)}
                            >
                              {paperPublic ? "공개" : "비공개"}
                            </button>
                          </div>
                        </div>
                        <div className={styles.푸터}>
                          <div className={styles.푸터인포}>
                            <span>스크랩허용</span>
                            <span>
                              나만보기일 경우 스크랩허용을 할 수 없습니다
                            </span>
                            <button
                              onClick={() => setScarpEnable(!scarpEnable)}
                            >
                              {scarpEnable ? "스크랩 허용" : "스크랩 비허용"}
                            </button>
                          </div>
                        </div>
                      </div>
                    </CreateModal>
                  </div>
                </div>
              </div>
            </div>

            <div className={styles.게시}>
              <button className={styles.버튼개시} onClick={saveContent}>
                게시
              </button>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
