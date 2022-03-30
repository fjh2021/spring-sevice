package com.fjh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fjh.common.ResponseResult;
import com.fjh.common.ValidationGroup;
import com.fjh.entity.MBaseEntity;
import com.fjh.service.MBaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/3/30 15:39
 */
public class BaseController {

    @Autowired
    private MBaseService mBaseService;

    @PostMapping("save")
    @ApiOperation("保存")
    public ResponseResult<Boolean> save(@RequestBody @Validated(ValidationGroup.Insert.class) MBaseEntity baseParam) {
        return ResponseResult.success(mBaseService.save(baseParam));
    }

    @PutMapping("edit")
    @ApiOperation("修改")
    public ResponseResult<Boolean> edit(@RequestBody @Validated(ValidationGroup.Update.class) MBaseEntity baseParam) {
        return ResponseResult.success(mBaseService.updateById(baseParam));
    }

    @DeleteMapping("del")
    @ApiOperation("删除")
    public ResponseResult<Boolean> delete(Long id) {
        return ResponseResult.success(mBaseService.removeById(id));
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    public ResponseResult<IPage<MBaseEntity>> pageList(Page page) {
        return ResponseResult.success(mBaseService.page(page));
    }

    @GetMapping("getById")
    @ApiOperation("详情")
    public ResponseResult<MBaseEntity> getById(Long id) {
        return ResponseResult.success(mBaseService.getById(id));
    }

}
