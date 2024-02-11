package com.mybrary.backend.domain.contents.thread.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE thread SET is_deleted = TRUE WHERE thread_id = ?")
public class Thread extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thread_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mybrary_id")
    private Mybrary mybrary;

    @Builder.Default()
    @Column(name = "is_scrap_enabled")
    private boolean isScrapEnabled = true;

    @Builder.Default()
    @Column(name = "is_paper_public")
    private boolean isPaperPublic = true;

    /* thread - paper 양방향 관계 설정 */
    @Builder.Default
    @OneToMany(mappedBy = "thread", cascade = {CascadeType.REMOVE})
    private List<Paper> paperList = new ArrayList<>();

    public void updateScrapEnabled(boolean enabled){
        isScrapEnabled = enabled;
    }
    public void updatePaperPublic(boolean enabled){
        isPaperPublic = enabled;
    }
}

