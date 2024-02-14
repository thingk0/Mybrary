import Container from "../components/frame/Container";
import React, { useState, useEffect } from "react";
import styles from "./style/ThreadsPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import 오른쪽 from "../assets/오른쪽.png";
import 왼쪽 from "../assets/왼쪽.png";
import Thread from "../components/common/Thread";
import { getDeskThread } from "../api/thread/Thread";
import { useNavigate, useParams } from "react-router-dom";
import useUserStore from "../store/useUserStore";
import useMybraryStore from "../store/useMybraryStore";
import BigModal from "../components/common/BigModal";
import OneThread from "../components/threads/OneThread";

export default function ThreadsPage() {
  const Params = useParams();
  const nowuser = Params.userid;
  const me = useUserStore((state) => state.user);
  const navigate = useNavigate();
  const [groupedData, setGroupedData] = useState(new Map());
  const [threadList, setThreadList] = useState([]);
  const [trueme, setTrueme] = useState(false);
  const mybrary = useMybraryStore((state) => state.mybrary);

  const [threadModal, setThreadModal] = useState(false);
  const [tId, setTId] = useState(0);

  useEffect(() => {
    async function fetchmyData() {
      try {
        const response = await getDeskThread(nowuser);
        setThreadList(response.data);

        const grouped = new Map();

        response.data.forEach((thread) => {
          const date = new Date(thread.threadCreatedAt);
          const yearMonth = `${date.getFullYear()}년 ${String(
            date.getMonth() + 1
          ).padStart(2, "0")}월`; // 월을 2자리 숫자로 표시

          if (!grouped.has(yearMonth)) {
            grouped.set(yearMonth, []);
          }

          grouped.get(yearMonth).push(thread);
        });

        setGroupedData(grouped);
        if (me.memberId == nowuser) {
          setTrueme(true);
        }
      } catch (error) {}
    }
    fetchmyData();
  }, []);

  return (
    <>
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate("../")}>
            &lt; 뒤로가기
          </div>
        </div>
        <div className={title.title}>
          <div
            className={title.left_title}
            onClick={() =>
              navigate(`../rollingpaper/${mybrary.rollingPaperId}`)
            }
          >
            &lt; 롤링페이퍼
          </div>
          <div className={title.main_title}>{mybrary.nickname}'s thread</div>
          <div
            className={title.right_title}
            onClick={() => navigate(`../${mybrary.bookShelfId}`)}
          >
            {" "}
            책장 &gt;
          </div>
        </div>

        <div className={styles.mainbook}>
          {/* 그룹화된 데이터 렌더링 */}
          {[...groupedData.keys()].map((yearMonth) => (
            <div key={yearMonth} className={styles.년도별}>
              <div className={styles.책}>
                <img className={styles.날짜막대기} src={왼쪽} alt="" />
                <span className={styles.fontsize2}>{yearMonth}</span>
                <img className={styles.날짜막대기} src={오른쪽} alt="" />
              </div>{" "}
              {/* 년-월 표시 */}
              <div className={styles.년도별스레드}>
                {groupedData.get(yearMonth).map((thread) => (
                  <Thread
                    thread={thread}
                    user={mybrary}
                    setThreadModal={setThreadModal}
                    setTId={setTId}
                  />
                ))}
              </div>
            </div>
          ))}
        </div>
      </Container>
      <BigModal
        modalIsOpen={threadModal}
        setModalIsOpen={setThreadModal}
        width="1300px"
        height="860px"
      >
        <OneThread
          threadId={tId}
          setThreadModal={setThreadModal}
          setThreadList={setThreadList}
          threadList={threadList}
        />
      </BigModal>
    </>
  );
}
