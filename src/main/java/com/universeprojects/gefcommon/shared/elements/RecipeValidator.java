package com.universeprojects.gefcommon.shared.elements;

import java.util.Collection;
import java.util.LinkedHashMap;
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
                    if (matchesSlot(slot, slotData.getObject()) != MatchType.MATCH) {
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

    public Map<GameObject, MatchType> findMatchingObjects(RecipeSlot slot, Iterable<GameObject> objects) {
        Map<GameObject, MatchType> map = new LinkedHashMap<>();
        for (GameObject<?> object : objects) {
            MatchType matchType = matchesSlot(slot, object);
            if (matchType == MatchType.MATCH || matchType == MatchType.MATCH_INSUFFICIENT_QUANTITY) {
                map.put(object, matchType);
            }
        }
        return map;
    }

    /**
     * Overload for a single game object
     */
    public Map<GameObject, MatchType> findMatchingObjects(RecipeSlot slot, GameObject object) {
        Map<GameObject, MatchType> map = new LinkedHashMap<>();
        MatchType matchType = matchesSlot(slot, object);
        if (matchType == MatchType.MATCH || matchType == MatchType.MATCH_INSUFFICIENT_QUANTITY) {
            map.put(object, matchType);
        }
        return map;
    }

    public MatchType matchesSlot(RecipeSlot slot, GameObject<?> object) {
        for (RecipeSlotOption option : slot.getOptions()) {
            boolean success = matchesSlotOptionWithoutQuantity(option, object);
            if (success) {
                if(matchesRequiredQuantity(object, option.getRequiredQuantity())) {
                    return MatchType.MATCH;
                } else {
                    return MatchType.MATCH_INSUFFICIENT_QUANTITY;
                }
            }
        }
        return MatchType.NO_MATCH;
    }

    private boolean matchesSlotOptionWithoutQuantity(RecipeSlotOption option, GameObject<?> object) {
        return matchRequiredAspects(object, option.getRequiredAspects()) &&
            matchFields(object, option.getFieldRequirements());
    }

    private boolean matchRequiredAspects(GameObject<?> object, Collection<String> requiredAspects) {
        return object.getAspectNames().containsAll(requiredAspects);
    }

    private boolean matchesRequiredQuantity(GameObject<?> object, int amount) {
        if(amount < 1) throw new IllegalStateException("Required amount cannot be < 1, is "+amount);
        if(amount == 1) return true;
        final GameAspect<?> aspect = object.getAspect("SimpleStackableAspect");
        if(aspect == null) return false;
        Object quantity = aspect.getProperty("FIELD_Quantity");
        return matchValues(quantity, amount, RecipeFieldRequirementOperator.GE);
    }

    private boolean matchFields(GameObject<?> object, Collection<? extends RecipeFieldRequirement> fieldRequirements) {
        for (RecipeFieldRequirement requirement : fieldRequirements) {
            boolean matches = matchFieldOptions(object, requirement.getFieldOptions());
            if (!matches) return false;
        }
        return true;
    }

    private boolean matchFieldOptions(GameObject<?> object, Collection<? extends RecipeFieldRequirementOption> fieldRequirementOptions) {
        for (RecipeFieldRequirementOption requirement : fieldRequirementOptions) {
            final String aspectCode = requirement.getAspect();
            if(aspectCode != null) {
                GameAspect aspect = object.getAspect(aspectCode);
                if (aspect != null) {
                    Object fieldValue = aspect.getProperty(requirement.getField());
                    if (matchValues(fieldValue, requirement.getValue(), requirement.getOperator())) {
                        return true;
                    }
                }
            } else {
                Object fieldValue = object.getProperty(requirement.getField());
                if (matchValues(fieldValue, requirement.getValue(), requirement.getOperator())) {
                    return true;
                }
            }

        }
        return false;
    }

    @SuppressWarnings("OverlyComplexMethod")
    private boolean matchValues(Object fieldValue, Object requirementValue, RecipeFieldRequirementOperator operator) {
        switch (operator) {
            case EQ:
                return matchEquals(fieldValue, requirementValue);
            case NE:
                return !matchEquals(fieldValue, requirementValue);
            case LT:
                return matchLessThan(fieldValue, requirementValue, false);
            case LE:
                return matchLessThan(fieldValue, requirementValue, true);
            case GT:
                return matchLessThan(requirementValue, fieldValue, false);
            case GE:
                return matchLessThan(requirementValue, fieldValue, true);
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
