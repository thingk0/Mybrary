import { useEffect, useState } from "react";
import styles from "./Comment.module.css";
import user_img from "../../assets/이미지예시.png";
import { getCommentList, createComment } from "../../api/comment/Comment";
import useUserStore from "../../store/useUserStore";

//commentId라고 들어오지만 이거 페이퍼아이디임
export default function Comment({ commentId }) {
  const [commentList, setCommentList] = useState([]);
  const [addcomment, setAddcomment] = useState("");
  const user = useUserStore((state) => state.user);
  console.log(commentId);

  const [formData, setFormData] = useState({
    paperId: commentId,
    parentCommentId: null,
    content: "",
    colorCode: 0,
  });

  useEffect(() => {
    setFormData({
      paperId: commentId,
      parentCommentId: null, // 초기값으로 설정
      content: "", // 초기값으로 설정
      colorCode: 0, // 초기값으로 설정
    });
  }, [commentId]);

  const updateContent = (newContent) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      content: newContent,
    }));
  };
  const updateColorCode = (newColorCode) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      colorCode: newColorCode,
    }));
  };
  const updateParentCommentId = (newParentCommentId, pcolorcode) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      parentCommentId: newParentCommentId,
      content: "",
      colorCode: pcolorcode,
    }));
  };

  const check = () => {
    console.log(formData);
  };

  const create = async () => {
    console.log("되긴함 ?");
    try {
      const response = await createComment(formData); // 올바른 API 함수 이름으로 교체해주세요.
      console.log("댓글 생성 성공:", response);
      // 댓글 생성 후 필요한 로직 추가 (예: 댓글 목록 새로고침)
    } catch (error) {
      console.error("댓글 생성 실패:", error);
    }
  };

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
                    <div
                      onClick={() =>
                        updateParentCommentId(
                          comment.commentId,
                          comment.colorCode
                        )
                      }
                    >
                      답글달기
                    </div>
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
            <img
              src={`https://jingu.s3.ap-northeast-2.amazonaws.com/${user.profileImageUrl}`}
              alt=""
              className={styles.user_img}
            />
            <div className={styles.user_nickname}>{user.nickname}</div>
          </div>
          <div className={styles.comment_colors}>
            <div
              className={styles.color_1}
              onClick={() => updateColorCode(1)}
            ></div>
            <div
              className={styles.color_2}
              onClick={() => updateColorCode(2)}
            ></div>
            <div
              className={styles.color_3}
              onClick={() => updateColorCode(3)}
            ></div>
            <div
              className={styles.color_4}
              onClick={() => updateColorCode(4)}
            ></div>
          </div>
        </div>
        <div className={styles.comment_input}>
          <input
            type="text"
            id="add"
            placeholder="댓글입력"
            value={formData.content}
            onChange={(e) => updateContent(e.target.value)}
          />
          <button onClick={() => create()}>확인</button>
        </div>
      </div>
    </>
  );
}
