package com.huawei.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author apple
 * @date 2018年2月19日-下午3:05:02
 * @description 全局异常过滤类
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@RestControllerAdvice
public class GlobalException {
	
	/**
	 * Description 
	 * @date 2018年2月19日-下午3:04:59
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public Map<String,Object> defaultException(
			HttpServletRequest request,HttpServletResponse response,Exception e){
		e.printStackTrace();
		Map<String,Object> map=new HashMap<String, Object>();
		String code="500";
		Object msg="系统错误"+code+"，请稍后再试";
		map.put(code, msg);
		return map;
	}
	
}
