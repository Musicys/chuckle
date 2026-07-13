package com.yupi.springbootinit;

import cn.hutool.crypto.digest.BCrypt;
import com.yupi.springbootinit.mapper.AdminUsersMapper;
import com.yupi.springbootinit.model.domain.AdminUsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest(properties = {
    "wx.mp.app-id=test",
    "wx.mp.secret=test",
    "spring.session.store-type=none",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration"
})
@Transactional
class AdminUsersTest {

    @Resource
    private AdminUsersMapper adminUsersMapper;

    @Test
    void testInsertAdminUser() {
        AdminUsers user = new AdminUsers();
        user.setUsername("admin");

        user.setPassword(BCrypt.hashpw("12345678"));
        System.out.println(BCrypt.hashpw("12345678"));
        user.setNickname("管理员");
        user.setRole(3);
        user.setStatus(1);

        int result = adminUsersMapper.insert(user);

        Assertions.assertEquals(1, result);
        Assertions.assertNotNull(user.getId());
    }
}
