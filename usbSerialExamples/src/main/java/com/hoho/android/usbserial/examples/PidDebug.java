package com.hoho.android.usbserial.examples;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class PidDebug {
	
	/**************************************************************************************************************
	 * final property
	 **************************************************************************************************************/
	private static final int LEVEL_ERROR = 0;
	private static final int LEVEL_WARNING = 1;
	private static final int LEVEL_INFO = 2;
	private static final int LEVEL_DEBUG = 3;
	private static final int LEVEL_VERBOSE = 4;

	/**************************************************************************************************************
	 * public method
	 **************************************************************************************************************/
	/**
	 * 로그출력 API(Log.e)
	 */
	public static void Error( Object... args ){

		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_ERROR, null, args );
		}
	}

	/**
	 * 로그출력 API(Log.w)
	 */
	public static void Warning( Object... args){
		
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_WARNING, null, args);
		}
	}

	/**
	 * 로그출력 API(Log.d)
	 */
	public static void Verbose( Object... args){
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_VERBOSE, null, args);
		}
	}

	/**
	 * 로그출력 API(Log.d)
	 */
	public static void Debug( Object... args ){
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_DEBUG, null, args);
		}
	}

	public static void d(String tag, Object... args ){
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_DEBUG, tag, args);
		}
	}


	/**
	 * 로그출력 API(Log.i)
	 */
	public static void Info( Object... args ){
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_INFO, null, args);
		}
	}

	public static void i(String tag, Object... args) {
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_INFO, null, args);
		}
	}
	public static void e(String tag, Object... args) {
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_ERROR, null, args);
		}
	}
	public static void v(String tag, Object... args) {
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_VERBOSE, null, args);
		}
	}

	public static void w(String tag, Object... args) {
		if(CONFIG.SUPPORT_DEBUG) {
			PidDebug.log( LEVEL_WARNING, null, args);
		}
	}
	public static void InfoWithLongLog(String strMsg){

		int maxLogStringSize = 1000;
		String strThreadName = Thread.currentThread().getName();
		for(int i = 0; i <= strMsg.length() / maxLogStringSize; i++) {
			int start = i * maxLogStringSize;
			int end = (i+1) * maxLogStringSize;
			end = end > strMsg.length() ? strMsg.length() : end;
			PidDebug.i(strThreadName, strMsg.substring(start, end));
		}
	}
	
	/**************************************************************************************************************
	 * private method
	 **************************************************************************************************************/

	/**
	 * 로그출력 API
	 */
