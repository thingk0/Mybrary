import React, { useEffect, useState } from "react";
import * as d3 from "d3";
import cloud from "d3-cloud";
import Container from "../components/frame/Container";
import styles from "./style/SearchPage.module.css";
import searchicon from "../assets/searchicon.png";
import { useNavigate } from "react-router-dom";

export default function SearchPage() {
  const [searchtext, setSearchtext] = useState("");
  const [animateOut, setAnimateOut] = useState(false);
  const [recentSearches, setRecentSearches] = useState([]);
  const navigate = useNavigate();

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
    // 애니메이션이 끝난 후 페이지 전환
    setTimeout(() => {
      navigate(`/search/${searchtext}`); // 페이지 전환
    }, 500);
  };
  useEffect(() => {
    // 컴포넌트 마운트 시 localStorage에서 최근 검색어 불러오기
    const savedSearches = JSON.parse(localStorage.getItem("recentSearches"));
    if (savedSearches) {
      setRecentSearches(savedSearches);
    }
  }, []);
  useEffect(() => {
    // 여기에 단어 구름 데이터를 배열로 정의합니다.
    const data = [
      "여행",
      "박혜선",
      "여진구",
      "최준호",
      "서만기",
      "최소영",
      "고명성",
      "프론트엔드",
      "백엔드",
      "싸피",
      "대전캠퍼스",
      "대전맛집",
      "대전2반",
      "성심당",
      "볼링",
      "가위바위보",
      "페페와친구들",
      "친구들",
      "노래방",
      "횟집",
      "날치알밥",
      "야구장",
      "여진구바보",
      "7조" /* ... , "word30" */,
    ];

    // 배열의 길이를 기준으로 단어 크기를 계산합니다.
    const maxFontSize = 60;
    const minFontSize = 20;
    const maxIndex = data.length - 1;
    const colors = [
      "#f5998f",
      "#57423f",
      "#bfa6a2",
      "#6a5b82",
      "#455371",
      "#2f4858",
    ];
    function customSpiral(size) {
      const e = size[0] / size[1];
      return function (t) {
        const r = t * 5;
        return [r * Math.cos(t) * e, r * Math.sin(t)];
      };
    }

    cloud()
      .size([1000, 500])
      .words(
        data.map((d, i) => {
          // 인덱스가 작을수록 크기가 커지도록 계산합니다.
          const size =
            maxFontSize - (i / maxIndex) * (maxFontSize - minFontSize);
          return { text: d, size: size };
        })
      )
      .padding(10)
      .fontSize(function (d) {
        return d.size;
      })
      .rotate(90)
      .spiral(customSpiral) // 이 부분을 추가하세요
      .on("end", draw)
      .start();

    function draw(words) {
      const textElements = d3
        .select(`.${styles.wordcloud}`)
        .append("svg")
        // ... SVG 설정 ...
        .attr("width", 1000)
        .attr("height", 500)
        // .style("border", "4px solid black")
        .style("box-Shadow", "0px 0px 25px rgba(0, 0, 0, 0.25)")
        .style("background-color", "#EFE3DF")
        .style("border-radius", "20px")
        .style("padding-top", "50px")
        .style("padding-right", "60px")
        .style("padding-left", "60px")
        .append("g")
        .attr("transform", "translate(500,250)")
        .selectAll("text")
        .data(words)
        .enter()
        .append("text")
        .style("font-size", function (d) {
          return d.size + "px";
        })
        .style("font-family", "BMDOHYEON")
        .style("fill", function (d) {
          return colors[Math.floor(Math.random() * colors.length)];
        })
        .style("stroke", function (d) {
          // 글자 색상과 동일한 테두리 색상을 적용합니다.
          return colors[Math.floor(Math.random() * colors.length)];
        })
        .style("z-index", 1)
        .style("stroke-width", "1px")
        .attr("text-anchor", "middle")
        .attr("transform", function (d) {
          // 여기서 'translate'와 'rotate'를 설정합니다.
          return `translate(${d.x},${d.y}) rotate(${d.rotate})`;
        })
        .text(function (d) {
          return d.text;
        });

      // 트랜지션 설정
      textElements.style("transition", "transform 0.2s");

      // 마우스 호버 시 scale을 1.2로 증가, 단 'translate'와 'rotate'는 그대로 유지합니다.
      textElements.on("mouseover", function (event, d) {
        d3.select(this)
          .transition()
          .style(
            "transform",
            `translate(${d.x}px, ${d.y}px) rotate(${d.rotate}deg) scale(1.3)`
          )
          .style("z-index", "3")
          .style("cursor", "pointer");
      });

      // 마우스 아웃 시 scale을 1로 복귀, 단 'translate'와 'rotate'는 그대로 유지합니다.
      textElements.on("mouseout", function (event, d) {
        d3.select(this)
          .transition()
          .style(
            "transform",
            `translate(${d.x}px, ${d.y}px) rotate(${d.rotate}deg) scale(1)`
          )
          .style("z-index", "1");
      });
      textElements.on("click", function (event, d) {
        if (d.text.trim()) {
          setRecentSearches((prevSearches) => {
            const updatedSearches = [...prevSearches];
            if (updatedSearches.includes(d.text)) {
              updatedSearches.splice(updatedSearches.indexOf(d.text), 1);
            }
            updatedSearches.unshift(d.text); // 클릭된 단어를 앞에 추가
            const newSearches = updatedSearches.slice(0, 5); // 최대 5개의 검색어만 유지

            // localStorage에 저장
            localStorage.setItem("recentSearches", JSON.stringify(newSearches));

            return newSearches;
          });
        }

        // 나머지 애니메이션 및 페이지 전환 로직
        setAnimateOut(true);
        setTimeout(() => {
          navigate(`/search/${d.text}`);
        }, 500);
      });
    }
    return () => {
      setAnimateOut(true);
    };
  }, [navigate]);
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
                      placeholder="검색하시오"
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
              className={`${styles.wordcloud} ${
                animateOut ? styles.fadeOut : styles.fadeIn
              }`}
            >
              <div className={styles.앱솔루트}>
                <div>인기검색어</div>
              </div>
              {/* 단어 구름이 여기에 그려집니다. */}
            </div>
          </div>
        </div>
      </Container>
    </>
  );
}
