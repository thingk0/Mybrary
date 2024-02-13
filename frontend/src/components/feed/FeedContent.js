//하나의 쓰레드

import icon_comment from "../../assets/icon/icon_comment.png";
import icon_book from "../../assets/icon/icon_book.png";
import icon_like from "../../assets/icon/icon_like.png";
import icon_nolike from "../../assets/icon/icon_nolike.png";
import icon_scrap from "../../assets/icon/icon_scrap.png";
import icon_share from "../../assets/icon/icon_share.png";
import user_img from "../../assets/이미지예시.png";
import next from "../../assets/next.png";
import prev from "../../assets/prev.png";
import styles from "./FeedContent.module.css";
import ContentItem from "./ContentItem";
import { useState } from "react";
import s from "classnames";
import FeedModal from "./FeedModal";
import { like } from "../../api/paper/Paper";
import toast from "react-hot-toast";
import useUserStore from "../../store/useUserStore";
import { useNavigate } from "react-router-dom";
export default function FeedContent({
  thread,
  setComment,
  setList,
  setCommentId,
  setZIndex,
  setScrapModal,
}) {
  const navigate = useNavigate();
  const user = useUserStore((state) => state.user);
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
      const response = await like(paperId);
      console.log(response);
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
  return (
    <div className={styles.content}>
      {thread.paperList.map((paper, index) => (
        <div className={s(styles.aa, styles[`a${x}`])} key={index}>
          <div className={styles.user_info}>
            <div
              className={styles.user_profile}
              onClick={() => navigate(`/mybrary/${thread.memberId}`)}
            >
              <img
                src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${thread.profileUrl}`}
                alt=""
                className={styles.user_img}
              />
              <div className={styles.user_nickdate}>
                <div className={styles.user_nickname}>{thread.nickname}</div>
                <div className={styles.user_date}>
                  {formatDate(thread.threadCreatedAt)}
                </div>
              </div>
            </div>
            {user.memberId != thread.memberId && (
              <div
                onClick={() => navigate(`/mybrary/${thread.memberId}`)}
                className={styles.user_follow}
              >
                {thread.followed ? "마이브러리방문" : "팔로우하러가기"}
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
              <img
                src={icon_scrap}
                alt=""
                onClick={() => setScrapModal(true)}
              />
              <div>{paper.scrapCount}</div>
              <img
                src={icon_book}
                alt=""
                onClick={() => {
                  setIsModalOpen(true);
                }}
              />
              <FeedModal
                setIsModalOpen={setIsModalOpen}
                isModalOpen={isModalOpen}
                width="300px"
                height="300px"
                left="0"
                top="0"
                header="이 페이퍼를 포함한 책"
                paperId={paper.id}
              />
            </div>

            <img src={icon_share} alt="" className={styles.icon_right} />
          </div>
          <div className={styles.main_content}>
            {/* 레이아웃번호, 글1, 글2, 사진1, 사진2 */}
            <ContentItem paper={paper} />
          </div>
          <div className={styles.tag_hash}>#</div>
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
  );
}
