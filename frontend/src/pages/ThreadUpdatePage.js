import React, { useState, useEffect } from "react";
import s from "classnames";
import styles from "./style/ThreadUpdatePage.module.css";
import { EditorState, ContentState, convertToRaw } from "draft-js";
import draftToHtml from "draftjs-to-html";
import htmlToDraft from "html-to-draftjs";
import Layout from "../components/threadupdate/Layout";
import Edit from "../components/threadupdate/Edit";
import Tag from "../components/threadupdate/Tag";
import Header from "../components/threadupdate/Header";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

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
  const [papers, setPapers] = useState([initialPaper()]);
  const [paperPublic, setPaperPublic] = useState(true);
  const [scarpEnable, setScarpEnable] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);

  const layouts = [
    1101, 1102, 1103, 1201, 1202, 1203, 1204, 1205, 1301, 1302, 1303, 1304,
    1401, 1402, 1501, 1502, 1503, 1504, 2111, 2112, 2141, 2411, 2151, 2511,
    2221, 2231, 2321, 2331, 2332, 2333, 2322, 2232, 2311, 2131, 2441, 2442,
    2551,
  ];

  const [bookId, setBookId] = useState(0); // 책 ID 상태 추가
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
          layoutType: 2333,
          tagList: ["태그1", "태그2"],
          mentionIdList: [101, 102],
        },
        {
          content1: "<p>두 번째 페이지의 첫 번째 텍스트 내용입니다.</p>",
          content2: "<p>두 번째 페이지의 두 번째 텍스트 내용입니다.</p>",
          image1: null,
          image2: null,
          layoutType: 2332,
          tagList: ["태그3", "태그4"],
          mentionIdList: [103, 104],
        },
        {
          content1: "<p>세 번째 페이지의 첫 번째 텍스트 내용입니다.</p>",
          content2: "<p>세 번째 페이지의 두 번째 텍스트 내용입니다.</p>",
          image1: null,
          image2: null,
          layoutType: 2331,
          tagList: ["태그5", "태그6"],
          mentionIdList: ["105", 106],
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
      layoutType: paper.layoutType,
      tagList: paper.tagList, // 태그 리스트 저장
      mentionIdList: paper.mentionIdList, // 멘션 리스트 저장
      // ... 기타 필드 설정
    }));

    setPapers(loadedPapers);
  }, []);

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
        layoutType: paper.layoutType,
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
  const [sectionVisible, setSectionVisible] = useState("left-center"); // 상태 변수 추가

  // "다음" 버튼 핸들러
  const handleNextClick = () => {
    setSectionVisible("center-right");
  };

  // ".미드오른쪽" 섹션 클릭 핸들러
  const handleRightSectionClick = () => {
    setSectionVisible("left-center");
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.header}>
          <Header
            papers={papers}
            setPapers={setPapers}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            initialPaper={initialPaper}
          />
        </div>
        <div
          className={s(
            styles.main,
            sectionVisible === "left-center"
              ? styles.showLeftCenter
              : styles.showCenterRight
          )}
        >
          <div className={styles.main_left}>
            <Layout
              papers={papers}
              setPapers={setPapers}
              currentPage={currentPage}
              layouts={layouts}
            >
              <button className={styles.nextButton} onClick={handleNextClick}>
                태그 선택하기&nbsp;&nbsp;&nbsp;{">"}
              </button>
            </Layout>
          </div>
          <div className={styles.main_center}>
            <Edit
              currentPage={currentPage}
              papers={papers}
              setPapers={setPapers}
            />
          </div>
          <div className={styles.main_right}>
            <Tag
              papers={papers}
              setPapers={setPapers}
              paperPublic={paperPublic}
              setPaperPublic={setPaperPublic}
              scarpEnable={scarpEnable}
              setScarpEnable={setScarpEnable}
              currentPage={currentPage}
            >
              <button
                className={styles.prevButton}
                onClick={handleRightSectionClick}
              >
                {"<"}&nbsp;&nbsp;&nbsp;레이아웃 선택하기
              </button>
            </Tag>
          </div>
        </div>
        <div className={styles.setting}>
          <div className={styles.title}>책선택</div>
          <div className={styles.subtitle}>
            쓰레드를 끼워넣을 책을 선택하세요
          </div>
          <div className={styles.settingButtons}>
            <button>선택되지않음</button>
          </div>

          <div className={styles.title}>공개설정</div>
          <div className={styles.subtitle}>
            나만보기일 경우 남에게 보여지지 않습니다
          </div>
          <div className={styles.settingButtons}>
            <button onClick={() => setPaperPublic(!paperPublic)}>
              {paperPublic ? "공개" : "나만보기"}
            </button>
          </div>

          <div className={styles.title}>스크랩허용</div>
          <div className={styles.subtitle}>
            나만보기일 경우 스크랩허용을 할 수 없습니다
          </div>
          <div className={styles.settingButtons}>
            <button onClick={() => setScarpEnable(!scarpEnable)}>
              {scarpEnable ? "스크랩 허용" : "스크랩 비허용"}
            </button>
          </div>

          <div className={styles.postButtons}>
            <button className={styles.postButton} onClick={() => saveContent()}>
              게시
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
