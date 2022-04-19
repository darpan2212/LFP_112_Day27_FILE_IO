package com.bl.employee.payroll.test;

import java.util.Arrays;

import org.junit.Test;

import com.bl.employee.payroll.EmployeePayrollService;
import com.bl.employee.payroll.EmployeePayrollService.IOStream;
import com.bl.employee.payroll.model.Employee;

public class EmployeePayrollTest {

	@Test
	public void givenEntriesWriteToFile() {

		int empId1 = (int) (Math.random() * 900 + 100);
		int empId2 = (int) (Math.random() * 900 + 100);
		int empId3 = (int) (Math.random() * 900 + 100);

		Employee[] emps = new Employee[] {
				new Employee(empId1, "Jeff Bezos", 200000),
				new Employee(empId2, "Bill Gates", 150000),
				new Employee(empId3, "Elon Musk",
						300000), };

		EmployeePayrollService service = new EmployeePayrollService(
				IOStream.FILE_IO);

		service.writeEmployeeData(Arrays.asList(emps));
		service.printData();
		int count = service.countEntries();
		System.out.println(
				"Number of entries in file : " + count);
	}

}