import Container from "../components/frame/Container";
import s from "classnames";
import styles from "./style/RollingpaperPage.module.css";
import title from "../components/atom/atomstyle/Title.module.css";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useRef, useEffect, useCallback } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import useMybraryStore from "../store/useMybraryStore";
import useMyStore from "../store/useMyStore";
import { getRollingPaper } from "../api/rollingpaper/RollingPaper";

export default function RollingpaperPage() {
  const navigate = useNavigate();
  const Params = useParams();
  const rollingpaperId = Params.rollingpaperId;
  const canvasRef = useRef(null);
  const isPainting = useRef(false);
  const startPoint = useRef({ x: 0, y: 0 });
  const [imageData, setImageData] = useState(null);
  const [lineColor, setLineColor] = useState(12);
  const stompClient = useRef(null);
  const mybrary = useMybraryStore((state) => state.mybrary);
  const panColors = Array.from({ length: 12 }, (_, i) => i + 1);

  //이 둘이 같으면 나, 다르면 딴사람
  const isMe = +Params.userid === useMyStore((state) => state.my.memberId);

  /* 여기서부터 그림 그리는 코드 */
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
          drawLine(
            startPoint.current.x,
            startPoint.current.y,
            newPoint.x,
            newPoint.y,
            colors[lineColor]
          );
          startPoint.current = newPoint;
        }
      }
    },
    [lineColor]
  );

  const exitPaint = useCallback(() => {
    if (isPainting.current) {
      sendImageData(); // 손 떼는 순간 웹소켓으로 전달
      isPainting.current = false;
    }
  }, []);

  // 터치 시작 이벤트
  const startPaintTouch = useCallback((event) => {
    event.preventDefault(); // 기본 터치 이벤트 방지
    const touch = event.touches[0];
    const coordinates = getCoordinates(touch);
    if (coordinates) {
      isPainting.current = true;
      startPoint.current = coordinates;
    }
  }, []);

  // 터치로 그리기 이벤트
  const paintTouch = useCallback(
    (event) => {
      event.preventDefault();
      if (isPainting.current && event.touches.length > 0) {
        const touch = event.touches[0];
        const newPoint = getCoordinates(touch);
        if (newPoint) {
          drawLine(
            startPoint.current.x,
            startPoint.current.y,
            newPoint.x,
            newPoint.y,
            colors[lineColor]
          );
          startPoint.current = newPoint;
        }
      }
    },
    [lineColor]
  );

  // 터치 종료 이벤트
  const exitPaintTouch = useCallback((event) => {
    event.preventDefault();
    if (isPainting.current) {
      sendImageData(); // 그리기 종료시 데이터 전송
      isPainting.current = false;
    }
  }, []);

  const getCoordinates = (event) => {
    if (!canvasRef.current) {
      return;
    }

    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();

    // 터치 이벤트인 경우
    if (event.touches && event.touches.length > 0) {
      return {
        x: event.touches[0].clientX - rect.left,
        y: event.touches[0].clientY - rect.top,
      };
    }
    // 마우스 이벤트인 경우
    return {
      x: event.clientX - rect.left,
      y: event.clientY - rect.top,
    };
  };

  /* 초기화 코드 */
  const handleResetImage = () => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 내용 지우기
    sendImageData();
  };

  const colors = [
    "",
    "#fffafa",
    "#FF2525",
    "#FF7E07",
    "#FFE70B",
    "#2FDB35",
    "#41EDC4",
    "#41CEED",
    "#356CD6",
    "#E543FF",
    "#909090",
    "#616161",
    "#242424",
  ];

  const handleChangeLineColor = (num) => {
    setLineColor(num);
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    const parentDiv = canvas.parentElement;

    const initCanvas = () => {
      canvas.width = parentDiv.clientWidth; // 부모 div의 너비를 canvas의 너비로 설정
      canvas.height = parentDiv.clientHeight; // 부모 div의 높이를 canvas의 높이로 설정
    };

    if (canvas) {
      initCanvas();
    }

    //어떻게 오는지 확인해보기.
    (async function () {
      const res = await getRollingPaper(rollingpaperId);
      if (res.status === "SUCCESS") {
        const loadedImage = res.data.rollingPaperString;
        if (loadedImage) setImageData(loadedImage);
      }
    })();
    //문자열을 캔버스에 로드하기

    const token = localStorage.getItem("accessToken");
    stompClient.current = new Client({
      webSocketFactory: () => new SockJS("https://i10b207.p.ssafy.io/ws"),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    stompClient.current.onConnect = () => {
      stompClient.current.subscribe(
        `/sub/rollingPaper/${rollingpaperId}`,
        (message) => {
          const receivedImageData = JSON.parse(message.body);
          const base64Image = receivedImageData.rollingPaperString;
          setImageData(base64Image);
        }
      );
    };

    stompClient.current.activate();

    return () => {
      if (stompClient.current) stompClient.current.deactivate();
    };
  }, []);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (canvas) {
      canvas.addEventListener("mousedown", startPaint);
      canvas.addEventListener("mousemove", paint);
      canvas.addEventListener("mouseup", exitPaint);
      canvas.addEventListener("mouseleave", exitPaint);

      canvas.addEventListener("touchstart", startPaintTouch);
      canvas.addEventListener("touchmove", paintTouch);
      canvas.addEventListener("touchend", exitPaintTouch);
      canvas.addEventListener("touchcancel", exitPaintTouch);
    }

    return () => {
      canvas.removeEventListener("mousedown", startPaint);
      canvas.removeEventListener("mousemove", paint);
      canvas.removeEventListener("mouseup", exitPaint);
      canvas.removeEventListener("mouseleave", exitPaint);

      canvas.removeEventListener("touchstart", startPaintTouch);
      canvas.removeEventListener("touchmove", paintTouch);
      canvas.removeEventListener("touchend", exitPaintTouch);
      canvas.removeEventListener("touchcancel", exitPaintTouch);
    };
  }, [
    startPaint,
    paint,
    exitPaint,
    startPaintTouch,
    paintTouch,
    exitPaintTouch,
  ]);

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

  const sendImageData = () => {
    if (stompClient.current && canvasRef.current) {
      // Canvas에서 이미지 데이터를 Base64 문자열로 추출
      const imageData = canvasRef.current.toDataURL("image/png");
      const message = {
        rollingPaperId: rollingpaperId,
        rollingPaperString: imageData,
      };

      // STOMP를 통해 서버로 전송
      const destination = `/pub/rollingPaper/${rollingpaperId}`;
      const bodyData = JSON.stringify(message);

      try {
        stompClient.current.publish({ destination, body: bodyData });
      } catch (err) {}
    }
  };

  // const handleRollingPaperSave = async (e) => {
  //   e.preventDefault();
  //   const imageData = canvasRef.current.toDataURL("image/png");
  //   if (imageData) {
  //     const rollingObj = {
  //       rollingPaperId: rollingpaperId,
  //       rollingPaperString: imageData,
  //     };

  //     const res = await saveRollingPaper(rollingObj);
  //   }
  // };

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
            onClick={() => navigate(`../${mybrary.bookShelfId}`)}
          >
            &lt; 책장
          </div>
          <div className={title.main_title}>
            {mybrary.nickname}'s rollingpaper
          </div>
          <div
            className={title.right_title}
            onClick={() => navigate("../threads")}
          >
            {" "}
            게시물 &gt;
          </div>
        </div>

        {isMe && (
          <div className={styles.초기화} onClick={handleResetImage}>
            <span>롤링페이퍼 초기화</span>
          </div>
        )}
        <div className={styles.main}>
          <div className={styles.롤링페이퍼}>
            <canvas ref={canvasRef}></canvas>
          </div>
          <div className={styles.색변경}>
            {panColors.map((num) => (
              <div
                className={s(styles[`color${num}`], styles.color)}
                onClick={() => handleChangeLineColor(num)}
              >
                {num === lineColor && <div className={styles.select}></div>}
              </div>
            ))}
          </div>
        </div>
      </Container>
    </>
  );
}
