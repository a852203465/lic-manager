package cn.darkjrong.licmanager.controller;

import cn.darkjrong.licmanager.common.pojo.dto.KeystoreDTO;
import cn.darkjrong.licmanager.common.pojo.dto.KeystoreFilterDTO;
import cn.darkjrong.licmanager.common.pojo.vo.KeystoreVO;
import cn.darkjrong.licmanager.common.pojo.vo.PageVO;
import cn.darkjrong.licmanager.common.pojo.vo.ResponseVO;
import cn.darkjrong.licmanager.service.KeystoreService;
import cn.hutool.extra.validation.ValidationUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 秘钥库 前端控制器
 * </p>
 *
 * @author Rong.Jia
 * @since 2023-07-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/keystore")
public class KeystoreController {

    @Autowired
    private KeystoreService keystoreService;

    @ApiOperation("添加秘钥库")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> saveKeystore(@Validated @RequestBody KeystoreDTO keystoreDTO) {
        log.info("saveKeystore {}", keystoreDTO.toString());
        keystoreService.saveKeystore(keystoreDTO);
        return ResponseVO.success();
    }

    @ApiOperation("添加秘钥库")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> updateKeystore(@Validated @RequestBody KeystoreDTO keystoreDTO) {
        log.info("updateKeystore {}", keystoreDTO.toString());
        keystoreService.updateKeystore(keystoreDTO);
        return ResponseVO.success();
    }

    @ApiOperation("查询秘钥库")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<PageVO<KeystoreVO>> queryKeystore(@Validated KeystoreFilterDTO filterDTO) {
        log.info("queryKeystore {}", filterDTO.toString());
        ValidationUtil.validate(filterDTO);
        return ResponseVO.success(keystoreService.page(filterDTO));
    }

    @ApiOperation("删除秘钥库")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class, value = "主键ID", required = true),
    })
    public ResponseVO<Void> deleteKeystore(@PathVariable("id") @NotNull(message = "主键ID 不能为空") Long id) {
        log.info("deleteKeystore {}", id);
        keystoreService.delete(id);
        return ResponseVO.success();
    }

    @ApiOperation("批量删除秘钥库")
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<Void> deleteKeystoreList(@RequestBody @Validated List<Long> ids) {
        log.info("deleteKeystoreList {}", ids);
        keystoreService.deleteKeystore(ids);
        return ResponseVO.success();
    }



}
