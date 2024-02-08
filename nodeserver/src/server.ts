import { AppDataSource } from "./data-source";
import { Image } from "./entity/Image";

import express, { Request, Response } from "express";
import multer, { FileFilterCallback } from "multer";
import path from "path";

const app = express();

AppDataSource.initialize()
  .then(() => {
    console.log("데이터베이스에 성공적으로 연결되었습니다.");
  })
  .catch((error) => {
    console.error("데이터베이스 연결 중 오류가 발생했습니다:", error);
  });

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, "images/");
  },
  filename: (req, file, cb) => {
    cb(null, `${Date.now()}-${file.originalname}`); // 중복 이름 방지
  },
});

const fileFilter: (
  req: Request,
  file: Express.Multer.File,
  cb: FileFilterCallback
) => void = (req, file, cb) => {
  const allowedTypes = /jpeg|jpg|png|gif/;
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

const upload = multer({
  storage: storage,
  fileFilter: fileFilter,
  limits: {
    fileSize: 1024 * 1024, // 1MB
  },
});

app.post(
  "/upload-single",
  upload.single("image"),
  async (req: Request, res: Response) => {
    if (!req.file) {
      return res.status(400).send("No file uploaded.");
    }

    const image = new Image();
    image.is_deleted = false;
    image.size = req.file.size;
    image.format = req.file.mimetype;
    image.image_name = req.file.originalname;
    image.image_url = `uploads/${req.file.filename}`; // 파일이 저장된 경로 업데이트

    try {
      await AppDataSource.getRepository(Image).save(image);
      res.json({ message: "Image uploaded and saved successfully.", image });
    } catch (error) {
      console.error(error);
      res.status(500).send("Error saving image metadata.");
    }
  }
);

const port = 5000;
app.listen(port, () => console.log(`Server running on port ${port}`));
