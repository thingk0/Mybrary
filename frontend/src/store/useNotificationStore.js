import { create } from "zustand";

const useNotificationStore = create((set) => ({
  hasNewNotification: false, // 새 알림이 있는지 여부
  notificationMessage: "", // 알림 메시지 내용

  setNewNotification: (hasNew, message = "") =>
    set({ hasNewNotification: hasNew, notificationMessage: message }),
}));

export default useNotificationStore;
