package com.xuanwu.export.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xuanwu.export.entity.DatabaseTable;
import com.xuanwu.export.entity.TableField;
import com.xuanwu.export.handler.DocumentHandler;

/**
 * @description 导出数据库字典
 * @author <a href="mailto:huyaoke@wxchina.com">YaoKe.Hu</a>
 * @Date 2017年11月24日
 * @version 1.0.0
 */
public class ExportService {

	private final static String TEMPLATE_PATH = "/";
	private ConnectDBService connectDBService;
	private DocumentHandler documentHandler;
	/**
	 * 导出文档名称
	 */
	private String fileName;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 导出文件路径
	 */
	private String exportPath;
	/**
	 * 系统名称
	 */
	private String systemName;
	/**
	 * 版本
	 */
	private String systemVersion;

	/**
	 * 导出文件
	 */
	public void exportFile() {
		// 文件默认名称
		if (fileName == null || "".equals(fileName)) {
			fileName = "database.doc";
		}
		Map<String, Object> dataMap = new HashMap();
		List<DatabaseTable> tables = this.getTables();
		if (tables == null) {
			System.out.println("数据库信息为空！");
		} else {
			Calendar cal = Calendar.getInstance();
			dataMap.put("year", cal.get(Calendar.YEAR));
			dataMap.put("month", cal.get(Calendar.MONTH) + 1);
			dataMap.put("systemName", systemName);
			dataMap.put("systemVersion", systemVersion);
			dataMap.put("tables", tables);
			try {
				documentHandler.createDoc(dataMap, fileName, TEMPLATE_PATH, templateName);
				System.out.println("导出完成！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取表信息
	 * 
	 * @return
	 */
	private List<DatabaseTable> getTables() {
		Connection conn = connectDBService.getDBConnection();
		List<DatabaseTable> tables = null;
		if (conn == null) {
			System.out.println("数据库连接失败，请检测数据库连接配置是否正确！");
		} else {
			try {
				tables = new ArrayList<DatabaseTable>();
				DatabaseMetaData dbMetaData = conn.getMetaData();
				ResultSet resultSet = dbMetaData.getTables(null, null, null, new String[] { "TABLE" });
				Statement stmt = conn.createStatement();
				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");
					String tableRemark = resultSet.getString("REMARKS");
					DatabaseTable table = new DatabaseTable();
					table.setTableName(tableName);
					table.setTableRemark(tableRemark);

					// 使用两个结果集获取信息,一个结果集获取的信息不完整
					Map<String, TableField> fieldMap = new HashMap<String, TableField>();
					
					// 2、获取数据库表字段名称、字段自增、可空信息
					String sql = "select * from " + tableName;
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet resultSet1 = ps.executeQuery();
					ResultSetMetaData meta = resultSet1.getMetaData();
					int columeCount = meta.getColumnCount();

					for (int i = 1; i <= columeCount; i++) {
						TableField tableField = new TableField();
						tableField.setFieldName(String.valueOf(meta.getColumnName(i)));
						tableField.setIncrement(String.valueOf(meta.isAutoIncrement(i)));
						tableField.setNullable(String.valueOf(meta.isNullable(i)));
						fieldMap.put(tableField.getFieldName(), tableField);
					}
					// 1、获取字段类型、缺省值和字段注释信息
					ResultSet rs = stmt.executeQuery("show full columns from " + tableName);
					while (rs.next()) {
						String fieldName =rs.getString("Field");
						TableField tableField = fieldMap.get(fieldName);
						tableField.setFieldType(rs.getString("Type"));
						if (null == rs.getString("Default") || "".equals(rs.getString("Default")) ) {
							tableField.setFieldDefault("null");
						}else {
							tableField.setFieldDefault(rs.getString("Default"));
						}
						tableField.setRemark(rs.getString("Comment"));
					}
					
					List<TableField> tableFields = new ArrayList<TableField>();
					Iterator<Map.Entry<String, TableField>> entries = fieldMap.entrySet().iterator();
					while (entries.hasNext()) {
						tableFields.add(entries.next().getValue());
					}
					table.setTableFields(tableFields);
					tables.add(table);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tables;
	}
	
	public void setConnectDBService(ConnectDBService connectDBService) {
		this.connectDBService = connectDBService;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public void setDocumentHandler(DocumentHandler documentHandler) {
		this.documentHandler = documentHandler;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	
	
}
