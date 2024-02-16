import Container from "../components/frame/Container";
import styles from "./style/SearchResultPage1.module.css";
import { useNavigate, useParams } from "react-router-dom";
import searchicon from "../assets/searchicon.png";
import React, { useState, useEffect } from "react";
import { keyword, searchBook } from "../api/search/Search";
import s from "classnames";
import useBookStore from "../store/useBookStore";
export default function SearchResultPage1() {
  const navigate = useNavigate();
  const Params = useParams();
  const [searchtext, setSearchtext] = useState(Params.word);
  const [animateOut, setAnimateOut] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const [bookList, setBookList] = useState([]);
  const setBook = useBookStore((state) => state.setBook);
  const handleBook = async (book) => {
    await setBook(book);
    navigate(`/book/${book.bookId}`);
  };
  useEffect(() => {
    async function fetchData() {
      try {
        if (searchtext.trim !== "") {
          const response = await searchBook(searchtext);
          setBookList(response.data.bookList);
        }
      } catch (error) {}
    }
    fetchData();
  }, [searchtext]);

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
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/1/${searchtext}`);
    }, 200);
  };
  const handleSubmit2 = (e) => {
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
    setAnimateOut(true);
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/1/${searchtext}`);
    }, 200);
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
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/${searchtext}`); // 페이지 전환
    }, 200);
  };
  const handle1 = (e) => {
    e.preventDefault();

    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/1/${searchtext}`); // 페이지 전환
    }, 200);
  };
  const handle2 = (e) => {
    e.preventDefault();

    // 검색 결과 페이지로 이동 navigate(`/search/${d.text}`)
    setAnimateOut(true); // fadeOut 애니메이션 시작
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      setAnimateOut(false);
      navigate(`/search/2/${searchtext}`); // 페이지 전환
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
                          onClick={() => handleSubmit2()}
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
                <span>'{Params.word}' 앨범 검색결과</span>
              </div>
              <div className={styles.mid버튼}>
                <button onClick={handle0}>게시글</button>
                <button onClick={handle1}>앨범</button>
                <button onClick={handle2}>계정</button>
              </div>
              <div className={styles.오버플로우확인}>
                <div className={styles.게시글들어갈공간}>
                  {bookList.map((book, index) => (
                    <div
                      key={index}
                      className={styles.main_left}
                      onClick={() => handleBook(book)}
                    >
                      <div
                        className={s(
                          styles.cover,
                          styles[`color${book.coverColorCode}`]
                        )}
                      >
                        <div
                          className={s(
                            styles.img,
                            styles[`layImg${book.coverLayout}`]
                          )}
                          style={{
                            backgroundImage: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${book.imageUrl}")`,
                          }}
                        ></div>
                        <div
                          className={s(
                            styles.text,
                            styles[`layText${book.coverLayout}`]
                          )}
                        >
                          {book.coverTitle}
                        </div>
                      </div>
                      <div
                        className={s(
                          styles.cover2,
                          styles[`backcolor${book.coverColorCode}`]
                        )}
                      ></div>
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
