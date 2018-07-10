package com.hoho.android.usbserial.examples;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

	public static String inputStreamToString(final InputStream stream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				stream));
		
		final StringBuffer sb = new StringBuffer(10240);
		String line = null;
		final String linesep = System.getProperty("line.separator"); // "\n"
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(linesep);
			}
		} catch (final IOException e) {
			sb.append("");
		}

		try {
			br.close();
		} catch (final IOException e) {

		}

		return sb.toString();
	}

	/**
	 * 매개변수 str 이 null 이면, "" 문자열로 리턴한다.
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToBlank(final String str) {

		if (str == null) {
			return "";
		}
		return str;
	}

	/**
	 * 매개변수 str 이 null 이거나, "" 문자열이면 true 를 리턴한다.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final String str) {

		final String temp = nullToBlank(str);
		if (temp.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 매개변수 str 이 null 이거나, "" 문자열이면 true 를 리턴한다.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(final byte[] str) {

		if (str.length > 0) {
			return true;
		}
		return false;
	}

	/**
	 * SpaceRemove(String srcStr) 원본 문자열에서 공백을 제거 한 후 공백이 제거된 문자열을 리턴한다.
	 * 
	 * @param srcStr
	 *            원본 문자열
	 * @return 공백제거된 문자열
	 */
	public static String spaceRemove(final String srcStr) {
		if (srcStr != null) {
			return srcStr.replaceAll("\\p{Space}", "");
		}
		return "";
	}

	/**
	 * Replace(String str) 원본 문자열에서하이픈을 제거 한 후 하이픈이 제거된 문자열을 리턴한다.
	 * 
	 * @param srcStr
	 *            원본 문자열
	 * @return 하이픈제거된 문자열
	 */
	public static String replaceHyphensToBlank(final String srcStr) {
		return srcStr.replaceAll("-", "");
	}

	/**
	 * 파일 사이즈를 String형식으로 변환하여 반환한다.
	 * 
	 * @param total
	 * @return String
	 */
	public static String convertFilesizeToString(final long total) {
		
		String strSize = null;
		
		try {
			if (total < 1024) {
				strSize = total + "byte";
			} else if (total < 1024 * 1024) {
				strSize = total / 1024 + "KB";
			} else if (total < 1024 * 1024 * 1024) {
				strSize = total / (1024 * 1024) + "MB";
			} else {
				// GB 단위를 소수점 둘째 자리까지 표시
				double nSize = ( total / ((double)1024 * 1024 * 1024) );
				strSize = (new DecimalFormat("#.##").format(nSize)) + "GB" ;
			}			
		} catch (Exception e) {
			strSize = "";
		}
		
		return strSize;
	}
	
	/**
	 * 파일 사이즈를 String형식으로 변환하여 반환한다.
	 * 소수점 둘째자리까지 
	 * @param total
	 * @return String
	 */
	public static String convertFilesizeToStringBelowDecimal(final long total) {
		String strSize = null;
		double nSize = 0;
		try {
			if (total < 1024) {
				strSize = total + "byte";
			} else if (total < 1024 * 1024) {
				nSize = total / (double)1024;
				strSize = (new DecimalFormat("#.##").format(nSize))  + "KB";
			} else if (total < 1024 * 1024 * 1024) {
				nSize = total / ((double)1024*1024);
				strSize = (new DecimalFormat("#.##").format(nSize)) + "MB";
			} else {
				// GB 단위를 소수점 둘째 자리까지 표시
				nSize = ( total / ((double)1024 * 1024 * 1024) );
				strSize = (new DecimalFormat("#.##").format(nSize)) + "GB" ;
			}			
		} catch (Exception e) {
			strSize = "";
		}
		return strSize;

	}
	
	/**
	 * Duration 값을 시간 표시 포맷으로 변환
	 * 
	 * @param duration
	 *            - Long
	 * @return - String
	 */
	public static String convertDurationToString(final long duration) {
		String result = null;
		long time = duration / 1000;
		long hour = time / (60 * 60);
		long min = time % (60 * 60) / 60;
		long sec = time % 60;
		result = String.format("%02d:%02d:%02d", hour, min, sec);
		return result;
	}

	/**
	 * milliseconds를 날짜(yyyy/MM/dd HH:mm:ss)로 변환 
	 * 
	 * @param milliseconds
	 * @return (yyyy/MM/dd HH:mm:ss)
	 */
	public static String convertTimeMillisToDate(final long milliseconds) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
		return mSimpleDateFormat.format(new Date(milliseconds));
	}

	/**
	 * 날짜(yyyy/MM/dd HH:mm:ss)를 milliseconds로 변환 
	 * 
	 * @param date (yyyy/MM/dd HH:mm:ss)
	 * @return milliseconds
	 */
	public static long convertDateToTimeMillis(String date) {
	    try {
	        final Date currentParsed = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
	        long milliseconds = currentParsed.getTime();
	        return milliseconds;
	    } catch (ParseException e) {
	        return 0L;
	    }
	}

	/**
	 *  DateFormat을 리턴한다.
	 * @return DateFormat
	 */
