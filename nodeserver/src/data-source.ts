import "reflect-metadata";
import { DataSource } from "typeorm";
import { Image } from "./entity/Image";

export const AppDataSource = new DataSource({
  type: "mysql",
  host: "thingk0.duckdns.org", // URL의 호스트 부분
  port: 3306, // 포트 번호
  username: "chiakpeaches", // 사용자 이름
  password: "chiak1234", // 비밀번호
  database: "mybrary", // 데이터베이스 이름
  synchronize: false, // 주의: 프로덕션 환경에서는 false로 설정하는 것이 좋습니다
  logging: false, // 로깅 여부
  entities: [Image], // 사용할 엔티티 배열
  migrations: [], // 사용할 마이그레이션 배열
  subscribers: [], // 사용할 서브스크라이버 배열
  // SSL 사용 설정
  extra: {
    ssl: true,
    // 다음 옵션은 필요에 따라 설정합니다.
    // allowPublicKeyRetrieval: true와 같이 추가 옵션을 여기에 넣을 수 있습니다.
    // 실제 연결에서 SSL 인증서를 검증해야 하는 경우에는
    // ssl: { rejectUnauthorized: true }와 같이 설정하고, 필요한 인증서 정보를 제공해야 합니다.
  },
});
