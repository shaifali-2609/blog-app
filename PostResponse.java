package blogapp_api.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
private List<PostDto> content;
private int pagesize;
private int pagenumber;
private long totalelement;
private int totalpage;
private boolean lstpage;
}
