package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.service.PortfolioService;
import com.yupi.springbootinit.mapper.PortfolioMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【portfolio(作品集表)】的数据库操作Service实现
* @createDate 2026-07-25 18:20:55
*/
@Service
public class PortfolioServiceImpl extends ServiceImpl<PortfolioMapper, Images.Portfolio>
    implements PortfolioService{

}




