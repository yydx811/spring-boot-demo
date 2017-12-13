package com.bestjlb.demo.validator;

import com.bestjlb.demo.vo.DemoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Component
public class DemoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return DemoVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DemoVO demoVO = (DemoVO) o;
        if (demoVO.getId() < 1L) {
            errors.reject("demo.id.required", "id is required!");
            return;
        }
        if (StringUtils.isBlank(demoVO.getName())) {
            errors.reject("demo.name.empty", "name can not be empty!");
            return;
        }
    }
}
