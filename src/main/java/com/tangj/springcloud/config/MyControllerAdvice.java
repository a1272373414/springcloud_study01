package com.tangj.springcloud.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tangj.springcloud.common.exception.MyException;
import com.tangj.springcloud.common.exception.MyException2;

/**
 * controller 增强器
 * 
 */
@ControllerAdvice
public class MyControllerAdvice {

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "Magical Sam");
	}

	/**
	 * 全局异常捕捉处理
	 * @param ex
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map errorHandler(Exception ex) {
		Map map = new HashMap();
		map.put("code", 100);
		map.put("msg", ex.getMessage());
		return map;
	}

	/**
	 * 拦截捕捉自定义异常 MyException.class,返回json
	 * @param ex
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@ExceptionHandler(value = MyException.class)
	public Map myErrorHandler(MyException ex) {
		Map map = new HashMap();
		map.put("code", ex.getCode());
		map.put("msg", ex.getMsg());
		return map;
	}

	/**
	 * 拦截自定义异常,返回异常页面
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = MyException2.class)
	public ModelAndView myErrorHandler2(MyException2 ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("code", ex.getCode());
		modelAndView.addObject("msg", ex.getMsg());
		return modelAndView;
	}

}