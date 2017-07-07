package com.universeprojects.gefcommon.shared.elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class RecipeValidator {
    public static RecipeValidator INSTANCE = new RecipeValidator();

    private RecipeValidator() {
    }

    public boolean validateRecipeConstruction(Recipe recipe, RecipeConstruction recipeConstruction) {
        if (recipe == null) throw new IllegalArgumentException("Recipe must not be null");
        if (recipeConstruction == null) throw new IllegalArgumentException("RecipeConstruction must not be null");
        if (recipeConstruction.getSlotData() == null) throw new IllegalArgumentException("RecipeConstruction.getSlotData() must not be null");
        Map<String, RecipeConstructionSlot> slotMap = createConstructionSlotMap(recipeConstruction);
        for (RecipeCategory category : recipe.getCategories()) {
            for (RecipeSlot slot : category.getSlots()) {
                RecipeConstructionSlot slotData = slotMap.get(slot.getSlotId());
                if (slotData == null) {
                    if (category.isRequired()) {
                        return false;
                    }
                } else {
                    int optionIndex = slotData.getOptionIndex();
                    final List<? extends RecipeSlotOption> options = slot.getOptions();
                    if (optionIndex >= options.size()) {
                        return false;
                    }
                    RecipeSlotOption slotOption = options.get(optionIndex);
                    boolean match = matchesSlotOption(slotOption, slotData.getObject());
                    if (!match) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Map<String, RecipeConstructionSlot> createConstructionSlotMap(RecipeConstruction recipeConstruction) {
        Map<String, RecipeConstructionSlot> slotMap = new LinkedHashMap<>();
        for (RecipeConstructionSlot slot : recipeConstruction.getSlotData()) {
            slotMap.put(slot.getSlotId(), slot);
        }
        return slotMap;
    }

    public List<GameObject> findMatchingObjects(RecipeSlot slot, Iterable<GameObject> objects) {
        List<GameObject> list = new ArrayList<>();
        for (GameObject<?> object : objects) {
            if (matchesSlot(slot, object)) {
                list.add(object);
            }
        }
        return list;
    }

    public boolean matchesSlot(RecipeSlot slot, GameObject<?> object) {
        for (RecipeSlotOption option : slot.getOptions()) {
            boolean success = matchesSlotOption(option, object);
            if (success) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesSlotOption(RecipeSlotOption option, GameObject<?> object) {
        return matchRequiredAspects(object, option.getRequiredAspects()) &&
                matchFields(object, option.getFieldRequirements());
    }

    private boolean matchRequiredAspects(GameObject<?> object, List<String> requiredAspects) {
        return object.getAspectNames().containsAll(requiredAspects);
    }

    private boolean matchFields(GameObject<?> object, List<? extends RecipeFieldRequirement> fieldRequirements) {
        for (RecipeFieldRequirement requirement : fieldRequirements) {
            GameAspect aspect = object.getAspect(requirement.getAspect());
            if (aspect == null) return false;
            Object fieldValue = aspect.getProperty(requirement.getField());
            if (!matchValues(fieldValue, requirement.getValue(), requirement.getOperator())) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("OverlyComplexMethod")
    private boolean matchValues(Object fieldValue, Object requirementValue, RecipeFieldRequirementOperator operator) {
        switch (operator) {
            case EQ:
                return matchEquals(fieldValue, requirementValue);
            case NE:
                return !matchEquals(fieldValue, requirementValue);
            case LT:
                return fieldValue != null && matchLessThan(fieldValue, requirementValue, false);
            case LE:
                return fieldValue != null && matchLessThan(fieldValue, requirementValue, true);
            case GT:
                return fieldValue != null && matchLessThan(requirementValue, fieldValue, false);
            case GE:
                return fieldValue != null && matchLessThan(requirementValue, fieldValue, true);
            case CONTAINS:
                return matchContains(fieldValue, requirementValue);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    @SuppressWarnings("OverlyComplexMethod")
    private boolean matchEquals(Object fieldValue, Object requirementValue) {
        if (fieldValue == null && requirementValue == null) return true;
        if (fieldValue == null || requirementValue == null) return false;
        if (fieldValue instanceof Number && requirementValue instanceof Number) {
            if (fieldValue instanceof Double || fieldValue instanceof Float || requirementValue instanceof Double || requirementValue instanceof Float) {
                return ((Number) fieldValue).doubleValue() == ((Number) requirementValue).doubleValue();
            } else {
                return ((Number) fieldValue).longValue() == ((Number) requirementValue).longValue();
            }
        }
        if (fieldValue.getClass() == requirementValue.getClass()) {
            return fieldValue.equals(requirementValue);
        }
        final String fieldValueString = objectToString(fieldValue);
        final String requirementValueString = objectToString(requirementValue);
        return fieldValueString.equals(requirementValueString);
    }

    private boolean matchLessThan(Object value1, Object value2, boolean includeEquals) {
        if (!(value1 instanceof Number) || !(value2 instanceof Number)) {
            return false;
        }
        double number1 = ((Number) value1).doubleValue();
        double number2 = ((Number) value2).doubleValue();
        if (includeEquals) {
            return number1 <= number2;
        } else {
            return number1 < number2;
        }
    }

    private boolean matchContains(Object fieldValue, Object requirementValue) {
        return (fieldValue instanceof String && requirementValue instanceof String) &&
                ((String) fieldValue).contains((String) requirementValue);
    }

    protected String objectToString(Object object) {
        return object.toString();
    }
}
