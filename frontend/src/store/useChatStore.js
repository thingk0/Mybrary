import { create } from "zustand";

const useChatStore = create(
  (set) => ({
    chatId: null,
    setBook: (Book) => {
      set({ book: Book });
    },
  }),
  {
    name: "book-storage", // 로컬 스토리지에 저장될 때 사용될 키 이름
    getStorage: () => localStorage, // 사용할 스토리지 종류를 지정 (여기서는 localStorage)
  }
);

export default useChatStore;
