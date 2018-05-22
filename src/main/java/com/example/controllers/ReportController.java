package com.example.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.entities.CandidateProfileReprot;
import com.example.entities.Role;
import com.example.services.CandiateProfileService;
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
		String user_name = p.getName();
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
		
		
		List<CandidateProfileReprot> reportList = candiateProfileService.getReport(user_name);
		model.addAttribute("reportList", reportList);
		return REPORT;
	}


	@SuppressWarnings("resource")
	@RequestMapping(value="/report_download",method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public HttpEntity<byte[]> downloadExcelReport(HttpServletResponse response,Principal p) throws FileNotFoundException {
		String user_name = p.getName();
		 //Create blank workbook
	      XSSFWorkbook workbook = new XSSFWorkbook();
	      
	      //Create a blank sheet
	      XSSFSheet spreadsheet = workbook.createSheet( " Employee Info ");

	      //Create row object
	      XSSFRow row;

	      List<CandidateProfileReprot> reportList = candiateProfileService.getReport(user_name);
	      
	      //This data needs to be written (Object[])
	      Map < Integer, Object[] > empinfo = new TreeMap < Integer, Object[] >();
	      
	      empinfo.put( 1, new Object[] { "Vendor", "Candidate_Name", "BRM", "SPOC"});
	      
	      
	      
	      for (int i = 0; i < reportList.size(); i++) {
	    	  CandidateProfileReprot candidateProfileReprot=reportList.get(i);
	    	  empinfo.put( i+2, new Object[] {candidateProfileReprot.getVendor(), 
		    		  candidateProfileReprot.getCandidate_name(), 
		    		  candidateProfileReprot.getBrm(),
		    		  candidateProfileReprot.getSpoc() });
	  	}
	      
	      //model.addAttribute("Emplinfo",empinfo);
	      //Iterate over data and write to sheet
	      Set<Integer> keyid = empinfo.keySet();
	      int rowid = 0;
	      
	      for (Integer key : keyid) {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = empinfo.get(key);
	         int cellid = 0;
	         
	         for (Object obj : objectArr){
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	        File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			String fileLocation = path.substring(0, path.length() - 1) + "Writesheet.xlsx";
	      //Write the workbook in file system
	      FileOutputStream out = new FileOutputStream(
	         new File(fileLocation));
	      
	      try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      FileInputStream fileInputStream = null;
	      File file = new File(fileLocation);

	      //init array with file length
	      byte[] excelContent = new byte[(int) file.length()];
	      
	    //read file into bytes[]
        fileInputStream = new FileInputStream(file);
        try {
			fileInputStream.read(excelContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      HttpHeaders header = new HttpHeaders();
	      header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	      header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");
	      header.setContentLength(excelContent.length);

	      System.out.println("Writesheet.xlsx written successfully");
	   
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
