package commons.utils;

import java.util.*;
import java.util.stream.Collectors;

public class Countries {
    private static String[] locales = Locale.getISOCountries();
    private static List<String> countries;
    private static Map<String, String> countryCodes = new HashMap<String, String>();
    //to fill comboBoxes
    public static List<String> getAll(){
        return countries;
    }

    //to be used to getFlag
    public static String getCodebyCountry(String cName){return countryCodes.get(cName); }
    static {
      countries =  Arrays.stream(locales).map(s -> {return new Locale("",s).getDisplayCountry();}).collect(Collectors.toList());
        for (String iso : locales) {
            Locale l = new Locale("", iso);
            countryCodes.put(l.getDisplayCountry(), iso);
        }
    }

}
