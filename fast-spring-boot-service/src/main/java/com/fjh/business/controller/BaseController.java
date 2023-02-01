package com.fjh.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fjh.common.ResponseResult;
import com.fjh.common.ValidationGroup;
import com.fjh.business.entity.MBaseEntity;
import com.fjh.business.service.MBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.config.CacheManagementConfigUtils;
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
@Api(tags = "测试")
@RestController
public class BaseController {

    @Autowired
    private MBaseService mBaseService;

    @Autowired
    private CacheManager cacheManager;

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
    public ResponseResult<Long> getById(Long id) {
        Cache  cache= cacheManager.getCache("test");
        cache.put("id",id);
        Long idd= (Long) cache.get("id").get();
//        return ResponseResult.success(mBaseService.getById(id));
        return ResponseResult.success(idd);
    }

}
