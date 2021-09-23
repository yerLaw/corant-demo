package org.corant.experiment.test;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/8/12
 * @since
 */
public class Test {
  public static void main(String[] args) {
    String bmwModelNumber = "F01";
    String taModelNumber = "F01|F02";
    String s1 = epcModelDesignNumber(bmwModelNumber, taModelNumber);
    System.out.println("s1 = " + s1);
    System.out.println("modelDesignNumber(taModelNumber) = " + modelDesignNumber(taModelNumber));
  }


  public static String epcModelDesignNumber(String modelNumber, String taModelNumber) {
    String[] strings = taModelNumber.split("\\|");
    StringBuilder sb = new StringBuilder();
    if (strings.length == 1) {
      sb.append(String.valueOf(modelNumber).repeat(10));
    } else if (strings.length >= 2) {

      sb.append(String.valueOf(modelNumber).repeat(strings.length));
    }
    return sb.toString();
  }

  public static String modelDesignNumber(String modelNumber) {
    String[] strings = modelNumber.split("\\|");
    StringBuilder sb = new StringBuilder();
    if (strings.length == 1) {
      sb.append(modelNumber.repeat(10));
    } else if (strings.length >= 2) {
      for (String string : strings) {
        sb.append(string);
      }
    }
    return sb.toString();
  }
}
