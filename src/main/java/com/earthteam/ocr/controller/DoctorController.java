package com.earthteam.ocr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.earthteam.ocr.domain.Appointment;
import com.earthteam.ocr.domain.Doctor;
import com.earthteam.ocr.service.DoctorService;

/**
 * 
 * @author Vivian Samson - vsamson92044@gmail.com
 *
 *
 */

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	DoctorService doctorService;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String home() {

		return "doctor/doctor_home";

	}

	@RequestMapping(value = { "/viewAppointments" }, method = RequestMethod.GET)
	public String viewAppointments(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Doctor doctor = doctorService.findByUserName(authentication.getName());
		List<Appointment> appointmentList = doctorService.findAllAppointments(doctor);
		model.addAttribute("appointments", appointmentList);
		model.addAttribute("doctor",doctor);
		return "doctor/appointment_list";

	}

}
