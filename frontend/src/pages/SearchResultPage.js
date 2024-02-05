import Container from "../components/frame/Container";
import styles from "./style/SearchResultPage.module.css";
import { useNavigate, useParams } from "react-router-dom";
import searchicon from "../assets/searchicon.png";
import React, { useState, useEffect } from "react";
import 예시이미지2 from "../assets/예시이미지2.png";
import Thread from "../components/common/Thread";

export default function SearchResultPage() {
  const navigate = useNavigate();
  const Params = useParams();
  const [searchtext, setSearchtext] = useState(Params.word);
  const [animateOut, setAnimateOut] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const threadList = [
    {
      threadId: 1,
      imageUrl: 예시이미지2,
      likeCount: 50,
      commentCount: 20,
      scrapCount: 10,
    },
    {
      threadId: 2,
      imageUrl: 예시이미지2,
      likeCount: 30,
      commentCount: 15,
      scrapCount: 5,
    },
    {
      threadId: 3,
      imageUrl: 예시이미지2,
      likeCount: 70,
      commentCount: 40,
      scrapCount: 25,
    },
    {
      threadId: 4,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },

    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },

    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
    {
      threadId: 5,
      imageUrl: 예시이미지2,
      likeCount: 45,
      commentCount: 18,
      scrapCount: 8,
    },
  ];

  console.log(animateOut);
  const handleSubmit = (e) => {
    e.preventDefault();
    if (searchtext.trim()) {
      setRecentSearches((prevSearches) => {
        const updatedSearches = [...prevSearches];
        if (updatedSearches.includes(searchtext)) {
          updatedSearches.splice(updatedSearches.indexOf(searchtext), 1);
        }
        updatedSearches.unshift(searchtext); // 새 검색어를 앞에 추가
        const newSearches = updatedSearches.slice(0, 5); // 최대 5개의 검색어만 유지

        // localStorage에 저장
        localStorage.setItem("recentSearches", JSON.stringify(newSearches));

        return newSearches;
      });
    }
    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    console.log(animateOut);
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}`); // 페이지 전환
    }, 500);
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
    }, 500);
  };
  useEffect(() => {
    // 컴포넌트 마운트 시 localStorage에서 최근 검색어 불러오기
    const savedSearches = JSON.parse(localStorage.getItem("recentSearches"));
    if (savedSearches) {
      setRecentSearches(savedSearches);
    }
  }, []);
  const handle0 = (e) => {
    e.preventDefault();

    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    console.log(animateOut);
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}`); // 페이지 전환
    }, 500);
  };
  const handle1 = (e) => {
    e.preventDefault();

    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    console.log(animateOut);
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}/1`); // 페이지 전환
    }, 500);
  };
  const handle2 = (e) => {
    e.preventDefault();

    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    console.log(animateOut);
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}/2`); // 페이지 전환
    }, 500);
  };
  return (
    <>
      <Container>
        <div className={styles.main}>
          <div className={styles.header}>
            <span className={styles.검색글자}>검색</span>
            <div>
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
                <span>'{Params.word}' 게시글 검색결과</span>
              </div>
              <div className={styles.mid버튼}>
                <button onClick={handle0}>게시글</button>
                <button onClick={handle1}>앨범</button>
                <button onClick={handle2}>계정</button>
              </div>
              <div className={styles.오버플로우확인}>
                <div className={styles.게시글들어갈공간}>
                  {threadList.map((thread) => (
                    <Thread thread={thread} user="cwnsgh" />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
