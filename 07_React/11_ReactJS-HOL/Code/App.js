import React from 'react';
import './App.css'; // Import the main CSS file for global styles
import EventHandlers from './EventHandlers'; // Import the EventHandlers component
import CurrencyConverter from './CurrencyConverter'; // Import the CurrencyConverter component

function App() {
  return (
    <div className="App">
      <h1>React Event Handling Examples</h1>
      <EventHandlers /> {/* Render the EventHandlers component */}
      <hr className="section-divider" /> {/* A visual divider */}
      <CurrencyConverter /> {/* Render the CurrencyConverter component */}
    </div>
  );
}

export default App;