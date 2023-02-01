package com.fjh.security.service;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fanjh37
 * @since 2023/2/1 10:12
 */
public class MyJdbcDaoImpl extends JdbcDaoSupport implements UserDetailsService, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private boolean enableAuthorities = true;

    private boolean usernameBasedPrimaryKey = true;

    private String rolePrefix = "";
    private String usersByUsernameQuery = "select username,password,enabled from users where username = ?";

    private String authoritiesByUsernameQuery = "select username,authority from authorities where username = ?";


    @Override
    public void setMessageSource(MessageSource messageSource) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = this.loadUsersByUsername(username);
        if (users.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
        } else {
            UserDetails user =users.get(0);
            Set<GrantedAuthority> dbAuthsSet = new HashSet();
//            if (this.enableAuthorities) {
//                dbAuthsSet.addAll(this.loadUserAuthorities(user.getUsername()));
//            }
            List<GrantedAuthority> dbAuths = new ArrayList(dbAuthsSet);
//            this.addCustomAuthorities(user.getUsername(), dbAuths);
//            if (dbAuths.size() == 0) {
//                this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
//                throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority", new Object[]{username}, "User {0} has no GrantedAuthority"));
//            } else {
                return this.createUserDetails(username, user, dbAuths);
//            }
        }
    }

    protected void addCustomAuthorities(String username, List<GrantedAuthority> authorities) {
    }



    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return this.getJdbcTemplate().query(this.authoritiesByUsernameQuery, new String[]{username}, (rs, rowNum) -> {
            String roleName = this.rolePrefix + rs.getString(2);
            return new SimpleGrantedAuthority(roleName);
        });
    }

    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        if (!this.usernameBasedPrimaryKey) {
            returnUsername = username;
        }

        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(), userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
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
