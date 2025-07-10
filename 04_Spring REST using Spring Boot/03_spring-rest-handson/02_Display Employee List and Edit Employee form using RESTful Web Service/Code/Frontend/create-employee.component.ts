import { Component, OnInit } from '@angular/core';
import { Employee, Department, Skill } from '../employee'; // Ensure all interfaces are imported
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {

  // Initialize employee object with default values as an object literal
  // This ensures all properties match the Employee interface types.
  employee: Employee = {
    id: '',              // Employee ID is string
    firstName: '',
    lastName: '',
    email: '',
    department: null,    // Department can be null
    skills: []           // Skills is an empty array
  };

  // Temporary variables for form inputs
  departmentName: string = '';
  skillNames: string = '';

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Initialization logic if needed
  }

  onSubmit() {
    console.log("Submitting employee:", this.employee);
    console.log("Department Name from form:", this.departmentName);
    console.log("Skill Names from form:", this.skillNames);

    // --- Process Department Name from temporary variable to employee.department ---
    if (this.departmentName && this.departmentName.trim() !== '') {
      this.employee.department = {
        id: 0, // Department ID is a NUMBER (as per employee.ts)
        name: this.departmentName.trim()
      };
    } else {
      this.employee.department = null;
    }

    // --- Process Skill Names from temporary variable to employee.skills ---
    if (this.skillNames && this.skillNames.trim() !== '') {
      const names = this.skillNames.split(',').map(name => name.trim()).filter(name => name);
      this.employee.skills = names.map(name => ({
        id: 0, // Skill ID is a NUMBER (as per employee.ts)
        name: name
      }));
    } else {
      this.employee.skills = [];
    }
    // --- End preparation for submission ---

    this.saveEmployee();
  }

  saveEmployee(){
    this.employeeService.createEmployee(this.employee).subscribe( data =>{
      console.log("Backend response (Employee created):", data);
      this.goToEmployeeList();
    },
    error => {
      console.error("Error creating employee:", error);
      alert('Failed to create employee. Check console for details. (e.g., ID might not be unique or backend error)');
    });
  }

  goToEmployeeList(){
    this.router.navigate(['/employees']);
  }
}