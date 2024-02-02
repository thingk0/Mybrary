import icon_comment from "../../assets/icon/icon_comment.png";
import styles from "./FeedContent.module.css";

export default function FeedContent({
  index,
  content,
  setComment,
  setCommentId,
}) {
  const openComment = (index) => {
    setComment(true);
    setCommentId(index);
  };
  return (
    <div className={styles.cont}>
      <div>{index}번 게시물이빈당</div>
      <img src={icon_comment} alt="" onClick={() => openComment(index)} />
    </div>
  );
}
