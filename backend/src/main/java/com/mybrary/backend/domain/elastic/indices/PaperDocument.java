package com.mybrary.backend.domain.elastic.indices;

import com.mybrary.backend.domain.contents.paper.entity.Paper;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Document(indexName = "paper")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long threadId;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "exact", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
                })
    private String content1;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "exact", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
                })
    private String content2;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "exact", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
                })
    private String tagList;


    public static PaperDocument of(Long threadId, Paper paper) {
        return PaperDocument.builder()
                            .id(paper.getId())
                            .threadId(threadId)
                            .content1(paper.getContent1())
                            .content2(paper.getContent2())
                            .build();
    }

}
