package FinalPrjTest.FinalPrj.service;

import FinalPrjTest.FinalPrj.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DesignerService designerService;
    @Autowired
    public CustomUserDetailsService(DesignerService designerService) {
        this.designerService = designerService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 고객 리스트에서 일치하는 아이디를 찾았을 경우
        Customer customer = designerService.findByIdCustomer(id);
        if (customer != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (String role : customer.getRoles()) {
                System.out.println("커스터머의 권한"+role);
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new User(id, customer.getPw(), authorities);
        }

        // 디자이너 리스트에서 일치하는 아이디를 찾았을 경우
        Designer designer = designerService.findByIdDesigner(id);
        if (designer != null) {

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (String role : designer.getRoles()) {
                System.out.println("디자이너의 권한"+role);
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new User(id, designer.getPw(), authorities);
        }

        throw new UsernameNotFoundException("User not found with username: " + id);
    }



}
