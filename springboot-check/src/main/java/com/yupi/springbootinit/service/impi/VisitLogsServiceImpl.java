package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.VisitLogs;
import com.yupi.springbootinit.service.VisitLogsService;
import com.yupi.springbootinit.mapper.VisitLogsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【visit_logs(访问日志表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class VisitLogsServiceImpl extends ServiceImpl<VisitLogsMapper, VisitLogs>
    implements VisitLogsService{

}




