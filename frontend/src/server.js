const express = require("express");
const fs = require("fs");
const multer = require("multer");

// dest 경로에 이미지가 저장됨
const upload = multer({ dest: "images/" });

const app = express();

const path = require("path");

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, "images/");
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + "-" + file.originalname); // 중복 이름 방지
  },
});

// 업로드되는 파일의 유형을 검증하는 필터
const multerFilter = function (req, file, cb) {
  const filetypes = /jpeg|jpg|png|gif/;
  // path.extname을 사용하여 파일 확장자를 추출하고, filetypes 정규식과 일치하는지 검사
  const extname = filetypes.test(path.extname(file.originalname).toLowerCase());
  // 파일의 MIME 타입이 이미지인지 확인
  const mimetype = filetypes.test(file.mimetype);
  // cb는 콜백함수. 첫번째 인자는 에러, 두번째 이후부터는 작업 결과를 반환.
  if (extname && mimetype) return cb(null, true);
  else {
    cb("Error: Please upload image ");
  }
};

const uploadStorage = multer({
  storage: storage, // 파일이 저장될 위치와 파일명 정의
  fileFilter: multerFilter, // 어떤 파일을 업로드 할 지
  limits: {
    fileSize: 1024 * 1024,
  },
});

// Routes

app.post(
  "/upload-single", // 포스트 요청을 처리
  uploadStorage.single("image"), // 단일 이미지 파일 업로드 (single 메소드는 한 번에 하나의 파일만 업로드 허용)
  (req, res) => {
    // if (next) {
    //   console.log("Error: " + next);
    //   return;
    // }

    console.log(req.file); // 업로드한 파일에 대한 정보
    res.send("file Uploaded");
  }
);

app.listen(5000, () => console.log("Server on...5000"));
