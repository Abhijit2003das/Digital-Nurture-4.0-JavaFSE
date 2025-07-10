import { Component, OnInit } from '@angular/core';
import { Employee, Department, Skill } from '../employee'; // Import all necessary interfaces
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

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

  constructor(
    private route: ActivatedRoute, // Inject ActivatedRoute
    private employeeService: EmployeeService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Get the ID from the route parameters
    this.id = this.route.snapshot.params['id'];

    // Fetch employee details
    this.employeeService.getEmployeeById(this.id).subscribe(
      data => {
        this.employee = data;
        console.log("Employee details loaded:", this.employee);
      },
      error => {
        console.error("Error fetching employee details:", error);
        alert('Could not load employee details.');
        this.goToEmployeeList(); // Go back to list if employee not found
      }
    );
  }

  // Helper method to format skill names for display
  getSkillNames(skills: Skill[] | null | undefined): string {
    if (skills && skills.length > 0) {
      return skills.map(skill => skill.name).join(', ');
    }
    return 'N/A';
  }

  // Method to navigate back to the employee list
  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }
}