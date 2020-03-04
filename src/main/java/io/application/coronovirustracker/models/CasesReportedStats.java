package io.application.coronovirustracker.models;

public class CasesReportedStats {

	private String state;
	private String country;
	private int latestTotalCases;
	private int diffFromPrevDay;
	private int latestTotalDeathCases;
	private int deathDiffFromPrevDay;
	
	
	public int getLatestTotalDeathCases() {
		return latestTotalDeathCases;
	}
	public void setLatestTotalDeathCases(int latestTotalDeathCases) {
		this.latestTotalDeathCases = latestTotalDeathCases;
	}
	public int getDeathDiffFromPrevDay() {
		return deathDiffFromPrevDay;
	}
	public void setDeathDiffFromPrevDay(int deathDiffFromPrevDay) {
		this.deathDiffFromPrevDay = deathDiffFromPrevDay;
	}
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
	@Override
	public String toString() {
		return "CasesReportedStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ ", diffFromPrevDay=" + diffFromPrevDay + ", latestTotalDeathCases=" + latestTotalDeathCases
				+ ", deathDiffFromPrevDay=" + deathDiffFromPrevDay + "]";
	}
	
	
	
	
}
