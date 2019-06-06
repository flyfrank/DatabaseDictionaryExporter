package com.xuanwu.export.entity;

import java.util.List;

/**
 *@description 数据库表基本信息
 * @author <a href="mailto:huyaoke@wxchina.com">YaoKe.Hu</a>
 * @Date 2017年11月24日 
 * @version 1.0.0
 */
public class DatabaseTable {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表描述
	 */
	private String tableRemark;
	/**
	 * 表字段
	 */
	private List<TableField> tableFields;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableRemark() {
		return tableRemark;
	}
	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}
	public List<TableField> getTableFields() {
		return tableFields;
	}
	public void setTableFields(List<TableField> tableFields) {
		this.tableFields = tableFields;
	}	
	
}
