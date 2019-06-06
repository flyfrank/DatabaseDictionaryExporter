package com.xuanwu.export.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @description 导出文档处理
 * @author <a href="mailto:huyaoke@wxchina.com">YaoKe.Hu</a>
 * @Date 2017年10月30日
 * @version 1.0.0
 */
public class DocumentHandler {

	/**
	 * 创建word文档
	 * 
	 * @param dataMap
	 *            数据
	 * @param fileName
	 *            导出文件名称
	 * @param templatePath
	 *            模板路径
	 * @param templateName
	 *            模板名称
	 * @throws IOException
	 */
	public void createDoc(Map<String, Object> dataMap, String fileName, String templatePath, String templateName)
			throws IOException {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), templatePath);

		Template template = template = configuration.getTemplate(templateName);

		// 输出文档路径及名称
		File outFile = new File(fileName);
		Writer out = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFile);
			// 导出文件编码设置
			OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
			out = new BufferedWriter(oWriter);

			template.process(dataMap, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			out.close();
			fos.close();
		}
	}
}
