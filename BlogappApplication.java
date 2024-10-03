package blogapp_api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import blogapp_api.Repositiory.RoleRepo;
import blogapp_api.config.AppConstant;
import blogapp_api.entity.Role;

@SpringBootApplication
public class BlogappApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo ro;

    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Role role = new Role();
            role.setId(AppConstant.admin_user);
            role.setName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setId(AppConstant.normal_user);
            role1.setName("ROLE_USER");

            List<Role> list = List.of(role, role1);
            List<Role> result = this.ro.saveAll(list);
            result.forEach(r -> {
                System.out.println(r);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
