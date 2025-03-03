package com.ruoyi.ry.tag.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ry.tag.domain.RyTag;
import com.ruoyi.ry.tag.service.IRyTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签信息Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/ry/tag")
public class RyTagController extends BaseController
{
    @Autowired
    private IRyTagService ryTagService;


    /**
     * 查询标签信息列表
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(RyTag ryTag)
    {
        startPage();
        List<RyTag> list = ryTagService.selectRyTagList(ryTag);
        return getDataTable(list);
    }

    /**
     * 导出标签信息列表
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:export')")
    @Log(title = "标签信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RyTag ryTag)
    {
        List<RyTag> list = ryTagService.selectRyTagList(ryTag);
        ExcelUtil<RyTag> util = new ExcelUtil<RyTag>(RyTag.class);
        util.exportExcel(response, list, "标签信息数据");
    }

    /**
     * 获取标签信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") Long tagId)
    {
        return success(ryTagService.selectRyTagByTagId(tagId));
    }

    /**
     * 新增标签信息
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:add')")
    @Log(title = "标签信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RyTag ryTag)
    {
        return toAjax(ryTagService.insertRyTag(ryTag));
    }

    /**
     * 修改标签信息
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:edit')")
    @Log(title = "标签信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RyTag ryTag)
    {
        return toAjax(ryTagService.updateRyTag(ryTag));
    }

    /**
     * 删除标签信息
     */
    @PreAuthorize("@ss.hasPermi('ry:tag:remove')")
    @Log(title = "标签信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds)
    {
        return toAjax(ryTagService.deleteRyTagByTagIds(tagIds));
    }
}
