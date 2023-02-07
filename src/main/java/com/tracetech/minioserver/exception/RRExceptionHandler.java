package com.tracetech.minioserver.exception;

import com.tracetech.minioserver.common.response.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author yangshaowei@tracetech.cn
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public CommonResult handleRRException(RRException e){
		logger.error(e.getMessage(), e);
		return CommonResult.error(e.getCode(),e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public CommonResult handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return CommonResult.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(Exception.class)
	public CommonResult handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return CommonResult.error(e.getMessage());
	}
}
