package ma.emsi.jpaap.sec.repositories;

import ma.emsi.jpaap.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository  extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
