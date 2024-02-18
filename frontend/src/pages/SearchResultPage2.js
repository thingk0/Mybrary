import Container from "../components/frame/Container";
import styles from "./style/SearchResultPage2.module.css";
import { useNavigate, useParams } from "react-router-dom";
import searchicon from "../assets/searchicon.png";
import React, { useState, useEffect } from "react";
import { keyword, searchAccount } from "../api/search/Search";
import iconhome from "../assets/icon/icon_home.png";
import gomimg from "../assets/icon/Iconuser2.png";
import useUserStore from "../store/useUserStore";
import { getFirstChat } from "../api/chat/Chat";

export default function SearchResultPage2() {
  const { user: nowuser } = useUserStore();

  const navigate = useNavigate();
  const Params = useParams();
  const [searchtext, setSearchtext] = useState(Params.word);
  const [animateOut, setAnimateOut] = useState(false);
  const [fo, setFo] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const [userList, setUserList] = useState([]);

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
      navigate(`/search/2/${searchtext}`);
    }, 200);
    setAnimateOut(false);
  };

  const handleContainerClick = (e) => {
    // 클릭된 요소가 검색어나 검색 버튼과 관련된 요소가 아닌 경우에만 추천 검색어 창을 사라지도록 처리
    const isSearchInput = e.target.closest(`.${styles.searchInput}`);
    const isSearchButton = e.target.closest(`.${styles.searchButton}`);
    const isRecentSearchBox = e.target.closest(`.${styles.최근검색어박스}`);
    const isRecommendedSearch = e.target.closest(`.${styles.key}`);

    if (
      !isSearchInput &&
      !isSearchButton &&
      !isRecentSearchBox &&
      !isRecommendedSearch
    ) {
      // 클릭된 요소가 검색어, 검색 버튼, 추천 검색어, 최근 검색어 창과 관련이 없는 경우에만 추천 검색어 창 숨김
      setList([]);
    }
  };

  const handleRecentSearchClick = (search) => {
    setAnimateOut(true);
    if (search.trim()) {
      setRecentSearches((prevSearches) => {
        const updatedSearches = [...prevSearches];
        if (updatedSearches.includes(search)) {
          updatedSearches.splice(updatedSearches.indexOf(search), 1);
        }
        updatedSearches.unshift(search);
        const newSearches = updatedSearches.slice(0, 5);
        localStorage.setItem("recentSearches", JSON.stringify(newSearches));

        return newSearches;
      });
    }
    setTimeout(() => {
      setSearchtext(search);
      setAnimateOut(false);
      navigate(`/search/${search}`);
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
    async function fetchData() {
      try {
        if (searchtext.trim !== "") {
          const response = await searchAccount(searchtext);
          setUserList(response.data.accountList);
        }
      } catch (error) {}
    }
    fetchData();
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

  const handleChatStart = async (memberId) => {
    if (memberId === nowuser.memberId) {
      navigate("/paperplane");
    } else {
      await getFirstChat(memberId);
      navigate(`/paperplane?chatuserid=${memberId}`);
    }
  };
  return (
    <>
      <Container>
        <div className={styles.main} onClick={handleContainerClick}>
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
                      autoComplete="off"
                    />
                  </div>
                </form>
                {list.length !== 0 && (
                  <div className={styles.absolute}>
                    <div className={styles.title}>추천검색어</div>
                    {list?.map((key) => (
                      <div key={key}>
                        <div
                          className={styles.key}
                          onClick={() => handleRecentSearchClick(key)}
                        >
                          {key}
                        </div>
                        <hr className={styles.hr}></hr>
                      </div>
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
                <span>'{searchtext}' 계정 검색결과</span>
              </div>
              <div className={styles.mid버튼}>
                <button onClick={handle0}>스레드</button>
                <button onClick={handle1}>책</button>
                <button onClick={handle2}>계정</button>
              </div>
              {userList.length === 0 && (
                <div className={styles.noneKeyword}>
                  {searchtext}의 계정 검색결과가 없습니다!
                </div>
              )}
              <div className={styles.오버플로우확인}>
                <div className={styles.유저들이들어갈공간}>
                  {userList.map((user) => (
                    <div key={user.memberId} className={styles.유저박스}>
                      {user.profileImageUrl != null ? (
                        <div
                          className={styles.유저아이콘}
                          style={{
                            background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${user.profileImageUrl}")no-repeat center/cover`,
                          }}
                        ></div>
                      ) : (
                        <div
                          className={styles.유저아이콘}
                          style={{
                            background: `url(${gomimg})no-repeat center/cover`,
                          }}
                        ></div>
                      )}
                      <div className={styles.유저이름들}>
                        <div>
                          <span className={styles.유저닉네임}>
                            {user.nickname}
                          </span>
                          <span className={styles.유저이름}>{user.name}</span>
                        </div>
                        <img
                          onClick={() => {
                            navigate(`/mybrary/${user.memberId}`);
                          }}
                          className={styles.방문하기}
                          src={iconhome}
                          alt="없음"
                        />
                      </div>
                      <div className={styles.유저인트로와팔로우}>
                        <span className={styles.유저인트로}>{user.intro}</span>
                        <div
                          className={styles.채팅하기}
                          onClick={() => handleChatStart(user.memberId)}
                        >
                          채팅하기
                        </div>
                      </div>
                    </div>
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