//	private static final boolean isLog = false;
	synchronized private static void log(int nLevel, String tag, Object[] args ) {
		if (args == null || args.length <= 0) {
			return;
		}

		String strThreadName = Thread.currentThread().getName();
		String strFileName = Thread.currentThread().getStackTrace()[5].getFileName();
		int nLineNumber = Thread.currentThread().getStackTrace()[5].getLineNumber();
		if (strFileName == null) {
			strFileName = "unknown";
		}
		// 파일이름은 20자로 제한한다.
		if (20 < strFileName.length()) {
			strFileName = strFileName.substring(0, 20);
		}

		// format
		String strFormat = "" + args[0];
		strFormat = strFormat.replaceAll("%d", "%s");
		strFormat = strFormat.replaceAll("%f", "%s");
		strFormat = strFormat.replaceAll("%c", "%s");
		strFormat = strFormat.replaceAll("%b", "%s");
		strFormat = strFormat.replaceAll("%x", "%s");
		strFormat = strFormat.replaceAll("%l", "%s");

		// argument
		String strArgument = "";
		switch (args.length - 1) {
			case 0:
				strArgument = strFormat;
				break;
			case 1:
				strArgument = String.format(strFormat, "" + args[1]);
				break;
			case 2:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2]);
				break;
			case 3:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3]);
				break;
			case 4:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4]);
				break;
			case 5:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5]);
				break;
			case 6:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5], "" + args[6]);
				break;
			case 7:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5], "" + args[6], ""
								+ args[7]);
				break;
			case 8:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5], "" + args[6], ""
								+ args[7], "" + args[8]);
				break;
			case 9:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5], "" + args[6], ""
								+ args[7], "" + args[8], "" + args[9]);
				break;
			case 10:
				strArgument = String.format(strFormat, "" + args[1], "" + args[2],
						"" + args[3], "" + args[4], "" + args[5], "" + args[6], ""
								+ args[7], "" + args[8], "" + args[9], ""
								+ args[10]);
				break;
			default:
				;
		}

		// log
		String strLog = String.format("%-20s %5d  %s\n", strFileName, nLineNumber, strArgument);

		// tag
		String strTag = "";

		if (!StringUtil.isEmpty(tag)) {
			strTag = tag;
		} else {
			strTag = strThreadName;
		}

		if (CONFIG.SUPPORT_FILE_LOGGER) {
			String strLevel = "D";
			switch (nLevel) {
				case LEVEL_ERROR:
					strLevel = "E";
					break;
				case LEVEL_WARNING:
					strLevel = "W";
					break;
				case LEVEL_INFO:
					strLevel = "I";
					break;
				case LEVEL_VERBOSE:
					strLevel = "V";
					break;
				case LEVEL_DEBUG:
				default:
					strLevel = "D";
					break;
			}
			SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lTime = System.currentTimeMillis();
			String strDate = oDateFormat.format(lTime);
			debugFile(strDate + " : " + "(" + strLevel + ")" + strTag + " " + strLog);
		} else if (CONFIG.SUPPORT_DEBUG) {
			// Level
			switch (nLevel) {
				case LEVEL_ERROR:
					Log.e(strTag, strLog);
					break;
				case LEVEL_WARNING:
					Log.w(strTag, strLog);
					break;
				case LEVEL_INFO:
					int maxLogStringSize = 1000;
					for (int i = 0; i <= strLog.length() / maxLogStringSize; i++) {
						int start = i * maxLogStringSize;
						int end = (i + 1) * maxLogStringSize;
						end = end > strLog.length() ? strLog.length() : end;
						Log.i(strTag, strLog.substring(start, end));
					}
					break;
				case LEVEL_VERBOSE:
					Log.v(strTag, strLog);
					break;
				case LEVEL_DEBUG:
				default:
					Log.d(strTag, strLog);
					break;
			}
		}
	}

	/**
	 * 파일로그 API
	 */
	public static void debugFile(String strLog)
	{
		if (getFileSize(getDownloadtestFilePath()) > 1024000){
			deleteFile(getDownloadtestFilePath());
		}

		if (isFileExist(getDownloadtestFilePath()) == false) {
//			String initContent = "DeviceID:" + SysUtil.getDeviceID(MainApp.getContext()) + System.getProperty( "line.separator" )
//					+ "MDN:" + SysUtil.getMDN(MainApp.getContext()) + System.getProperty( "line.separator" )
//					+ "OSVersion:" + SysUtil.getOSVersion() + System.getProperty( "line.separator" )
//					+ "MODEL:" + SysUtil.getModelName() + System.getProperty( "line.separator" )
//					+ "UICC:" + SysUtil.getUicc(MainApp.getContext()) + System.getProperty( "line.separator" );

//			makeFile(getDownloadtestFilePath(), "[APP FILE LOG : ]" + System.getProperty( "line.separator" ) + initContent);
			makeFile(getDownloadtestFilePath(), "[APP FILE LOG : ]" + System.getProperty( "line.separator" ));
		}

		try {
			SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
			long lTime = System.currentTimeMillis();
			String strDate = oDateFormat.format(lTime);

			writeString(getDownloadtestFilePath(), "[" + strDate + "] " + strLog + System.getProperty( "line.separator" ));
		} catch (Exception e) {

		}
	}

	/**
	 * 파일로그를 작성할 path를 반환한다.
	 * @return String
	 */
	public static String getDownloadtestFilePath()
	{
		String strLogFileName = "log.txt";
		String strDownloadFilePath = Environment.getExternalStorageDirectory() + "/pid/" ;
		File f = new File(strDownloadFilePath);
		if( !f.exists() )
			f.mkdirs();
		return strDownloadFilePath + strLogFileName;
	}

	/**
	 * 파일의 사이즈를 리턴한다.
	 * @return long
	 */
	public static long getFileSize( String strPath )
	{
		if( strPath.isEmpty() ) {
			return 0;
		}

		File file = new File( strPath );
		long size = file.length();
		return size;
	}

	/**
	 * 파일을 삭제한다.
	 * @return boolean
	 */
	public static boolean deleteFile( String strPath )
	{
		if( strPath.isEmpty() ) {
			return false;
		}

		File file = new File( strPath );
		return file.delete();
	}


	/**
	 * 파일이 존재하는 확인한다.
	 * @return boolean
	 */
	public static boolean isFileExist( String strPath )
	{
		if( strPath.isEmpty() ) {
			return false;
		}

		File file = new File( strPath );
		boolean bExist = file.exists();
		return bExist;
	}


	/**
	 * 파일을 생성한다.
	 * @return boolean
	 */
	public static boolean makeFile(String strPath, String strContent ){
		try {
			FileWriter fw                 = new FileWriter( strPath );
			fw.write( strContent );
			fw.flush();
			fw.close();

			return true;
		} catch( Exception e) {

		}

		return false;
	}

	/**
	 * 파일에 내용을 쓴다.
	 */
	public static void writeString(String strPath, String strContent ) throws Exception {
		FileWriter fw = new FileWriter( strPath, true ); // File path, append할지

		fw.write( strContent );
		fw.flush();
		fw.close();
	}

}
