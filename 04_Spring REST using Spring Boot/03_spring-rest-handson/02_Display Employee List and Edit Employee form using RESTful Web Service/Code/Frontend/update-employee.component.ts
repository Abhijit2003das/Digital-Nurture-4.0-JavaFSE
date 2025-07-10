import { Component, OnInit } from '@angular/core';
import { Employee, Department, Skill } from '../employee'; // Import all necessary interfaces
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute to get route params

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  id: string = ''; // To store the employee ID from the route parameter
  // Initialize employee as an object literal with default values
  employee: Employee = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    department: null,
    skills: []
  };

  departmentName: string = ''; // For department input
  skillNames: string = '';     // For skills input

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute, // Inject ActivatedRoute
    private router: Router
  ) { }

  ngOnInit(): void {
    // Get the ID from the route parameters
    this.id = this.route.snapshot.params['id'];

    // Fetch the employee details using the ID
    this.employeeService.getEmployeeById(this.id).subscribe(
      data => {
        this.employee = data;
        console.log("Employee fetched for update:", this.employee);

        // Populate temporary form fields from fetched employee data
        if (this.employee.department) {
          this.departmentName = this.employee.department.name;
        }
        if (this.employee.skills && this.employee.skills.length > 0) {
          this.skillNames = this.employee.skills.map(skill => skill.name).join(', ');
        }
      },
      error => {
        console.error("Error fetching employee for update:", error);
        alert('Could not load employee details for update.');
        this.goToEmployeeList(); // Go back to list if employee not found
      }
    );
  }

  // Method to handle form submission for update
  onSubmit() {
    console.log("Updating employee:", this.employee);

    // --- Prepare Department and Skills for submission (similar to create) ---
    if (this.departmentName && this.departmentName.trim() !== '') {
      this.employee.department = {
        id: 0, // Placeholder ID for new department, or 0 for existing
        name: this.departmentName.trim()
      };
    } else {
      this.employee.department = null;
    }

    if (this.skillNames && this.skillNames.trim() !== '') {
      const names = this.skillNames.split(',').map(name => name.trim()).filter(name => name);
      this.employee.skills = names.map(name => ({
        id: 0, // Placeholder ID for new skill, or 0 for existing
        name: name
      }));
    } else {
      this.employee.skills = [];
    }
    // --- End preparation ---

    this.employeeService.updateEmployee(this.id, this.employee).subscribe(
      data => {
        console.log("Employee updated successfully:", data);
        this.goToEmployeeList(); // Navigate back to the list after success
      },
      error => {
        console.error("Error updating employee:", error);
        alert('Failed to update employee. Check console for details.');
      }
    );
  }

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }
}