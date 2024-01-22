import s from "classnames";
import styles from "./atomstyle/Nav.module.css";
import { useState } from "react";

export default function Nav() {
  const [active, setActive] = useState([false, true, false, false]);

  return (
    <div className={s(styles.nav_container)}>
      <div className={s(styles.nav_paper0, styles.nav_paper)}>
        <div className={s(styles.nav_paper1, styles.nav_paper)}>
          <div className={s(styles.nav_paper2, styles.nav_paper)}>
            <div className={s(styles.nav_paper3, styles.nav_paper)}>
              <div className={s(styles.nav_icon)}>알림</div>
              <div className={s(styles.nav_icon)}>설정</div>
              <div className={s(styles.nav_icon)}>로아</div>
            </div>
          </div>
        </div>
      </div>
      <div className={s(styles.nav_section_container)}>
        <div
          className={s(
            styles.nav_section,
            active[0] ? styles.nav_active : styles.nav_not_active
          )}
          onClick={() => setActive([true, false, false, false])}
        >
          {active[0] ? "마이" : ""}
        </div>
        <div
          className={s(
            styles.nav_section,
            active[1] ? styles.nav_active : styles.nav_not_active
          )}
          onClick={() => setActive([false, true, false, false])}
        >
          {active[1] ? "피드" : ""}
        </div>
        <div
          className={s(
            styles.nav_section,
            active[2] ? styles.nav_active : styles.nav_not_active
          )}
          onClick={() => setActive([false, false, true, false])}
        >
          {active[2] ? "피피" : ""}
        </div>
        <div
          className={s(
            styles.nav_section,
            active[3] ? styles.nav_active : styles.nav_not_active
          )}
          onClick={() => setActive([false, false, false, true])}
        >
          {active[3] ? "검색" : ""}
        </div>
      </div>
    </div>
  );
}
