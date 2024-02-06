import { useState } from "react";
import axios from "axios";

export default function ImageTestPage() {
  const [file, setFile] = useState();
  const [imageName, setImageName] = useState();

  const Submit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("image", file[0]);

    const result = await axios.post(
      "http://localhost:5000/upload-single",
      formData,
      { headers: { "Content-Type": "multipart/form-data" } }
    );

    setImageName(result.data.imageName);
  };

  return (
    <div style={{ marginLeft: "500px", marginTop: "200px" }}>
      <form onSubmit={Submit}>
        <label>이미지선택</label>
        <input
          filename={file}
          onChange={(e) => setFile(e.target.files)}
          type="file"
          accept="image/*"
        />
        <button>저장</button>
      </form>
    </div>
  );
}
