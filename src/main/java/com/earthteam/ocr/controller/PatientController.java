/**
 * 
 */
package com.earthteam.ocr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.earthteam.ocr.domain.Appointment;
import com.earthteam.ocr.domain.Authority;
import com.earthteam.ocr.domain.Doctor;
import com.earthteam.ocr.domain.Patient;
import com.earthteam.ocr.service.PatientService;

/**
 * @author Vivian Samson
 *
 */

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService patientService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home() {
		return "patient/patient_home";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPatient(@ModelAttribute("patient") Patient patient, Model model) {

		return "patient/patient-registration";

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveDoctor(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IOException {

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "patient/patient-registration";
		}

		Authority authority = new Authority();

		patient.getCredentials().setEnabled(true);

		authority.setUsername(patient.getCredentials().getUsername());
		authority.setAuthority("ROLE_PATIENT");

		List<Authority> list = new ArrayList<>();
		list.add(authority);
		patient.getCredentials().setAuthority(list);

		long patientId = patientService.save(patient);
		System.out.println("Saved Patient with id: " + patientId + " and userName is: "
				+ patient.getCredentials().getUsername() + "and password is:" + patient.getCredentials().getPassword());

		redirectAttributes.addFlashAttribute("patient", patient);
		return "redirect:details";
	}

	@RequestMapping(value = { "/viewAppointments" }, method = RequestMethod.GET)
	public String viewAppointments(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientService.getPatientByUserName(authentication.getName());
		List<Appointment> appointmentList = patientService.findAllAppointments(patient);
		model.addAttribute("appointments", appointmentList);
		model.addAttribute("patient", patient);
		return "patient/appointment_list";

	}

	@RequestMapping(value = "/details")
	public String showPatientDetails() {
		return "patient/patient_details";
	}
}
