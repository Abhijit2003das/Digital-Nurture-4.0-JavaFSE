import React from 'react';
import './App.css'; // Import the main CSS file for styling
import BookDetails from './BookDetails';
import BlogDetails from './BlogDetails';
import CourseDetails from './CourseDetails';

function App() {
  return (
    <div className="App">
      <h1>Blogger App Dashboard</h1>
      <div className="dashboard-container">
        {/* Render each detail component */}
        <CourseDetails />
        <BookDetails />
        <BlogDetails />
      </div>
    </div>
  );
}

export default App;