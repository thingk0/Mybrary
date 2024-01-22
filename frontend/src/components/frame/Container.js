import classNames from "classnames";
import styles from "./framestyle/Container.module.css";

function Container({
  className,
  children,
  width = "1440px",
  height = "1200px",
  backgroundColor = "aliceblue",
}) {
  return (
    <div
      className={classNames(styles.container, className)}
      style={{
        width,
        height,
        backgroundColor, // camelCase를 사용하고 있습니다.
      }}
    >
      {children}
    </div>
  );
}

export default Container;
