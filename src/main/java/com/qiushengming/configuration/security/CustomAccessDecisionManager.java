package com.qiushengming.configuration.security;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 校验
 *
 * @author qiushengming
 * @date 2018/7/1
 */
@Service(value = "accessDecisionManager")
public class CustomAccessDecisionManager
    implements AccessDecisionManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 进行校验，决定当前访问是否具备权限
     * Resolves an access control decision for the passed parameters.
     *
     * @param authentication   调用该方法的调用者(非空)，里面包含调用者所具备的权限信息
     * @param object           被调用的受保护对象
     * @param configAttributes 与被调用的受保护对象相关联的配置属性，访问所需要的权限{@link CustomInvocationSecurityMetadataSource#loadResourceDefine()}
     * @throws AccessDeniedException               如果访问被拒绝，因为身份验证不具有所需的权限或ACL特权
     * @throws InsufficientAuthenticationException 如果访问被拒绝，因为身份验证没有提供足够的信任级别
     */
    @Override
    public void decide(Authentication authentication, Object object,
        Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {

        if (CollectionUtils.isEmpty(configAttributes)) {
            logger.debug("未定义任何资源，未对任何资源进行权限配置");
            return;
        }

        String needRole;
        for (ConfigAttribute c : configAttributes) {
            needRole = c.getAttribute();
            //authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (StringUtils.equals(needRole.trim(), ga.getAuthority())) {
                    return;
                }
            }
        }

        logger.warn("没有权限！---------------------------");
        throw new AccessDeniedException("没有权限！---------------------------");
    }

    /**
     * Indicates whether this <code>AccessDecisionManager</code> is able to process
     * authorization requests presented with the passed <code>ConfigAttribute</code>.
     * <p>
     * This allows the <code>AbstractSecurityInterceptor</code> to check every
     * configuration attribute can be consumed by the configured
     * <code>AccessDecisionManager</code> and/or <code>RunAsManager</code> and/or
     * <code>AfterInvocationManager</code>.
     * </p>
     *
     * @param attribute a configuration attribute that has been configured against the
     *                  <code>AbstractSecurityInterceptor</code>
     * @return true if this <code>AccessDecisionManager</code> can support the passed
     * configuration attribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * Indicates whether the <code>AccessDecisionManager</code> implementation is able to
     * provide access control decisions for the indicated secured object type.
     *
     * @param clazz the class that is being queried
     * @return <code>true</code> if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
