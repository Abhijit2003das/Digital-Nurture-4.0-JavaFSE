import React from 'react';

// CohortDetails component displays information about a single cohort.
// It expects a 'cohort' object as a prop.
function CohortDetails({ cohort }) {
  // If no cohort data is provided, display a message.
  if (!cohort) {
    return <div className="no-cohort">No cohort data available.</div>;
  }

  return (
    <div className="cohort-details-card">
      {/* Display the cohort's name as a heading */}
      <h3 className="cohort-name">{cohort.name}</h3>
      {/* Display cohort ID */}
      <p><strong>Code:</strong> {cohort.id}</p>
      {/* Display cohort status */}
      <p><strong>Status:</strong> {cohort.status}</p>
      {/* Display number of students */}
      <p><strong>Students:</strong> {cohort.students}</p>
      <button className="view-details-button">View Details</button>
    </div>
  );
}

export default CohortDetails;