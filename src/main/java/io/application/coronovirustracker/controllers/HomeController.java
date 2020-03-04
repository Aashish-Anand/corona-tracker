package io.application.coronovirustracker.controllers;

import io.application.coronovirustracker.models.CasesReportedStats;
import io.application.coronovirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		/*
		 * total new reported cases stats
		 */
		List<CasesReportedStats> allStats = coronaVirusDataService.getReportedStats();
		int totalReportedCase = 0;
		for(CasesReportedStats singleStat : allStats) {
			totalReportedCase += singleStat.getLatestTotalCases();
		}
		int totalNewReportedCases = 0;
		for(CasesReportedStats singleStat : allStats) {
			totalNewReportedCases += singleStat.getDiffFromPrevDay();
		}
		
		//List<DeathReportedStats> deathStats = coronaVirusDataService.getDeathStats();
		int totalDeathCase = 0;
		for(CasesReportedStats death : allStats) {
			totalDeathCase += death.getLatestTotalDeathCases();
		}
		int totalNewDeathCase = 0;
		for(CasesReportedStats death : allStats) {
			totalNewDeathCase += death.getDeathDiffFromPrevDay();
		}
		
		
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalReportedCase);
		model.addAttribute("totalNewCases", totalNewReportedCases);
		model.addAttribute("totalDeathCases", totalDeathCase);
		model.addAttribute("totalNewDeathCases", totalNewDeathCase);
		return "home";  // returning string that points to the template
	}
}
