package com.ams.interfacewithoa.archive.service;

import java.util.Map;

import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;

/**
 * �ļ���Ŀ����ӿ�
 * @author Administrator
 *
 */
public interface IFileService {
	Map<String,Object> getFileData(JdbcConfig jdbcConfig);
	void saveFile(Map<String,Object> file, JdbcConfig jdbcConfig);
	void updateState(JdbcConfig jdbcConfig, String syscode);
	void updateDate(JdbcConfig jdbcConfig, String syscode);
	String getPrjsysByPrjcode(String string, JdbcConfig jdbcConfig);
}
