import Iconuser2 from "../../assets/icon/Iconuser2.png";

export default function SharedPaperPage() {
  return (
    <>
      <div
        style={{
          alignSelf: true ? "flex-end" : "flex-start",
        }}
      >
        <div
          style={{
            margin: "15px 25px",
            width: "320px",
            height: "400px",
            background: true
              ? "rgba(221, 204, 195, 0.5)"
              : "var(--Main2, #57423f)",
            boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.25)",
            borderRadius: "5px",
            display: "flex",
            flexDirection: "column",
          }}
        >
          <div
            style={{
              display: "flex",
              alignItems: "center",
              margin: "15px 20px",
              gap: "8px",
            }}
          >
            <img src={Iconuser2} alt="프로필" style={{ width: "30px" }}></img>
            <span style={{ fontSize: "15px" }}>cwnsgh98 님의 쓰레드</span>
          </div>

          <img
            style={{
              width: "280px",
              height: "325px",
              margin: "0 auto",
              borderRadius: "5px",
            }}
            alt="고양이"
            src="https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg"
          ></img>
        </div>
      </div>
    </>
  );
}
