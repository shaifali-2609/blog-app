package blogapp_api.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import blogapp_api.entity.Category;
import blogapp_api.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int postid;

    @NotEmpty(message = "title cant be empty")
    @Column(name="title")
    private String title;

    @NotEmpty(message = "content cant be empty")
    private String content;

    private String imagename;
    private Date adddate;

    @JsonIgnore
    private Category category;

    @JsonIgnore
    private User user;

    private List<CommentDto> comments = new ArrayList<>();
}
