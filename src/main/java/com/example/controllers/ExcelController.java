package com.example.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Role;
import com.example.entities.excel.AjaxResponseBody;
import com.example.entities.excel.CandidateDto;
import com.example.entities.excel.ExcelPOIHelper;
import com.example.entities.excel.MyCell;
import com.example.entities.excel.ProfilesToSave;
import com.example.services.CandiateProfileService;
import com.example.services.CandidateProfileServiceImp;
import com.example.services.UserRegistrationService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RestController
@Controller
public class ExcelController {

	private String fileLocation;
	// @Resource(name = "excelPOIHelper")
	@Autowired
	private ExcelPOIHelper excelPOIHelper;
	
	@Autowired
	private CandiateProfileService candidateProfileService;

	@Autowired
	UserRegistrationService userRegistrationService;
	
	@GetMapping("/uploadExcelFile")
	public String uploadPage(Model model,Principal p) {
		// List<CandidateDto> candidates = null;
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		model.addAttribute("role", role);
		model.addAttribute("candidates", null);
		model.addAttribute("uploadStatus", "start");
		return "excel";
	}

	@PostMapping("/uploadExcelFile")
	public String uploadFile(Model model, MultipartFile file,Principal p) throws IOException {
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		model.addAttribute("role", role);
		System.out.println("reading file................");
		if (file != null && file.getOriginalFilename() != null
				&& (file.getOriginalFilename().endsWith("xls") || file.getOriginalFilename().endsWith("xlsx"))) {
			InputStream in = file.getInputStream();
			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
			FileOutputStream f = new FileOutputStream(fileLocation);
			int ch = 0;
			while ((ch = in.read()) != -1) {
				f.write(ch);
			}
			f.flush();
			f.close();

		} else {
			model.addAttribute("uploadStatus", "Incorrect file");
		}

		// reading file starts
		if (fileLocation != null) {
			if (fileLocation.endsWith(".xlsx") || fileLocation.endsWith(".xls")) {
				Map<Integer, List<MyCell>> data = excelPOIHelper.readExcel(fileLocation);
				List<CandidateDto> candidatesFetched = new ArrayList<CandidateDto>();
				candidatesFetched = fetchData(data);
				model.addAttribute("candidates", candidatesFetched);
				ProfilesToSave profilesToSave = new ProfilesToSave();
				profilesToSave.setProfilesToSave(candidatesFetched);
				model.addAttribute("uploadStatus", "file loaded to save");
			} else {
				model.addAttribute("message", "Not a valid excel file!");
			}
		} else {
			model.addAttribute("message", "File missing! Please upload an excel file.");
		}
		// reading file ends

		return "excel";
	}

	@ResponseBody
	@RequestMapping(value = "/addListOfProfiles", method = RequestMethod.POST)
	public AjaxResponseBody saveListOfProfiles(Model model, @RequestBody String candidatesToSaveStr,Principal p) {
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}else {
			role.setName("");
		}
		model.addAttribute("role", role);
		System.out.println("***********the data ocmding is:"+candidatesToSaveStr);
		AjaxResponseBody arb=new AjaxResponseBody();
		ObjectMapper mapper=new ObjectMapper();
		TypeReference<List<CandidateDto>> typeReference = new TypeReference<List<CandidateDto>>() {};
		System.out.println("saving all lsits is Empty?");
		try {
			List<CandidateDto> candidatesToSave=mapper.readValue(candidatesToSaveStr, typeReference);
			candidateProfileService.saveAllCandidate(candidatesToSave);
			arb.setMsg("Successfuly Saved");
			arb.setCode("200");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arb.setMsg("Parsing error");
			arb.setCode("404");
		} 
//		arb.setMsg("Successfuly Saved");
//		arb.setCandidatesToSave(candidatesToSave);
		// model.addAttribute("listOfProfilesToSave", profilesToSave);
		
