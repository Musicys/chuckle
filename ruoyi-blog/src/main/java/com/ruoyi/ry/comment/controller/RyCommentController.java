package com.ruoyi.ry.comment.controller;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
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
import com.ruoyi.ry.comment.domain.RyComment;
import com.ruoyi.ry.comment.service.IRyCommentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/ry/comment")
public class RyCommentController extends BaseController
{
    @Autowired
    private IRyCommentService ryCommentService;

    @Autowired
    private SysPermissionService permissionService;
    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(RyComment ryComment)
    {
        startPage();
        // Set<String> roles = permissionService.getRolePermission(getLoginUser().getUser());
        if(!SecurityUtils.isAdmin(getUserId())){
            ryComment.setCreateBy(getUsername());
        }
        ryComment.setDelFlag("0");
        List<RyComment> list = ryCommentService.selectRyCommentList(ryComment);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:export')")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RyComment ryComment)
    {
        List<RyComment> list = ryCommentService.selectRyCommentList(ryComment);
        ExcelUtil<RyComment> util = new ExcelUtil<RyComment>(RyComment.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ryCommentService.selectRyCommentById(id));
    }

    /**
     * 新增评论
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:add')")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RyComment ryComment)
    {
        return toAjax(ryCommentService.insertRyComment(ryComment));
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:edit')")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RyComment ryComment)
    {
        return toAjax(ryCommentService.updateRyComment(ryComment));
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('ry:comment:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ryCommentService.deleteRyCommentByIds(ids));
    }
}
