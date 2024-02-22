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
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String email;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "verbatim", type = FieldType.Keyword),
                    @InnerField(suffix = "untouched", type = FieldType.Text, analyzer = "standard")
                })
    private String name;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
                otherFields = {
                    @InnerField(suffix = "exact", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
                })
    private String nickname;

    @Field(type = FieldType.Text, index = false)
    private String intro;

    @Field(type = FieldType.Keyword, index = false)
    private String profileImageUrl;
}

