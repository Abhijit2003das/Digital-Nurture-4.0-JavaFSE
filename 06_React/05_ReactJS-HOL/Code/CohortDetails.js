import React from 'react';
import styles from './CohortDetails.module.css'; // Import the CSS Module

function CohortDetails({ cohort }) {
  // Use the cohort prop directly, or provide a default if cohort is undefined
  const cohortData = cohort || {
    id: 'DEFAULT001',
    stream: 'Unknown Stream',
    startedOn: 'N/A',
    status: 'Unknown',
    coach: 'N/A',
    trainer: 'N/A'
  };

  // Define the style for <h3> element to use “green” color font when cohort status is “ongoing”
  // and “blue” color in all other scenarios.
  const headingStyle = {
    color: cohortData.status === 'Ongoing' ? 'green' : 'blue'
  };

  return (
    // Apply the 'box' class from the CSS Module to the container div
    <div className={styles.box}>
      <h3 style={headingStyle}>{cohortData.id} - {cohortData.stream}</h3>
      <dl>
        {/* Define a css style for html <dt> element using tag selector in CohortDetails.module.css */}
        <dt>Started On</dt>
        <dd>{cohortData.startedOn}</dd>

        <dt>Current Status</dt>
        <dd>{cohortData.status}</dd>

        <dt>Coach</dt>
        <dd>{cohortData.coach}</dd>

        <dt>Trainer</dt>
        <dd>{cohortData.trainer}</dd>
      </dl>
    </div>
  );
}

export default CohortDetails;