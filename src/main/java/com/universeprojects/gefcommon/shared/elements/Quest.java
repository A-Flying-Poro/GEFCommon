package com.universeprojects.gefcommon.shared.elements;

import java.util.Map;

public interface Quest {
	/**
	 * All the possible quest states
	 */
	enum QuestState {
		Skipped,
		Incomplete,
		Complete,
		Abandoned
	}

	/**
	 * @return The name of the quest
	 */
	String getName ();

	/**
	 * @return The description for the quest
	 */
	String getDescription ();

	/**
	 * @return The file path for the quest's icon
	 */
	String getIcon ();

	/**
	 * @return A map of the quest's objectives
	 */
	Map<String, QuestObjective> getObjectives ();

	/**
	 * @return Whether this quest is a noob quest
	 */
	boolean isNoobQuest ();

	/**
	 * @return Whether this quest is skippable
	 */
	boolean isSkippable ();

	/**
	 * @return A string representing the quest owner's key
	 */
	String getOwnerKey ();

	/**
	 * @return A string representing the quest definition's key
	 */
	String getQuestDefKey ();

	/**
	 * Update the quest state based on the quest's objectives
	 */
	void update ();

	/**
	 * @return The state of the quest
	 */
	QuestState getQuestState ();

	/**
	 * Abandon the quest
	 */
	void abandon ();

	/**
	 * Skip the quest
	 */
	void skip ();

	/**
	 * @return Whether the quest is complete
	 */
	boolean isComplete ();

	/**
	 * @return The number of objectives that are completed
	 */
	int getCurrentCompletion ();
}
