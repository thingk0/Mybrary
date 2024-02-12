import Iconuser2 from "../../assets/icon/Iconuser2.png";

function ChatProfile({ isSelected }) {
  return (
    <>
      <div
        style={{
          width: "94%",
          height: "72px",
          display: "flex",
          alignItems: "center",
          justifyContent: "start",
          background: isSelected ? "rgba(152, 115, 96, 0.5)" : "#eee3dd",
          borderRadius: "5px",
          marginBottom: "12px",
          padding: "0 10px",
        }}
      >
        <img
          src={Iconuser2} // 선택된 이미지 또는 기본 이미지
          alt="프로필"
          style={{
            width: "14%",
            objectFit: "cover",
            borderRadius: "50%",
            marginRight: "6px",
          }}
        />
        <div
          style={{
            width: "70%",
          }}
        >
          <div
            style={{
              marginBottom: "8px",
            }}
          >
            <span>manmangi_98</span>
          </div>
          <div
            style={{
              fontSize: "14px",
              overflow: "hidden", // 내용이 넘치면 숨김
              whiteSpace: "nowrap", // 텍스트를 한 줄로 표시
              textOverflow: "ellipsis", // 넘치는 텍스트는 ...으로 표시
              color: "var(--main5)",
            }}
          >
            <span>
              공유좀 그만해 준호야!! 알림에 너밖에 어디까지 늘어나나요
            </span>
          </div>
        </div>
        <div
          style={{
            marginLeft: "15px",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            gap: "17px",
            width: "36px",
          }}
        >
          <div
            style={{
              fontSize: "30px",
              letterSpacing: "0.05em",
            }}
          >
            ...
          </div>
          <div style={{ marginBottom: "27px" }}>new</div>
          {/* <div style={{ marginBottom: "27px" }}>&nbsp;</div> */}
        </div>
      </div>
    </>
  );
}

export default ChatProfile;
