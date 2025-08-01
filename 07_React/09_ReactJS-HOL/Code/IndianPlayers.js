import React from 'react';

// Functional component to display Odd Team Players using array destructuring
// It expects an array and destructures the first, third, and fifth elements.
const OddPlayers = ({ players }) => {
  // Destructuring the array directly in the function parameters
  // Using a default empty array to prevent errors if players is undefined
  const [first, , third, , fifth] = players || [];
  return (
    <div className="team-section">
      <h3 className="team-title">Odd Players</h3>
      <ul className="player-ul">
        <li className="player-item">First : {first}</li>
        <li className="player-item">Third : {third}</li>
        <li className="player-item">Fifth : {fifth}</li>
      </ul>
    </div>
  );
};

// Functional component to display Even Team Players using array destructuring
// It expects an array and destructures the second, fourth, and sixth elements.
const EvenPlayers = ({ players }) => {
  // Destructuring the array directly in the function parameters
  const [, second, , fourth, , sixth] = players || [];
  return (
    <div className="team-section">
      <h3 className="team-title">Even Players</h3>
      <ul className="player-ul">
        <li className="player-item">Second : {second}</li>
        <li className="player-item">Fourth : {fourth}</li>
        <li className="player-item">Sixth : {sixth}</li>
      </ul>
    </div>
  );
};

// Functional component to display a merged list of Indian players
// It takes an array of players and displays them.
const ListofIndianPlayers = ({ IndianPlayers }) => {
  return (
    <div className="team-section">
      <h3 className="team-title">List of Indian Players Merged:</h3>
      <ul className="player-ul">
        {IndianPlayers.map((player, index) => (
          <li key={index} className="player-item">Mr. {player}</li>
        ))}
      </ul>
    </div>
  );
};

class IndianPlayers extends React.Component {
  render() {
    // Define two arrays for T20 and Ranji Trophy players
    const T20Players = ['First Player', 'Second Player', 'Third Player'];
    const RanjiTrophyPlayers = ['Fourth Player', 'Fifth Player', 'Sixth Player'];

    // Merge the two arrays using the ES6 spread syntax (...)
    // This creates a new array containing all elements from both T20Players and RanjiTrophyPlayers
    const mergedIndianPlayers = [...T20Players, ...RanjiTrophyPlayers];

    // Example array for Odd/Even Players (can be actual player names)
    const IndianTeam = ['Sachin', 'Dhoni', 'Virat', 'Rohit', 'Yuvraj', 'Raina'];

    return (
      <div className="indian-players-container">
        <h2 className="main-section-title">Indian Team</h2>
        {/* Render OddPlayers component, passing the IndianTeam array */}
        <OddPlayers players={IndianTeam} />
        <hr className="divider" />
        {/* Render EvenPlayers component, passing the IndianTeam array */}
        <EvenPlayers players={IndianTeam} />
        <hr className="divider" />
        {/* Render ListofIndianPlayers component, passing the merged array */}
        <ListofIndianPlayers IndianPlayers={mergedIndianPlayers} />
      </div>
    );
  }
}

export default IndianPlayers;