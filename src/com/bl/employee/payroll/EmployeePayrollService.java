package com.bl.employee.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bl.employee.payroll.model.Employee;

public class EmployeePayrollService {

	public enum IOStream {
		CONSOLE_IO, FILE_IO, DATABASE_IO, REST_IO;
	}

	List<Employee> employeeList;
	IOStream ioStream;

	public EmployeePayrollService(IOStream ioStream) {
		employeeList = new ArrayList<>();
		this.ioStream = ioStream;
	}

	public static void main(String[] args) {
		EmployeePayrollService service = new EmployeePayrollService(
				IOStream.CONSOLE_IO);

		if (service.ioStream.equals(IOStream.CONSOLE_IO)) {
			Scanner sc = new Scanner(System.in);
			int option = 0;
			while (option != 3) {
				System.out.println(
						"1. Add Employee\n2. Show Employees\n3. Exit");
				option = sc.nextInt();
				switch (option) {
				case 1:
					sc = new Scanner(System.in);
					service.readEmployeeData(sc);
					break;
				case 2:
					service.writeEmployeeData(
							service.employeeList);
					break;
				case 3:
					option = 3;
					break;
				default:
					throw new IllegalArgumentException(
							"Enter valid option");
				}
			}

			sc.close();
		}
		System.out.println("------Good bye-----------");

	}

	public void readEmployeeData(Scanner consoleScanner) {
		if (ioStream.equals(IOStream.CONSOLE_IO)) {
			Employee emp = new Employee();

			int id = (int) (Math.random() * 900 + 100);

			emp.setId(id);
			System.out
					.println("Enter the employee name : ");
			emp.setName(consoleScanner.nextLine());

			System.out.println(
					"Enter the employee salary : ");
			emp.setSalary(consoleScanner.nextDouble());

			employeeList.add(emp);
		}
	}

	public void writeEmployeeData(
			List<Employee> employeeList) {
		if (ioStream.equals(IOStream.CONSOLE_IO)) {
			System.out.println(employeeList);
		} else if (ioStream.equals(IOStream.FILE_IO)) {
			EmployeePayrollFileIOService fileService = new EmployeePayrollFileIOService();
			fileService.writeData(employeeList);
		}
	}

	public void printData() {
		if (ioStream.equals(IOStream.CONSOLE_IO)) {
			System.out.println(employeeList);
		} else if (ioStream.equals(IOStream.FILE_IO)) {
			EmployeePayrollFileIOService fileService = new EmployeePayrollFileIOService();
			fileService.printData();
		}
	}

	public int countEntries() {
		if (ioStream.equals(IOStream.CONSOLE_IO)) {
			return employeeList.size();
		} else if (ioStream.equals(IOStream.FILE_IO)) {
			EmployeePayrollFileIOService fileService = new EmployeePayrollFileIOService();
			return fileService.countEntries();
		}
		return 0;
	}
}