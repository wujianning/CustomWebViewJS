package com.wjn.customwebviewjs;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wjn on 2017/11/21.
 * 自定义Toast
 */

public class ToastUtils {

	private Activity activity;

	public ToastUtils(Activity activity) {
		this.activity = activity;
	}

	private Toast mToast;

	// 普通
	public void showToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

}
