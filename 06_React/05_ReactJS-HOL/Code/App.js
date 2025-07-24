import React from 'react';
import CohortDetails from './CohortDetails';
import './App.css'; // Keep this for any general app-wide styles, or remove if not used.

function App() {
  // Mock data for multiple cohorts to simulate the dashboard layout
  const cohorts = [
    {
      id: 'INTADMFDF10',
      stream: '.NET FSD',
      startedOn: '22-Feb-2022',
      status: 'Scheduled', // Example status
      coach: 'Aathma',
      trainer: 'Jojo Jose'
    },
    {
      id: 'ADM21JF014',
      stream: 'Java FSD',
      startedOn: '10-Sep-2021',
      status: 'Ongoing', // Example status
      coach: 'Apoorv',
      trainer: 'Elisa Smith'
    },
    {
      id: 'CDBJF21025',
      stream: 'Java FSD',
      startedOn: '24-Dec-2021',
      status: 'Ongoing', // Example status
      coach: 'Aathma',
      trainer: 'John Doe'
    },
    {
      id: 'PYT23AI001',
      stream: 'Python AI',
      startedOn: '01-Jan-2023',
      status: 'Completed', // Example status
      coach: 'Sarah Lee',
      trainer: 'Mike Ross'
    }
  ];

  return (
    <div className="App p-6">
      {/* You might want to include the Tailwind CSS CDN in your public/index.html for global styling */}
      {/* <script src="https://cdn.tailwindcss.com"></script> */}

      <h1 className="text-4xl font-bold mb-8 text-center text-gray-800">Cohorts Details</h1>
      <div className="flex flex-wrap justify-center gap-6">
        {cohorts.map(cohort => (
          <CohortDetails key={cohort.id} cohort={cohort} />
        ))}
      </div>
    </div>
  );
}

export default App;