//	public static DateFormat getDateFormat(){
//		Configuration config = PidDemoApp.getContext().getResources().getConfiguration();
//		Locale setLocale =  config.locale;
//		return new SimpleDateFormat("EEE, MMM d, yyyy",setLocale);
//	}
//
//	/**
//	 *  홈화면에 쓰이는 DateFormat을 리턴한다.
//	 * @return DateFormat
//	 */
//	public static DateFormat getMainDateFormat(){
//		Configuration config = PidDemoApp.getContext().getResources().getConfiguration();
//		Locale setLocale =  config.locale;
//		return new SimpleDateFormat("EEE, MMM d, yyyy",setLocale);
//	}
//
//	/**
//	 *  날씨 팝업화면에 쓰이는 DateFormat을 리턴한다.
//	 * @return DateFormat
//	 */
//	public static DateFormat getWeatherDateFormat(){
//		Configuration config = PidDemoApp.getContext().getResources().getConfiguration();
//		Locale setLocale =  config.locale;
//		return new SimpleDateFormat("EEE, MMM d, yyyy",setLocale);
//	}
//
//
//	/**
//	 *  월정보의 DateFormat을 리턴한다.
//	 * @return DateFormat
//	 */
//	public static DateFormat getMonthDateFormat(){
//		Configuration config = PidDemoApp.getContext().getResources().getConfiguration();
//		Locale setLocale =  config.locale;
//		return new SimpleDateFormat("MMM",setLocale);
//	}

