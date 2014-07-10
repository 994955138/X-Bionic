package com.imcore.x_bionic.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * JSON閺佺増宓佺憴锝嗙�瀹搞儱鍙跨猾浼欑礉閻€劋绨亸鍜糞ON鐎涙顑佹稉鑼舵祮閹广垺鍨氶幐鍥х暰閻ㄥ嫬顕挒鈩冨灗鐎电
 * 钖凩ist閿涘奔浜掗崣濂檃p閹存牕瀵橀崥鐜�p閻ㄥ嚛ist
 * 
 * @author Li Bin
 */
public class JsonUtil<T> {
	private static final String LOG_JSON_ERROR = "com.imcore.common.util.JsonError";

	private static final String BYTE = "java.lang.Byte";
	private static final String INTEGER = "java.lang.Integer";
	private static final String SHORT = "java.lang.Short";
	private static final String LONG = "java.lang.Long";
	private static final String BOOLEAN = "java.lang.Boolean";
	private static final String CHAR = "java.lang.Character";
	private static final String FLOAT = "java.lang.Float";
	private static final String DOUBLE = "java.lang.Double";

	private static final String VALUE_BYTE = "byte";
	private static final String VALUE_INTEGER = "int";
	private static final String VALUE_SHORT = "short";
	private static final String VALUE_LONG = "long";
	private static final String VALUE_BOOLEAN = "boolean";
	private static final String VALUE_CHAR = "char";
	private static final String VALUE_FLOAT = "float";
	private static final String VALUE_DOUBLE = "double";

	/**
	 * 閺嶈宓乲ey閼惧嘲褰囩紒娆忕暰閻ㄥ埊son閺佺増宓侀惃鍕拷
	 * 
	 * @param json
	 *            缂佹瑥鐣鹃惃鍑ON鐎涙顑佹稉锟� * @param key 閹稿洤鐣鹃惃鍕渽閼惧嘲褰囬崐鍏煎鐎电懓绨查惃鍒眅y
	 * @return 鏉╂柨娲栨稉锟介嚋鐎涙顑佹稉璇х礉鐞涖劎銇氶弽瑙勫祦閹稿洤鐣鹃惃鍒眅y閹碉拷绶遍崚鎵畱閸婄》绱濋懢宄板絿婢惰精瑙﹂幋鏍у絺閻㈢
	 *         儼SON鐟欙絾鐎介柨娆掝嚖閸掓瑨绻戦崶鐐碘敄鐎涙顑佹稉锟�
	 */
	public static String getJsonValueByKey(String json, String key) {
		String value = "";
		try {
			JSONObject jo = new JSONObject(json);
			value = jo.getString(key);
		} catch (JSONException e) {
			Log.e(LOG_JSON_ERROR, e.getLocalizedMessage());
		}
		return value;
	}

