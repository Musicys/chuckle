package com.ruoyi.ry.message.controller;

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
import com.ruoyi.ry.message.domain.RyMessage;
import com.ruoyi.ry.message.service.IRyMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 留言Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/ry/message")
public class RyMessageController extends BaseController
{
    @Autowired
    private IRyMessageService ryMessageService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 查询留言列表
     */
    @PreAuthorize("@ss.hasPermi('ry:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(RyMessage ryMessage)
    {
        startPage();
        //list
        // Set<String> roles = permissionService.getRolePermission(getLoginUser().getUser());
        //查询登录的用户是不是admin不是的话就只查询自己创造的留言
        if(!SecurityUtils.isAdmin(getUserId())){
            ryMessage.setCreateBy(getUsername());
        }
        ryMessage.setDelFlag("0");
        //查询没有删除的留言 0存在 1删除
        List<RyMessage> list = ryMessageService.selectRyMessageList(ryMessage);
        return getDataTable(list);
    }

    /**
     * 导出留言列表
     */
    @PreAuthorize("@ss.hasPermi('ry:message:export')")
    @Log(title = "留言", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RyMessage ryMessage)
    {
        List<RyMessage> list = ryMessageService.selectRyMessageList(ryMessage);
        ExcelUtil<RyMessage> util = new ExcelUtil<RyMessage>(RyMessage.class);
        util.exportExcel(response, list, "留言数据");
    }

    /**
     * 获取留言详细信息
     */
    @PreAuthorize("@ss.hasPermi('ry:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ryMessageService.selectRyMessageById(id));
    }

    /**
     * 新增留言
     */
    @PreAuthorize("@ss.hasPermi('ry:message:add')")
    @Log(title = "留言", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RyMessage ryMessage)
    {
        return toAjax(ryMessageService.insertRyMessage(ryMessage));
    }

    /**
     * 修改留言
     */
    @PreAuthorize("@ss.hasPermi('ry:message:edit')")
    @Log(title = "留言", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RyMessage ryMessage)
    {
        return toAjax(ryMessageService.updateRyMessage(ryMessage));
    }

    /**
     * 删除留言
     */
    @PreAuthorize("@ss.hasPermi('ry:message:remove')")
    @Log(title = "留言", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ryMessageService.deleteRyMessageByIds(ids));
    }
}
