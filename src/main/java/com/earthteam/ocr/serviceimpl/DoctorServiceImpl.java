package com.earthteam.ocr.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.earthteam.ocr.domain.Appointment;
import com.earthteam.ocr.domain.Doctor;
import com.earthteam.ocr.repository.AppointmentRepository;
import com.earthteam.ocr.repository.DoctorRepository;
import com.earthteam.ocr.service.AppointmentService;
import com.earthteam.ocr.service.DoctorService;
/**
 * 
 * @author Vivian Samson - vsamson92044@gmail.com
 *
 *
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	AppointmentRepository appoitnmentsRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public long save(Doctor doctor) {
		
		String encodedPassword = passwordEncoder.encode(doctor.getCredentials().getPassword());
		doctor.getCredentials().setPassword(encodedPassword);
		doctor.getCredentials().setVerifyPassword(encodedPassword);
		return doctorRepository.save(doctor).getId();
	}

	@Override
	public List<Doctor> findAll() {
		List<Doctor> list = new ArrayList<>();
		doctorRepository.findAll().forEach((Doctor d) -> list.add(d));
		return list;
	}

	@Override
	public List<Doctor> findByCategory(int categoryId) {
		return doctorRepository.findByCategoryId(categoryId);
	}

	@Override
	public Doctor findById(long id) {
		return doctorRepository.findOne(id);
	}

	@Override
	public Doctor findByUserName(String username) {
		// TODO Auto-generated method stub
		return doctorRepository.findByUserName(username);
	}

	@Override
	public List<Appointment> findAllAppointments(Doctor doctor) {
		// TODO Auto-generated method stub
		return appoitnmentsRepository.findByDoctorId(doctor.getId());
	}

}
