// src/app/employee-list/employee-list.component.ts

import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee'; // Ensure this path is correct
import { EmployeeService } from '../employee.service'; // Ensure this path is correct
import { Router } from '@angular/router'; // <<< IMPORT ROUTER HERE <<<

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[] = []; // Initialize as an empty array

  // <<< INJECT ROUTER IN THE CONSTRUCTOR <<<
  constructor(private employeeService: EmployeeService,
              private router: Router) { } // Added Router here

  ngOnInit(): void {
    this.getEmployees();
  }

  private getEmployees(){
    this.employeeService.getEmployeesList().subscribe(data => {
      this.employees = data;
      console.log("Employees loaded:", this.employees);
    },
    error => {
        console.error("Error fetching employees:", error);
    });
  }

  getSkillNames(skills: any[] | null | undefined): string {
    if (skills && skills.length > 0) {
      return skills.map(skill => skill.name).join(', ');
    }
    return 'N/A';
  }

  // --- Action Methods ---

  updateEmployee(id: string) {
    console.log('Navigating to update employee for ID:', id);
    // Navigate to the 'update-employee' route, passing the employee ID as a parameter
    this.router.navigate(['update-employee', id]);
  }

  // <<< FULLY IMPLEMENTED DELETE EMPLOYEE >>>
  deleteEmployee(id: string) {
    if (confirm('Are you sure you want to delete this employee?')) { // Optional: Add a confirmation dialog
      this.employeeService.deleteEmployee(id).subscribe(
        data => {
          console.log("Employee deleted successfully:", data);
          this.getEmployees(); // Refresh the list after successful deletion
        },
        error => {
          console.error("Error deleting employee:", error);
          // You might want to display an error message to the user here
        }
      );
    }
  }

  employeeDetails(id: string) {
    console.log('Navigating to employee details for ID:', id);
    // Navigate to the 'employee-details' route, passing the employee ID as a parameter
    this.router.navigate(['employee-details', id]);
  }
}