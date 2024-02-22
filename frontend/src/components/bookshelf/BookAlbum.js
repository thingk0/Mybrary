import styles from "./BookAlbum.module.css";

//책들 랜더링 하는 부분
export default function BookAlbum({ category }) {
  const colors = [
    "#946087",
    "#BA667E",
    "#F5998F",
    "#455371",
    "#57423F",
    "#7E994F",
    "#2F4858",
  ];

  const bookCount = category ? category.bookCount : 0;
  return (
    <>
      {Array.from({
        length: bookCount < 10 ? bookCount : 10,
      }).map((_, idx) => {
        const height = Math.random() * 50 + 90;
        const color = colors[Math.floor(Math.random() * colors.length)];
        return (
          <div
            key={idx}
            className={styles.album}
            style={{
              height: `${height}px`,
              backgroundColor: color,
            }}
          ></div>
        );
      })}
      {bookCount - 10 > 0 && <div>+ {bookCount - 10}</div>}
    </>
  );
}
