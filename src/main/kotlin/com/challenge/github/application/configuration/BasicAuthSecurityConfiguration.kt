package com.challenge.github.application.configuration

import org.springframework.security.core.userdetails.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class BasicAuthSecurityConfiguration (
        private val userProperties: UserProperties
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("users/{username}/repos")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        val githubUser: UserDetails = User.builder()
                .username(userProperties.name)
                .password(passwordEncoder().encode(userProperties.secret))
                .roles("ADMIN")
                .build()
        return InMemoryUserDetailsManager(githubUser)
    }
}