package com.universeprojects.gefcommon.shared.elements;

public interface QuestObjective {
	/**
	 * All the different possible progression types
	 */
	enum ProgressionType {
		OBJECTIVE_COMPLETE,
		PROGRESSED,
		NO_CHANGE
	}

	/**
	 * @return The objective key
	 */
	String getKey ();

	/**
	 * @return The name of the objective
	 */
	String getName ();

	/**
	 * @return A hint for completing the objective
	 */
	String getHint ();

	/**
	 * @return A list of field filters for items
	 */
	RecipeSlot getFieldFilters ();

	/**
	 * @return The required quantity of the item to complete the objective
	 */
	long getRequiredQuantity ();

	/**
	 * @return The "done-ness" of the objective
	 */
	double getCompletion ();

	/**
	 * Set the "done-ness" of the objective
	 * @param completion The new "done-ness" of the objective
	 */
	void setCompletion (double completion);

	/**
	 * @return The required "done-ness" of the objective before it is complete
	 */
	double getRequiredCompletion ();

	/**
	 * @return Whether the objective is complete
	 */
	boolean isComplete ();

	String getTriggeredTutorial();
}