//	/**
//	 *  Calendar 값을 설정 언어에 따른 홈화면에 쓰이는 DateFormat 형태로 변환하여 반환한다
//	 * @param _cal
//	 * @return
//	 */
//	public static String calendarToMainDateFormatStr(Calendar _cal){
//		DateFormat df = getMainDateFormat();
//		_cal.set(Calendar.HOUR_OF_DAY,0);
//		_cal.set(Calendar.MINUTE,0);
//		_cal.set(Calendar.SECOND,0);
//		_cal.set(Calendar.MILLISECOND,0);
//
//		return df.format(_cal.getTime());
//	}
//
//	/**
//	 *  Calendar 값을 설정 언어에 따른 DateFormat 형태로 변환하여 반환한다
//	 * @param _cal
//	 * @return
//	 */
//	public static String calendarToDateFormatStr(Calendar _cal){
//		DateFormat df = getDateFormat();
//		_cal.set(Calendar.HOUR_OF_DAY,0);
//		_cal.set(Calendar.MINUTE,0);
//		_cal.set(Calendar.SECOND,0);
//		_cal.set(Calendar.MILLISECOND,0);
//
//		return df.format(_cal.getTime());
//	}
//
//	/**
//	 *  Calendar 값을 설정 언어에 따른 월 정보D ateFormat 형태로 변환하여 반환한다
//	 * @param _cal
//	 * @return
//	 */
//	public static String calendarToMonthDateFormatStr(Calendar _cal){
//		DateFormat df = getMonthDateFormat();
//		_cal.set(Calendar.HOUR_OF_DAY,0);
//		_cal.set(Calendar.MINUTE,0);
//		_cal.set(Calendar.SECOND,0);
//		_cal.set(Calendar.MILLISECOND,0);
//
//		return df.format(_cal.getTime());
//	}
//
//
//	/**
//	 *  Calendar 값을 설정 언어에 따른 날씨팝업 DateFormat 형태로 변환하여 반환한다
//	 * @param _cal
//	 * @return
//	 */
//	public static String calendarToWeatherPopupDateFormatStr(Calendar _cal){
//		DateFormat df = getWeatherDateFormat();
//		_cal.set(Calendar.HOUR_OF_DAY,0);
//		_cal.set(Calendar.MINUTE,0);
//		_cal.set(Calendar.SECOND,0);
//		_cal.set(Calendar.MILLISECOND,0);
//
//		return df.format(_cal.getTime());
//	}
//
//	/**
//	 * 날짜 형태의 String을 설정 언어에 따른 DateFormat 형태로 변환하여 반환한다
//	 * @param dateStr yyyyMMdd
//	 * @return
//	 */
//	public static String dateStrToDateFormatStr(String dateStr){
//
//		PidDebug.d("StringUtil",dateStr);
//		PidDebug.d("StringUtil",dateStr.substring(0,4));
//		PidDebug.d("StringUtil",dateStr.substring(4,6));
//		PidDebug.d("StringUtil",dateStr.substring(6));
//
//
//		DateFormat df = getDateFormat();
//		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
//		cal.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0,4)));
//		cal.set(Calendar.MONTH, (Integer.parseInt(dateStr.substring(4,6))-1+12)%12);
//		cal.set(Calendar.DATE, Integer.parseInt(dateStr.substring(6)));
//		cal.set(Calendar.HOUR_OF_DAY,0);
//		cal.set(Calendar.MINUTE,0);
//		cal.set(Calendar.SECOND,0);
//		cal.set(Calendar.MILLISECOND,0);
//
//		return df.format(cal.getTime());
//	}

	/**
	 * 날짜 형태의 String을 timpStamp 형태로 변환하여 반환한다.
	 * @param dateTime yyyyMMdd
	 * @return
	 */
	public static long stringToTimestamp(String dateTime){

		if (dateTime.length()<8){
			return -1;
		}
		int year = Integer.parseInt(dateTime.substring(0,3));
		int month = Integer.parseInt(dateTime.substring(4,5))-1;
		int day = Integer.parseInt(dateTime.substring(6,7));

		return dateInfoToTimestamp(year, month, day);
	}

	/**
	 *  년,월.일 정보로 TimeStamp 값을 반환한다.
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static long dateInfoToTimestamp(int year, int month, int day){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		return new Timestamp(cal.getTime().getTime()).getTime();
	}

	/**
	 * 날짜 시간 형태의 String을 timpStamp 형태로 변환하여 반환한다.
	 * @param dateTime yyyyMMddHHmm
	 * @return
	 */
	public static long stringToDetailTimestamp(String dateTime){

		if (dateTime.length()<12){
			return -1;
		}
		int year = Integer.parseInt(dateTime.substring(0, 3));
		int month = Integer.parseInt(dateTime.substring(4,5))-1;
		int day = Integer.parseInt(dateTime.substring(6,7));
		int hour = Integer.parseInt(dateTime.substring(6,7));
		int min = Integer.parseInt(dateTime.substring(6,7));

		return dateTimeInfoToTimestamp(year, month, day, hour, min);
	}

	/**
	 *  년.월.일.시.분 정보로 Timestamp 값을 반환한다.
	 * @param year
	 * @param month
	 * @param day
	 * @param hour		24시 기준
	 * @param min
	 * @return
	 */
	public static long dateTimeInfoToTimestamp(int year, int month, int day, int hour, int min){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,min);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		return new Timestamp(cal.getTime().getTime()).getTime();
	}

	/**
	 * Timestamp 값을 Calendar 로 반환한다.
	 * @param timestamp
	 * @return
	 */
	public static Calendar timestampToCalendar(long timestamp){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.setTimeInMillis(timestamp);
		return cal;
	}

	/**
	 *  Timestamp 값에서 시간 정보를 빼서 "hh:mm" 형태로 반환하는 Method
	 * @param timestamp
	 * @return
	 */
	public static String timeStampToTimeStr(long timestamp){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.setTimeInMillis(timestamp);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
	}

	/**
	 * Timestamp 값을 "yyyy.mm.dd  hh:mm" 형태로 반환하는 Method
	 * @param timestamp
	 * @return
	 */
	public static String timeStampToDateTimeStr(long timestamp){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.setTimeInMillis(timestamp);
		return String.format("%4d.%02d.%02d %02d:%02d:%02d",cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
	}

	/**
	 * Timestamp 값을 "yyyy.mm.dd" 형태로 반환하는 Method
	 * @param timestamp
	 * @return
	 */
	public static String timeStampToDateStr(long timestamp){
		Calendar cal = Calendar.getInstance(CONFIG.USER_TIMEZONE);
		cal.setTimeInMillis(timestamp);
		return String.format("%4d.%02d.%02d",cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 시간정보  20170215112000 형태를 11:20 형태로 변환하여 반환한다
	 * @param timeStr
	 * @return
	 */
	public static String timeStrToTimeFormat(String timeStr){
		PidDebug.d("String Util",timeStr);
		PidDebug.d("String Util",timeStr.substring(8,10));
		PidDebug.d("String Util",timeStr.substring(10,12));
		return timeStr.substring(8,10)+":"+timeStr.substring(10,12);
	}


	/**
	 * date 값을 String형식으로 반환한다.
	 * 
	 * @param milliseconds
	 * @return String
	 */
	public static String convertDateToLong(long milliseconds) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		return format.format(new Date(milliseconds));
	}

	public static boolean containsSpecialChar(final String string) {
		if (string.contains("@") || string.contains("+")
				|| string.contains(",") || string.contains("?")
				|| string.contains("&") || string.contains("<")
				|| string.contains(">") || string.contains("/")
				|| string.contains("\\") || string.contains(":")
				|| string.contains("*") || string.contains("\"")
				|| string.contains("|")) {
			return true;
		}
		return false;
	}

	/**
	 * String을 replace하여 반환한다.
	 * 
	 * @param oldString
	 * @return String
	 */
	public static String replaceSpecialChar(String oldString) {
		oldString = oldString.replace("@", "_");
		oldString = oldString.replace("+", "_");
		oldString = oldString.replace(",", "_");
		oldString = oldString.replace("?", "_");
		oldString = oldString.replace("&", "_");
		oldString = oldString.replace("<", "_");
		oldString = oldString.replace(">", "_");
		oldString = oldString.replace("/", "_");
		oldString = oldString.replace("\\", "_");
		oldString = oldString.replace(":", "_");
		oldString = oldString.replace("*", "_");
		oldString = oldString.replace("\"", "_");
		oldString = oldString.replace("|", "_");
		return oldString;
	}

	/**
	 * String에 포함된 문자열이 있는지를 판단하여 반환한다.
	 * 
	 * @param oldString
	 * @return boolean
	 */
	public static boolean isContainSpecialChar(String oldString) {
		return oldString.contains("@") || oldString.contains("+")
				|| oldString.contains(",") || oldString.contains("?")
				|| oldString.contains("&") || oldString.contains("<")
				|| oldString.contains(">") || oldString.contains("/")
				|| oldString.contains("\\") || oldString.contains(":")
				|| oldString.contains("*") || oldString.contains("\"")
				|| oldString.contains("|");

	}

	/**
	 * String이 email형식이 맞는지를 판단하여 반환한다.
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmailPattern(String email) {
		if (email.contains(" ")) {
			return false;
		}

		String rfc2322Pattern = "^[0-9a-zA-Z_-]+(\\.[0-9a-zA-Z_-]+)*@([0-9a-zA-Z_-]+)(\\.[0-9a-zA-Z_-]+){1,2}$";
		Pattern pattern = Pattern.compile(rfc2322Pattern);
		Matcher match = pattern.matcher(email);
		boolean isfind = match.find();
		if (isfind) {
			final String[] es = email.split("@");
			if (es[0].length() < 64 && es[1].length() > 255) {
				return false;
			}
		}
		return isfind;
	}

	/**
	 * String이 email형식이 맞는지를 판단하여 반환한다.
	 * 
	 * @param domain
	 * @return boolean
	 */
	public static boolean isEmailDomain(String domain) {
		int count = 0;
		if (domain != null) {
			for (int i = 0; i < domain.length(); i++) {
				if (domain.charAt(i) == '.') {
					count++;
				}
			}
		}
		return count > 3 ? true : false;
	}

	/**
	 * 쌍모음 -> 단모음 (ㄲ -> ㄱ)
	 * 
	 * @param chosung
	 * @return
	 */
	public static String convertFortisToChosung(String chosung) {

		if (chosung.equals("ㄲ")) {
			chosung = "ㄱ";
		} else if (chosung.equals("ㅉ")) {
			chosung = "ㅈ";
		} else if (chosung.equals("ㄸ")) {
			chosung = "ㄷ";
		} else if (chosung.equals("ㅆ")) {
			chosung = "ㅅ";
		} else if (chosung.equals("ㅃ")) {
			chosung = "ㅂ";
		}

		return chosung;
	}

	/**
	 * yyyyMMdd -> yyyy.MM.dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static String toDateByToken(String strDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format.parse(strDate);

			format = new SimpleDateFormat("yyyy.MM.dd");
			return format.format(date);
		} catch (ParseException e) {
			return "";			
		}
	}

	/**
	 * yyyyMMddHHmmss -> yyyy.MM.dd 오전 HH:MM
	 * 
	 * @param strDate
	 * @return
	 */

	public static String toDateTimeByToken(String strDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			Date date = format.parse(strDate);

			format = new SimpleDateFormat("yyyy.MM.dd a hh:mm");
			return format.format(date);
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * string의 null check
	 * 
	 * @param str
	 * @return null : true
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}

		str = str.trim();
		if (str.equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 1023 -> 1023byte 944238 -> 992KB 111222444 -> 106MB 6060606060 -> 5GB
	 * 
	 * @param strFileSize
	 * @return
	 */
	public static String getFileSize(String strFileSize) {
		String strRet = "0byte";
		if(false == TextUtils.isEmpty(strFileSize)){
			
			double lsize = Double.parseDouble(strFileSize);

			String strSize;
			String strSizeType;
			if (lsize < 1024) {
				strSize = String.format("%.0f" , lsize);
				strSizeType = "byte";
			} else if (lsize < 1024 * 1024) {
				strSize = String.format("%.0f" , lsize / 1024);
				strSizeType = "KB";
			} else if (lsize < 1024 * 1024 * 1024) {
				strSize = String.format("%.0f" , lsize / (1024 * 1024));
				strSizeType = "MB";
			} else {
				double gbSize = StringUtil.round(lsize / (1024 * 1024 * 1024), 2);
				if(gbSize%1 == 0){
					strSize = String.format("%.0f" , gbSize);
				}else{
					strSize = String.format("%.2f" , gbSize);
				}
				
				strSizeType = "GB";
			}
			
			return strSize + strSizeType;
		}

		return strRet;
	}

	public static double round(double d, int n) {
		return Math.round(d * Math.pow(10, n)) / Math.pow(10, n);
	}

	/**
	 * String을 replace하여 반환한다.
	 * 
	 * @param str
	 *            , index
	 * @return String
	 */
	public static String replaceChanger(String str, int index) {

		if (str == null)
			return "";
		if (0 == str.length())
			return str;
		String result = str;
		if (index < result.length()) {
			String tmp = result.substring(0, index);
			result = result.replaceFirst(tmp, tmp + "\n");
		}
		return result;
	}

	/**
	 * String에 해당하는 수의 1000 단위 구분 점 넣는 함수
	 * 
	 * @param inputNumber
	 *            구분 점 넣을 수
	 * @return String
	 * @since 2013.11.27
	 */
	public static String changeNumberToTerm(String inputNumber) {
		if (isEmpty(inputNumber) || !isNumber(inputNumber)) {
			return "";
		}

		String result_num = "";
		DecimalFormat df = new DecimalFormat("###,###");

		result_num = df.format(Integer.valueOf(inputNumber));
		return result_num;
	}

	/**
	 * String에 해당하는 수의 1000 단위 구분 점 넣는 함수
	 * 
	 * @param inputNumber
	 *            구분 점 넣을 수
	 * @return String
	 * @since 2013.11.27
	 */	
	public static String changeNumberToTerm(long inputNumber) {

		String result_num = "";
		DecimalFormat df = new DecimalFormat("###,###");

		result_num = df.format(inputNumber);
		return result_num;
	}
	
	/**
	 * String이 숫자인지 아닌지 판별
	 * 
	 * @param s
	 * @return boolean
	 * @since 2013.11.27
	 */
	public static boolean isNumber(String s) {
		int size = s.length();
		for (int i = 0; i < size; i++) {
			int a = s.charAt(i) - 48;
			if (a < 0 || a > 9) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param input : 입력값
	 * @param length : 보여주고 싶은 입력값의 길이
	 * @return : 최대 length 까지의 입력값
	 */
	public static String getLimitLengthString(String input, int length) {
		
		if(input == null || input.length() <=0) {
			return "";
		}
		
		if(input.length() > length) {
			return input.substring(0, length);
		}
		else {
			return input;
		}
	}
	
	/**
	 * 
	 * @param input : 입력값
	 * @param length : 보여주고 싶은 입력값의 길이
	 * @return : input의 길이가 length를 넘기면 true, 아니면 true
	 */
	public static boolean isOverLimit(String input, int length) {
		
		if(input == null || input.length() <=0) {
			return false;
		}
		
		if(input.length() > length) {
			return true;
		}
		else {
			return false;
		}
	}
	

	public static String formattedDate(String strDate)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format.parse(strDate);

			format = new SimpleDateFormat("yyyy년 MM월 dd일");
			return format.format(date);
		} catch (ParseException e) {
			return "";
		}
	}
	
	public static String formattedDate2(String strDate)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format.parse(strDate);

			format = new SimpleDateFormat("yyyy.MM.dd");
			return format.format(date);
		} catch (ParseException e) {
			return "";
		}
	}
	
	
	
	public static String getBlankPhoneNumber(String phoneNumber){
		if(false == TextUtils.isEmpty(phoneNumber)){
			String number = phoneNumber.trim().replace("-", "");
			int length = number.length();
			
			if(length == 10){
				return phoneNumber.subSequence(0, 3) + "-" + "XXX-XXXX";
			}else if(length == 11){
				return phoneNumber.subSequence(0, 3) + "-" + "XXXX-XXXX";
			}else if(length >=3){
				StringBuilder sb = new StringBuilder(phoneNumber.subSequence(0, 3));
				if(length > 3){
					sb.append("-");
    				for(int idx = 0; idx < length-3; idx++){
    					sb.append("X");
    				}
				}
				return sb.toString();
			}
		}
		return null;
	}
	
	public static String getFormatedPhoneNumber(String number) {
        if(TextUtils.isEmpty(number))
        	return null;
        
        if(!isNumber(number))
            return number;
		
		StringBuilder formatedNumber = new StringBuilder(number);
		int type = getPhoneNumberType(number);
		switch(type) {
			case NUMBER_TYPE_AREA_TWO: {
				int len = getPhoneNumberLength(formatedNumber.toString());
    			if(len > 2) {
    				formatedNumber.insert(2, "-");
    				if(len >= 6 && len < 10) {
    					formatedNumber.insert(6, "-");
    				} else if(len == 10) {
    					formatedNumber.insert(7, "-");
    				} 
    			}
				break;
			}
			case NUMBER_TYPE_AREA_THREE:
    		case NUMBER_TYPE_MOBILE_THREE:
    		case NUMBER_TYPE_ETC_THREE: {
    			int len = getPhoneNumberLength(formatedNumber.toString());
    			if(len > 3) {
    				formatedNumber.insert(3, "-");
    				if(len > 7 && len < 11) {
    					formatedNumber.insert(7, "-");
    				} else if(len >= 11) {
    					formatedNumber.insert(8, "-");
    				} 
    			}
				break;
			}
    		case NUMBER_TYPE_ETC_FOUR: {
    			int len = getPhoneNumberLength(formatedNumber.toString());
    			if(len > 4 && len <= 8) {
    				formatedNumber.insert(4, "-");
    			}
				break;
			}
		}
		return formatedNumber.toString();
	}
	
	public static final int NUMBER_TYPE_NONE			= 0;
	public static final int NUMBER_TYPE_AREA_TWO		= 1;	// 서울 02와 같에 2자리 지역번호
	public static final int NUMBER_TYPE_AREA_THREE		= 2;	// 031 등과 같은 3자리 지역번호
	public static final int NUMBER_TYPE_MOBILE_THREE	= 3;	// 010과같은 3자리 핸드폰
	public static final int NUMBER_TYPE_ETC_THREE		= 4;	// 070, 080과 같은 사업자 3자리 번호
	public static final int NUMBER_TYPE_ETC_FOUR		= 5;	// 1566등과 같은 사업자 4자리 번호 또는 0이 아닌 숫자로 시작하는 번호
	public static int getPhoneNumberType(String number) {
		try {
			if(isValidPhoneNumber(number)) {
				if(number.length() >= 2) {
					if(number.charAt(0) == '0') {
						char c = number.charAt(1);
						if(c == '1') {
							return NUMBER_TYPE_MOBILE_THREE;
						} else if(c == '2') {
							return NUMBER_TYPE_AREA_TWO;
						} else if(c <= '6') {
							return NUMBER_TYPE_AREA_THREE;
						} else {
							return NUMBER_TYPE_ETC_THREE;
						}
					} else {
						if(number.length() > 8) {
							return NUMBER_TYPE_AREA_THREE;
						}
						return NUMBER_TYPE_ETC_FOUR;
					}
				}
			}
		} catch (Exception e) {

		}
		return NUMBER_TYPE_NONE;
	}
	
	private static int getPhoneNumberLength(String number) {
		int count = 0;
		for(int i=0; i<number.length(); i++) {
			char c = number.charAt(i);
			if(c >= '0' && c <= '9') {
				count++;
				continue;
			}
			break;
		}
		return count;
	}
	
	/**
	 * phone number 인지 확인 
	 * @param number
	 * @return
	 */
	public static boolean isValidPhoneNumber(String number) {
		try {
			char c;
			int len = number.length();
			for(int i=0; i<len; i++) {
				c = number.charAt(i);
				if((c >= '0' && c <= '9') || c == '*' || c == '#' || c == '-') {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}	
	
	/**
	 * 주민등록번호 체크섬 확인
	 * 
	 * @param sid1
	 *            주민등록번호 앞 6자리
	 * @param sid2
	 *            주민등록번호 뒤 7자리
	 * @return 체크섬 확인
	 */
	public static boolean isValidSidCheckSum(String sid1, String sid2) {
		if (TextUtils.isEmpty(sid1) || TextUtils.isEmpty(sid2)) {
			return false;
		}
		if (sid1.length() != 6 || sid2.length() != 7) {
			return false;
		}
		if (!TextUtils.isDigitsOnly(sid1) || !TextUtils.isDigitsOnly(sid2)) {
			return false;
		}
		int n = 2;
		int sum = 0;
		for (final char c : sid1.toCharArray()) {
			sum += (c - '0') * n++;
			if (n == 10) {
				n = 2;
			}
		}
		for (final char c : sid2.substring(0, 6).toCharArray()) {
			sum += (c - '0') * n++;
			if (n == 10) {
				n = 2;
			}
		}
		int check = 11 - sum % 11;
		if (check == 11) {
			check = 1;
		}
		if (check == 10) {
			check = 0;
		}
		return check == (sid2.charAt(6) - '0');
	}
	
	/**
	 * 주민등록번호에서 생년을 구하기 위한 값
	 */
	private final static int[] CENTURY_BIRTH = {
			//
			1800, 1900,
			//
			1900, 2000,
			//
			2000, 1900,
			//
			1900, 2000,
			//
			2000, 1800 };

	/**
	 * 주민등록번호에서 생년(YYYY포맷)을 구한다.
	 */
	public static String getBirthYear(String sid1, String sid2) {
		final int yy = Integer.valueOf(sid1.substring(0, 2));
		final int cc = sid2.charAt(0) - '0';
		return String.valueOf(CENTURY_BIRTH[cc] + yy);
	}

//	/**
//	 * {@link Glide} 에서 사용할 리소스 URI 를 생성한다
//	 */
//	public static String getResourceUri(Context context, int id) {
//		final Resources resources = context.getResources();
//		final Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//				+ "://" + resources.getResourcePackageName(id) + '/'
//				+ resources.getResourceTypeName(id) + '/'
//				+ resources.getResourceEntryName(id));
//		return uri.toString();
//	}

	public static String convertDuration(long duration) {
//        String out = null;
//        long hours = 0;
//        try {
//            hours = (duration / 3600000);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return out;
//        }
//        long remaining_minutes = (duration - (hours * 3600000)) / 60000;
//        String minutes = String.valueOf(remaining_minutes);
//        if (minutes.equals(0)) {
//            minutes = "00";
//        }
//        long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
//        String seconds = String.valueOf(remaining_seconds);
//        if (seconds.length() < 2) {
//            seconds = "00";
//        } else {
//            seconds = seconds.substring(0, 2);
//        }
//
//        if (hours > 0) {
//            out = hours + ":" + minutes + ":" + seconds;
//        } else {
//            out = minutes + ":" + seconds;
//        }
//
//        return out;

		int min = 0;
		int sec = 0;

		String minStr = null;
		String secStr = null;

		min = (int) Math.floor(duration / (1000 * 60));
		sec = (int) Math.floor((duration - (1000 * 60) * min) / 1000);

		minStr = min < 10 ? "0" + min : "" + min;
		secStr = sec < 10 ? "0" + sec : "" + sec;

		return minStr + ":" + secStr;
	}
}
