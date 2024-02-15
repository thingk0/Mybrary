package com.mybrary.backend.domain.elastic.indices;

import com.mybrary.backend.domain.contents.paper.dto.requestDto.PaperUpdateDto;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import com.mybrary.backend.domain.contents.tag.entity.Tag;
import java.time.LocalDateTime;
import java.util.List;
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


    public static PaperDocument of(Long threadId, Paper paper, String tagList) {
        return PaperDocument.builder()
                            .id(paper.getId())
                            .threadId(threadId)
                            .content1(paper.getContent1())
                            .content2(paper.getContent2())
                            .tagList(tagList)
                            .build();
    }

    public void update(PaperUpdateDto updateDto) {
        this.content1 = updateDto.getContent1();
        this.content2 = updateDto.getContent2();
        this.tagList = getTags(updateDto.getTagList()).toString();
    }

    private static String getTags(List<String> tagList) {
        StringBuilder tags = new StringBuilder();
        if (!tagList.isEmpty()) {
            for (String tag : tagList) {
                tags.append(tag).append(' ');
            }
        }
        return tags.toString();
    }

}
