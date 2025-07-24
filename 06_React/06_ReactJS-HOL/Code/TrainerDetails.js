import React from 'react';
import { useParams } from 'react-router-dom';
import trainersMock from './TrainersMock'; // Import mock data

function TrainerDetail() {
  const { id } = useParams(); // Get the 'id' parameter from the URL
  const trainer = trainersMock.find(t => t.trainerId === id); // Find the trainer by ID

  if (!trainer) {
    return <h2>Trainer Not Found</h2>;
  }

  return (
    <div>
      <h2>Trainers Details</h2>
      <h3>{trainer.name} ({trainer.technology})</h3>
      <p>{trainer.email}</p>
      <p>{trainer.phone}</p>
      <h4>Skills</h4>
      <ul>
        {trainer.skills.map((skill, index) => (
          <li key={index}>{skill}</li>
        ))}
      </ul>
    </div>
  );
}

export default TrainerDetail;