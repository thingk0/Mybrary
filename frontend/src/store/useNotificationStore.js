import { create } from "zustand";
import { persist } from "zustand/middleware";

const useNotificationStore = create(
  persist(
    (set) => ({
      hasNewNotification: false, // 새 알림이 있는지 여부
      notificationMessage: "", // 알림 메시지 내용

      setNewNotification: (hasNew, message = "") =>
        set({ hasNewNotification: hasNew, notificationMessage: message }),
    }),
    {
      name: "user-storage", // 로컬 스토리지에 저장될 때 사용될 키 이름
      getStorage: () => localStorage, // 사용할 스토리지 종류를 지정 (여기서는 localStorage)
    }
  )
);

export default useNotificationStore;
