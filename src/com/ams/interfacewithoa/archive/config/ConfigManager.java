package com.ams.interfacewithoa.archive.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ams.interfacewithoa.archive.dao.impl.RecordDaoImpl;

/**
 * �����ļ�������
 * @author Administrator
 *
 */
public class ConfigManager {
	private static Logger logger = Logger.getLogger(RecordDaoImpl.class);
	private static final Properties properties = new Properties();
	public static ConfigManager config = null;
	private ConfigManager() throws FileNotFoundException, IOException {
		properties.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
	}
	public static ConfigManager getInstance() {
		if(config == null){
			synchronized(ConfigManager.class){
				try {
					config = new ConfigManager();
				} catch (FileNotFoundException e) {
					logger.error("��ȡ�����ļ������Ҳ��������ļ���" + e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("��ȡ�����ļ�����" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return config;
	}
	public String getProperty(String key){
		return properties.getProperty(key);
	}
}
