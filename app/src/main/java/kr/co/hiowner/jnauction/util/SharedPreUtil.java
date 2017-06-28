package kr.co.hiowner.jnauction.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreUtil {

	//SharedPreference 이름
	static public String SHARED_PREF_NAME = "hiowner_jnauction";
/*************************************************************************************************/
	//TokenID
	static public String PREF_TOKEN_ID = "token_id";
	/**
	 * Token ID
	 *
	 *  ctx
	 *  value = Member UID
	 */
	static public void setTokenID(Context ctx, String value) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
		editor.putString(PREF_TOKEN_ID, value);
		editor.commit();
	}

	/**
	 * Token ID
	 *
	 *  default -> null
	 */
	static public String getTokenID(Context ctx) {
//		return "L9d1XqDdX14=";
		return ctx.getSharedPreferences(SHARED_PREF_NAME, 0).getString(PREF_TOKEN_ID, null);
	}
	/*************************************************************************************************/
	//자동로그인
	static public String PREF_AUTO_LOGIN = "auto_login";
	/**
	 *  ctx
	 *  value = check 자동로그인
	 */
	static public void setAutoLogin(Context ctx, boolean value) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
		editor.putBoolean(PREF_AUTO_LOGIN, value);
		editor.commit();
	}

	/**
	 * 자동로그인
	 *
	 *  default -> false
	 */
	static public boolean getAutoLogin(Context ctx) {
//		return "L9d1XqDdX14=";
		return ctx.getSharedPreferences(SHARED_PREF_NAME, 0).getBoolean(PREF_AUTO_LOGIN, false);
	}
/*************************************************************************************************/
	//UUID
	static public String PREF_UUID = "uuid";
	/**
	 * UUID
	 *  ctx
	 *  value = UUID
	 */
	static public void setUUID(Context ctx, String value) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
		editor.putString(PREF_UUID, value);
		editor.commit();
	}

	/**
	 * UUID
	 *
	 *  default -> null
	 */
	static public String getUUID(Context ctx) {
//		return "L9d1XqDdX14=";
		return ctx.getSharedPreferences(SHARED_PREF_NAME, 0).getString(PREF_UUID, null);
	}
/*************************************************************************************************/
	//DEVICE_MODEL
	static public String PREF_DEVICE_MODEL = "device_model";
	/**
	 * DEVICE_MODEL
	 *  ctx
	 *  value = UUID
	 */
	static public void setDeviceModel(Context ctx, String value) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
		editor.putString(PREF_DEVICE_MODEL, value);
		editor.commit();
	}

	/**
	 * DEVICE_MODEL
	 *
	 *  default -> null
	 */
	static public String getDeviceModel(Context ctx) {
//		return "L9d1XqDdX14=";
		return ctx.getSharedPreferences(SHARED_PREF_NAME, 0).getString(PREF_DEVICE_MODEL, null);
	}
/*************************************************************************************************/
	//PUSH_TOKEN
	static public String PREF_PUSH_TOKEN = "push_token";
	/**
	 * PUSH_TOKEN
	 *  ctx
	 *  value = UUID
	 */
	static public void setPushToken(Context ctx, String value) {
		SharedPreferences.Editor editor = ctx.getSharedPreferences(SHARED_PREF_NAME, 0).edit();
		editor.putString(PREF_PUSH_TOKEN, value);
		editor.commit();
	}

	/**
	 * PUSH_TOKEN
	 *
	 *  default -> null
	 */
	static public String getPushToken(Context ctx) {
		return ctx.getSharedPreferences(SHARED_PREF_NAME, 0).getString(PREF_PUSH_TOKEN, null);
	}
}
