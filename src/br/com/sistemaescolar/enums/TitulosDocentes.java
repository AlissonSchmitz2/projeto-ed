package br.com.sistemaescolar.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TitulosDocentes {
	POS_GRADUACAO("01", "Pós-Graduação"),
	
	MESTRADO("02", "Mestrado"),
	
	DOUTORADO("03", "Doutorado"),
	
	POS_DOUTORADO("04", "Pós-Doutorado");

    private final String code;
    private final String description;
    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private TitulosDocentes(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByCode(String code) {
        if (mMap == null) {
            initializeMapping();
        }
        
        if (mMap.containsKey(code)) {
            return mMap.get(code);
        }
        return null;
    }
    
    public static Map<String, String> getTitulosDocentes() {
    	if (mMap == null) {
            initializeMapping();
        }
        
        return mMap;
    }

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (TitulosDocentes s : TitulosDocentes.values()) {
            mMap.put(s.code, s.description);
        }
        return mMap;
    }
    
    public String toString() {
		return "xxx";
	}
}
