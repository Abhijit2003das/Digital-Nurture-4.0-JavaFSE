import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './Home';
import TrainersList from './TrainersList';
import TrainerDetail from './TrainerDetails';
import trainersMock from './TrainersMock';
import './App.css'; // Assuming you have an App.css for basic styles if needed, otherwise remove.

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100 font-sans p-4">
        <header className="bg-white shadow-md rounded-lg p-6 mb-6">
          <h1 className="text-4xl font-extrabold text-gray-800 mb-4">My Academy Trainers App</h1>
          <nav className="flex space-x-6 text-xl">
            <Link
              to="/"
              className="text-blue-600 hover:text-blue-800 visited:text-purple-600 font-medium"
            >
              Home
            </Link>
            <span className="text-gray-400">|</span>
            <Link
              to="/trainers"
              className="text-blue-600 hover:text-blue-800 visited:text-purple-600 font-medium"
            >
              Show Trainers
            </Link>
          </nav>
        </header>

        <main className="bg-white shadow-md rounded-lg p-6">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/trainers" element={<TrainersList trainers={trainersMock} />} />
            <Route path="/trainers/:id" element={<TrainerDetail />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;