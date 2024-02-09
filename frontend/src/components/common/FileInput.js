import { useEffect, useRef, useState } from "react";
import placeholderImg from "../../assets/img.png";
import "./FileInput.css";

/**
 * 파일 입력 컴포넌트
 *
 * @param {string} className - 추가적인 클래스명
 * @param {string} name - input 요소의 name 속성
 * @param {File} value - 현재 선택된 파일 객체
 * @param {string} initialPreview - 초기 미리보기 이미지 경로
 * @param {Function} onChange - 파일 변경 시 호출되는 콜백 함수
 */
function FileInput({ className = "", name, value, initialPreview, onChange }) {
  // 파일 미리보기 상태
  const [preview, setPreview] = useState(initialPreview);

  // input 요소에 접근하기 위한 ref
  const inputRef = useRef();

  // 파일 변경 이벤트 핸들러
  const handleChange = (e) => {
    const nextValue = e.target.files[0];
    onChange(name, nextValue);
  };

  // 선택된 파일 초기화 버튼 클릭 핸들러
  const handleClearClick = () => {
    const inputNode = inputRef.current;
    if (!inputNode) return;

    inputNode.value = "";
    onChange(name, null);
  };

  // 컴포넌트가 처음 렌더링 될 때와 value가 변경될 때 미리보기 업데이트
  useEffect(() => {
    if (!value) return;
    const nextPreview = URL.createObjectURL(value);
    setPreview(nextPreview);

    // 컴포넌트가 언마운트되거나 value가 변경될 때 이전 URL 해제
    return () => {
      setPreview(initialPreview);
      URL.revokeObjectURL(nextPreview);
    };
  }, [value, initialPreview]);

  // JSX로 렌더링
  return (
    <div className={`FileInput ${className}`}>
      {/* 파일 미리보기 */}
      <img
        className={`FileInput-preview ${preview ? "selected" : ""}`}
        src={preview || placeholderImg}
        alt="이미지 미리보기"
      />
      {/* 파일 선택 input */}
      <input
        className="FileInput-hidden-overlay"
        type="file"
        accept="image/png, image/jpeg"
        onChange={handleChange}
        ref={inputRef}
      />
      {/* 선택된 파일이 있을 때만 초기화 버튼 표시 */}
      {value && (
        <button className="FileInput-clear-button" onClick={handleClearClick}>
          X
        </button>
      )}
    </div>
  );
}

export default FileInput;
