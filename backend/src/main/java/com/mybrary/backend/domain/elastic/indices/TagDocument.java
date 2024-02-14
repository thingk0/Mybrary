package com.mybrary.backend.domain.elastic.indices;

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

@Document(indexName = "tag")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long threadId;

    @Field(type = FieldType.Long)
    private Long paperId;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "verbatim", type = FieldType.Keyword),
                    @InnerField(suffix = "untouched", type = FieldType.Text, analyzer = "standard")
                })
    private String tagName;
}
