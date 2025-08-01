import React from 'react';
import './App.css'; // Import the main CSS file
import ListofPlayers from './ListofPlayers'; // Import the ListofPlayers component
import IndianPlayers from './IndianPlayers'; // Import the IndianPlayers component

class App extends React.Component {
  constructor(props) {
    super(props);
    // Initialize state with a flag to control which component is displayed
    this.state = {
      displayListofPlayers: true // Set to true to show ListofPlayers, false for IndianPlayers
    };
  }

  // Method to toggle the display flag
  toggleDisplay = () => {
    this.setState(prevState => ({
      displayListofPlayers: !prevState.displayListofPlayers
    }));
  };

  render() {
    const { displayListofPlayers } = this.state;

    return (
      <div className="App">
        {/* Button to toggle between the two main displays */}
        <button onClick={this.toggleDisplay} className="toggle-button">
          Toggle Display (Current: {displayListofPlayers ? 'All Players' : 'Indian Team'})
        </button>

        {/* Conditional rendering based on the 'displayListofPlayers' state */}
        {displayListofPlayers ? (
          // Render ListofPlayers component when displayListofPlayers is true
          <ListofPlayers />
        ) : (
          // Render IndianPlayers component when displayListofPlayers is false
          <IndianPlayers />
        )}
      </div>
    );
  }
}

export default App;