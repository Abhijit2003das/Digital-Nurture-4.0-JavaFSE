import React from 'react';
import './App.css'; // Import the main CSS file for styling
import CohortDetails from './CohortDetails'; // Import the CohortDetails component
import { CohortData } from './Cohort'; // Import the sample cohort data

function App() {
  return (
    <div className="App">
      <h1>Cohort Dashboard</h1>
      <div className="cohort-list-container">
        {/* Map through the CohortData to render multiple CohortDetails components */}
        {CohortData.map(cohort => (
          <CohortDetails key={cohort.id} cohort={cohort} />
        ))}
      </div>
    </div>
  );
}

export default App;