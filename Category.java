package blogapp_api.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	 private int categoryId;
	
	 @Column(name="title",length = 100)
	 private String  categoryTitle;
	
	 private String categoryDescription;
	 @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  private List<Post> post=new ArrayList<>();

}
