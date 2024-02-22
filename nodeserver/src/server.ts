import { AppDataSource } from "./data-source";
import { Image } from "./entity/Image";
import express, { Request, Response } from "express";
import multer, { FileFilterCallback } from "multer";
import path from "path";
import sharp from "sharp";
import { v4 as uuidv4 } from "uuid"; // 고유 이름을 주기 위함
import * as dotenv from "dotenv";
import {
  S3Client,
  PutObjectCommand,
  GetObjectCommand,
} from "@aws-sdk/client-s3";
import { getSignedUrl } from "@aws-sdk/s3-request-presigner";
import { In } from "typeorm";
import bodyParser from "body-parser";

// 환경변수 불러오기
dotenv.config({ path: path.resolve(__dirname, "..", ".env") });

// 시작
const app = express();
app.use(bodyParser.json());

// AWS S3와 연결
const s3 = new S3Client({
  region: process.env.AWS_REGION,
  credentials: {
    accessKeyId: process.env.AWS_ACCESS_KEY_ID,
    secretAccessKey: process.env.AWS_SECRET_ACCESS_KEY,
  },
});

// 파일 확장자 제한
const fileFilter: (
  req: Request,
  file: Express.Multer.File,
  cb: FileFilterCallback
) => void = (req, file, cb) => {
  const allowedTypes = /jpeg|jpg|png|jfif/;
  const isAcceptedExtension = allowedTypes.test(
    path.extname(file.originalname).toLowerCase()
  );
  const isAcceptedMimeType = allowedTypes.test(file.mimetype);

  if (isAcceptedExtension && isAcceptedMimeType) {
    cb(null, true);
  } else {
    cb(new Error("Error: Only image files are allowed!"));
  }
};

// 파일 업로드에 관한 설정 (저장위치, 확장자, 용량)
const upload = multer({
  storage: multer.memoryStorage(),
  fileFilter: fileFilter,
  limits: {
    fileSize: 1024 * 1024 * 20, // 20MB
  },
});

// 데이터베이스 연결
AppDataSource.initialize()
  .then(() => {
    console.log("데이터베이스에 성공적으로 연결되었습니다.");
  })
  .catch((error) => {
    console.error("데이터베이스 연결 중 오류가 발생했습니다:", error);
  });

// 이미지 저장 (단일 or 여러장)
app.post(
  "/image",
  upload.array("images", 20), // "images"는 폼 데이터의 필드 이름, 5는 최대 파일 수
  async (req: Request, res: Response) => {
    if (!req.files || req.files.length === 0) {
      return res.status(400).send("No files uploaded.");
    }

    try {
      const uploadPromises = (req.files as Express.Multer.File[]).map(
        async (file) => {
          const filename = `${Date.now()}-${uuidv4()}.jpeg`;
          const compressedImagePath = `images/${filename}`;

          // 파일 압축
          const buffer = await sharp(file.buffer)
            .rotate()
            .resize(800)
            .toFormat("jpeg")
            .jpeg({ quality: 80 })
            .toBuffer();

          // S3에 업로드
          await s3.send(
            new PutObjectCommand({
              Bucket: process.env.AWS_BUCKET_NAME,
              Key: compressedImagePath,
              Body: buffer,
              ContentType: "image/jpeg",
            })
          );

          // 이미지 메타데이터 생성
          const image = new Image();
          image.is_deleted = false;
          image.size = buffer.length;
          image.format = "jpeg";
          image.image_name = filename;
          image.image_url = compressedImagePath;
          image.created_at = new Date();

          // 데이터베이스에 저장
          return AppDataSource.getRepository(Image).save(image);
        }
      );

      // 모든 파일 처리를 병렬로 실행
      const savedImages = await Promise.all(uploadPromises);
      const imageIds = savedImages.map((image) => image.image_id);

      // 모든 파일이 처리된 후 응답 반환
      res.json({
        message: "Images uploaded and saved successfully.",
        imageIds: imageIds,
      });
    } catch (e) {
      console.error("Error processing images:", e);
      res.status(500).send("Error processing images.");
    }
  }
);

// 이미지 ID 배열을 받아 Presigned URL 배열을 반환하는 엔드포인트
app.post("/get-image", async (req: Request, res: Response) => {
  const imageIds = req.body.imageIds;

  if (!imageIds || imageIds.length === 0) {
    return res.status(400).send("No image IDs provided.");
  }

  try {
    const imageRepository = AppDataSource.getRepository(Image);
    const images = await imageRepository.findBy({
      image_id: In(imageIds),
    });

    const presignedUrls = await Promise.all(
      images.map(async (image) => {
        const command = new GetObjectCommand({
          Bucket: process.env.AWS_BUCKET_NAME,
          Key: image.image_url, // 이 경우 image_url이 S3 객체의 Key와 동일해야 함
        });

        const url = await getSignedUrl(s3, command, { expiresIn: 3600 }); // URL 유효 시간: 1시간
        return url;
      })
    );

    res.json({ presignedUrls });
  } catch (error) {
    console.error("Failed to generate presigned URLs:", error);
    res.status(500).send("Failed to generate presigned URLs.");
  }
});

const port = 5000;
app.listen(port, () => console.log(`Server running on port ${port}`));
