package com.example.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NamedStoredProcedureQueries;
import javax.xml.ws.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.CandidateProfile;
import com.example.entities.CandidateProfileReprot;
import com.example.entities.Criteria;
import com.example.entities.Role;
import com.example.entities.StatusChange;
import com.example.entities.User;
import com.example.enums.RoleEnum;
import com.example.services.CandiateProfileService;
import com.example.services.CandidateProfileServiceImp;
import com.example.services.UserRegistrationService;



@Controller
public class ReportController {

	private static final String REPORT = "report";
	@Autowired
	CandiateProfileService candiateProfileService;
	@Autowired
	UserRegistrationService userRegistrationService;

	
	@RequestMapping(value="/report",method = RequestMethod.GET)
	public String showReport(Model model,Principal p){
		try {
			this.wrtieExcelFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}
		else{
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		
		List<CandidateProfileReprot> reportList = candiateProfileService.getReport("brm@gmail.com");
		model.addAttribute("reportList", reportList);
		return REPORT;
	}


	@RequestMapping(value="/report_download",method = RequestMethod.GET,produces="application/vnd.ms-excel")
	@ResponseStatus(value=HttpStatus.OK)
	public HttpEntity<byte[]> downloadExcelReport() {
	 
	    /** assume that below line gives you file content in byte array **/
		
		List<String> names=new ArrayList<>();
		names.add("ezedin");
		names.add("sulu");
		//1st option create a local excel file from list of object
		//create byte[] from the file
		//pass it
		byte[] excelContent = SerializationUtils.serialize(names);
//	    byte[] excelContent = {};
	    // prepare response
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my_file.xls");
	    header.setContentLength(excelContent.length);
	 
	    return new HttpEntity<byte[]>(excelContent, header);
	}
	
	@RequestMapping(value="/reportByDate")
	public String showReportByDate(Model model,Principal p,@RequestParam("start") String start,@RequestParam("end") String end) {
		
		Role role=new Role();
		if(p!=null) {
		role=userRegistrationService.getRoleByEmail(p.getName());
		}
		else{
			role.setName("");
		}
		System.out.println("role is:"+role.getName());
		model.addAttribute("role", role);
		
		List<CandidateProfileReprot> reportList = candiateProfileService.getReportByDate("brm@gmail.com",start,end);
		model.addAttribute("reportList", reportList);
		
		return REPORT;
		
	}
	
	public void wrtieExcelFile() throws FileNotFoundException {
		String fileLocation;
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		fileLocation = path.substring(0, path.length() - 1) + "MyFirstExcel.xlsx";
		FileOutputStream f = new FileOutputStream(fileLocation);

	    //final String FILE_NAME = "/tmp/MyFirstExcel.xlsx";


	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
	        Object[][] datatypes = {
	                {"Datatype", "Type", "Size(in bytes)"},
	                {"int", "Primitive", 2},
	                {"float", "Primitive", 4},
	                {"double", "Primitive", 8},
	                {"char", "Primitive", 1},
	                {"String", "Non-Primitive", "No fixed size"}
	        };

	        int rowNum = 0;

	        for (Object[] datatype : datatypes) {
	            Row row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : datatype) {
	                Cell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }

	        try {
	            FileOutputStream outputStream = new FileOutputStream(fileLocation);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Done");
	    }
	
	
	
}
