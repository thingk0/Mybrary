import { useEffect, useState } from "react";
import {
  deleteFollow,
  getFollowingList,
  getMyFollowingList,
} from "../../api/member/Follow";
import styles from "./FollowList.module.css";
import Iconuser2 from "../../assets/icon/Iconuser2.png";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export default function FollowList({
  updateFollowingCount,
  setShowList,
  me,
  nowuser,
}) {
  const [followList, setFollowList] = useState([{}]);
  useEffect(() => {
    async function fetchFollowingData() {
      try {
        if (nowuser == me) {
          const response = await getMyFollowingList();
          setFollowList(response.data);
        } else {
          const response = await getFollowingList(nowuser);
          setFollowList(response.data);
        }
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchFollowingData();
  }, []);
  const handleDeleteFollow = async (memberId) => {
    try {
      const response2 = await deleteFollow(memberId);

      // 팔로우 삭제 성공 후 상태 업데이트
      if (me == nowuser) {
        const updatedFollowerList = followList.filter(
          (follow) => follow.memberId !== memberId
        );
        setFollowList(updatedFollowerList);
        updateFollowingCount(updatedFollowerList.length);
      } else {
        const response = await getFollowingList(nowuser);
        setFollowList(response.data);
      }
      toast.success("팔로잉삭제 되었습니다.", {
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
    } catch (error) {
      console.error("업데이트 실패:", error);
      toast.error("변경 실패: " + error.message);
    }
  };
  const navigate = useNavigate();

  return (
    <>
      <div className={styles.mainbox}>
        <div className={styles.header}>
          <span className={styles.팔로우목록글자}>팔로우 목록</span>
          <span className={styles.닫기버튼} onClick={() => setShowList(null)}>
            닫기
          </span>
        </div>
        <div className={styles.followbox}>
          {followList.map((follow) => (
            <div key={follow.memberId} className={styles.팔로우리스트}>
              <div className={styles.userimg}>
                <img
                  className={styles.img}
                  src={
                    `https://jingu.s3.ap-northeast-2.amazonaws.com/${follow.profileImageUrl}` ||
                    Iconuser2
                  }
                  alt="이미지없음"
                />
              </div>
              <div
                className={styles.이름정보들}
                onClick={() => navigate(`/mybrary/${follow.memberId}`)}
              >
                <span>{follow.nickname}</span>
                <span className={styles.사용자이름}>{follow.name}</span>
              </div>
              {follow.memberId != me && (
                <div className={styles.팔로잉박스}>
                  <span
                    onClick={() => handleDeleteFollow(follow.memberId)}
                    className={styles.팔로잉글자}
                  >
                    팔로잉
                  </span>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
