export default function SharedPaperPage() {
  return (
    <>
      <div style={{ alignSelf: true ? "flex-end" : "flex-start" }}>
        <div
          style={{
            margin: "15px 25px",
            width: "320px",
            height: "380px",
            background: true
              ? "rgba(221, 204, 195, 0.5)"
              : "var(--Main2, #57423f)",
            boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.25)",
            borderRadius: "5px",
          }}
        >
          <div></div>
          <img
            style={{
              width: "280px",
              height: "320px",
            }}
            alt="고양이"
            src="https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg"
          ></img>
        </div>
      </div>
    </>
  );
}
