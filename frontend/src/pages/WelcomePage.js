import Container from "../components/frame/Container";
import styles from "./style/WelcomePage.module.css";

export default function WelcomePage() {
  return (
    <>
      <Container
        width={"1000px"}
        height={"100px"}
        backgroundColor={"var(--accent-100)"}
      >
        <div className={styles.div1}>
          <div>
            <div></div>
          </div>
        </div>
      </Container>
    </>
  );
}
