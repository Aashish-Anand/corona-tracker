package io.application.coronovirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.application.coronovirustracker.models.CasesReportedStats;

@Service
public class CoronaVirusDataService {
	
	private static String VIRUS_CASES_REPORTED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	
	private static String VIRUS_CASES_DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	
	private static String VIRUS_CASES_RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
	
	/*
	 * List of Object of CasesReportedStats
	 */
	private List<CasesReportedStats> reportedStats = new ArrayList<CasesReportedStats>();
	
	public List<CasesReportedStats> getReportedStats() {
		return reportedStats;
	}

	@PostConstruct
	@Scheduled(cron="* * 1 * * *")  // s m H 
	public void fetchVirusData() throws IOException, InterruptedException {
		
		List<CasesReportedStats> newStats = new ArrayList<CasesReportedStats>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_CASES_REPORTED_URL)).build();
		
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpResponse.body());
		StringReader csvReader = new StringReader(httpResponse.body());
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(VIRUS_CASES_DEATH_URL)).build();
		
		HttpResponse<String> httpReponse1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
		StringReader csvReader1 = new StringReader(httpReponse1.body());
		
		Iterable<CSVRecord> deathRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader1);
		
		for (CSVRecord record : records) {
			
			CasesReportedStats locationStats = new CasesReportedStats();
			
		    locationStats.setState(record.get("Province/State"));
		    locationStats.setCountry(record.get("Country/Region"));
		    int latestCases = Integer.parseInt(record.get(record.size() - 1));
		    int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
		    
		    locationStats.setLatestTotalCases(latestCases);
		    locationStats.setDiffFromPrevDay(latestCases - prevDayCases);
		    
		    for(CSVRecord deathRecord : deathRecords) {
		    	if(deathRecord.get("Lat").equals(record.get("Lat")) && deathRecord.get("Long").equals(record.get("Long"))) {
		    		int latestDeath = Integer.parseInt(deathRecord.get(deathRecord.size() - 1));
					int prevDayDeath = Integer.parseInt(deathRecord.get(deathRecord.size() - 2));
					
					locationStats.setLatestTotalDeathCases(latestDeath);
					locationStats.setDeathDiffFromPrevDay(latestDeath - prevDayDeath);
					break;
		    	}
		    }
		    
		    //System.out.println(locationStats.toString());
		    newStats.add(locationStats);
		}
		this.reportedStats = newStats;
		
	}

}
