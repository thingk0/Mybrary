import Container from "../components/frame/Container";
import styles from "./style/SearchResultPage2.module.css";
import { useNavigate, useParams } from "react-router-dom";
import searchicon from "../assets/searchicon.png";
import React, { useState, useEffect } from "react";

export default function SearchResultPage2() {
  const navigate = useNavigate();
  const Params = useParams();
  const [searchtext, setSearchtext] = useState(Params.word);
  const [animateOut, setAnimateOut] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const userList = [
    {
      memberId: "1",
      memberName: "최준호",
      memberNickname: "cwnsgh",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "반갑습니다 최준호입니당",
    },
    {
      memberId: "2",
      memberName: "박혜선",
      memberNickname: "qgptjs",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 1,
      intro: "반갑습니다 박혜선입니당~~~~~",
    },
    {
      memberId: "3",
      memberName: "여진구",
      memberNickname: "dwlsrn",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 1,
      intro: "반갑습니다 여진구입니당 ~~~~ 러닝을 좋아해영",
    },
    {
      memberId: "4",
      memberName: "최소영",
      memberNickname: "cthdud",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "쏘영이예용",
    },
    {
      memberId: "5",
      memberName: "서만기",
      memberNickname: "taksrl",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "만기입니다 만기만기만만기",
    },
    {
      memberId: "6",
      memberName: "고명성",
      memberNickname: "raudtjd",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "딩성입니당",
    },
    {
      memberId: "7",
      memberName: "777777",
      memberNickname: "raudtjd",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "7777",
    },
    {
      memberId: "7",
      memberName: "777777",
      memberNickname: "raudtjd",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "7777",
    },
    {
      memberId: "7",
      memberName: "777777",
      memberNickname: "raudtjd",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "7777",
    },
    {
      memberId: "7",
      memberName: "777777",
      memberNickname: "raudtjd",
      memberImageUrl: "https://via.placeholder.com/150",
      isFollowing: 0,
      intro: "7777",
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
                <span>'{Params.word}' 계정 검색결과</span>
              </div>
              <div className={styles.mid버튼}>
                <button onClick={handle0}>게시글</button>
                <button onClick={handle1}>앨범</button>
                <button onClick={handle2}>계정</button>
              </div>
              <div className={styles.오버플로우확인}>
                <div className={styles.유저들이들어갈공간}>
                  {userList.map((user) => (
                    <div key={user.memberName} className={styles.유저박스}>
                      <img
                        src={user.memberImageUrl}
                        alt={`Thread ${user.memberId}`}
                      />
                      <div className={styles.유저이름들}>
                        <span className={styles.유저닉네임}>
                          {user.memberNickname}
                        </span>
                        <span className={styles.유저이름}>
                          {user.memberName}
                        </span>
                      </div>
                      <div className={styles.유저인트로와팔로우}>
                        <span className={styles.유저인트로}>{user.intro}</span>
                        {user.isFollowing === 1 && (
                          <div className={styles.팔로우}>팔로우</div>
                        )}
                        {user.isFollowing === 0 && (
                          <div className={styles.노노팔로우}>팔로우</div>
                        )}
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