		return arb;
	}

	private List<CandidateDto> fetchData(Map<Integer, List<MyCell>> data) {
		CandidateDto candidate = null;
		// boolean isNumber = false;
		boolean isEmail = false;
		boolean isDate = false;
		List<String> rejectedData = new ArrayList<String>();
		List<CandidateDto> candidates = new ArrayList<CandidateDto>();
		String BRMEmail = "";
		String SPOCEmail = "";
		String degitRegex = "[0-9]*\\.?[0-9]*";
		String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		String formateddate="";
		Iterator<Map.Entry<Integer, List<MyCell>>> it = data.entrySet().iterator();
		it.next();
		while (it.hasNext()) {
			Map.Entry<Integer, List<MyCell>> row = it.next();
			if (!row.getValue().isEmpty()) {
				candidate = new CandidateDto();
				// candidate.setVendor(row.getValue().size() >= 1 ?
				// row.getValue().get(0).getContent() : "");
				if (row.getValue().size() >= 1) {
					candidate.setVendor(row.getValue().get(0).getContent());
				} else {
					candidate.setVendor("");
				}
				if (row.getValue().size() >= 2) {
					candidate.setCandidate_name(row.getValue().get(1).getContent());
				} else {
					rejectedData.add("vendor:" + candidate.getVendor() + "rejected");
					continue;
				}
				if (row.getValue().size() >= 3) {
					candidate.setSkills(row.getValue().get(2).getContent());
				} else {
					rejectedData.add("vendor:" + candidate.getVendor() + "rejected");
					continue;
				}

				if (row.getValue().size() >= 4) {
					isEmail = row.getValue().get(3).getContent().matches(emailRegex);
					if (isEmail) {
						BRMEmail = row.getValue().get(3).getContent();
						candidate.setBRM(BRMEmail);
						// get user by email >> User user=
					} else {
						rejectedData.add("Vendor:" + candidate.getVendor() + " candidate:"
								+ candidate.getCandidate_name() + " is rejected. Invalid BRM Email");
						continue;
					}

				}
				if (row.getValue().size() >= 5) {
					isEmail = row.getValue().get(4).getContent().matches(emailRegex);
					if (isEmail) {
						SPOCEmail = row.getValue().get(4).getContent();
						candidate.setSPOC(SPOCEmail);
						// get user by email >> User user=
					} else {
						rejectedData.add("Vendor:" + candidate.getVendor() + " candidate:"
								+ candidate.getCandidate_name() + " is rejected. Invalid SPCO Email");
						continue;
					}

				}
				if (row.getValue().size() >= 6) {
					System.out.println("+++++++++++The date found is:"+row.getValue().get(5).getContent());
					//Sun Mar 04 00:00:00 CST 2018
					
					
//					isDate = row.getValue().get(5).getContent().matches(dateRegex);
					isDate=true;
					if (isDate) {
						String dateContent=row.getValue().get(5).getContent();
						SimpleDateFormat formater= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
						SimpleDateFormat formate2= new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date date=formater.parse(dateContent);
							formateddate=formate2.format(date);
							System.out.println("formated as:"+formateddate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						candidate.setDate(formateddate);
						// get user by email >> User user=
					} else {
						rejectedData.add("Vendor:" + candidate.getVendor() + " candidate:"
								+ candidate.getCandidate_name() + " is rejected. Invalid Date");
						continue;
					}

				}
				if (row.getValue().size() >= 7) {
					candidate.setLocation(row.getValue().get(6).getContent());
				} else {
					rejectedData.add("Vendor:" + candidate.getVendor() + " candidate:" + candidate.getCandidate_name()
							+ " is rejected. Invalid BRM Email");
					continue;
				}
				if (row.getValue().size() >= 8) {
					candidate.setSpecialSkills(row.getValue().get(7).getContent());
				} else {
					rejectedData.add("Vendor:" + candidate.getVendor() + " candidate:" + candidate.getCandidate_name()
							+ " is rejected. Invalid BRM Email");
					continue;
				}

				candidates.add(candidate);

			} else {
				rejectedData.add("Blank row");
			}
		}

		System.out.println(rejectedData.size() + " data are rejected");
		System.out.println(candidates.size() + " data accepted");
		return candidates;
	}


}