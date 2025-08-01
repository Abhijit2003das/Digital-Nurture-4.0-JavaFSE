import React from 'react';
import EmployeeCard from './EmployeeCard'; // Import the EmployeeCard component

// EmployeesList is a functional component that displays a list of employees.
// It receives the 'employees' array as a prop.
function EmployeesList({ employees }) {
  return (
    <div className="employees-list">
      <h2>Our Employees</h2>
      <div className="employee-cards-container">
        {/* Map over the employees array to render an EmployeeCard for each employee.
            The theme is now provided by the ThemeContext.Provider higher up in the tree,
            so it doesn't need to be passed as a prop here. */}
        {employees.map(employee => (
          <EmployeeCard key={employee.id} employee={employee} />
        ))}
      </div>
    </div>
  );
}

export default EmployeesList;