import React from 'react';
import './App.css'; // Import the main CSS file for global styles
import GetUser from './GetUser'; // Import the GetUser component

function App() {
  return (
    <div className="App">
      <h1>Random User Profile</h1>
      <GetUser /> {/* Render the GetUser component */}
    </div>
  );
}

export default App;