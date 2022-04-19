package com.bl.employee.payroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.bl.employee.payroll.model.Employee;

public class EmployeePayrollFileIOService {

	public static String EMP_FILE_PATH = "payroll-data.txt";

	public void writeData(List<Employee> employeeList) {
		StringBuffer strBuffer = new StringBuffer();

		if (Files.exists(Paths.get(EMP_FILE_PATH))) {
			List<String> readData = null;
			try {
				readData = Files.readAllLines(
						Paths.get(EMP_FILE_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			readData.forEach(
					s -> strBuffer.append(s + "\n"));
		}

		employeeList.forEach(e -> {
			strBuffer.append(e.getId() + "\t'" + e.getName()
					+ "'\t" + e.getSalary() + "\n");
		});

		try {
			Files.write(Paths.get(EMP_FILE_PATH),
					strBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printData() {
		try {
			Files.lines(Paths.get(EMP_FILE_PATH))
					.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int countEntries() {
		int count = 0;
		try {
			count = (int) Files
					.lines(Paths.get(EMP_FILE_PATH))
					.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}