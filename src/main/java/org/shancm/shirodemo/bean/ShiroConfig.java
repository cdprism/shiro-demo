package org.shancm.shirodemo.bean;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        return manager;
    }

    /**
     * 认证过滤器：
     * anon：无需认证即可访问，游客身份。
     * authc：必须认证（登录）才能访问。
     * authcBasic：需要通过 httpBasic 认证。
     * user：不一定已通过认证，只要是曾经被 Shiro 记住过登录状态的用户就可以正常发起请求，比如 rememberMe。
     *
     * 授权过滤器:
     * perms：必须拥有对某个资源的访问权限（授权）才能访问。
     * role：必须拥有某个角色权限才能访问。
     * port：请求的端口必须为指定值才可以访问。
     * rest：请求必须是 RESTful，method 为 post、get、delete、put。
     * ssl：必须是安全的 URL 请求，协议为 HTTPS。
     */
    /*@Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        return factoryBean;
    }*/

    /**
     * 比如，我们创建三个页面，main.html、manage.html、administrator.html，要求如下：
     * 1、必须是登录状态才可以访问 main.html。
     * 2、用户必须拥有 manage 授权才可以访问 manage.html。
     * 3、用户必须拥有 administrator 角色才能访问 administrator.html。
     */
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        Map<String, String> map = new HashMap<>();
        map.put("/main", "authc");
        map.put("/manage", "perms[manage]");
        map.put("/administrator", "roles[administrator]");
        factoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        factoryBean.setLoginUrl("/login");
        //未授权页面
        factoryBean.setUnauthorizedUrl("/unAuth");
        return factoryBean;
    }
}
