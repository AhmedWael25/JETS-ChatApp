package jets.chatclient.gui.utils;

import java.util.*;
import java.util.stream.Collectors;

public class Countries {
    private static String[] locales = Locale.getISOCountries();
    private static List<String> countries;
    private static Map<String, String> countryToCodeAndphone = new HashMap<String, String>();


    public static List<String> getAll(){
        return countries;
    }
    static {
      countries =  Arrays.stream(locales).map(s -> {return new Locale("",s).getDisplayCountry();}).collect(Collectors.toList());

    }

}
