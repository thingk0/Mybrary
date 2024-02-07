import { useState, useRef } from "react";
import styles from "./Edit.module.css";
import item from "./Item.module.css";
import s from "classnames";
import { Editor } from "react-draft-wysiwyg";

export default function Edit({ currentPage, papers, setPapers }) {
  const inputRef = useRef(null);
  const persent = ["", "9:16", "3:4", "1:1", "4:3", "16:9"];

  const [toolbarZIndex, setToolbarZIndex] = useState(1);
  const [toolbarZIndex2, setToolbarZIndex2] = useState(1);

  const handleFocus1 = () => {
    setToolbarZIndex(4);
  };
  const handleFocus2 = () => {
    setToolbarZIndex2(4);
  };
  const handleBlur = () => {
    setToolbarZIndex(1);
    setToolbarZIndex2(1);
  };

  const handleImageChange = (e, isImage1) => {
    const file = e.target.files[0];
    if (file && file.type.match("image.*")) {
      const reader = new FileReader();
      reader.onload = (readerEvent) => {
        const imageSrc = readerEvent.target.result;
        setPapers((prevPapers) => {
          const updatedPapers = [...prevPapers];
          const imageKey = isImage1 ? "image1" : "image2";
          updatedPapers[currentPage][imageKey] = {
            name: file.name,
            url: imageSrc,
          };
          return updatedPapers;
        });
      };
      reader.readAsDataURL(file);
    }
  };

  const onEditorStateChange = (editorState) => {
    const updatedPapers = [...papers];
    updatedPapers[currentPage].editorState = editorState;
    setPapers(updatedPapers);
  };
  const onEditorStateChange2 = (editorState2) => {
    const updatedPapers = [...papers];
    updatedPapers[currentPage].editorState2 = editorState2;
    setPapers(updatedPapers);
  };

  return (
    <div className={s(styles.미드중앙사이즈조정, item.position)}>
      <Editor
        editorClassName={s(
          item[`text1_${papers[currentPage].layoutType}`],
          styles.editor1
        )}
        toolbarClassName={styles.toolbar}
        toolbarStyle={{
          zIndex: toolbarZIndex,
          backgroundColor: "#ffffff00",
          width: "35.2vi",
          border: "none",
          padding: "0",
          margin: "0",
        }}
        toolbar={{
          options: [
            "inline",
            "blockType",
            "colorPicker",
            "emoji",
            "link",
            "textAlign",
            "history",
          ],
        }}
        placeholder="내용을 작성해주세요."
        localization={{
          locale: "ko",
        }}
        // editorStyle={{ margin: "0", padding: "0" }} // 에디터 스타일 적용
        editorState={papers[currentPage].editorState}
        onEditorStateChange={onEditorStateChange}
        onFocus={handleFocus1}
        onBlur={handleBlur}
      />

      <Editor
        editorClassName={s(
          item[`text2_${papers[currentPage].layoutType}`],
          styles.editor2
        )}
        toolbarClassName={styles.toolbar}
        toolbarStyle={{
          zIndex: toolbarZIndex2,
          backgroundColor: "#ffffff00",
          width: "35.2vi",
          border: "none",
          padding: "0",
          margin: "0",
        }}
        toolbar={{
          options: [
            "inline",
            "blockType",
            "colorPicker",
            "emoji",
            "link",
            "textAlign",
            "history",
          ],
        }}
        placeholder="내용을 작성해주세요."
        localization={{
          locale: "ko",
        }}
        editorState={papers[currentPage].editorState2}
        onEditorStateChange={onEditorStateChange2}
        onFocus={handleFocus2}
        onBlur={handleBlur}
      />

      <div
        className={
          !papers[currentPage].image1 &&
          item[`img1_${papers[currentPage].layoutType}`]
        }
      >
        <input
          ref={inputRef}
          type="file"
          accept="image/*"
          capture="camera"
          className={styles.FileInput_hidden_overlay}
          onChange={(e) => handleImageChange(e, true)}
        />
        {papers[currentPage].image1 && (
          <img
            className={item[`img1_${papers[currentPage].layoutType}`]}
            src={papers[currentPage].image1.url}
            alt="이미지1"
          />
        )}
        {!papers[currentPage].image1 && (
          <div>
            {persent[Math.floor((papers[currentPage].layoutType % 1000) / 100)]}
            사진 추가
          </div>
        )}
      </div>
      <div className={item[`img2_${papers[currentPage].layoutType}`]}>
        <input
          ref={inputRef}
          type="file"
          accept="image/*"
          capture="camera"
          className={styles.FileInput_hidden_overlay}
          onChange={(e) => handleImageChange(e, false)}
        />
        {papers[currentPage].image2 && (
          <img
            className={item[`img2_${papers[currentPage].layoutType}`]}
            src={papers[currentPage].image2.url}
            alt="이미지2"
          />
        )}
        <div>
          {persent[Math.floor((papers[currentPage].layoutType % 100) / 10)]}
          사진 추가
        </div>
      </div>
    </div>
  );
}
