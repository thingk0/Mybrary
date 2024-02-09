import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";

@Entity("image") // "image"는 데이터베이스 테이블 이름과 일치해야 합니다.
export class Image {
  @PrimaryGeneratedColumn()
  image_id: number;

  @Column({
    type: "boolean",
    nullable: true,
    default: () => "'0'", // SQL의 bit은 TypeORM에서 boolean 타입으로 매핑되며, 기본 값으로 '0' (false)을 사용할 수 있습니다.
  })
  is_deleted: boolean | null;

  @Column({
    type: "double",
    nullable: true,
  })
  size: number | null;

  @Column({
    type: "timestamp",
    default: () => "CURRENT_TIMESTAMP",
  })
  created_at: Date | null;

  @Column({
    type: "timestamp",
    onUpdate: "CURRENT_TIMESTAMP", // MySQL의 경우, 업데이트 시간 자동 갱신
    default: () => "CURRENT_TIMESTAMP",
  })
  modified_at: Date | null;

  @Column({
    type: "varchar",
    length: 255,
    nullable: true,
  })
  format: string | null;

  @Column({
    type: "varchar",
    length: 255,
    nullable: true,
  })
  image_name: string | null;

  @Column({
    type: "varchar",
    length: 255,
    nullable: true,
  })
  image_url: string | null;
}
