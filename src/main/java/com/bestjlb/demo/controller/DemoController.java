package com.bestjlb.demo.controller;

import com.bestjlb.demo.meta.Demo;
import com.bestjlb.demo.service.DemoService;
import com.bestjlb.demo.validator.DemoValidator;
import com.bestjlb.demo.vo.DemoVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoValidator validator;

    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "1.1 添加", notes = "添加一条新数据", response = Demo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "name", name = "name", dataType = "String", paramType = "query", defaultValue = "", required = true)
    })
    @ApiResponses({
            @ApiResponse(response = Demo.class, code = 200, message = "Success")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Demo add(String name) {
        return demoService.save(new Demo(name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Demo> getAll() {
        return demoService.getAll();
    }

    @RequestMapping(value = "/modify/name", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public boolean updateName(@Valid @RequestBody DemoVO demoVO, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getAllErrors().get(0).getDefaultMessage());
        }
        validator.validate(demoVO, result);
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getAllErrors().get(0).getDefaultMessage());
        }
//        return demoService.updateName(demo.getId(), demo.getName()) > 0;
        Demo old = demoService.getById(demoVO.getId());
        if (old == null || old.getId() < 1L) {
            return false;
        }
        old.setName(demoVO.getName());
        return demoService.save(old) != null;
    }

    @PreAuthorize("hasRole('ROLE_YY')")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "id", name = "id", dataType = "long", paramType = "query", required = true)
    })
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Demo getById(long id) {
        return demoService.getById(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "id", name = "id", dataType = "long", paramType = "query", required = true)
    })
    @PreAuthorize("hasRole('ROLE_GUEST')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(long id) {
        demoService.deleteById(id);
    }

    @RequestMapping(value = "/listInSet", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Demo> findInSet(@RequestBody List<Long> ids) {
        return demoService.findInSet(ids);
    }
}
