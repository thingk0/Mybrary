import { useState } from "react";
import axios from "axios";

export default function ImageTestPage() {
  // const [file, setFile] = useState();
  const [files, setFiles] = useState([]);

  // const Submit = async (e) => {
  //   e.preventDefault();
  //   const formData = new FormData();
  //   formData.append("image", file[0]);

  //   const result = await axios.post("/upload-single", formData, {
  //     headers: { "Content-Type": "multipart/form-data" },
  //   });
  //   console.log(result);
  // };

  const Submit = async (e) => {
    e.preventDefault();
    const formData = new FormData();

    // 선택된 모든 파일을 formData에 추가
    Array.from(files).forEach((file) => {
      formData.append("images", file); // 서버에서 "images"로 받을 수 있도록 함
    });

    try {
      const res = await axios.post("/image", formData, {
        // 업로드 URL을 여러 파일 처리용으로 변경
        headers: { "Content-Type": "multipart/form-data" },
      });
      console.log(res);
    } catch (error) {
      console.error("이미지 업로드 중 오류 발생", error);
    }
  };

  const updateImage = async () => {
    const formData = new FormData();
    formData.append("imageId", "323"); // 이미지 ID
    formData.append("image", files[0]); // file은 사용자가 선택한 이미지 파일입니다.

    axios
      .post("/image", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log("Success:", response.data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const loadImages = async () => {
    let arr = [];
    for (let i = 230; i <= 320; i++) {
      arr.push(i);
    }
    axios
      .post("/get-image", {
        imageIds: arr,
      })
      .then((response) => {
        const presignedUrls = response.data.presignedUrls;
        const imagesContainer = document.getElementById("images");
        presignedUrls.forEach((url) => {
          const img = document.createElement("img");
          img.src = url;
          img.style.width = "200px"; // 이미지 크기 조절
          imagesContainer.appendChild(img);
        });
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  return (
    <div style={{ marginLeft: "500px", marginTop: "200px" }}>
      <form onSubmit={Submit}>
        <label>이미지선택</label>
        <input
          onChange={(e) => setFiles(e.target.files)}
          type="file"
          accept="image/*"
          multiple
        />
        <button>저장</button>
      </form>

      <button onClick={updateImage}>수정</button>
      <button onClick={loadImages}>이미지 불러오기</button>
      <div id="images"></div>
    </div>
  );
}
