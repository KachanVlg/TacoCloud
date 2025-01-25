package sia.tacocloud.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    //Принимает имя пользователя, ищет его в памяти и возвращает его UserDetails.
    //Если такого юзера нет, выбрасывает исключение
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
