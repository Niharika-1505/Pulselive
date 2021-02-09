package com.pulselive.client;

import java.util.ArrayList;
import java.util.List;

import com.pulselive.demo.app.service.LeagueTable;
import com.pulselive.demo.dto.Match;

public class PulseLiveClient {

	public static void main(String[] args) {

		
		List<Match> matches = new ArrayList<Match>();
		Match m1 = new Match("A", "B", 2, 3);
		Match m2 = new Match("A", "C",1, 1);
		Match m3 = new Match("A", "D", 3, 0);
		Match m4 = new Match("B", "C", 2, 1);
		Match m5 = new Match("B", "D", 1, 1);
		Match m6 = new Match("C", "D", 1, 3);
		Match m7 = new Match("A", "B", 4, 2);
		Match m8 = new Match("A", "C", 1, 2);
		Match m9 = new Match("A", "D", 2, 1);
		Match m10 = new Match("B", "C", 4, 2);
		Match m11 = new Match("B", "D", 1, 2);
		Match m12 = new Match("C", "D", 1, 2);
		
		matches.add(m1);
		matches.add(m2);
		matches.add(m3);
		matches.add(m4);
		matches.add(m5);
		matches.add(m6);
		matches.add(m7);
		matches.add(m8);
		matches.add(m9);
		matches.add(m10);
		matches.add(m11);
		matches.add(m12);
		
		LeagueTable leagueTable = new LeagueTable(matches);
		System.out.println(" League Display Table " + leagueTable.getTableEntries());
		
	
	}

}
