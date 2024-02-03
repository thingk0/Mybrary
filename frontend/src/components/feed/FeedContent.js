import icon_comment from "../../assets/icon/icon_comment.png";
import icon_book from "../../assets/icon/icon_book.png";
import icon_like from "../../assets/icon/icon_like.png";
import icon_scrap from "../../assets/icon/icon_scrap.png";
import icon_share from "../../assets/icon/icon_share.png";
import user_img from "../../assets/이미지예시.png";
import styles from "./FeedContent.module.css";

export default function FeedContent({
  index,
  content,
  setComment,
  setCommentId,
  setZIndex,
}) {
  const openComment = (index) => {
    setComment(true);
    setCommentId(index);
    setTimeout(() => {
      setZIndex(3);
    }, 800);
  };
  return (
    <div className={styles.content}>
      <div className={styles.user_info}>
        <div className={styles.user_profile}>
          <img src={user_img} alt="" className={styles.user_img} />
          <div className={styles.user_nickdate}>
            <div className={styles.user_nickname}>manmangi_98_{index}</div>
            <div className={styles.user_date}>01.14 AM 03:06</div>
          </div>
        </div>
        <div className={styles.user_follow}>팔로우</div>
      </div>
      <div className={styles.icon_container}>
        <div className={styles.icon_left}>
          <img src={icon_like} alt="" />
          <div>33</div>
          <img src={icon_comment} alt="" onClick={() => openComment(index)} />
          <div>33</div>
          <img src={icon_scrap} alt="" />
          <div>33</div>
          <img src={icon_book} alt="" />
        </div>

        <img src={icon_share} alt="" className={styles.icon_right} />
      </div>
      <div className={styles.main_content}>콘텐츠영역</div>
      <div className={styles.tag_container}>
        <div className={styles.tag_user}>@</div>
        <div className={styles.tag_hash}>#</div>
      </div>
    </div>
  );
}
