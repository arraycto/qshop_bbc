package co.lq.modules.system.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.system.domain.SellerUser;

/**
 * @author billy
 * @date 2018-11-22
 */
public interface SellerUserRepository extends JpaRepository<SellerUser, Long>, JpaSpecificationExecutor<SellerUser> {

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return /
     */
    SellerUser findByUsername(String username);

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return /
     */
    SellerUser findByEmail(String email);

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param pass 密码
     * @param lastPasswordResetTime /
     */
    @Modifying
    @Query(value = "update user set password = ?2 , last_password_reset_time = ?3 where username = ?1", nativeQuery = true)
    void updatePass(String username, String pass, Date lastPasswordResetTime);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email 邮箱
     */
    @Modifying
    @Query(value = "update user set email = ?2 where username = ?1", nativeQuery = true)
    void updateEmail(String username, String email);
}
