package io.github.ovso.massage.framework;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by jaeho on 2017. 10. 11
 */

public class SystemUtility {

  public static void hideKeyboard(Context context, View view) {
    InputMethodManager imm =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
