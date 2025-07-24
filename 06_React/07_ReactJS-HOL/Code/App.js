import React from 'react';
import './App.css'; // Import the CSS file for styling
import OnlineShopping from './OnlineShopping'; // Import the OnlineShopping component

// The main App component, which renders the OnlineShopping component
function App() {
  return (
    <div className="App">
      {/* Render the OnlineShopping component, which contains the shopping list */}
      <OnlineShopping />
    </div>
  );
}

export default App;