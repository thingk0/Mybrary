import React, { useState } from "react";
import s from "classnames";
import styles from "./style/ThreadCreatePage.module.css";
import Layout from "../components/threadcreate/Layout";
import Edit from "../components/threadcreate/Edit";
import { EditorState, convertToRaw } from "draft-js";
import draftToHtml from "draftjs-to-html";
import Tag from "../components/threadcreate/Tag";
import Header from "../components/threadcreate/Header";

const initialPaper = () => ({
  layoutType: 1101,
  editorState: EditorState.createEmpty(),
  editorState2: EditorState.createEmpty(),
  content1: null,
  content2: null,
  image1: null,
  image2: null,
  tagList: [],
  mentionIdList: [],
});
export default function ThreadCreatePage() {
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
