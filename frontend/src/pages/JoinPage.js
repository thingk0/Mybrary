import Container from "../components/frame/Container";
import LoginForm from "../components/join/LoginForm";
import SignUpForm from "../components/join/SignUpForm";
// 안써서 주석처리
// import styles from "./style/JoinPage.module.css";
export default function JoinPage() {
  return (
    <>
      <Container>
        <div style={{ marginLeft: "100px", marginTop: "100px" }}>
          <div>JoinPage.</div>
          <SignUpForm></SignUpForm>
          <LoginForm></LoginForm>
        </div>
      </Container>
    </>
  );
}
