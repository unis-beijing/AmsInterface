package com.ams.interfacewithoa.archive.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;

/**
 * �����ļ�ftp��������࣬�����ļ��ѷŵ��洢�㣬���账��
 * @author Administrator
 *
 */
public class FtpManager {
	private static FtpManager connManager = null;
	
	public FtpManager() {
	}
//	public static FtpManager getInstance(){
//		if(connManager == null){
//			synchronized(FtpManager.class){
//				connManager = new FtpManager();
//			}
//		}
//		return connManager;
//	}
//	public boolean testFtp(FtpConfig ftpConfig) {
//		 boolean flag = true; 
//		 FTPClient ftpClient = null;
//         try { 
//             ftpClient = new FTPClient(); 
//             ftpClient.setControlEncoding(ftpConfig.getFileEncoding()); 
//             ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort()); 
//             ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
//             int reply = ftpClient.getReplyCode(); 
//             ftpClient.setDataTimeout(120000); 
//             if (!FTPReply.isPositiveCompletion(reply)) { 
//                 ftpClient.disconnect(); 
//                 System.out.println("FTP ����ܾ����ӣ�"); 
//                 flag = false; 
//             } 
//         } catch (SocketException e) { 
//                 flag = false; 
//                 e.printStackTrace(); 
//                 System.err.println("��¼ftp������ " + ftpConfig.getIp() + " ʧ��,���ӳ�ʱ��"); 
//         } catch (IOException e) { 
//                 flag = false; 
//                 e.printStackTrace(); 
//                 System.err.println("��¼ftp������ " + ftpConfig.getIp() + " ʧ�ܣ�FTP�������޷��򿪣�"); 
//         } 
//         return flag;
//	}
//    
//    /**
//     * ��FTP�������ϴ��ļ�
//     * @param url FTP������hostname
//     * @param port FTP�������˿�
//     * @param username FTP��¼�˺�
//     * @param password FTP��¼����
//     * @param path FTP����������Ŀ¼,����Ǹ�Ŀ¼��Ϊ��/��
//     * @param filename �ϴ���FTP�������ϵ��ļ���
//     * @param input �����ļ�������
//     * @return �ɹ�����true�����򷵻�false
//     */
//    public boolean uploadFile(FtpConfig ftpConfig, String path, String filename, InputStream input) {
//        boolean result = false;
//        FTPClient ftpClient = new FTPClient();
//        try {
//            int reply;
//            ftpClient.setControlEncoding(ftpConfig.getFileEncoding());
//            ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());// ����FTP������
//            // ��¼
//            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
//            // �����Ƿ����ӳɹ�
//            reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                System.out.println("����ʧ��");
//                ftpClient.disconnect();
//                return result;
//            }
// 
//            // ת�ƹ���Ŀ¼��ָ��Ŀ¼��
//            boolean change = ftpClient.changeWorkingDirectory(path);
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            if (change) {
//                result = ftpClient.storeFile(new String(filename.getBytes(ftpConfig.getFileEncoding()),"iso-8859-1"), input);
//                if (result) {
//                    System.out.println("�ϴ��ɹ�!");
//                }
//            }
//            input.close();
//            ftpClient.logout();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return result;
//    }
// 
// 
//    /**
//     * ��FTP�����������ļ�
//     * 
//     * @param url FTP������hostname
//     * @param port FTP�������˿�
//     * @param username FTP��¼�˺�
//     * @param password FTP��¼����
//     * @param remotePath FTP�������ϵ����·��
//     * @param fileName Ҫ���ص��ļ���
//     * @param localPath ���غ󱣴浽���ص�·��
//     * @return
//     */
//    public boolean downFile(FtpConfig ftpConfig, String remotePath, String fileName,
//            String localPath) {
//        boolean result = false;
//        FTPClient ftpClient = new FTPClient();
//        try {
//            int reply;
//            ftpClient.setControlEncoding(ftpConfig.getFileEncoding());
//             
//            /*
//             *  Ϊ���ϴ������������ļ�����Щ�ط�����ʹ�������������
//             *  new String(remotePath.getBytes(encoding),"iso-8859-1")ת�롣
//             *  �������ԣ�ͨ������
//             */
////            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
////            conf.setServerLanguageCode("zh");
// 
//            ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());
//            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());// ��¼
//            // �����ļ���������Ϊ������
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            // ��ȡftp��¼Ӧ�����
//            reply = ftpClient.getReplyCode();
//            // ��֤�Ƿ��½�ɹ�
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                System.err.println("FTP server refused connection.");
//                return result;
//            }
//            // ת�Ƶ�FTP������Ŀ¼��ָ����Ŀ¼��
//            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(ftpConfig.getFileEncoding()),"iso-8859-1"));
//            // ��ȡ�ļ��б�
//            FTPFile[] fs = ftpClient.listFiles();
//            for (FTPFile ff : fs) {
//                if (ff.getName().equals(fileName)) {
//                    File localFile = new File(localPath + "/" + ff.getName());
//                    OutputStream is = new FileOutputStream(localFile);
//                    ftpClient.retrieveFile(ff.getName(), is);
//                    is.close();
//                }
//            }
//            ftpClient.logout();
//            result = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return result;
//    }
 

}
