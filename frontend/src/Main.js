import { BrowserRouter, Routes, Route } from "react-router-dom";
import ScrollToTop from "./components/common/ScrollToTop";
import App from "./App";
import BookPage from "./pages/BookPage";
import BookshelfPage from "./pages/BookshelfPage";
import FeedPage from "./pages/FeedPage";
import JoinPage from "./pages/JoinPage";
import MybraryPage from "./pages/MybraryPage";
import PaperplanePage from "./pages/PaperplanePage";
import RollingpaperPage from "./pages/RollingpaperPage";
import SearchPage from "./pages/SearchPage";
import SearchResultPage from "./pages/SearchResultPage";
import SettingPage from "./pages/SettingPage";
import ThreadCreatePage from "./pages/ThreadCreatePage";
import ThreadsPage from "./pages/ThreadsPage";
import WelcomePage from "./pages/WelcomePage";
import SearchResultPage1 from "./pages/SearchResultPage1";
import SearchResultPage2 from "./pages/SearchResultPage2";
import BookDetailPage from "./pages/BookDetailPage";
import ErrorPage from "./pages/ErrorPage";
import ThreadUpdatePage from "./pages/ThreadUpdatePage";

function Main() {
  return (
    <BrowserRouter>
      <ScrollToTop />
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<WelcomePage />} />
          {/*이거 나중에 동적처리 :userid를 쓰는 이유는 오로지 구별용이다. url로 접근했을때 원하는 페이지로 가기 위함*/}
          <Route path="error" element={<ErrorPage />}></Route>
          <Route path="mybrary/:userid">
            <Route index element={<MybraryPage />} />
            <Route path="threads" element={<ThreadsPage />} />
            <Route
              path="rollingpaper/:rollingpaperId"
              element={<RollingpaperPage />}
            />
            <Route path=":bookShelfId">
              <Route index element={<BookshelfPage />} />
              {/*이거 나중에 동적처리 */}
              <Route path=":categoryid">
                <Route index element={<BookPage />} />
              </Route>
            </Route>
          </Route>
          <Route path="book/:bookId" element={<BookDetailPage />} />
          {/* 아래는 사람마다 보여지는 페이지가 같기 때문에 동적처리를 할 필요가 없다. */}
          <Route path="paperplane" element={<PaperplanePage />} />
          <Route path="feed" element={<FeedPage />} />
          <Route path="join" element={<JoinPage />} />
          <Route path="search">
            <Route index element={<SearchPage />} />
            <Route path=":word" element={<SearchResultPage />} />
            <Route path="1/:word" element={<SearchResultPage1 />} />
            <Route path="2/:word" element={<SearchResultPage2 />} />
          </Route>
          <Route path="threadCreate" element={<ThreadCreatePage />} />
          <Route path="threadUpdate" element={<ThreadUpdatePage />} />
          <Route path="account" element={<SettingPage />} />
        </Route>
        <Route path="*" element={<ErrorPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Main;
