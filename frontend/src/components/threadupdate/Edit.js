import { useState } from "react";
import styles from "./Edit.module.css";
import item from "./Item.module.css";
import s from "classnames";
import { Editor } from "react-draft-wysiwyg";

export default function Edit({ currentPage, papers, setPapers }) {
  // const persent = ["", "9:16", "3:4", "1:1", "4:3", "16:9"];

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
            "emoji",
            "link",
            "colorPicker",
            "blockType",
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
            "emoji",
            "link",
            "colorPicker",
            "blockType",
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
        className={item[`img1_${papers[currentPage].layoutType}`]}
        style={{
          background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${papers[currentPage].imageUrl1}")no-repeat center/cover`,
        }}
      ></div>
      <div
        className={item[`img2_${papers[currentPage].layoutType}`]}
        style={{
          background: `url("https://jingu.s3.ap-northeast-2.amazonaws.com/${papers[currentPage].imageUrl2}")no-repeat center/cover`,
        }}
      ></div>
    </div>
  );
}
