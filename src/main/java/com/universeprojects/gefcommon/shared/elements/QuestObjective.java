package com.universeprojects.gefcommon.shared.elements;

import java.util.List;

public interface QuestObjective {
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
	List getFieldFilters ();

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
}
