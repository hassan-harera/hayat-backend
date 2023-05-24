package com.harera.hayat.shared.model.announcement;

import com.harera.hayat.framework.model.BaseDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "announcements")
public class Announcement extends BaseDocument {

    @Field(name = "title")
    public String title;

    @Field(name = "description")
    public String description;

    @Field(name = "image_url")
    public String imageUrl;
}
