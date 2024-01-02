package com.sukanta.springboottodo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "todos")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Todo {
    @Id
    private String id = String.valueOf(ObjectId.get());
    @Field(name = "title")
    @NonNull
    private String title;
    @Field(name = "description")
    @NonNull
    private String description;
    @Field("createdBy")
    private String createdBy;
    private User user;
    @Field(name = "createdAt")
    @CreatedDate
    private LocalDateTime createdAt;
    @Field(name = "updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Todo() {
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

