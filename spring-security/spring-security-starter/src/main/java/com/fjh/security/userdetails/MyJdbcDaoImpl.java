package com.fjh.security.userdetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author fanjh37
 * @since 2023/2/1 10:12
 */
public class MyJdbcDaoImpl extends JdbcDaoSupport implements UserDetailsService, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


    @Value("${spring.security.usernameQuery:select username,password,enabled from users where username = ?}")
    private String usersByUsernameQuery;

    @Override
    public void setMessageSource(MessageSource messageSource) {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = this.loadUsersByUsername(username);
        if (users.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
        }
        UserDetails user = users.get(0);
        return this.createUserDetails(user, getAuthorityByUsername(username));
    }

    protected List<GrantedAuthority> getAuthorityByUsername(String username) {

        String[] authorityList = {"getCurrentUser", "getById"};
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.createAuthorityList(authorityList);
        return grantedAuthorityList;
    }

    protected UserDetails createUserDetails(UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        return new User(userFromUserQuery.getUsername(), userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(), userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
    }

    protected List<UserDetails> loadUsersByUsername(String username) {
        RowMapper<UserDetails> mapper = (rs, rowNum) -> {
            String username1 = rs.getString(1);
            String password = rs.getString(2);
            boolean enabled = rs.getBoolean(3);
            return new User(username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        };
        return this.getJdbcTemplate().query(this.usersByUsernameQuery, mapper, new Object[]{username});
    }

}
