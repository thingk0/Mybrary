import React, { useState, useEffect, useCallback } from "react";
import styles from "./style/FeedPage.module.css";
import s from "classnames";
import { useNavigate } from "react-router-dom";
import Comment from "../components/feed/Comment";
import FeedContent from "../components/feed/FeedContent";

export default function FeedPage() {
  const [activeIndex, setActiveIndex] = useState(0);
  const threadList = [
    {
      bookId: 1,
      threadId: 101,
      time: "2024-02-03T12:30:00234234",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1101,
          content1:
            "본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1102,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1103,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 2,
      threadId: 101,
      time: "2024-02-03T12:30:002222",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1201,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 3,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1202,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1203,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 4,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1204,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1205,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1301,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 5,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1302,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 6,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1303,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1304,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 7,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1401,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1402,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1501,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 8,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1502,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 9,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1503,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1504,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 10,
      threadId: 101,
      time: "2024-02-03T12:30:00sdsd",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 1101,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 1102,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
  ];
  const threadList2 = [
    {
      bookId: 1,
      threadId: 101,
      time: "2024-02-03T12:30:00234234",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2111,
          content1:
            "본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2112,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2141,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 2,
      threadId: 101,
      time: "2024-02-03T12:30:002222",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2411,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 3,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2151,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2511,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 4,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2221,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2231,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2321,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 5,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2331,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 6,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2332,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2333,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 7,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2322,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2232,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2311,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 8,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2131,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 9,
      threadId: 101,
      time: "2024-02-03T12:30:00",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2441,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2442,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
    {
      bookId: 10,
      threadId: 101,
      time: "2024-02-03T12:30:00sdsd",
      isOwner: true,
      owner: {
        ownerId: 201,
        name: "너의이름",
        nickname: "닉네임",
        profileImageUrl: "프로필이미지주소",
        isFollowed: false,
      },
      paperList: [
        {
          paperId: 301,
          layoutType: 2551,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
        {
          paperId: 301,
          layoutType: 2111,
          content1: "본문1 내용",
          content2: "본문2 내용",
          thumbnail1Url: "썸네일1 주소",
          thumbnail2Url: "썸네일2 주소",
          likeCount: 15,
          commentCount: 7,
          scrapCount: 3,
          isLiked: false,
          isPaperPublic: true,
          isScrapEnable: true,
          tagList: [
            {
              tagName: "태그1",
            },
            {
              tagName: "태그2",
            },
          ],
        },
      ],
    },
  ];
  const [list, setList] = useState(threadList);
  const [comment, setComment] = useState(false);
  const [commentId, setCommentId] = useState(0);
  const [zIndex, setZIndex] = useState(-1);
  const navigate = useNavigate();

  // 스로틀링을 위한 상태
  const [isThrottled, setIsThrottled] = useState(false);

  // useCallback 내에서 함수 정의
  const handlePrevClick = useCallback(() => {
    setComment(false);
    setZIndex(-1);
    if (activeIndex > 0) {
      setActiveIndex(activeIndex - 1);
    }
  }, [activeIndex]);

  // useCallback 내에서 함수 정의
  const handleNextClick = useCallback(() => {
    setActiveIndex(activeIndex + 1);
    setComment(false);
    setZIndex(-1);
    if (activeIndex === list.length - 3 && list.length - 4 < activeIndex) {
      setList([...list, ...threadList2]);
    }
  }, [activeIndex, list]);

  // useCallback으로 함수 래핑
  const handleWheelThrottled = useCallback(
    (event) => {
      if (!isThrottled) {
        if (event.deltaY < 0) {
          handlePrevClick();
        } else if (event.deltaY > 0) {
          handleNextClick();
        }
        setIsThrottled(true);
        setTimeout(() => setIsThrottled(false), 1000); // 0.5초 동안 다음 이벤트 차단
      }
    },
    [isThrottled, handlePrevClick, handleNextClick]
  );

  useEffect(() => {
    const stackCarouselContents = document.querySelector(
      `.${styles.StackCarousel_contents}`
    );

    if (stackCarouselContents) {
      stackCarouselContents.addEventListener("wheel", handleWheelThrottled);
    }

    return () => {
      if (stackCarouselContents) {
        stackCarouselContents.removeEventListener(
          "wheel",
          handleWheelThrottled
        );
      }
    };
  }, [handleWheelThrottled]);

  // useEffect(() => {
  //   setList(threadList);
  // }, []);

  return (
    <>
      <div className={styles.feedContainer}>
        <div
          className={s(
            styles.StackCarousel_contents,
            comment && styles.StackCarousel_translate
          )}
        >
          {list.map((thread, index) => (
            <div
              key={index}
              className={s(
                styles.StackCarousel_content,
                {
                  [styles.StackCarousel_above]: activeIndex > index,
                  [styles.StackCarousel_active]: activeIndex === index,
                  [styles.StackCarousel_second]: activeIndex === index - 1,
                  [styles.StackCarousel_third]: activeIndex === index - 2,
                },
                activeIndex < index - 2 ? "" : null
              )}
              style={{ zIndex: `-${index}` }}
            >
              {/* 하나의 쓰레드에 해당 */}
              <FeedContent
                index={index}
                thread={thread}
                setCommentId={setCommentId}
                setComment={setComment}
                setZIndex={setZIndex}
              />
            </div>
          ))}
        </div>

        <div
          className={s(
            styles.commentContainer,
            comment ? styles.commentActive : styles.commentHide
          )}
          style={{ zIndex: zIndex }}
        >
          <Comment commentId={commentId} />
        </div>
      </div>
      <div className={styles.create} onClick={() => navigate("/threadCreate")}>
        +
      </div>
    </>
  );
}
