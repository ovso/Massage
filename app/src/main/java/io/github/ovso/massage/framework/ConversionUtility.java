package io.github.ovso.massage.framework;

/**
 * Created by jaeho on 2017. 12. 5
 */

public class ConversionUtility {
  public static String convertUnit(int number) {
    if (number < 1000) {
      return String.valueOf(number);
    } else if (number < 1000000) {
      return (number / 1000) + "K+";
    } else if (number < 1000000000) {
      return (number / 1000000) + "M+";
    } else {
      return "∞";
    }
  }
}