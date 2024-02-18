//하나의 쓰레드

import icon_comment from "../../assets/icon/icon_comment.png";
import icon_book from "../../assets/icon/icon_book.png";
import icon_like from "../../assets/icon/icon_like.png";
import icon_nolike from "../../assets/icon/icon_nolike.png";
import icon_scrap from "../../assets/icon/icon_scrap.png";
import next from "../../assets/next.png";
import prev from "../../assets/prev.png";
import styles from "./FeedContent.module.css";
import ContentItem from "./ContentItem";
import { useState } from "react";
import s from "classnames";
import { like } from "../../api/paper/Paper";
import toast from "react-hot-toast";
import useUserStore from "../../store/useUserStore";
import { useLocation, useNavigate } from "react-router-dom";
import { getPaperinBook } from "../../api/book/Book";
import useBookStore from "../../store/useBookStore";
import FeedModal2 from "./FeedModal2";
import useUrlStore from "../../store/useUrlStore";
import { deleteThread } from "../../api/thread/Thread";
import 곰탱이 from "../../assets/icon/Iconuser2.png";
import useThreadStore from "../../store/useThreadStore";
import BigModal from "../common/BigModal";

export default function FeedContent({
  thread,
  setComment,
  list,
  setList,
  setCommentId,
  setZIndex,
  handleOpenBookList,
  setThreadModal,
}) {
  const navigate = useNavigate();
  const user = useUserStore((state) => state.user);
  const setBook = useBookStore((state) => state.setBook2);
  const setThread = useThreadStore((state) => state.setThread);
  const [x, setX] = useState(1);
  const openComment = (id) => {
    setCommentId(id);
    setComment(true);
    setTimeout(() => {
      setZIndex(3);
    }, 800);
  };
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
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalOpen2, setIsModalOpen2] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [id, setId] = useState(0);
  const [booklist, setBooklist] = useState([]);
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return (
      date.getFullYear().toString().substring(2) +
      "년 " +
      ("0" + (date.getMonth() + 1)).slice(-2) +
      "월 " +
      ("0" + date.getDate()).slice(-2) +
      "일 " +
      ("0" + date.getHours()).slice(-2) +
      ":" +
      ("0" + date.getMinutes()).slice(-2)
    );
  };
  const toggleLike = async (paperId, liked) => {
    try {
      await like(paperId);
      if (!liked) {
        showToast("좋아요 !");
      } else {
        showToast("좋아요취소");
      }
      setList((currentList) =>
        currentList.map((thread) => ({
          ...thread,
          paperList: thread.paperList.map((paper) => {
            if (paper.id === paperId) {
              // liked 상태에 따라 likeCount 조정 및 liked 상태 토글
              const updatedLikeCount = paper.liked
                ? paper.likesCount - 1
                : paper.likesCount + 1;
              return {
                ...paper,
                likesCount: updatedLikeCount,
                liked: !paper.liked,
              };
            }
            return paper;
          }),
        }))
      );
    } catch (error) {
      console.error("좋아요갱신실패", error);
    }
  };

  const handelFeedModal = async (paperId) => {
    const response = await getPaperinBook(paperId);
    setBooklist(response.data);
  };

  //현재 URL저장
  const sampleLocation = useLocation();
  const setUrl = useUrlStore((state) => state.setUrl);
  const handelBookNavi = async (book) => {
    setUrl({ url: sampleLocation.pathname });
    await setBook(book);
    navigate(`/book/${book.bookId}`);
  };

  const handleDeleteThread = async (threadId) => {
    const updatedThreadList = list.filter(
      (thread) => thread.threadId !== threadId
    );
    await deleteThread(threadId);
    setList(updatedThreadList);
    setThreadModal(false);
  };
  return (
    <>
      <div className={styles.content}>
        {thread.paperList.map((paper, index) => (
          <div className={s(styles.aa, styles[`a${x}`])} key={index}>
            <div className={styles.user_info}>
              <div
                className={styles.user_profile}
                onClick={() => navigate(`/mybrary/${thread.memberId}`)}
              >
                {thread.profileUrl != null ? (
                  <div
                    className={styles.user_img}
                    style={{
                      background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${thread.profileUrl}")no-repeat center/cover`,
                    }}
                  ></div>
                ) : (
                  <img src={곰탱이} alt="" className={styles.user_img} />
                )}
                <div className={styles.user_nickdate}>
                  <div className={styles.user_nickname}>{thread.nickname}</div>
                  <div className={styles.user_date}>
                    {formatDate(thread.threadCreatedAt)}
                  </div>
                </div>
              </div>
              {user.memberId !== thread.memberId ? (
                <div
                  onClick={() => navigate(`/mybrary/${thread.memberId}`)}
                  className={styles.user_follow}
                >
                  {thread.followed ? "마이브러리방문" : "팔로우하러가기"}
                </div>
              ) : (
                <div>
                  <span
                    className={styles.수정글자}
                    onClick={async () => {
                      await setThread(thread);
                      navigate("/threadUpdate");
                    }}
                  >
                    수정
                  </span>{" "}
                  <span className={styles.중간바}> | </span>{" "}
                  <span
                    className={styles.삭제글자}
                    onClick={() => {
                      setDeleteModal(true);
                      setId(thread.threadId);
                    }}
                  >
                    삭제
                  </span>
                </div>
              )}
            </div>
            <div className={styles.icon_container}>
              <div className={styles.icon_left}>
                <img
                  src={paper.liked ? icon_like : icon_nolike}
                  alt=""
                  onClick={() => toggleLike(paper.id, paper.liked)}
                />
                <div>{paper.likesCount}</div>
                <img
                  src={icon_comment}
                  alt=""
                  onClick={() => openComment(paper.id)}
                />
                <div>{paper.commentCount}</div>
                {thread.scrapEnable && (
                  <>
                    <img
                      src={icon_scrap}
                      alt=""
                      onClick={() => {
                        handleOpenBookList(thread.paperList);
                      }}
                    />
                    <div>{paper.scrapCount}</div>
                  </>
                )}
                <img
                  src={icon_book}
                  alt=""
                  onClick={() => {
                    setIsModalOpen(true);
                    handelFeedModal(paper.id);
                  }}
                />
                <FeedModal2
                  setIsModalOpen={setIsModalOpen}
                  isModalOpen={isModalOpen}
                  width="300px"
                  left="-7.4vi"
                  top="1.2vi"
                  header="이 페이퍼를 포함한 작성자의 책"
                  paperId={paper.id}
                >
                  <div className={styles.책모음}>
                    {booklist.map((book) => (
                      <div
                        className={styles.책한권}
                        key={book.bookId}
                        onClick={() => handelBookNavi(book)}
                      >
                        <div>
                          <span className={styles.푸터}>
                            {book.coverImageUrl != null ? (
                              <div
                                className={styles.유저이미지}
                                style={{
                                  background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.coverImageUrl}")no-repeat center/cover`,
                                }}
                              ></div>
                            ) : (
                              <div
                                className={styles.유저이미지}
                                style={{
                                  background: `url("${곰탱이}")no-repeat center/cover`,
                                }}
                              ></div>
                            )}
                            {book.coverTitle}
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                </FeedModal2>
              </div>

              {/* <img src={icon_share} alt="" className={styles.icon_right} /> */}
            </div>
            <div className={styles.main_content}>
              {/* 레이아웃번호, 글1, 글2, 사진1, 사진2 */}
              <ContentItem paper={paper} />
            </div>
            {paper.tagList.length !== 0 && (
              <div
                onClick={() => setIsModalOpen2(true)}
                className={styles.tag_hash}
              >
                #
              </div>
            )}
            <FeedModal2
              setIsModalOpen={setIsModalOpen2}
              isModalOpen={isModalOpen2}
              width="17vw"
              left="15vw"
              bottom="4vw"
              header="태그"
              paperId={paper.id}
            >
              <div className={styles.태그모음}>
                {paper.tagList.map((tag) => (
                  <>
                    <span
                      onClick={() => navigate(`/search/${tag}`)}
                      className={styles.태그한줄}
                    >
                      # {tag}
                    </span>
                  </>
                ))}
              </div>
            </FeedModal2>
          </div>
        ))}
        <div className={styles.page}>
          {x} / {thread.paperList.length}
        </div>
        <div className={styles.navigate}>
          {thread.paperList.length > x && (
            <div
              onClick={() => {
                setX(x + 1);
                setComment(false);
                setZIndex(-1);
              }}
              className={styles.next}
            >
              <img src={next} alt="" className={styles.next_icon} />
            </div>
          )}
          {x !== 1 && (
            <div
              onClick={() => {
                setX(x - 1);
                setComment(false);
                setZIndex(-1);
              }}
              className={styles.prev}
            >
              <img src={prev} alt="" className={styles.prev_icon} />
            </div>
          )}
        </div>
      </div>
      <BigModal
        modalIsOpen={deleteModal}
        setModalIsOpen={setDeleteModal}
        width="400px"
        height="160px"
      >
        <div className={styles.deleteTitle}>스레드를 삭제 하시겠습니까?</div>
        <div className={styles.fff}>
          <div className={styles.can} onClick={() => setDeleteModal(false)}>
            취소
          </div>
          <div className={styles.del} onClick={() => handleDeleteThread(id)}>
            삭제
          </div>
        </div>
      </BigModal>
    </>
  );
}
