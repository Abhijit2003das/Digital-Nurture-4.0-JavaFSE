import React, { useState } from 'react';
import './App.css'; // Import the main CSS file for styling
import EmployeesList from './EmployeesList'; // Import EmployeesList component
import ThemeContext from './ThemeContext'; // Import the ThemeContext

function App() {
  // Use React's useState hook to manage the current theme ('light' or 'dark').
  const [theme, setTheme] = useState('light');

  // Sample employee data
  const employees = [
    { id: 1, name: 'Alice Smith', department: 'Engineering' },
    { id: 2, name: 'Bob Johnson', department: 'HR' },
    { id: 3, name: 'Charlie Brown', department: 'Marketing' },
    { id: 4, name: 'Diana Prince', department: 'Sales' },
  ];

  // Function to toggle the theme between 'light' and 'dark'.
  const toggleTheme = () => {
    setTheme(prevTheme => (prevTheme === 'light' ? 'dark' : 'light'));
  };

  return (
    // ThemeContext.Provider makes the 'theme' value available to all components
    // within its subtree. Any component can consume this value using useContext.
    <ThemeContext.Provider value={theme}>
      <div className={`App ${theme}-mode`}> {/* Apply theme class to App container */}
        <h1>Employee Dashboard</h1>
        {/* Button to toggle the theme */}
        <button onClick={toggleTheme} className="theme-toggle-button">
          Toggle Theme (Current: {theme.charAt(0).toUpperCase() + theme.slice(1)})
        </button>
        {/* Render the EmployeesList component, which will then render EmployeeCards.
            The theme is implicitly available to EmployeeCard via Context. */}
        <EmployeesList employees={employees} />
      </div>
    </ThemeContext.Provider>
  );
}

export default App;