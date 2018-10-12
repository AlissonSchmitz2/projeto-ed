package br.com.sistemaescolar.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DiasSemana {
	DOMINGO("01", "Domingo"),
	
	SEGUNDA("02", "Segunda-Feira"),
	
	TERCA("03", "Terça-Feira"),
	
	QUARTA("04", "Quarta-Feira"),
	
	QUINTA("05", "Quinta-Feira"),
	
	SEXTA("06", "Sexta-Feira"),
	
	SABADO("07", "Sábado");

    private final String code;
    private final String description;
    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private DiasSemana(String code, String description) {
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
    
    public static Map<String, String> getDiasSemana() {
    	if (mMap == null) {
            initializeMapping();
        }
        
        return mMap;
    }

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (DiasSemana s : DiasSemana.values()) {
            mMap.put(s.code, s.description);
        }
        return mMap;
    }
}
