package com.yupi.springbootinit.controller.admin;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWTUtil;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.AdminUsers;
import com.yupi.springbootinit.model.dto.admin.AdminLoginRequest;
import com.yupi.springbootinit.model.dto.admin.AdminRefreshTokenRequest;
import com.yupi.springbootinit.model.vo.AdminLoginVO;
import com.yupi.springbootinit.model.vo.AdminUserInfoVO;
import com.yupi.springbootinit.service.AdminUsersService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Api(tags = "管理员认证")
public class AdminAuthController {

    @Resource
    private AdminUsersService adminUsersService;

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    public BaseResponse<AdminLoginVO> login(@RequestBody AdminLoginRequest request) {
        String userName = request.getUserName();
        String password = request.getPassword();

        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码不能为空");
        }

        AdminUsers admin = adminUsersService.lambdaQuery()
                .eq(AdminUsers::getUsername, userName)
                .one();

        if (admin == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        if (admin.getStatus() == 0) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        if (!BCrypt.checkpw(password, admin.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        // 生成 JWT Token（7天有效）
        Long userId = admin.getId().longValue();
        String token = JwtUtils.generateToken(userId, admin.getRole());

        // 生成刷新令牌
        String refreshToken = UUID.randomUUID().toString().replace("-", "");

        // 保存刷新令牌
        admin.setRefreshToken(refreshToken);
        admin.setLastLoginTime(new Date());
        adminUsersService.updateById(admin);

        AdminLoginVO vo = new AdminLoginVO(token, refreshToken);
        return ResultUtils.success(vo);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取当前管理员信息")
    public BaseResponse<AdminUserInfoVO> getUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        String token = authHeader.substring(7);
        if (!JwtUtils.verify(token)) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        Long userId = JwtUtils.getUserId(token);
        Integer role = JwtUtils.getRole(token);

        AdminUsers admin = adminUsersService.getById(userId.intValue());
        if (admin == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        // 角色映射：3→R_SUPER  1→R_ADMIN
        String roleStr = role == 3 ? "R_SUPER" : "R_ADMIN";

        AdminUserInfoVO vo = new AdminUserInfoVO();
        vo.setUserId(String.valueOf(userId));
        vo.setUserName(admin.getUsername());
        vo.setRoles(Arrays.asList(roleStr));
        vo.setButtons(Arrays.asList("*:*:*"));

        return ResultUtils.success(vo);
    }

    @PostMapping("/refreshToken")
    @ApiOperation(value = "刷新 Token")
    public BaseResponse<AdminLoginVO> refreshToken(@RequestBody AdminRefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();
        if (!StringUtils.hasText(oldRefreshToken)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "刷新令牌不能为空");
        }

        AdminUsers admin = adminUsersService.lambdaQuery()
                .eq(AdminUsers::getRefreshToken, oldRefreshToken)
                .one();

        if (admin == null) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        if (admin.getStatus() == 0) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 签发新 Token
        Long userId = admin.getId().longValue();
        String newToken = JwtUtils.generateToken(userId, admin.getRole());
        String newRefreshToken = UUID.randomUUID().toString().replace("-", "");

        admin.setRefreshToken(newRefreshToken);
        adminUsersService.updateById(admin);

        AdminLoginVO vo = new AdminLoginVO(newToken, newRefreshToken);
        return ResultUtils.success(vo);
    }
}
