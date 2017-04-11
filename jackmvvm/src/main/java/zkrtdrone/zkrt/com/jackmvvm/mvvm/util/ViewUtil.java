package zkrtdrone.zkrt.com.jackmvvm.mvvm.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

import zkrtdrone.zkrt.com.jackmvvm.R;


public class ViewUtil {

	/**
	 * 隐藏Android底部的虚拟按键
	 */
	public static void hideVirtualKey(Activity activity){
		Window window = activity.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		window.setAttributes(params);
	}

	/**
	 * activity自动findview
	 */
	public static void autoFind(Activity activity) {
		try {
			Class<?> clazz = activity.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget,WebView
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());// 获得view的id
						field.setAccessible(true);
						field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fragment以及ViewHolder等自动findview
	 */

	public static void autoFind(Object obj, View view) {
		try {
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());
						field.setAccessible(true);
						field.set(obj, view.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideKeyboard(Activity c) {
		try {
			InputMethodManager imm = (InputMethodManager) c
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
		} catch (NullPointerException e) {
		}
	}
}
