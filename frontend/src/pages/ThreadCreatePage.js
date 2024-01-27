import React, { useState, useMemo, useRef, useEffect } from "react";
import ReactQuill from "react-quill";
// import ReactQuill, { Quill } from "react-quill";
import "react-quill/dist/quill.snow.css";
//아직 안쓰는 거 일단 주석처리
// import ImageResize from "@looop/quill-image-resize-module-react";
import Container from "../components/frame/Container";

export default function ThreadCreatePage() {
  // Quill.register("modules/ImageResize", ImageResize);

  const [content1, setContent1] = useState(""); // 첫 번째 Quill 컴포넌트의 내용
  // const [content2, setContent2] = useState(""); // 두 번째 Quill 컴포넌트의 내용

  const quillRef1 = useRef(null); // 첫 번째 Quill 컴포넌트의 ref
  // const quillRef2 = useRef(null); // 두 번째 Quill 컴포넌트의 ref

  // Quill 모듈 설정
  const modules = useMemo(
    () => ({
      toolbar: {
        container: [
          ["bold", "italic", "underline", "strike"], // 원하는 툴바 버튼 설정
          [{ list: "ordered" }, { list: "bullet" }],
          [{ indent: "-1" }, { indent: "+1" }],
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }],
          [{ align: [] }],
          // ["image"],
        ],
      },
      // ImageResize: {
      //   parchment: Quill.import("parchment"),
      //   modules: ["Resize", "DisplaySize"],
      // },
      history: {
        delay: 500,
        maxStack: 100,
        userOnly: true,
      },
      // 다른 모듈 설정 추가
    }),
    []
  );

  // Quill 컴포넌트의 툴바 설정
  useEffect(() => {
    if (quillRef1.current) {
      quillRef1.current.getEditor().getModule("toolbar").update();
    }
  }, []);

  // 두 번째 Quill 컴포넌트의 툴바 설정
  // useEffect(() => {
  //   if (quillRef2.current) {
  //     quillRef2.current.getEditor().getModule("toolbar").update();
  //   }
  // }, []);

  return (
    <>
      <Container>
        <div>
          <div>
            {/* 첫 번째 Quill 컴포넌트 */}
            <ReactQuill
              ref={quillRef1}
              value={content1}
              onChange={setContent1}
              modules={modules}
            />
          </div>
          {/* <div> */}
          {/* 두 번째 Quill 컴포넌트 */}
          {/* <ReactQuill
              ref={quillRef2}
              value={content2}
              onChange={setContent2}
              modules={modules}
            />
          </div> */}
        </div>
      </Container>
    </>
  );
}
