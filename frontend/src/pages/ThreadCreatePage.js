import React, { useEffect, useState } from "react";
import s from "classnames";
import styles from "./style/ThreadCreatePage.module.css";
import Layout from "../components/threadcreate/Layout";
import Edit from "../components/threadcreate/Edit";
import Tag from "../components/threadcreate/Tag";
import Header from "../components/threadcreate/Header";
import { EditorState, convertToRaw } from "draft-js";
import draftToHtml from "draftjs-to-html";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import BigModal from "../components/common/BigModal";
import BookCreate from "../components/common/BookCreate";
import { getMYBooks } from "../api/book/Book";
import BookSelect from "../components/threadcreate/BookSelect";
import { uplodaImage } from "../api/image/Image";
import toast from "react-hot-toast";
import { createThread } from "../api/thread/Thread";
import { useNavigate } from "react-router-dom";
import LoadModal from "../components/common/LoadModal";
import LottieAnimation from "../components/common/LottieAnimation";
import animationData from "../assets/lottie/loading.json";
const initialPaper = () => ({
  layoutType: 1101,
  editorState: EditorState.createEmpty(),
  editorState2: EditorState.createEmpty(),
  content1: null,
  content2: null,
  image1: null,
  image2: null,
  tagList: [],
  mentionList: [],
});
export default function ThreadCreatePage() {
  const navigate = useNavigate();
  const [papers, setPapers] = useState([initialPaper()]);
  const [paperPublic, setPaperPublic] = useState(true);
  const [scrapEnable, setScrapEnable] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [modalIsOpen2, setModalIsOpen2] = useState(false);
  const [postPossible, setPostPossible] = useState(false);
  const [Loading, setLoading] = useState(false);

  const layouts = [
    1101, 1102, 1103, 1201, 1202, 1203, 1204, 1205, 1301, 1302, 1303, 1304,
    1401, 1402, 1501, 1502, 1503, 1504, 2111, 2112, 2141, 2411, 2151, 2511,
    2221, 2231, 2321, 2331, 2332, 2333, 2322, 2232, 2311, 2131, 2441, 2442,
    2551,
  ];
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

  const [booklist, setBookList] = useState([]);
  const [book, setBook] = useState({}); // 책선택
  const [bookId, setBookId] = useState(null); // 책 ID 상태 추가
  const saveContent = async () => {
    setLoading(true);
    let a = 0;
    const formData = new FormData();

    for (let paper of papers) {
      if (Math.floor(paper.layoutType / 1000) === 1) {
        formData.append("images", paper.image1);
      } else if (Math.floor(paper.layoutType / 1000) === 2) {
        formData.append("images", paper.image1);
        formData.append("images", paper.image2);
      }
    }
    const coverImageId = await uplodaImage(formData);

    const postPaperDto = papers.map((paper) => {
      return {
        layoutType: paper.layoutType,
        content1: draftToHtml(
          convertToRaw(paper.editorState.getCurrentContent())
        ),
        content2: draftToHtml(
          convertToRaw(paper.editorState2.getCurrentContent())
        ),
        imageId1: coverImageId.imageIds[a++],
        imageId2:
          Math.floor(paper.layoutType / 1000) === 1
            ? null
            : coverImageId.imageIds[a++],
        tagList: paper.tagList,
        mentionList: paper.mentionList,
      };
    });

    const Thread = {
      bookId: bookId,
      postPaperDto,
      paperPublic: paperPublic,
      scrapEnable: scrapEnable,
    };

    const threadId = await createThread(Thread);
    showToast("게시글을 생성했습니다 !");
    navigate(`../feed`);
  };
  const noneImg = () => {
    toast.error("이미지를 전부 채워주세요", {
      position: "top-center",
    });
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

  const handleOpenBookList = async () => {
    const booklists = await getMYBooks();
    setBookList(booklists.data);
    setModalIsOpen2(true);
  };

  useEffect(() => {
    if (!modalIsOpen2 && bookId === -1) {
      setBook({});
    }
  }, [setBook, modalIsOpen2, bookId]);

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
            setBookId={setBookId}
            bookId={bookId}
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
              setPostPossible={setPostPossible}
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
          <div className={styles.title}>책선택</div>
          <div className={styles.subtitle}>스레드를 담을 책을 선택하세요.</div>
          <div className={styles.settingButtons}>
            <button
              onClick={() => handleOpenBookList()}
              className={styles.책선택버튼}
            >
              {bookId !== null ? book.title : "선택되지않음"}
            </button>
          </div>

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
                postPossible ? saveContent() : noneImg();
                // setModalIsOpen(true);
              }}
            >
              게시
            </div>
          </div>
        </div>
      </div>
      <BigModal
        modalIsOpen={modalIsOpen2}
        setModalIsOpen={setModalIsOpen2}
        width="800px"
        height="600px"
      >
        <BookSelect
          setModalIsOpen={setModalIsOpen}
          setModalIsOpen2={setModalIsOpen2}
          papers={papers}
          booklist={booklist}
          setBook={setBook}
          book={book}
          setBookId={setBookId}
        />
      </BigModal>
      <BigModal
        modalIsOpen={modalIsOpen}
        setModalIsOpen={setModalIsOpen}
        width="1200px"
        height="800px"
        background="var(--main4)"
      >
        <BookCreate
          setBookList={setBookList}
          booklist={booklist}
          setModalIsOpen={setModalIsOpen}
        />
      </BigModal>
      <LoadModal
        modalIsOpen={Loading}
        setModalIsOpen={setLoading}
        width="400px"
        height="500px"
      >
        <div className={styles.loadTitle}>업로드 중</div>
        <div className={styles.loadSub}>
          이미지 용량이 크면 오래걸릴 수 있습니다.
        </div>
        <LottieAnimation
          animationPath={animationData}
          className={styles.loadImg}
        />
        <div className={styles.loadSub2}>중간에 닫거나 이동하지 마세요!!!</div>
      </LoadModal>
    </>
  );
}
