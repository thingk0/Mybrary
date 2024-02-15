import Iconuser2 from "../../assets/icon/Iconuser2.png";
import styles from "../../pages/style/PaperplanePage.module.css";

function ChatProfile({
  isSelected,
  otherMemberNickname,
  otherMemberProfileImageUrl,
  latestMessage,
  unreadMessageCount,
  onClick,
  isNew,
}) {
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
          cursor: "pointer",
        }}
        onClick={onClick}
      >
        <div
          className={styles.imgotheruser2}
          style={{
            background: otherMemberProfileImageUrl
              ? `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${otherMemberProfileImageUrl}")no-repeat center/cover`
              : `url(${Iconuser2}) no-repeat center/cover`,
          }}
        ></div>
        <div
          style={{
            width: "65%",
            marginLeft: "1vw",
          }}
        >
          <div
            style={{
              marginBottom: "8px",
            }}
          >
            <span>{otherMemberNickname}</span>
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
            {latestMessage ? (
              <span>{latestMessage}</span>
            ) : (
              <span>종이 비행기를 보내 채팅을 시작하세요</span>
            )}
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
          {unreadMessageCount > 0 && isNew ? (
            <div style={{ marginBottom: "27px" }}>new</div>
          ) : (
            <div style={{ marginBottom: "27px" }}>&nbsp;</div>
          )}
        </div>
      </div>
    </>
  );
}

export default ChatProfile;
