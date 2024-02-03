import { useEffect, useState } from "react";
import styles from "./Comment.module.css";
import user_img from "../../assets/이미지예시.png";

export default function Comment({ commentId }) {
  const [commentList, setCommentList] = useState([]);
  const [addcomment, setAddcomment] = useState("");

  useEffect(() => {
    //   - 댓글ID(commentId)
    // - 작성자ID(ownerId)
    // - 작성자닉네임(ownerNickname)
    // - 작성자여부(isOwner)
    // - 댓글내용(comment)
    // - 컬러코드(colorCode)
    // - 시간(time)
    // - 대댓글리스트(recommentList)
    // [
    //   - 대댓글ID(recommentId)
    //   - 대댓글작성자ID(recommentOwnerId)
    //   - 대댓글작성자닉네임(recommentOwnerNickname)
    //   - 대댓글내용(recomment)
    //   - 대댓글시간(recommentTime)
    // ]
    setCommentList([
      {
        commentId: 1,
        ownerId: 1,
        ownerNickname: "manmangi_98",
        comment:
          "메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱메롱",
        colorCode: 1,
        time: "33:33:22",
        recommentList: [],
      },
      {
        commentId: 2,
        ownerId: 2,
        ownerNickname: "cwnsgh98",
        comment: "fdgdfgdfg",
        colorCode: 2,
        time: "33:33:22",
        recommentList: [],
      },
      {
        commentId: 3,
        ownerId: 3,
        ownerNickname: "thingko",
        isOwner: false,
        comment: "24234534656",
        colorCode: 3,
        time: "33:33:22",
        recommentList: [
          {
            recommentId: 1,
            recommentOwnerId: 2,
            recommentOwnerNickname: "cwnsgh98",
            recomment: "3895fddg",
            recommentTime: "44:44:22",
          },
        ],
      },
      {
        commentId: 3,
        ownerId: 3,
        ownerNickname: "thingko",
        isOwner: false,
        comment: "24234534656",
        colorCode: 3,
        time: "33:33:22",
        recommentList: [
          {
            recommentId: 1,
            recommentOwnerId: 2,
            recommentOwnerNickname: "cwnsgh98",
            recomment: "3895fddg",
            recommentTime: "44:44:22",
          },
        ],
      },
    ]);
  }, [setCommentList]);
  return (
    <>
      <div className={styles.comment_container}>
        {commentList.map((comment) => (
          <div className={styles.comment_item}>
            <div className={styles.comment_nickname}>
              {comment.ownerNickname}
            </div>
            <div className={styles.comment_text}>{comment.comment}</div>
            <div className={styles.comment_info}>
              <div className={styles.comment_left}>
                <div>{comment.time}</div> | <div>수정</div> | <div>삭제</div>
              </div>
              <div className={styles.comment_right}>
                <div>{comment.recommentList.length}</div> | <div>답글달기</div>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className={styles.comment_create}>
        <div className={styles.comment_create}>댓글을 입력하세여~</div>
        <div className={styles.comment_create}>
          <div className={styles.comment_create}>
            <img src={user_img} alt="" className={styles.user_img} />
            <div>{"mangmangi_98"}</div>
          </div>
          <div className={styles.comment_create}>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
          </div>
        </div>
        <div>
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
