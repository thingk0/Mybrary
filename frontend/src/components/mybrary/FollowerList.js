import { useEffect, useState } from "react";
import {
  getMyFollowerList,
  getFollowerList,
  getMyFollowingList,
  getFollowingList,
  follow,
  deleteFollower,
} from "../../api/member/Follow";
import styles from "./FollowerList.module.css";
import Iconuser2 from "../../assets/icon/Iconuser2.png";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
export default function FollowerList({
  updateFollowerCount,
  updateFollowingCount,
  setShowList,
  me,
  nowuser,
}) {
  const [followerList, setFollowerList] = useState([{}]);
  const [followList, setFollowList] = useState([{}]);
  useEffect(() => {
    async function fetchFollowerData() {
      try {
        if (nowuser == me) {
          const response = await getMyFollowerList();
          console.log(response.data);
          setFollowerList(response.data);
          console.log("내 팔로워리스트");
        } else {
          const response = await getFollowerList(nowuser);
          console.log(response.data);
          setFollowerList(response.data);
          console.log("상대방의팔로워리스트");
        }
      } catch (error) {
        console.log("데이터를 가져오는데실패함");
      }
    }
    fetchFollowerData();
  }, []);
  useEffect(() => {
    async function fetchFollowingData() {
      try {
        if (nowuser == me) {
          const response = await getMyFollowingList();
          console.log(response.data);
          console.log("내 팔로우리스트");
          setFollowList(response.data);
        } else {
          const response = await getFollowingList(nowuser);
          console.log(response.data);
          setFollowList(response.data);
          console.log("상대방의팔로우리스트");
        }
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }
    fetchFollowingData();
  }, []);
  const handleFollow = async (memberId) => {
    try {
      await follow(memberId);
      if (me == nowuser) {
        const response = await getMyFollowerList();
        console.log(response.data);
        setFollowerList(response.data);
        updateFollowingCount(followList.length + 1);
      } else {
        const response = await getFollowerList(nowuser);
        console.log(response.data);
        setFollowerList(response.data);
      }

      toast.success("팔로우 완료 되었습니다.", {
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
  const handleDeleteFollower = async (memberId) => {
    try {
      const response = await deleteFollower(memberId);
      // 팔로우 삭제 성공 후 상태 업데이트
      if (me == nowuser) {
        const updatedFollowerList = followerList.filter(
          (follower) => follower.memberId !== memberId
        );
        setFollowerList(updatedFollowerList);
        updateFollowerCount(updatedFollowerList.length);
      } else {
        const response = await getFollowerList(nowuser);
        console.log(response.data);
        setFollowerList(response.data);
      }
      toast.success("팔로우삭제 되었습니다.", {
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
          <span className={styles.팔로우목록글자}>팔로워 목록</span>
          <span className={styles.닫기버튼} onClick={() => setShowList(null)}>
            닫기
          </span>
        </div>
        <div className={styles.followbox}>
          {followerList.map((follow) => (
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
              <div className={styles.이름정보들}>
                <div
                  className={styles.이름들}
                  onClick={() => navigate(`/mybrary/${follow.memberId}`)}
                >
                  <span>{follow.nickname}</span>
                  <span className={styles.사용자이름}>{follow.name}</span>
                </div>
                {follow.memberId != me &&
                  (follow.followed === false ? (
                    <span
                      onClick={() => handleFollow(follow.memberId)}
                      className={styles.팔로우글자}
                    >
                      팔로우
                    </span>
                  ) : null)}
              </div>
              {me == nowuser && follow.memberId != me && (
                <div className={styles.팔로잉박스}>
                  <span
                    onClick={() => handleDeleteFollower(follow.memberId)}
                    className={styles.삭제글자}
                  >
                    삭제
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
