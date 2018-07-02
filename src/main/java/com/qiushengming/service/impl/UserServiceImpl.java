package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.entity.User;
import com.qiushengming.mybatis.support.Criteria;
import com.qiushengming.service.PermissionService;
import com.qiushengming.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinMin
 * @date 2018/3/31
 */
@Service(value = "userDetailsService")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl
    extends AbstractManagementService<User>
    implements UserService, UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Override
    public List<User> findAllUser() {
        return this.getAll();
    }

    /**
     * 如果查询多个同名称的用户，则返回第一个
     *
     * @param name 用户名称
     * @return 用户实体
     */
    @Override
    @Cacheable(value = "user", key = "#name")
    public User findUserName(String name) {
        Criteria criteria = Criteria.create().andEqualTo("userName", name);
        User user = queryOneByCriteria(criteria);

        if (user == null) {
            logger.warn("{} - 用户不存在", name);
            throw new UsernameNotFoundException(name + "，用户不存在！");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //对应的权限添加
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        user.setAuthorities(authorities);

        return user;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = findUserName(username);

        logger.debug("{} - 用户登录", user.getUserName());

        return new org.springframework.security.core.userdetails.User(user.getUserName(),
            user.getPassWord(),
            user.getAuthorities());
    }
}
