import Container from "../components/frame/Container";
import s from "classnames";
import styles from "./style/RollingpaperPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useRef, useEffect, useCallback } from "react";
import SockJS from "sockjs-client";
import { getMybrary } from "../api/mybrary/Mybrary";
import { Client } from "@stomp/stompjs";

export default function RollingpaperPage() {
  const navigate = useNavigate();
  const Params = useParams();
  const nowuser = Params.userid;
  const rollingpaperId = Params.rollingpaperId;
  const [user, setUser] = useState({});
  const canvasRef = useRef(null);
  const isPainting = useRef(false);
  const startPoint = useRef({ x: 0, y: 0 });
  const [imageData, setImageData] = useState(null);
  const [lineColor, setLineColor] = useState("black");

  useEffect(() => {
    async function fetchmyData() {
      try {
        const response = await getMybrary(nowuser);
        setUser(response.data);
      } catch (error) {
        console.log("데이터를 가져오는데 실패함");
      }
    }
    fetchmyData();
  }, []);
  const drawLine = (originalX, originalY, newX, newY, color) => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    context.strokeStyle = color; // 선 색상 설정
    context.lineWidth = 2; // 선 두께 설정
    context.beginPath();
    context.moveTo(originalX, originalY);
    context.lineTo(newX, newY);
    context.stroke();
    context.closePath();
  };

  const startPaint = useCallback((event) => {
    const coordinates = getCoordinates(event);
    console.log(coordinates);
    if (coordinates) {
      isPainting.current = true;
      startPoint.current = coordinates;
    }
  }, []);

  const paint = useCallback(
    (event) => {
      if (isPainting.current) {
        const newPoint = getCoordinates(event);
        if (newPoint) {
          console.log(lineColor);
          drawLine(
            startPoint.current.x,
            startPoint.current.y,
            newPoint.x,
            newPoint.y,
            lineColor
          );
          startPoint.current = newPoint;
        }
      }
    },
    [lineColor]
  );

  const exitPaint = useCallback(() => {
    if (isPainting.current) {
      sendImageData();
      isPainting.current = false;
    }
  }, []);

  const getCoordinates = (event) => {
    if (!canvasRef.current) {
      return;
    }

    //console.log(event.target);
    const canvas = canvasRef.current;
    return {
      // event.pageX 는 내가 클릭한 지점의 페이지 상에서의 절대 좌표
      // offsetLeft는 요소의 왼쪽 가장 자리가 상위 요소의 왼쪽 가장자리로부터 얼마나 떨어져 있는지를 나타냄
      x: event.pageX - canvas.parentElement.offsetLeft,
      y: event.pageY - canvas.parentElement.offsetTop,
    };
  };

  const handleResetImage = () => {
    console.log("reset");
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 내용 지우기
    sendImageData();
  };

  const colors = {
    1: "#fffafa", // color1
    2: "#FF2525", // color2
    3: "#FF7E07", // color3
    4: "#FFE70B", // color4
    5: "#2FDB35", // color5
    6: "#41EDC4", // color6
    7: "#41CEED", // color7
    8: "#356CD6", // color8
    9: "#E543FF", // color9
    10: "#909090", // color10
    11: "#616161", // color11
    12: "#242424", // color12
  };

  const handleChangeLineColor = (num) => {
    console.log(colors[num]);
    setLineColor(colors[num]);
    console.log(lineColor);
  };

  let client = null;

  const sendImageData = () => {
    console.log(client);
    if (client && canvasRef.current) {
      // Canvas에서 이미지 데이터를 Base64 문자열로 추출
      const imageData = canvasRef.current.toDataURL("image/png");
      const message = {
        content: imageData,
      };

      // STOMP를 통해 서버로 전송
      const destination = `/pub/rp/${rollingpaperId}/draw`;
      const bodyData = JSON.stringify(message);

      try {
        client.publish({ destination, body: "hi" });
      } catch (err) {
        console.log("전송에러");
        console.log(err);
      }
    }
  };

  const connect = () => {
    const token = localStorage.getItem("accessToken");
    client = new Client({
      webSocketFactory: () => new SockJS("https://i10b207.p.ssafy.io/ws"),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    client.onConnect = () => {
      console.log("Connected!");
      console.log(rollingpaperId);

      client.subscribe(`/rp/${rollingpaperId}`, (message) => {
        console.log("receive check!! ");
        console.log(message);
        const receivedImageData = JSON.parse(message.body);
        const base64Image = receivedImageData.content;
        setImageData(base64Image);
      });
    };

    client.activate();
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    const parentDiv = canvas.parentElement;

    const initCanvas = () => {
      canvas.width = parentDiv.clientWidth; // 부모 div의 너비를 canvas의 너비로 설정
      canvas.height = parentDiv.clientHeight; // 부모 div의 높이를 canvas의 높이로 설정
    };

    if (canvas) {
      canvas.addEventListener("mousedown", startPaint);
      // canvas.addEventListener("mousemove", paint);
      canvas.addEventListener("mouseup", exitPaint);
      canvas.addEventListener("mouseleave", exitPaint);
      initCanvas();
    }

    connect();

    return () => {
      canvas.removeEventListener("mousedown", startPaint);
      // canvas.removeEventListener("mousemove", paint);
      canvas.removeEventListener("mouseup", exitPaint);
      canvas.removeEventListener("mouseleave", exitPaint);
    };
  }, []);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (canvas) {
      canvas.addEventListener("mousedown", startPaint);
      canvas.addEventListener("mousemove", paint);
      canvas.addEventListener("mouseup", exitPaint);
      canvas.addEventListener("mouseleave", exitPaint);
    }

    return () => {
      canvas.removeEventListener("mousedown", startPaint);
      canvas.removeEventListener("mousemove", paint);
      canvas.removeEventListener("mouseup", exitPaint);
      canvas.removeEventListener("mouseleave", exitPaint);
    };
  }, [lineColor, startPaint, exitPaint, paint]);

  useEffect(() => {
    // imageData 상태가 변경될 때 실행되는 useEffect
    if (imageData) {
      const canvas = canvasRef.current;
      const context = canvas.getContext("2d");

      // 이미지 객체 생성
      const image = new Image();
      image.onload = function () {
        // 이미지 로드가 완료되면 캔버스에 이미지를 그립니다.
        context.clearRect(0, 0, canvas.width, canvas.height); // 캔버스를 초기화
        context.drawImage(image, 0, 0, canvas.width, canvas.height); // 캔버스에 이미지를 그림
      };
      // 서버로부터 받은 Base64 이미지 데이터를 이미지 객체의 src에 할당
      image.src = imageData;
    }
  }, [imageData]); // imageData가 변경될 때마다 이 useEffect가 실행됩니다.
  return (
    <>
      <Container>
        <div>
          <div className={title.back} onClick={() => navigate("../")}>
            &lt; 뒤로가기
          </div>
        </div>
        <div className={title.title}>
          <div
            className={title.left_title}
            onClick={() => navigate(`../${user.bookShelfId}`)}
          >
            &lt; 책장
          </div>
          <div className={title.main_title}>{user.nickname}'s rollingpaper</div>
          <div
            className={title.right_title}
            onClick={() => navigate("../threads")}
          >
            {" "}
            게시물 &gt;
          </div>
        </div>

        <div className={styles.초기화} onClick={handleResetImage}>
          <span>롤링페이퍼초기화</span>
        </div>
        <div className={styles.main}>
          <div className={styles.롤링페이퍼}>
            {" "}
            <canvas ref={canvasRef}></canvas>
          </div>
          <div className={styles.색변경}>
            <div
              className={s(styles.color1, styles.color)}
              onClick={() => handleChangeLineColor(1)}
            ></div>
            <div
              className={s(styles.color2, styles.color)}
              onClick={() => handleChangeLineColor(2)}
            ></div>
            <div
              className={s(styles.color3, styles.color)}
              onClick={() => handleChangeLineColor(3)}
            ></div>
            <div
              className={s(styles.color4, styles.color)}
              onClick={() => handleChangeLineColor(4)}
            ></div>
            <div
              className={s(styles.color5, styles.color)}
              onClick={() => handleChangeLineColor(5)}
            ></div>
            <div
              className={s(styles.color6, styles.color)}
              onClick={() => handleChangeLineColor(6)}
            ></div>
            <div
              className={s(styles.color7, styles.color)}
              onClick={() => handleChangeLineColor(7)}
            ></div>
            <div
              className={s(styles.color8, styles.color)}
              onClick={() => handleChangeLineColor(8)}
            ></div>
            <div
              className={s(styles.color9, styles.color)}
              onClick={() => handleChangeLineColor(9)}
            ></div>
            <div
              className={s(styles.color10, styles.color)}
              onClick={() => handleChangeLineColor(10)}
            ></div>
            <div
              className={s(styles.color11, styles.color)}
              onClick={() => handleChangeLineColor(11)}
            ></div>
            <div
              className={s(styles.color12, styles.color)}
              onClick={() => handleChangeLineColor(12)}
            ></div>
          </div>
          <div className={styles.저장}>
            <button>저장</button>
          </div>
        </div>
      </Container>
    </>
  );
}
