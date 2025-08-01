import React from 'react';
import './App.css'; // Import the main CSS file for global styles
import ComplaintRegister from './ComplaintRegister'; // Import the ComplaintRegister component

function App() {
  return (
    <div className="App">
      <ComplaintRegister /> {/* Render the ComplaintRegister component */}
    </div>
  );
}

export default App;