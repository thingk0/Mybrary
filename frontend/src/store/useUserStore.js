import create from "zustand";

const useUserStore = create((set) => ({
  user: {
    email: "",
    memberId: "",
    nickname: "",
  },
  setUser: (userInfo) => set({ user: userInfo }),
}));

export default useUserStore;

/* 꺼내서 쓰는 코드 
 const memberId = useUserStore(state => state.user.memberId);
*/
