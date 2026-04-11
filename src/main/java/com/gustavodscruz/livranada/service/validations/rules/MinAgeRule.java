package com.gustavodscruz.livranada.service.validations.rules;

import java.util.Map;

public class MinAgeRule {
    private MinAgeRule() {}

    public static Map<String, String> minAgePerCountryValidationRule() {
        return Map.of(
                "BR", "18",
                "US", "21",
                "DE", "16",
                "FR", "18",
                "JP", "20"
        );
    }
}
