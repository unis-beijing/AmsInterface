import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ams.interfacewithoa.archive.config.ConfigManager;
import com.ams.interfacewithoa.archive.jdbc.ConnectionManager;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
import com.ams.interfacewithoa.archive.service.IEfileService;
import com.ams.interfacewithoa.archive.service.IFileService;
import com.ams.interfacewithoa.archive.service.impl.EfileServiceImpl;
import com.ams.interfacewithoa.archive.service.impl.FileServiceImpl;
import com.ams.interfacewithoa.archive.util.DateHelper;
import com.ams.interfacewithoa.archive.util.UUIDHexGenerator;

/**
 * 归档接口主程序类
 * 
 *
 */
public class ArcService {
	private static Logger logger = Logger.getLogger(ArcService.class);
	public static IFileService fileService = new FileServiceImpl();
	public static IEfileService efileService = new EfileServiceImpl();
	public static UUIDHexGenerator idGenerator = new UUIDHexGenerator();
	private static String jdbc_url = ConfigManager.getInstance().getProperty(
			"jdbc.url");
	private static String jdbc_username = ConfigManager.getInstance()
			.getProperty("jdbc.username");
	private static String jdbc_password = ConfigManager.getInstance()
			.getProperty("jdbc.password");

	private static String ams_jdbc_url = ConfigManager.getInstance()
			.getProperty("ams.jdbc.url");
	private static String ams_jdbc_username = ConfigManager.getInstance()
			.getProperty("ams.jdbc.username");
	private static String ams_jdbc_password = ConfigManager.getInstance()
			.getProperty("ams.jdbc.password");

	public static void main(String[] args) {
		PropertyConfigurator.configure(ClassLoader
				.getSystemResource("log4j.properties"));
		JdbcConfig jdbcConfig = new JdbcConfig(jdbc_url, jdbc_username,
				jdbc_password);
		JdbcConfig amsJdbcConfig = new JdbcConfig(ams_jdbc_url,
				ams_jdbc_username, ams_jdbc_password);
		if (ConfigManager.getInstance() == null) {
			logger.error("读取配置文件出错，请检查配置文件！");
			return;
		}
		if (!ConnectionManager.getInstance().testConnection(jdbcConfig)
				&& !ConnectionManager.getInstance().testConnection(
						amsJdbcConfig)) {
			logger.error("测试数据库连接出错，请检查配置文件！");
			return;
		}
		String psyscode = "";
		String syscode = "";
		String zjk_psyscode = "";
		String zjk_syscode = "";
		String prjsys = "";
		String prjcode = "";
		while (true) {
			// 获取文件条目
			Map<String, Object> file = fileService.getFileData(jdbcConfig);
			if (file == null) {
				break;
			} else {
				// 根据prjcode找到prjsys
				prjcode = (String) file.get("PRJCODE");
				prjsys = fileService.getPrjsysByPrjcode(prjcode, amsJdbcConfig);
				file.put("PRJSYS", prjsys);
				// 获取挂接的电子文件
				zjk_psyscode = (String) file.get("SYSCODE");
				List<Map<String, Object>> efiles = efileService.getEfileData(
						zjk_psyscode, jdbcConfig);
				// 生成新的id，否则会存在唯一性的问题
				psyscode = idGenerator.generate();
				file.put("SYSCODE", psyscode);
				file.put("CREATETIME", DateHelper.generateTime());
				file.put("STATUS", "0");
				file.put("ATTR", "0");
				file.put("ATTREX", "3");
				file.put("ATTRZB", "0");
				file.put("ATTRINPUT", "0");
				for (int i = 0, size = efiles.size(); i < size; i++) {
					Map<String, Object> efile = efiles.get(i);
					zjk_syscode = (String) efile.get("SYSCODE");
					// 电子文件挂接到新的id下
					syscode = idGenerator.generate();
					efile.put("SYSCODE", syscode);
					efile.put("PSYSCODE", psyscode);
					efile.put("CREATETIME", DateHelper.generateTime());
//					efile.put("FILETYPE", "1");
					efile.put("STATUS", "0");
					efileService.saveEfile(efile, amsJdbcConfig);
					// 更新电子文件状态
					efileService.updateState(jdbcConfig, zjk_syscode);
				}
				// 添加文件到ams
				fileService.saveFile(file, amsJdbcConfig);
				// 更新文件状态
				fileService.updateState(jdbcConfig, zjk_psyscode);
				// 更新读取时间
				fileService.updateDate(jdbcConfig, zjk_psyscode);
				
			}
		}
		logger.info("归档接口执行完成。");
		System.exit(0);
	}

}
