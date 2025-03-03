package com.ruoyi.ry.blog.controller;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.ry.tag.domain.RyTag;
import com.ruoyi.ry.tag.service.IRyTagService;
import com.ruoyi.ry.type.domain.RyType;
import com.ruoyi.ry.type.service.IRyTypeService;
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
import com.ruoyi.ry.blog.domain.RyBlog;
import com.ruoyi.ry.blog.service.IRyBlogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 博客信息Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/ry/blog")
public class RyBlogController extends BaseController
{
    @Autowired
    private IRyBlogService ryBlogService;
    @Autowired
    private SysPermissionService permissionService;
    @Resource
    private IRyTagService ryTagService;
    @Resource
    private IRyTypeService ryTypeService;


    /**
     * 查询博客信息列表
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(RyBlog ryBlog)
    {
        System.out.println("list被调用..");
        startPage();
        //状态发布
        Set<String> roles = permissionService.getRolePermission(getLoginUser().getUser());
        if(!SecurityUtils.isAdmin(getUserId()) && !roles.contains("admin")){
            ryBlog.setCreateBy(getUsername());
        }
        List<RyBlog> list = ryBlogService.selectRyBlogList(ryBlog);
        return getDataTable(list);
    }

    /**
     * 导出博客信息列表
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:export')")
    @Log(title = "博客信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RyBlog ryBlog)
    {
        List<RyBlog> list = ryBlogService.selectRyBlogList(ryBlog);
        ExcelUtil<RyBlog> util = new ExcelUtil<RyBlog>(RyBlog.class);
        util.exportExcel(response, list, "博客信息数据");
    }

    /**
     * 获取博客信息详细信息
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:query')")
    @GetMapping(value = {"/","/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id" , required = false) Long id)
    {
        AjaxResult ajaxResult = AjaxResult.success();
        RyType ryType = new RyType();
        RyTag ryTag = new RyTag();
        ajaxResult.put("types",ryTypeService.selectRyTypeList(ryType));
        ajaxResult.put("tags",ryTagService.selectRyTagList(ryTag));
        if(StringUtils.isNotNull(id)){
            ajaxResult.put(AjaxResult.DATA_TAG,ryBlogService.selectRyBlogById(id));
        }

        return ajaxResult;
    }

    /**
     * 新增博客信息
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:add')")
    @Log(title = "博客信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RyBlog ryBlog)
    {
        ryBlog.setCreateBy(getUsername());
        Long blogId = ryBlogService.insertRyBlog(ryBlog);

        if(!StringUtils.isNotNull(blogId)){
            return AjaxResult.error("msg","id为空");
        }
        return AjaxResult.success(blogId);
    }

    /**
     * 修改博客信息
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:edit')")
    @Log(title = "博客信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RyBlog ryBlog)
    {
        //修改信息 需要带tags和types

        return toAjax(ryBlogService.updateRyBlog(ryBlog));
    }

    /**
     * 删除博客信息
     */
    // @PreAuthorize("@ss.hasPermi('ry:blog:remove')")
    @Log(title = "博客信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ryBlogService.deleteRyBlogByIds(ids));
    }
}
