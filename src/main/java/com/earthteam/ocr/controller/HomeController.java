package com.earthteam.ocr.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.earthteam.ocr.domain.Doctor;
import com.earthteam.ocr.domain.Patient;
import com.earthteam.ocr.service.DoctorService;
import com.earthteam.ocr.service.PatientService;

/**
 * 
 * @author Vivian Samson - vsamson92044@gmail.com
 *
 *
 */

@Controller
public class HomeController {
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping({ "", "/", "/welcome" })
	public String welcome(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
		// model.addAttribute("greeting", "Welcome to ABC Medical Clinic");
		if (authentication != null) {
			Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			if (roles.contains("ROLE_ADMIN")) {
				return "redirect:/admin";
			}
			if (roles.contains("ROLE_DOCTOR")) {
				Doctor doctor = doctorService.findByUserName(authentication.getName());
				redirectAttributes.addFlashAttribute("doctor",doctor);
				return "redirect:/doctor";
			}
			if (roles.contains("ROLE_PATIENT")) {
				Patient patient = patientService.getPatientByUserName(authentication.getName());
				redirectAttributes.addFlashAttribute("patient",patient);
				return "redirect:/patient";
			}
		}
		return "welcome";
	}

	@RequestMapping("/welcome/greeting")
	public String greeting() {
		return "welcome";
	}

	@RequestMapping("/test")
	public String test() throws Exception {
		return "home";
	}
}
