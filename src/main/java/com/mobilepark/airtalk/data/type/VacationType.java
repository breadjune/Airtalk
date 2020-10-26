package com.mobilepark.airtalk.data.type;

import java.util.HashMap;
import java.util.Map;

public enum VacationType {
    // @formatter:off
    MORNING_LEAVE("M", "반차(오전)"),
    AFTERNOON_LEAVE("A", "반차(오후)"),
    LEAVE("L", "연차"),
    OFFICIAL_LEAVE("O", "공가"),
    CON_LEAVE("C", "경조사");
    // @formatter:on

    private String code;
    private String label;

    private static Map<String, VacationType> codeToUserTypeMapping;

    private VacationType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static VacationType getType(String i) {
        initMapping();
        VacationType result = null;
        result = codeToUserTypeMapping.get(i);

        if (result == null) {
            labelMapping();
            result = codeToUserTypeMapping.get(i);
        }

        return result;
    }

    private static void initMapping() {
        codeToUserTypeMapping = new HashMap<String, VacationType>();
        for (VacationType s : values()) {
            codeToUserTypeMapping.put(s.code, s);
        }
    }

    private static void labelMapping() {
        codeToUserTypeMapping = new HashMap<String, VacationType>();
        for (VacationType s : values()) {
            codeToUserTypeMapping.put(s.label, s);
        }
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Status");
        sb.append("{code=").append(code);
        sb.append(", label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
