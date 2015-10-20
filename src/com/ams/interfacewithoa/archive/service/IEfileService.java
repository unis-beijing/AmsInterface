package com.ams.interfacewithoa.archive.service;

import java.util.List;
import java.util.Map;

import com.ams.interfacewithoa.archive.ftp.FtpConfig;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
/**
 *  �����ļ���Ŀ����ӿ�
 * @author Administrator
 *
 */
public interface IEfileService {
	List<Map<String,Object>> getEfileData(String psyscode, JdbcConfig jdbcConfig);
	void saveEfile(Map<String,Object> efile, JdbcConfig jdbcConfig);
	String move(FtpConfig ftpConfig, String psyscode, String filepath, FtpConfig amsFtpConfig);
	void updateState(JdbcConfig jdbcConfig, String syscode);
}
