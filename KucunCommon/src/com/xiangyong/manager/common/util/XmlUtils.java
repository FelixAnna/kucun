package com.xiangyong.manager.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 依赖Jackson框架封装的工具类
 * 用于Object与XML之间的互转
 */
public class XmlUtils {
	
	private static XmlMapper xmlMapper;
	
	static{
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		xmlMapper = new XmlMapper(module);
		xmlMapper.setSerializationInclusion(Include.NON_DEFAULT);
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	

	/**
	 * Object to XML
	 * @param object
	 * @param rootName
	 * @return
	 */
	public static String toXml(Object object) {
		try {
			return xmlMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * XML to Object
	 * @param xmlStr
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromXml(String xmlStr, Class<T> clazz) throws Exception{
		if(ValidUtils.isValid(xmlStr)){
			return xmlMapper.readValue(xmlStr, clazz);
		}
		return null;
	}
}
