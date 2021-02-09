package com.pulselive.demo.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pulselive.demo.dto.LeagueTableEntry;
import com.pulselive.demo.dto.Match;

// TODO: Auto-generated Javadoc
/**
 * The Class LeagueTable.
 */
public class LeagueTable {
	
	/** The matches. */
	private final List<Match> matches;

	/**
	 * Instantiates a new league table.
	 *
	 * @param matches the matches
	 */
	public LeagueTable(final List<Match> matches) {
		this.matches = matches;
	}

	/**
	 * Get the ordered list of league table entries for this league table.
	 *
	 * @return the table entries
	 */
	public List<LeagueTableEntry> getTableEntries() {
		
		List<LeagueTableEntry> leagueTable = calculateLeagueTable();
		Comparator<LeagueTableEntry> leagueTableComparator = Comparator
				.comparing(LeagueTableEntry::getPoints, Comparator.reverseOrder())
				.thenComparing(LeagueTableEntry::getGoalDifference, Comparator.reverseOrder())
				.thenComparing(LeagueTableEntry::getGoalsFor, Comparator.reverseOrder())
				.thenComparing(LeagueTableEntry::getTeamName);

		Collections.sort(leagueTable, leagueTableComparator);
		return leagueTable;
	}
	/**
	 * Calculate league table.
	 *
	 * @return the list
	 */
	private List<LeagueTableEntry> calculateLeagueTable() {
		Map<String, LeagueTableEntry> leagueTable = new HashMap<>();

		getMatches().forEach(match -> {
			populateTeamNames(leagueTable, match);
		});

		getMatches().forEach(match -> {
			calculatePlayed(leagueTable, match);
		});

		getMatches().forEach(match -> {
			calculateGoals(leagueTable, match);
		});

		calculateGoalDifference(leagueTable);

		getMatches().forEach(match -> {
			calculateResults(leagueTable, match);
		});

		calculatePoints(leagueTable);

		List<LeagueTableEntry> table = new ArrayList<>(leagueTable.values());

		return table;
	}

	/**
	 * Calculate points.
	 *
	 * @param leagueTable the league table
	 */
	private void calculatePoints(Map<String, LeagueTableEntry> leagueTable) {
		leagueTable.forEach((k, v) -> {
			v.setPoints((v.getWon() * 3) + (v.getDrawn()));
		});
	}

	/**
	 * Calculate results.
	 *
	 * @param leagueTable the league table
	 * @param match the match
	 */
	private void calculateResults(Map<String, LeagueTableEntry> leagueTable, Match match) {
		LeagueTableEntry tableEntryHome = leagueTable.get(match.getHomeTeam());
		LeagueTableEntry tableEntryAway = leagueTable.get(match.getAwayTeam());

		if (match.getHomeScore() > match.getAwayScore()) {
			tableEntryHome.setWon(tableEntryHome.getWon() + 1);
			tableEntryAway.setLost(tableEntryAway.getLost() + 1);
		} else if (match.getAwayScore() > match.getHomeScore()) {
			tableEntryAway.setWon(tableEntryAway.getWon() + 1);
			tableEntryHome.setLost(tableEntryHome.getLost() + 1);
		} else {
			tableEntryHome.setDrawn(tableEntryHome.getDrawn() + 1);
			tableEntryAway.setDrawn(tableEntryAway.getDrawn() + 1);
		}
	}

	/**
	 * Calculate goal difference.
	 *
	 * @param leagueTable the league table
	 */
	private void calculateGoalDifference(Map<String, LeagueTableEntry> leagueTable) {
		leagueTable.forEach((k, v) -> {
			v.setGoalDifference(v.getGoalsFor() - v.getGoalsAgainst());
		});
	}

	/**
	 * Calculate goals.
	 *
	 * @param leagueTable the league table
	 * @param match the match
	 */
	private void calculateGoals(Map<String, LeagueTableEntry> leagueTable, Match match) {
		LeagueTableEntry tableEntryHome = leagueTable.get(match.getHomeTeam());
		tableEntryHome.setGoalsFor(tableEntryHome.getGoalsFor() + match.getHomeScore());
		tableEntryHome.setGoalsAgainst(tableEntryHome.getGoalsAgainst() + match.getAwayScore());

		LeagueTableEntry tableEntryAway = leagueTable.get(match.getAwayTeam());
		tableEntryAway.setGoalsFor(tableEntryAway.getGoalsFor() + match.getAwayScore());
		tableEntryAway.setGoalsAgainst(tableEntryAway.getGoalsAgainst() + match.getHomeScore());

	}

	/**
	 * Calculate played.
	 *
	 * @param leagueTable the league table
	 * @param match the match
	 */
	private void calculatePlayed(Map<String, LeagueTableEntry> leagueTable, Match match) {
		LeagueTableEntry tableEntryHome = leagueTable.get(match.getHomeTeam());
		tableEntryHome.setPlayed(tableEntryHome.getPlayed() + 1);

		LeagueTableEntry tableEntryAway = leagueTable.get(match.getAwayTeam());
		tableEntryAway.setPlayed(tableEntryAway.getPlayed() + 1);
	}

	/**
	 * Populate team names.
	 *
	 * @param leagueTable the league table
	 * @param match the match
	 */
	private void populateTeamNames(Map<String, LeagueTableEntry> leagueTable, Match match) {
		leagueTable.putIfAbsent(match.getHomeTeam(), new LeagueTableEntry(match.getHomeTeam(), 0, 0, 0, 0, 0, 0, 0, 0));
		leagueTable.putIfAbsent(match.getAwayTeam(), new LeagueTableEntry(match.getAwayTeam(), 0, 0, 0, 0, 0, 0, 0, 0));
	}

	/**
	 * Gets the matches.
	 *
	 * @return the matches
	 */
	public List<Match> getMatches() {
		return this.matches;
	}


}
