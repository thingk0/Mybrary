import axios from "axios";

export async function uplodaImage(formData) {
  try {
    const response = await axios.post("/image", formData);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function getImageList(imageIdArr) {
  try {
    const response = await axios.post("/get-image", { imageIds: imageIdArr });
    return response.data;
  } catch (error) {
    throw error;
  }
}
