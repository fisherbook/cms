package net.stackoverflow.cms.security;

import lombok.extern.slf4j.Slf4j;
import net.stackoverflow.cms.constant.RedisPrefixConst;
import net.stackoverflow.cms.model.dto.PermissionDTO;
import net.stackoverflow.cms.model.dto.RoleDTO;
import net.stackoverflow.cms.model.entity.User;
import net.stackoverflow.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetails加载服务
 *
 * @author 凉衫薄
 */
@Component
@Slf4j
public class CmsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user != null) {
            List<RoleDTO> roleDTOS = userService.findRoleByUserId(user.getId());
            List<PermissionDTO> permissionDTOS = userService.findPermissionByUserId(user.getId());
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (RoleDTO roleDTO : roleDTOS) {
                SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + roleDTO.getName());
                authorities.add(sga);
            }
            for (PermissionDTO permissionDTO : permissionDTOS) {
                SimpleGrantedAuthority sga = new SimpleGrantedAuthority(permissionDTO.getName());
                authorities.add(sga);
            }
            Boolean lock = (Boolean) redisTemplate.opsForValue().get(RedisPrefixConst.LOCK_PREFIX + user.getId());
            return new CmsUserDetails(lock != null, user, authorities);
        } else {
            log.error("找不到对应的用户:{}", username);
            throw new UsernameNotFoundException(username);
        }
    }
}
