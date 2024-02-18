import Container from "../components/frame/Container";
import styles from "./style/SearchResultPage.module.css";
import { useNavigate, useParams } from "react-router-dom";
import searchicon from "../assets/searchicon.png";
import React, { useState, useEffect } from "react";
import Thread from "../components/common/Thread2";
import { keyword, searchContents } from "../api/search/Search";
import BigModal from "../components/common/BigModal";
import OneThread from "../components/threads/OneThread";
export default function SearchResultPage() {
  const navigate = useNavigate();
  const Params = useParams();
  const [searchtext, setSearchtext] = useState(Params.word);
  const [animateOut, setAnimateOut] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const [threadModal, setThreadModal] = useState(false);
  const [tId, setTId] = useState(0);
  const [threadList, setThreadList] = useState([]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (searchtext.trim()) {
      setRecentSearches((prevSearches) => {
        const updatedSearches = [...prevSearches];
        if (updatedSearches.includes(searchtext)) {
          updatedSearches.splice(updatedSearches.indexOf(searchtext), 1);
        }
        updatedSearches.unshift(searchtext);
        const newSearches = updatedSearches.slice(0, 5);
        localStorage.setItem("recentSearches", JSON.stringify(newSearches));
        return newSearches;
      });
    }
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}`);
    }, 200);
    setAnimateOut(false);
  };

  const handleRecentSearchClick = (search) => {
    setAnimateOut(true);
    if (search.trim()) {
      setRecentSearches((prevSearches) => {
        const updatedSearches = [...prevSearches];
        if (updatedSearches.includes(search)) {
          updatedSearches.splice(updatedSearches.indexOf(search), 1);
        }
        updatedSearches.unshift(search); // 클릭된 단어를 앞에 추가
        const newSearches = updatedSearches.slice(0, 5); // 최대 5개의 검색어만 유지

        // localStorage에 저장
        localStorage.setItem("recentSearches", JSON.stringify(newSearches));

        return newSearches;
      });
    }
    setTimeout(() => {
      setSearchtext(search);
      setAnimateOut(false);
      navigate(`/search/${search}`); // 페이지 전환
    }, 200);
  };

  const [list, setList] = useState([]);
  useEffect(() => {
    async function fetchData() {
      try {
        if (searchtext.trim !== "") {
          const response = await keyword(searchtext);
          setList(response.data);
        }
      } catch (error) {}
    }
    fetchData();
  }, [searchtext]);
  useEffect(() => {
    async function fetchThreadData() {
      try {
        if (searchtext.trim !== "") {
          const response = await searchContents(searchtext);
          setThreadList(response.data.content);
        }
      } catch (error) {}
    }
    fetchThreadData();
  }, [searchtext]);
  useEffect(() => {
    const savedSearches = JSON.parse(localStorage.getItem("recentSearches"));
    if (savedSearches) {
      setRecentSearches(savedSearches);
    }
  }, []);

  const handle0 = (e) => {
    e.preventDefault();
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}`);
    }, 200);
  };
  const handle1 = (e) => {
    e.preventDefault();
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/1/${searchtext}`);
    }, 200);
  };
  const handle2 = (e) => {
    e.preventDefault();
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/2/${searchtext}`);
    }, 200);
  };

  return (
    <>
      <Container>
        <div className={styles.main}>
          <div className={styles.header}>
            <span className={styles.검색글자}>검색</span>
            <div className={styles.relative}>
              <>
                <form onSubmit={handleSubmit}>
                  <label htmlFor="search"></label>
                  <div className={styles.searchContainer}>
                    <button type="submit" className={styles.searchButton}>
                      <img
                        className={styles.searchicon}
                        src={searchicon}
                        alt=""
                      />
                    </button>
                    <input
                      type="text"
                      id="search"
                      placeholder={Params.word}
                      value={searchtext}
                      className={styles.searchInput}
                      onChange={(e) => setSearchtext(e.target.value)}
                    />
                  </div>
                </form>
                {list.length !== 0 && (
                  <div className={styles.absolute}>
                    <div className={styles.title}>추천검색어</div>
                    {list?.map((key) => (
                      <>
                        <div
                          className={styles.key}
                          onClick={() => handleRecentSearchClick(key)}
                        >
                          {key}
                        </div>
                        <hr className={styles.hr}></hr>
                      </>
                    ))}
                  </div>
                )}
              </>
            </div>
            <div className={styles.최근검색어}>
              <span>최근검색어</span>
              <div>
                <div className={styles.최근검색어박스}>
                  {recentSearches.map((search, index) => (
                    <div
                      key={index}
                      className={styles.box}
                      onClick={() => handleRecentSearchClick(search)}
                    >
                      {search}
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
          <div className={styles.middle}>
            <div
              className={`${styles.middlemain} ${
                animateOut ? styles.fadeOut : styles.fadeIn
              }`}
            >
              <div className={styles.mid검색결과}>
                <span>'{searchtext}' 스레드 검색결과</span>
              </div>
              <div className={styles.mid버튼}>
                <button onClick={handle0}>스레드</button>
                <button onClick={handle1}>책</button>
                <button onClick={handle2}>계정</button>
              </div>
              {threadList.length === 0 && (
                <div className={styles.noneKeyword}>
                  {searchtext}의 스레드 검색결과가 없습니다!
                </div>
              )}
              <div className={styles.오버플로우확인}>
                <div className={styles.게시글들어갈공간}>
                  {threadList.map((thread) => (
                    <Thread
                      thread={thread}
                      setThreadModal={setThreadModal}
                      setTId={setTId}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </Container>
      <BigModal
        modalIsOpen={threadModal}
        setModalIsOpen={setThreadModal}
        width="1300px"
        height="860px"
      >
        <OneThread threadId={tId} setThreadModal={setThreadModal} />
      </BigModal>
    </>
  );
}
