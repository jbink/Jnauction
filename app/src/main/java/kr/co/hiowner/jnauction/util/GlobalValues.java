package kr.co.hiowner.jnauction.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.hiowner.jnauction.R;


public class GlobalValues {

	public final static int WIFI = 0;	
	public final static int LTE_3G = 1;
	public final static int POPUP_RESULT = 1414;
	public final static int POPUP_REQUEST = 1515;
	public final static String SERVER = "http://joongna.hiowner.co.kr/api";
	public final static String SERVER1 = "http://joongna.hiowner.co.kr/api/";
	public final static String SERVER_SUCCESS = "200";
	public final static int LIMIT= 100;


	static Typeface mFont = null;
	/**
	 * @param context
	 * @return Typeface 폰트
	 */
	public static Typeface getFont(Context context){
		if(mFont == null)
			mFont = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothic.ttf");
		return mFont;
	}

	static Typeface mBoldFont = null;
	public static Typeface getBoldFont(Context context){
		if(mBoldFont == null)
			mBoldFont = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothicBold.ttf");
		return mBoldFont;
	}

	static Typeface mLightFont = null;
	public static Typeface getLightFont(Context context){
		if(mLightFont == null)
			mLightFont = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothicLight.ttf");
		return mLightFont;
	}

	/**
	 *  email
	 *  true :이메일 형식
	 */
	public static boolean isEmailValid(String _email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//	    String expression = "/^([a-z0-9\\+_\\-]+)(\\.[a-z0-9\\+_\\-]+)*@([a-z0-9\\-]+\\.)+[a-z]{2,6}$ix)";
	    CharSequence inputStr = _email;
	    
	    //이메일 @ 앞의 글자가 최소 3자 이상
	    String[] lengthCheck = _email.split("@");
	    if(lengthCheck[0].length() < 3 || _email.length() > 50){
	    	return false;
	    }

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	/**
	 * 영문,숫자 2가지 이상으로 조합이 된지를 체크하는 메소드 
	 *  newPassword
	 *  true : 2가지 이상 조합, false : 1가지 문자로만 구성 
	 */
	public static boolean isPasswdValid(String _newPassword) {
		
		char [] c = _newPassword.toCharArray();
		boolean isValid = false;
		int passLeng = _newPassword.length();
		if(passLeng < 6 || passLeng > 16)
			return isValid;
	
		int hasEng = 0;
		int hasNum = 0;
		int hasMark = 0;
		int mixNum = 0;
		
		for(int i = 0 ; i < passLeng ; i++){
			if((0x0041 <= c[i] && c[i] <= 0x005A) || (0x0061 <= c[i] && c[i] <= 0x007A)) {
				hasEng = 1;
			}else if(0x0030 <= c[i] && c[i] <= 0x0039) {
				hasNum = 1;
			}else{
				hasMark = 1;
			}
			mixNum = hasEng+hasNum+hasMark;
			
			if(mixNum >= 2){
				isValid = true;
				break;
			}
		}
		return isValid;
	}
	
	/**
	 * @param _name
	 * @return true : 이름이 한글 8자 이내, 영문 31자 이내
	 */
	public static boolean isNameValid(String _name){
		boolean isValid = false;
		
		char [] c = _name.toCharArray();
		
		int nameLength = _name.length();
		int korLength = 0;
		int engLength = 0;
		int numLength = 0;
		
		for (int i=0 ; i<nameLength ; i++){
			if(( 0xAC00 <= c[i] && c[i] <= 0xD7A3 ) || ( 0x3131 <= c[i] && c[i] <= 0x318E ))
				korLength++;//한글
			else if((0x0041 <= c[i] && c[i] <= 0x005A) || (0x0061 <= c[i] && c[i] <= 0x007A)) 
				engLength++;//영문
			else if(0x0030 <= c[i] && c[i] <= 0x0039)
				numLength++;//숫자
		}
			
		int totalLength = korLength + engLength + numLength;
		if(totalLength < 3 && totalLength > 8){
			return isValid;
		}
		else{
			isValid = true;
			return isValid;
		}
		
	}
	
	/**
	 *  context
	 *  Package : 해당패키지명
	 */
	public static void keyhash(Context context, String pkg) {
		try{
			PackageInfo info = 
					context.getPackageManager()
					.getPackageInfo(pkg, PackageManager.GET_SIGNATURES);
			
			for(Signature sign : info.signatures){
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(sign.toByteArray());
			}
					
		}catch(Exception e){
			
		}
	}
	
	/**
	 * @param packageNmae : 카카오스토리, 페이스북 Package명
	 * @return true : 각각 설치되어 있음 false : 없음
	 */
	public static boolean checkPackage(Context context, String packageNmae){
			
		try {
	        PackageManager pm = context.getPackageManager();
	        PackageInfo pi = pm.getPackageInfo(packageNmae.trim(), PackageManager.GET_META_DATA);
	        ApplicationInfo appInfo = pi.applicationInfo;
	         
	        // 패키지가 있을 경우.
	        return true;
	    }
	     
	    catch (NameNotFoundException e)
	    {
	        // 패키지가 없을 경우.
	        Log.e("where", "패키지가 설치 되어 있지 않습니다.");
	        return false;
	    }
	}
	
	
	/**
	 * 업데이트 하기 위해 구글플레이로 이동
	 */
	public static void updateAppGoToGooglePlay(Context context, String packageName){
		final Intent marketIntent = new Intent(Intent.ACTION_VIEW);
		marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		marketIntent.setData( Uri.parse("market://details?id=" + packageName));
		context.startActivity(marketIntent);
	}
	
	public static String getDeviceId(Context context){
		
		String deviceID = "";
		
		try {
			deviceID = deviceID + ((String) Build.class.getField("SERIAL").get(null));
//			return (String) Build.class.getField("SERIAL").get(null);
		} catch (Exception ignored) {
//			return null;
		}

//		deviceID = deviceID + Settings.Secure.ANDROID_ID;

		
		TelephonyManager telephony = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		deviceID = deviceID + telephony.getDeviceId();
		
		return deviceID;
	}
	
	public static String getTimeStampToDay(String serverValue) {
		Timestamp serverTime = null;
		try{
			serverTime = new Timestamp(Long.parseLong(serverValue));
		}catch(NumberFormatException e){
			return null;
		}
		Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());

		long diffInSeconds = (serverTime.getTime() - (currentTime.getTime() / 1000));

		if (diffInSeconds < 0)
			return "";

		String ment = DateUtils.formatElapsedTime(diffInSeconds);
		long sec = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
		long min = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
		long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
		long days = (diffInSeconds = (diffInSeconds / 24)) >= 30 ? diffInSeconds % 30 : diffInSeconds;
		long months = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12 : diffInSeconds;
		long years = (diffInSeconds = (diffInSeconds / 12));
		
		String day = String.valueOf(days);
//		if(hour.length() == 1)
//			hour = "0"+hour;


		return day;
	}
	
