package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author qiushengming
 * @date 2018/7/2
 */
@Table(value = "SYS_AUTHORITY", resultMapId = "AuthorityMap")
public class Authority
    extends BaseEntity
    implements GrantedAuthority {

    private String userId;

    private String authority;

    @Column("USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Authority
            && authority.equals(((Authority) obj).authority);

    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public String toString() {
        return this.authority;
    }


}
