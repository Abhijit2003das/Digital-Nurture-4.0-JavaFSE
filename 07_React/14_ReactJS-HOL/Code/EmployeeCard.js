import React, { useContext } from 'react';
import ThemeContext from './ThemeContext'; // Import the ThemeContext

// EmployeeCard is a functional component that displays employee details
// and applies a theme-based class to its button.
function EmployeeCard({ employee }) {
  // Use the useContext hook to retrieve the current theme value from ThemeContext.
  // This hook makes it easy to consume context in functional components.
  const theme = useContext(ThemeContext);

  // Determine the button class based on the current theme.
  // If theme is 'dark', apply 'dark-button'; otherwise, apply 'light-button'.
  const buttonClassName = theme === 'dark' ? 'dark-button' : 'light-button';

  return (
    <div className="employee-card">
      <h3>{employee.name}</h3>
      <p>ID: {employee.id}</p>
      <p>Department: {employee.department}</p>
      {/* The button's class name dynamically changes based on the theme context */}
      <button className={`view-profile-button ${buttonClassName}`}>
        View Profile
      </button>
    </div>
  );
}

export default EmployeeCard;