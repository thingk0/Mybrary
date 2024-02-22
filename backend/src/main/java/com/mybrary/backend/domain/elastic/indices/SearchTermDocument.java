package com.mybrary.backend.domain.elastic.indices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Document(indexName = "search_term")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchTermDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String email;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "nori"),
                otherFields = {
                    @InnerField(suffix = "exact", type = FieldType.Keyword),
                    @InnerField(suffix = "autocomplete", type = FieldType.Text, analyzer = "autocomplete_analyzer")
                })
    private String searchTerm;

    @Field(type = FieldType.Date)
    private LocalDate timestamp;

    public static SearchTermDocument of(String email, String searchTerm) {
        return SearchTermDocument.builder()
                                 .id(String.valueOf(UUID.randomUUID()))
                                 .email(email)
                                 .searchTerm(searchTerm)
                                 .timestamp(LocalDate.now())
                                 .build();
    }

}
