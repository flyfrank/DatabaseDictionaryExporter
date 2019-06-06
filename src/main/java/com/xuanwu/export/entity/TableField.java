package com.xuanwu.export.entity;
/**
 *@description 表字段信息
 * @author <a href="mailto:huyaoke@wxchina.com">YaoKe.Hu</a>
 * @Date 2017年11月24日 
 * @version 1.0.0
 */
public class TableField {

	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * 字段类型
	 */
	private String fieldType;
	/**
	 * 自增
	 */
	private String increment;
	/**
	 * 可空
	 */
	private String nullable;
	/**
	 * 缺省
	 */
	private String fieldDefault;
	/**
	 * 备注
	 */
	private String remark;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getIncrement() {
		return increment;
	}
	public void setIncrement(String increment) {
		this.increment = increment;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getFieldDefault() {
		return fieldDefault;
	}
	public void setFieldDefault(String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
