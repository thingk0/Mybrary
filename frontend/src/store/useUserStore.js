import { create } from "zustand";
import { persist } from "zustand/middleware";

const useUserStore = create(
  persist(
    (set) => ({
      user: {
        memberId: "",
        email: "",
        nickname: "",
        bookshelfId: 0,
        profileImageUrl: "",
      },
      setUser: (userInfo) => {
        set({ user: userInfo });
      },
    }),
    {
      name: "user-storage", // 로컬 스토리지에 저장될 때 사용될 키 이름
      getStorage: () => localStorage, // 사용할 스토리지 종류를 지정 (여기서는 localStorage)
    }
  )
);

export default useUserStore;

/* 꺼내서 쓰는 코드
 */
//  const memberId = useUserStore(state => state.user.memberId);
