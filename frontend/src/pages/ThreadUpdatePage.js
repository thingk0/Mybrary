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
import useThreadStore from "../store/useThreadStore";
import { updateThread } from "../api/thread/Thread";
import useUserStore from "../store/useUserStore";
import { useNavigate } from "react-router-dom";

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
  const navigate = useNavigate();
  const [papers, setPapers] = useState([initialPaper()]);
  const [paperPublic, setPaperPublic] = useState(true);
  const [scrapEnable, setScrapEnable] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const thread = useThreadStore((state) => state.thread);
  const user = useUserStore((state) => state.user);

  const layouts = [
    1101, 1102, 1103, 1201, 1202, 1203, 1204, 1205, 1301, 1302, 1303, 1304,
    1401, 1402, 1501, 1502, 1503, 1504, 2111, 2112, 2141, 2411, 2151, 2511,
    2221, 2231, 2321, 2331, 2332, 2333, 2322, 2232, 2311, 2131, 2441, 2442,
    2551,
  ];

  useEffect(() => {
    console.log(thread);

    setPaperPublic(thread.paperPublic);
    setScrapEnable(thread.scrapEnable);

    const loadedPapers = thread.paperList.map((paper) => ({
      ...initialPaper(),
      editorState: htmlToEditorState(paper.content1),
      editorState2: htmlToEditorState(paper.content2),
      layoutType: paper.layoutType,
      tagList: paper.tagList,
      mentionList: paper.mentionList,
      imageUrl1: paper.imageUrl1,
      imageUrl2: paper.imageUrl2,
      paperId: paper.id,
    }));

    setPapers(loadedPapers);
  }, []);

  const saveContent = async () => {
    const paperList = papers.map((paper) => {
      return {
        paperId: paper.paperId,
        layoutType: paper.layoutType,
        content1: draftToHtml(
          convertToRaw(paper.editorState.getCurrentContent())
        ),
        content2: draftToHtml(
          convertToRaw(paper.editorState2.getCurrentContent())
        ),
        tagList: paper.tagList,
      };
    });

    const payload = {
      threadId: thread.threadId,
      paperList,
      paperPublic: paperPublic,
      scrapEnable: scrapEnable,
    };

    await updateThread(payload);
    navigate(`../mybrary/${user.memberId}/threads`);
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
          <div className={styles.title}>공개설정</div>
          <div className={styles.subtitle}>
            나만보기일 경우 스레드가 남에게 보여지지 않습니다.
          </div>
          <div className={styles.settingButtons}>
            <div
              className={paperPublic ? styles.select : styles.button}
              onClick={() => setPaperPublic(true)}
            >
              공개
            </div>

            <div
              className={!paperPublic ? styles.select : styles.button}
              onClick={() => {
                setPaperPublic(false);
                setScrapEnable(false);
              }}
            >
              나만보기
            </div>
          </div>

          <div className={styles.title}>스크랩허용</div>
          <div className={styles.subtitle}>
            나만보기일 경우 스크랩허용을 할 수 없습니다.
          </div>
          <div className={styles.settingButtons}>
            {paperPublic && (
              <div
                className={scrapEnable ? styles.select : styles.button}
                onClick={() => setScrapEnable(true)}
              >
                스크랩 허용
              </div>
            )}

            <div
              className={!scrapEnable ? styles.select : styles.button}
              onClick={() => setScrapEnable(false)}
            >
              스크랩 비허용
            </div>
          </div>

          <div className={styles.postButtons}>
            <div
              className={s(styles.postButton)}
              onClick={() => {
                saveContent();
              }}
            >
              게시
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
