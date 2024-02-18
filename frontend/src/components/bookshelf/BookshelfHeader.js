import React, { useState, useEffect } from "react";
import title from "../atom/atomstyle/Title.module.css";
import { useNavigate, useParams } from "react-router-dom";
import { getMybrary } from "../../api/mybrary/Mybrary";
import useMybraryStore from "../../store/useMybraryStore";
//책장 페이지 헤더
export default function BookshelfHeader() {
  const navigate = useNavigate();
  const { userid } = useParams();
  const [user, setUser] = useState({});
  const mybrary = useMybraryStore((state) => state.mybrary);
  useEffect(() => {
    async function fetchMyData() {
      try {
        const response = await getMybrary(userid);
        setUser(response.data);
      } catch (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
      }
    }

    fetchMyData();
  }, [userid]);

  return (
    <div>
      <div>
        <div className={title.back} onClick={() => navigate("../")}>
          &lt; 뒤로가기
        </div>
      </div>
      <div className={title.title}>
        <div
          className={title.left_title}
          onClick={() => navigate("../threads")}
        >
          &lt; 게시물
        </div>
        <div className={title.main_title}>{user.nickname}'s bookshelf</div>
        <div
          className={title.right_title}
          onClick={() => navigate(`../rollingpaper/${mybrary.rollingPaperId}`)}
        >
          {" "}
          롤링페이퍼 &gt;
        </div>
      </div>
    </div>
  );
}
