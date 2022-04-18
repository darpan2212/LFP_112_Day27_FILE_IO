package com.bl.employee.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bl.employee.payroll.model.Employee;

enum IOStream {
	CONSOLE_IO, FILE_IO, DATABASE_IO, REST_IO;
}

public class EmployeePayrollService {

	List<Employee> employeeList;
	IOStream ioStream;

	public EmployeePayrollService() {
		employeeList = new ArrayList<>();
		ioStream = IOStream.CONSOLE_IO;
	}

	public static void main(String[] args) {
		EmployeePayrollService service = new EmployeePayrollService();

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
					service.readEmployeeDataFromConsole(sc);
					break;
				case 2:
					service.writeEmployeeDataToConsole();
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

	public void readEmployeeDataFromConsole(
			Scanner consoleScanner) {
		Employee emp = new Employee();

		int id = (int) (Math.random() * 900 + 100);

		emp.setId(id);
		System.out.println("Enter the employee name : ");
		emp.setName(consoleScanner.nextLine());

		System.out.println("Enter the employee salary : ");
		emp.setSalary(consoleScanner.nextDouble());

		employeeList.add(emp);
	}

	public void writeEmployeeDataToConsole() {
		System.out.println(employeeList);
	}
}