public class Utils {

    public static boolean isAllPositiveNumbers(String... str) {

        boolean checkStr = true;

        for (String string : str) {

            if (!StringUtils.isPositiveNumber(string)) {

                checkStr = false;
                break;
            }
        }

        return checkStr;
    }
}
