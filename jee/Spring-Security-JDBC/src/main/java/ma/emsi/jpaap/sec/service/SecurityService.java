package ma.emsi.jpaap.sec.service;

import ma.emsi.jpaap.sec.entities.AppRole;
import ma.emsi.jpaap.sec.entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleToUser(String username,String roleName);
    AppUser loadUserByUserName(String username);

}
