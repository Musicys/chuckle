package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.model.vo.TagVO;
import com.yupi.springbootinit.service.TagsService;
import com.yupi.springbootinit.mapper.TagsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 黎旺
* @description 针对表【tags(标签表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService {

    @Override
    public List<TagVO> listTagCounts() {
        List<Map<String, Object>> maps = baseMapper.listTagCounts();
        return maps.stream().map(this::toTagVO).collect(Collectors.toList());
    }

    private TagVO toTagVO(Map<String, Object> map) {
        TagVO vo = new TagVO();
        Number id = (Number) map.get("id");
        vo.setId(id != null ? id.intValue() : null);
        vo.setName((String) map.get("name"));
        vo.setColor((String) map.get("color"));
        Number count = (Number) map.get("article_count");
        vo.setArticleCount(count != null ? count.intValue() : 0);
        return vo;
    }
}




