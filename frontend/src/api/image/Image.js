import axios from "axios";

const BASE_URL = "https://i10b207.p.ssafy.io/";

export async function uplodaImage(formData) {
  try {
    const response = await axios.post(BASE_URL + "image", formData);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function getImageList(imageIdArr) {
  try {
    const response = await axios.post(BASE_URL + "image/get", {
      imageIds: imageIdArr,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}
