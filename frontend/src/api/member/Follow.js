import axios from "axios";
const BASE_URL = "http://thingk0.duckdns.org:8080/api/v1/";
/* 특정 회원 팔로우 */
export async function follow(id) {
  try {
    const response = await axios.post(BASE_URL + `member/${id}/follow`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 특정 회원 팔로잉 리스트 조회 */
export async function getFollowingList(id) {
  try {
    const response = await axios.get(BASE_URL + `member/${id}/followings`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 특정 회원 팔로워 리스트 조회 */
export async function getFollowerList(id) {
  try {
    const response = await axios.get(BASE_URL + `member/${id}/followers`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 팔로잉 리스트 조회 */
export async function getMyFollowingList() {
  try {
    const response = await axios.get(BASE_URL + `member/me/followings`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 나의 팔로워 리스트 조회 */
export async function getMyFollowerList() {
  try {
    const response = await axios.get(BASE_URL + `member/me/followers`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 특정 회원 언팔로우 (내가 팔로우 하고 있는 사람 끊기) */
export async function deleteFollow(id) {
  try {
    const response = await axios.delete(BASE_URL + `member/${id}/unfollow`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

/* 팔로워 끊기 (나를 팔로우 하고 있는 사람 끊기) */
export async function deleteFollower(id) {
  try {
    const response = await axios.delete(BASE_URL + `member/${id}/follower`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
