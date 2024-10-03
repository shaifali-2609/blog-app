package blogapp_api.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	 private int categoryId;
	 @NotEmpty(message = "title cant be empty")
	 private String  categoryTitle;
	 @NotEmpty(message = "please discribe somthing")
	 private String categoryDescription;


}
