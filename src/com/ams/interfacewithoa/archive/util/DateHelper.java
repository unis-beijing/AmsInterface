/*
 * �������� 2007-2-3
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.ams.interfacewithoa.archive.util;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.platform.fileManipulation.FileManiConstant;


public class DateHelper {
	
	public static String generateTime(){
		String timeStr = new String();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		timeStr = sdf.format(time);
		
		return timeStr;
	}
}