	public static String getWonFormat(String value){
		DecimalFormat df = new DecimalFormat("#,###");
		try{
			return df.format(value);
		}catch(Exception e){
			return value;
		}
	}
	
	public static String toSHA256(String plain) {
		MessageDigest messageDigest = null;
		String result = null;
		try 
		{
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.reset();
			byte [] bytes = messageDigest.digest(plain.getBytes("UTF-8"));
			StringBuffer buffer = new StringBuffer();
			for(int count = 0; count < bytes.length; count++)
				{ buffer.append(Integer.toString((bytes[count] & 0xff) + 0x100, 16).substring(1)); }
			result = buffer.toString();
		}
		catch (NoSuchAlgorithmException e) { e.printStackTrace(); } 
		catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		return result;
	}
	
	public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }
	
	public static String getDateTime(long time) {
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTimeInMillis(time);
		String date = DateFormat.format("yyyy-MM-dd hh:mm", cal).toString();
		return date;
	}
	
	public static String removeTag(String value) {
		String tempGuide = value;
		if(tempGuide.contains("<")){
			tempGuide = tempGuide.replace("<p>", "");
			tempGuide = tempGuide.replace("</p>", "<br>");
			tempGuide = tempGuide.substring(0, tempGuide.lastIndexOf("<"));
			tempGuide = Html.fromHtml(tempGuide).toString();
//			tempGuide = Jsoup.parse(tempGuide).text();
			return tempGuide;
	}else
		return tempGuide;
	}
	
	/**
	 * network 연결 상태 확인
	 * @return 
	 * true : 네트워크 나 와이파이로 연결이 되어 있음
	 * false : 깡통폰임
	 */
	public static int isOnline(Context context) { // 
		try {
			ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			State wifi = conMan.getNetworkInfo(1).getState(); // wifi
			if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return GlobalValues.WIFI;

			}
			State mobile = conMan.getNetworkInfo(0).getState(); // mobile
																// ConnectivityManager.TYPE_MOBILE
			if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
				return GlobalValues.LTE_3G;
			}
		} catch (NullPointerException e) {
			return -1;
		}
		return -1;
	}
	
	/**
	 * This method converts dp unit to equivalent pixels, depending on device density. 
	 * 
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
	/**픽셀을 DP로 계산.
	 * @param context
	 * @param x PX값
	 * @return
	 */
	public static int getDP(Context context, int x){
		Resources r = context.getResources();
		x = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, 
				r.getDisplayMetrics());
		return x;
	}
	
	/**
	 * 단말기 density 구함
	 * 
	 * @param con
	 *            사용법 : if(getDensity(context) == 2f && (float으로 형변환해서 사용 해야함.)
	 */
	public static float getDensity(Context con) {
		float density = 0.0f;
		density = con.getResources().getDisplayMetrics().density;
		Log.d("value", "density = " + density);
		return density;
	}

	/**
	 * px을 dp로 변환
	 * 
	 * @param con
	 * @param px
	 * @return dp
	 */
	public static int getPxToDp(Context con, int px) {
		float density = 0.0f;
		density = con.getResources().getDisplayMetrics().density;
		Log.d("value", "density1 = " + density);
		return (int) (px / density);
	}

	/**
	 * dp를 px로 변환
	 * 
	 * @param con
	 * @param dp
	 * @return px
	 */
	public static int getDpToPix(Context con, double dp) {
		float density = 0.0f;
		density = con.getResources().getDisplayMetrics().density;
		Log.d("value", "density = " + density);
		return (int) (dp * density + 0.5);
	}

	/**
	 * 단말기 가로 해상도 구하기
	 * 
	 * @param activity
	 * @return width
	 */
	public static int getScreenWidth(Activity activity) {
		int width = 0;
		width = activity.getWindowManager().getDefaultDisplay().getWidth();
		Log.i("value", "Screen width = " + width);
		return width;
	}

	/**
	 * 단말기 세로 해상도 구하기
	 * 
	 * @param activity
	 * @return hight
	 */
	public static int getScreenHeight(Activity activity) {
		int height = 0;
		height = activity.getWindowManager().getDefaultDisplay().getHeight();
		Log.i("value", "Screen height = " + height);
		return height;
	}

	/**
	 * 단말기 가로 해상도 구하기
	 * 
	 * @param context
	 */
	public static int getScreenWidth(Context context) {
		Display dis = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = dis.getWidth();
		Log.i("value", "Screen Width = " + width);
		return width;
	}

	/**
	 * 단말기 세로 해상도 구하기
	 * 
	 * @param context
	 */
	public static int getScreenHeight(Context context) {
		Display dis = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int height = dis.getHeight();
		Log.i("value", "Screen height = " + height);
		return height;
	}
	
	/**
	 *  format에 맞는 day
	 *  특정날짜 구하기
	 */
	public static String getDetailDay(String format, String day, int index) {
		Date dDate = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			dDate = sdf.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long temp = dDate.getTime();
		dDate = new Date(temp+(1000*60*60*24*+index)); //어제
		
		return sdf.format(dDate);
	}
	public static String getWeekOfDay(int day){
		switch (day){
			case Calendar.SUNDAY : return "일요일";
			case Calendar.MONDAY : return"월요일";
			case Calendar.TUESDAY : return"화요일";
			case Calendar.WEDNESDAY : return"수요일";
			case Calendar.THURSDAY : return"목요일";
			case Calendar.FRIDAY : return"금요일";
			case Calendar.SATURDAY : return"토요일";
			default: return "X";
		}
	}

	public static void setStatusBarColor(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
		}
	}

	public static String getMyPhoneNumber(Context context){
		TelephonyManager telManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		String phoneNum = telManager.getLine1Number();
		Log.d("where", "tel : " + phoneNum);
		if(!TextUtils.isEmpty(phoneNum) && phoneNum.substring(0,3).equals("+82")){
			phoneNum = phoneNum.replace("+82", "0");
			return phoneNum;
		}
		else
			return "Empty Phone Number!";
	}

	public static String setTextAdd_0(long value){
		String returnValue = null;
		returnValue = String.valueOf(value);
		if(returnValue.length() < 2)
			returnValue = "0"+returnValue;
		return returnValue;
	}
}
