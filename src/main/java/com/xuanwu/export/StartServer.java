package com.xuanwu.export;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xuanwu.export.service.ExportService;

/**
 * @description 程序启动类
 * @author <a href="mailto:huyaoke@wxchina.com">YaoKe.Hu</a>
 * @Date 2017年11月24日
 * @version 1.0.0
 */
public class StartServer {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
		ExportService bean = context.getBean("exportService", ExportService.class);
		bean.exportFile();
	}
}