	/**
	 * 鐏忓棙瀵氱�姘辨畱JSON鐎涙顑佹稉鑼舵祮閹广垺鍨歝ls閹稿洤鐣鹃惃鍕閻ㄥ嫬鐤勬笟瀣嚠鐠烇拷
	 * 
	 * @param json
	 *            缂佹瑥鐣鹃惃鍑ON鐎涙顑佹稉锟� * @param cls
	 *            閹稿洤鐣剧憰浣芥祮閹广垺鍨氶惃鍕嚠鐠炩剝澧嶇仦鐐垫畱缁鐎稢lass鐎圭偘绶�
	 * @return 
	 *         鏉╂柨娲朿ls閹稿洤鐣剧猾璇茬�閻ㄥ嫬顕挒鈥崇杽娓氾拷閸忔湹鑵戦惃鍕摟濞堝吀绗宩son閺佺増宓侀柨顔硷拷鐎甸�绔存稉锟筋嚠鎼达拷
	 */
	public static <T> T toObject(String json, Class<T> cls) {
		T obj = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			obj = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isPrivate(field.getModifiers())) {
					continue;
				}
				try {
					String key = field.getName();
					if (jsonObject.get(key) == JSONObject.NULL) {
						field.set(obj, null);
					} else {
						Object value = getValue4Field(jsonObject.get(key),
								jsonObject.get(key).getClass().getName());
						field.set(obj, value);
					}
				} catch (Exception e) {
					field.set(obj, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG_JSON_ERROR, e.getLocalizedMessage());
		}
		return obj;
	}

	/**
	 * 閹跺﹥瀵氱�姘辨畱鐎电钖刼rginalValue鏉烆剚宕查幋鎭peName閹稿洤鐣鹃惃鍕閸ㄥ娈戠�纭呰杽
	 * 
	 * @param orginalValue
	 *            鐎电钖勯崷銊ㄦ祮閹诡枍绠ｉ崜宥囨畱閸婏拷
	 * @param fieldType
	 *            鐟曚浇娴嗛幑銏㈡畱缁鐎烽崥宥囆�
	 * @return
	 */
	private static Object getValue4Field(Object orginalValue, String typeName) {
		Log.i("Json_Util", typeName);
		Object value = orginalValue.toString();
		if (typeName.equals(BYTE) || typeName.equals(VALUE_BYTE)) {
			value = Byte.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(INTEGER) || typeName.equals(VALUE_INTEGER)) {
			value = Integer.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(SHORT) || typeName.equals(VALUE_SHORT)) {
			value = Short.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(LONG) || typeName.equals(VALUE_LONG)) {
			value = Long.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(BOOLEAN) || typeName.equals(VALUE_BOOLEAN)) {
			value = Boolean.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(CHAR) || typeName.equals(VALUE_CHAR)) {
			value = Character.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(FLOAT) || typeName.equals(VALUE_FLOAT)) {
			value = Float.class.cast(orginalValue);
			return value;
		}
		if (typeName.equals(DOUBLE) || typeName.equals(VALUE_DOUBLE)) {
			value = Double.class.cast(orginalValue);
			return value;
		}
		return value;
	}

	/**
	 * 鐏忓棙瀵氱�姘辨畱JSON鐎涙顑佹稉鑼舵祮閹广垺鍨氶崠鍛儓cls閹稿洤鐣鹃惃鍕閸ㄥ娈戠�鐐扮秼鐎电钖凩ist闂嗗棗鎮�
	 * 
	 * @param json
	 *            缂佹瑥鐣鹃惃鍑ON鐎涙顑佹稉锟� * @param cls
	 *            閹稿洤鐣剧憰浣芥祮閹广垺鍨氶惃鍕嚠鐠炩剝澧嶇仦鐐垫畱缁鐎稢lass鐎圭偘绶�
	 * @return 
	 *         鏉╂柨娲栨稉锟介嚋List闂嗗棗鎮庨敍灞藉従娑擃厼瀵橀崥鐜on娑擃厾娈戦弫鐗堝祦閸忓啰绀岄幍锟筋嚠鎼存梻娈戠�鐐扮秼鐎电钖勭�鐐扮伐
	 */
	public static <T> List<T> toObjectList(String json, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			List<String> jsonStrList = toJsonStrList(json);
			for (String jsonStr : jsonStrList) {
				T obj = toObject(jsonStr, cls);
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG_JSON_ERROR, e.getLocalizedMessage());
		}
		return list;
	}

	/**
	 * 鐏忓棔绔存稉顏呮殶缂佸嫬鐎穓son鐎涙顑佹稉鑼舵祮閹广垺鍨氶崠鍛儓鐎涙亾son鐎涙顑佹稉鑼畱List闂嗗棗鎮�
	 * 
	 * @param json
	 *            缂佹瑥鐣鹃惃鍑ON鐎涙顑佹稉锟� * @return
	 *            鏉╂柨娲栨稉锟介嚋List闂嗗棗鎮庨敍灞藉瘶閸氼偂绔寸紒鍕摟缁楋缚瑕嗛敍灞筋嚠鎼存柧绨
	 *            紒娆忕暰閸樼喎顬奐SON閺佺増宓侀崘鍛帗缁辩姷娈戠�妤冾儊娑撴彃鑸板锟�
	 */
	public static List<String> toJsonStrList(String json) {
		List<String> strList = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				String jsonStr = jsonArray.getString(i);
				strList.add(jsonStr);
			}
		} catch (JSONException e) {
			Log.e(LOG_JSON_ERROR, e.getMessage());
		}
		return strList;
	}

	/**
	 * 鐏忓攬son鐎涙顑佹稉鑼舵祮閹诡枍璐烳ap
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json) {
		Map<String, Object> map = null;
		try {
			JSONObject jo = new JSONObject(json);
			map = convertJSONObjectToMap(jo);
		} catch (Exception e) {
			Log.e(LOG_JSON_ERROR, e.getMessage());
		}
		return map;
	}

	/**
	 * 鐏忓攬son鐎涙顑佹稉鑼舵祮閹诡枍璐熼崠鍛儓Map閻ㄥ嚛ist闂嗗棗鎮�
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> toMapList(String json) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				Map<String, Object> map = convertJSONObjectToMap(jo);
				mapList.add(map);
			}
		} catch (JSONException e) {
			Log.e(LOG_JSON_ERROR, e.getMessage());
		}
		return mapList;
	}

	/**
	 * 鐏忓棔绔存稉鐙玈ONObject鐎电钖勬潪顒佸床娑撶瘲ap
	 * 
	 * @param jo
	 * @return
	 * @throws JSONException
	 */
	private static Map<String, Object> convertJSONObjectToMap(JSONObject jo)
			throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject newJo = mergeJsonNodes(jo);

		JSONArray names = newJo.names();
		for (int i = 0; i < names.length(); i++) {
			String key = names.getString(i);
			Object value = newJo.get(key);
			if ((value != null) && (!value.toString().equals(""))
					&& (!value.toString().equals("null"))) {
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * 鐏忓捈SON鐎电钖勯惃鍕姜娑擄拷妯佺�鎰波閻愰�绗屾稉锟芥▉缂佹挾鍋ｉ崥鍫濊嫙
	 * 
	 * @param oldJo
	 *            閸栧懎鎯堥棃鐐扮闂冨墎绮ㄩ悙鍦畱Json鐎电钖�
	 * @return 鏉╂柨娲栭崥鍫濊嫙娑斿鎮楅惃鍕剁礉閸欘亜瀵橀崥顐＄闂冨墎绮ㄩ悙鍦畱Json鐎电钖�
	 */
	private static JSONObject mergeJsonNodes(JSONObject oldJo)
			throws JSONException {
		JSONObject newJo = oldJo;
		JSONArray names = newJo.names();
		List<String> delKeys = new ArrayList<String>(); // 瀵板懎鍨归梽銈囨畱闂堢偘绔撮梼鍓佺波閻愬湱娈慗son鐎电钖勯惃鍒眅y

		// 閹垫儳鍤棁锟筋渽閸氬牆鑻熼惃鍕摍缂佹挾鍋ｉ惃鍒眅y
		for (int i = 0; i < names.length(); i++) {
			String key = names.getString(i);
			if (newJo.optJSONObject(key) != null) {
				delKeys.add(key);
			}
		}
		// 閸氬牆鑻熼幍鎯у煂閻ㄥ嫬鐡欑紒鎾跺仯娑撳簼绔撮梼鍓佺波閻愮櫢绱濋獮璺哄灩闂勩倕甯崗鍫㈡畱鐎涙劗绮ㄩ悙锟�
		for (String key : delKeys) {
			JSONObject subJo = newJo.getJSONObject(key);
			subJo = mergeJsonNodes(subJo); // 闁帒缍婇弫瀵告倞鐎涙劗绮ㄩ悙鍦畱閹碉拷婀佺�鎰波閻愶拷
			newJo = merge(newJo, subJo);
			newJo.remove(key);
		}
		return newJo;
	}

	/**
	 * 閸氬牆鑻熸稉銈勯嚋JSON鐎电钖�
	 * 
	 * @param jo1
	 * @param jo2
	 * @return 鏉╂柨娲栭崥鍫濊嫙娑斿鎮楅惃鍑ON鐎电钖�
	 */
	private static JSONObject merge(JSONObject jo1, JSONObject jo2)
			throws JSONException {
		JSONObject newJo = jo1;
		JSONArray names = jo2.names();
		for (int i = 0; i < names.length(); i++) {
			String key = names.getString(i);
			newJo.put(key, jo2.get(key));
		}
		return newJo;
	}

	/**
	 * 閸掋倖鏌囨稉锟介嚋JSON鐎涙顑佹稉鍙夋Ц閸氾附妲哥粚鐑樻殶閹癸拷
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJsonNull(String json) {
		if (json == null || json.equals("") || json.equals("null")
				|| json.equals("{}") || json.equals("[]")) {
			return true;
		} else {
			return false;
		}
	}
}