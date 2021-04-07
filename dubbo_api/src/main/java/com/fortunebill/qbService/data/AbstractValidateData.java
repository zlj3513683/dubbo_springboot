package com.fortunebill.qbService.data;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 提交之前需要接口调用端校验的对象
 * 
 * @author luhq
 *
 */
public abstract class AbstractValidateData extends AbstractParamData{
	/**
	 * 验证结果
	 */
	private boolean valide = false;

	/**
	 * @return the valide
	 */
	public boolean isValide() {
		return valide;
	}
}
