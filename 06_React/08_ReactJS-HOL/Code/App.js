import React from 'react';
import './App.css'; // Import the main CSS file for global styles
import CountPeople from './CountPeople'; // Import the CountPeople component

// The main App component, responsible for rendering the CountPeople component
function App() {
  return (
    <div className="App">
      {/* Render the CountPeople component */}
      <CountPeople />
    </div>
  );
}

export default App;