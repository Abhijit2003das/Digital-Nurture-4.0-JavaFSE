import React from 'react';
import { Link } from 'react-router-dom';

function TrainersList({ trainers }) {
  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Trainers List</h2>
      <ul className="list-disc pl-5">
        {trainers.map(trainer => (
          <li key={trainer.trainerId} className="mb-2">
            <Link
              to={`/trainers/${trainer.trainerId}`}
              className="text-blue-600 hover:text-blue-800 visited:text-purple-600 text-lg"
            >
              {trainer.name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainersList;