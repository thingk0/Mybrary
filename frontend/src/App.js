import { Outlet } from "react-router-dom";
import Nav from "./components/atom/Nav";
import "./App.css";

export default function App() {
  return (
    <>
      <Nav />
      <div>
        <Outlet />
      </div>
    </>
  );
}
