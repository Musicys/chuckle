package com.ruoyi.ry.file.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.ry.file.domain.RyBlogFile;
import com.ruoyi.ry.file.service.IRyBlogFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * blog文件Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/ry/file")
public class RyBlogFileController extends BaseController
{
    @Autowired
    private IRyBlogFileService ryBlogFileService;

    /**
     * 查询blog文件列表
     */
    @PreAuthorize("@ss.hasPermi('ry:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(RyBlogFile ryBlogFile)
    {
        startPage();
        List<RyBlogFile> list = ryBlogFileService.selectRyBlogFileList(ryBlogFile);
        return getDataTable(list);
    }


    /**
     * 获取blog文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('ry:file:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(ryBlogFileService.selectRyBlogFileByFileId(fileId));
    }

    /**
     * 新增blog文件
     */
    @PreAuthorize("@ss.hasPermi('ry:file:add')")
    @Log(title = "blog文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RyBlogFile ryBlogFile)
    {
        return toAjax(ryBlogFileService.insertRyBlogFile(ryBlogFile));
    }



    /**
     * 删除blog文件
     */
    @PreAuthorize("@ss.hasPermi('ry:file:remove')")
    @Log(title = "blog文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(ryBlogFileService.deleteRyBlogFileByFileIds(fileIds));
    }
}
