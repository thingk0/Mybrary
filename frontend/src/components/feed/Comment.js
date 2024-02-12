import { useEffect, useState } from "react";
import styles from "./Comment.module.css";
import user_img from "../../assets/이미지예시.png";
import { getCommentList, createCommnet } from "../../api/comment/Comment";

export default function Comment({ commentId }) {
  const [commentList, setCommentList] = useState([]);
  const [addcomment, setAddcomment] = useState("");

  console.log(commentId);
  const [formData, setFormData] = useState({
    paperId: 0,
    parentCommentId: 0,
    content: "string",
    colorCode: 0,
  });

  useEffect(() => {
    async function fetchCommentData() {
      try {
        const response = await getCommentList(commentId);
        console.log(commentId);
        console.log(response.data);
        if (commentId != 0) {
          setCommentList(response.data.commentGetDtoList);
        }
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchCommentData();
  }, [commentId]);
  return (
    <>
      <div className={styles.comment_container}>
        {commentList.length !== 0 ? (
          <>
            {commentList.map((comment) => (
              <div className={styles.comment_item}>
                <div className={styles.comment_nickname}>
                  {comment.ownerNickname}
                </div>
                <div className={styles.comment_text}>{comment.content}</div>
                <div className={styles.comment_info}>
                  <div className={styles.comment_left}>
                    <div>{comment.time}</div> | <div>수정</div> |{" "}
                    <div>삭제</div>
                  </div>
                  <div className={styles.comment_right}>
                    {/* <div>{comment.recommentList.length}</div> |{" "} */}
                    <div>답글달기</div>
                  </div>
                </div>
              </div>
            ))}
          </>
        ) : (
          <div className={styles.comment_none}>
            <div>게시물에 댓글이 하나도 없습니다.</div>
          </div>
        )}
      </div>
      <div className={styles.comment_create}>
        <div className={styles.comment_create_header}>
          <div className={styles.comment_create_left}>
            <img src={user_img} alt="" className={styles.user_img} />
            <div className={styles.user_nickname}>{"mangmangi_98"}</div>
          </div>
          <div className={styles.comment_colors}>
            <div className={styles.color_1}></div>
            <div className={styles.color_2}></div>
            <div className={styles.color_3}></div>
            <div className={styles.color_4}></div>
          </div>
        </div>
        <div className={styles.comment_input}>
          <input
            type="text"
            id="add"
            placeholder="댓글입력"
            value={addcomment}
            onChange={(e) => setAddcomment(e.target.value)}
          />
        </div>
      </div>
    </>
  );
}
