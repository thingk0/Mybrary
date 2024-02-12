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

export default function FeedContent({
  thread,
  setComment,
  setCommentId,
  setZIndex,
  setScrapModal,
}) {
  const [x, setX] = useState(1);
  const openComment = (id) => {
    setCommentId(id);
    console.log(id);
    setComment(true);
    setTimeout(() => {
      setZIndex(3);
    }, 800);
  };

  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div className={styles.content}>
      {thread.paperList.map((paper, index) => (
        <div className={s(styles.aa, styles[`a${x}`])} key={index}>
          <div className={styles.user_info}>
            <div className={styles.user_profile}>
              <img src={user_img} alt="" className={styles.user_img} />
              <div className={styles.user_nickdate}>
                <div className={styles.user_nickname}>{thread.memberName}</div>
                <div className={styles.user_date}>{thread.threadCreatedAt}</div>
              </div>
            </div>
            <div className={styles.user_follow}>
              {thread.followed ? "팔로잉" : "팔로우"}
            </div>
          </div>
          <div className={styles.icon_container}>
            <div className={styles.icon_left}>
              <img src={icon_nolike} alt="" />
              <div>{paper.likesCount}</div>
              <img
                src={icon_comment}
                alt=""
                onClick={() => openComment(paper.id)}
              />
              <div>{paper.commentCount}</div>
              <div>
                <img
                  src={icon_scrap}
                  alt=""
                  onClick={() => setScrapModal(true)}
                />
              </div>
              <div>{paper.scrapCount}</div>
              <div>
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
                />
              </div>
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
