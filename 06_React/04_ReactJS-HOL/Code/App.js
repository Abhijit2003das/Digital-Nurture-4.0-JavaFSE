import React from 'react';
import Posts from './Posts';
import './App.css';

function App() {
  return (
    <div className="App">
      {/* The Posts component will handle its own styling and data fetching */}
      <Posts />
    </div>
  );
}

export default App;