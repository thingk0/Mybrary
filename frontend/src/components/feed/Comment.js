import { useEffect, useState } from "react";
import styles from "./Comment.module.css";
import s from "classnames";
import {
  getCommentList,
  createComment,
  getCommentbabyList,
  deleteComment,
} from "../../api/comment/Comment";
import useUserStore from "../../store/useUserStore";

//commentId라고 들어오지만 이거 페이퍼아이디임
export default function Comment({
  commentId,
  updateCommentCount,
  updateCommentCount2,
}) {
  const [commentList, setCommentList] = useState([]);
  const [childComments, setChildComments] = useState({}); // 대댓글 목록 상태
  const user = useUserStore((state) => state.user);
  console.log(commentId);

  const [formData, setFormData] = useState({
    paperId: commentId,
    parentCommentId: null,
    content: "",
    colorCode: 0,
  });
  const [selectedCommentId, setSelectedCommentId] = useState(null);

  const selectCommentForReply = (commentId, colorCode) => {
    setSelectedCommentId(commentId);
    updateParentCommentId(commentId, colorCode);
  };

  const cancelReply = () => {
    setSelectedCommentId(null);
    setFormData((prevFormData) => ({
      ...prevFormData,
      parentCommentId: null,
      content: "",
    }));
  };

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

  const check = async (parentId) => {
    // 이미 대댓글 목록이 로딩된 경우, 해당 대댓글 목록을 숨김 처리 (토글 기능)
    if (childComments[parentId]) {
      setChildComments((prev) => {
        const newState = { ...prev };
        delete newState[parentId]; // 해당 댓글 ID의 대댓글 목록을 삭제하여 숨김 처리
        return newState;
      });
    } else {
      // 대댓글 목록이 없는 경우, API 호출을 통해 대댓글 목록을 가져옴
      try {
        const response = await getCommentbabyList(parentId);
        console.log(response);
        setChildComments((prev) => ({
          ...prev,
          [parentId]: response.data.commentGetDtoList,
        }));
      } catch (error) {
        console.error("대댓글 불러오기 실패", error);
      }
    }
  };
  const deletecomment = async (commentIdToDelete) => {
    try {
      await deleteComment(commentIdToDelete); // 댓글 ID를 사용하여 댓글 삭제
      console.log("댓글 삭제 성공");
      const response = await getCommentList(commentId); // 페이퍼 ID를 사용하여 댓글 목록 재조회
      setCommentList(response.data.commentGetDtoList); // 새로운 댓글 목록으로 상태 업데이트
      updateCommentCount2(commentId, response.data.commentGetDtoList.length); // 새로운 댓글 수로 업데이트
    } catch (error) {
      console.error("댓글 삭제 실패:", error);
    }
  };
  const create = async () => {
    console.log("되긴함 ?");
    if (formData.parentCommentId == null) {
      try {
        console.log("댓글", formData);
        const response = await createComment(formData); // 올바른 API 함수 이름으로 교체해주세요.
        console.log("댓글 생성 성공:", response);
        const response2 = await getCommentList(commentId);
        updateCommentCount(commentId);
        // console.log(commentId);
        // console.log(response2.data);
        if (commentId != 0) {
          setCommentList(response2.data.commentGetDtoList);
        }
        //const newComment = response.data; // response.data가 새로 생성된 댓글 데이터를 포함하고 있다고 가정
        // setCommentList((prevCommentList) => [...prevCommentList, newComment]);
        setFormData({
          ...formData,
          content: "",
          parentCommentId: null,
          colorCode: 0,
        });
        setSelectedCommentId(null);
        // 댓글 생성 후 필요한 로직 추가 (예: 댓글 목록 새로고침)
      } catch (error) {
        console.error("댓글 생성 실패:", error);
      }
    } else if (formData.parentCommentId != null) {
      try {
        console.log("대댓글", formData);
        const response = await createComment(formData);
        console.log("대댓글 생성 성공", response);

        // 대댓글 목록 상태 업데이트
        setChildComments((prev) => {
          // 기존에 로딩된 대댓글 목록이 있는 경우, 새 대댓글을 추가
          const existingChildComments = prev[formData.parentCommentId] || [];
          return {
            ...prev,
            [formData.parentCommentId]: [
              ...existingChildComments,
              response.data,
            ],
          };
        });
        setSelectedCommentId(null);
        if (formData.parentCommentId) {
          setCommentList((currentList) =>
            currentList.map((comment) =>
              comment.commentId === formData.parentCommentId
                ? {
                    ...comment,
                    childCommentCount: comment.childCommentCount + 1,
                  }
                : comment
            )
          );
        }

        // 댓글 수 업데이트 로직 (이 부분은 상황에 따라 다를 수 있음)
        updateCommentCount(commentId);

        // 폼 초기화
        setFormData({
          ...formData,
          content: "",
          parentCommentId: null,
          colorCode: 0,
        });
      } catch (error) {
        console.log("대댓글 생성 실패", error);
      }
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
              <>
                <div
                  className={s(
                    styles.comment_item,
                    styles[`color_${comment.colorCode}`], // 수정된 부분
                    {
                      [styles.selected]:
                        comment.commentId === selectedCommentId,
                    }
                  )}
                >
                  <div className={styles.comment_nickname}>
                    {comment.ownerNickname}
                  </div>
                  <div className={styles.comment_text}>{comment.content}</div>
                  <div className={styles.comment_info}>
                    <div className={styles.comment_left}>
                      {user.memberId === comment.ownerId && (
                        <>
                          <div
                            onClick={() => deletecomment(comment.commentId)}
                            className={styles.삭제글자}
                          >
                            삭제
                          </div>
                        </>
                      )}
                    </div>
                    <div className={styles.comment_right}>
                      <div
                        className={styles.대댓글수}
                        onClick={() => check(comment.commentId)}
                      >
                        {comment.childCommentCount}
                      </div>
                      <span className={styles.중간막대기}> | </span>
                      <div
                        className={styles.답글달기글자}
                        onClick={() => {
                          updateParentCommentId(
                            comment.commentId,
                            comment.colorCode
                          );
                          selectCommentForReply(
                            comment.commentId,
                            comment.colorCode
                          );
                        }}
                      >
                        답글달기
                      </div>
                    </div>
                  </div>
                </div>
                {childComments[comment.commentId] && (
                  <div className={styles.대댓글박스}>
                    {childComments[comment.commentId].map((childComment) => (
                      <div
                        className={s(
                          styles.comment_item2,
                          styles[`color_${childComment.colorCode}`] // 수정된 부분
                        )}
                        key={childComment.commentId}
                      >
                        <div className={styles.comment_nickname}>
                          {childComment.ownerNickname}
                        </div>
                        <div className={styles.comment_text}>
                          {childComment.content}
                        </div>
                        <div className={styles.comment_info}>
                          <div className={styles.comment_left}>
                            {user.memberId === childComment.ownerId && (
                              <>
                                <div
                                  onClick={() =>
                                    deletecomment(childComment.commentId)
                                  }
                                  className={styles.삭제글자}
                                >
                                  삭제
                                </div>
                              </>
                            )}
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </>
            ))}
          </>
        ) : (
          <div className={styles.comment_none}>
            <div>게시물에 댓글이 하나도 없습니다.</div>
          </div>
        )}
      </div>
      <div
        className={s(
          styles.comment_create,
          styles[`color_${formData.colorCode}`]
        )}
      >
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
            {formData.parentCommentId == null && (
              <>
                <div
                  className={styles.color_0}
                  onClick={() => updateColorCode(0)}
                ></div>
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
              </>
            )}
            {selectedCommentId && (
              <button
                className={styles.cancelReplyButton}
                onClick={cancelReply}
              >
                답글 달기 취소
              </button>
            )}
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
          <button className={styles.버튼} onClick={() => create()}>
            입력
          </button>
        </div>
      </div>
    </>
  );
}
