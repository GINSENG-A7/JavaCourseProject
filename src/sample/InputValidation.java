package sample;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Locale;

public class InputValidation {
    public static Boolean isRowConsistsOfLetters(String textField) {
        String []  str = new String []{"й","ц","у","к","е","н","г","ш","щ","з","х","ъ","ф","ы","в","а","п","р","о","л","д","ж","э","я","ч","с","м","и","т","ь","б","ю","ё","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        char[]  textFieldAsChars = textField.toLowerCase(Locale.ROOT).toCharArray();

        if(textFieldAsChars.length==0)
        {
            return false;
        }

        Boolean flag = false;
        for (int i = 0; i < textFieldAsChars.length; i++) {

            flag = false;

            for (int j = 0; j <  str.length ; j++) {

                if(Character.toString(textFieldAsChars[i]).equals(str[j])){
                    flag = true;
                    break;
                }
            }
            if(flag != true)
            {
                return  false;
            }
        }
        return  true;
    }

    public static boolean isRowConsistsOfNumbers(String value) {
        char [] symbol = value.toCharArray();
        try {
            for (var s: symbol) {
                Integer.parseInt(String.valueOf(s));
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean CheckUniquenessObjectInToDataBase(String str, ArrayList<String> list){
        for (var item : list){
            if(item.equals(str)) {
                return false;
            }
        }
        return  true;
    }
    public static boolean CheckUniquenessObjectInToDataBase(String str,Integer id, ArrayList<String> list, ArrayList<Integer> idList){
        for (int i = 0; i < list.size(); i++) {
            if( (list.get(i).equals(str)) && !idList.get(i).equals(id)) {
                return false;
            }
        }
        return  true;
    }

    public static boolean CheckLenthOfPassportSeries(String str) {
        if(str.length() == 4) {
            return true;
        }
        return false;
    }

    public static boolean CheckLenthOfPassportNumber(String str) {
        if(str.length() == 6) {
            return true;
        }
        return false;
    }

    public static void ShowAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Результат выполнения операции");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
