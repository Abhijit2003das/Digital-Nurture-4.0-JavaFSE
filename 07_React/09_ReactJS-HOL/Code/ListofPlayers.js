import React from 'react';

class ListofPlayers extends React.Component {
  render() {
    // Define an array of player objects with name and score
    const players = [
      { name: "Jack", score: 50 },
      { name: "Michael", score: 70 },
      { name: "John", score: 40 },
      { name: "Ann", score: 61 },
      { name: "Elisabeth", score: 61 },
      { name: "Sachin", score: 95 },
      { name: "Dhoni", score: 100 },
      { name: "Virat", score: 84 },
      { name: "Jadeja", score: 64 },
      { name: "Raina", score: 75 },
      { name: "Rohit", score: 80 }
    ];

    // Filter players with scores less than 70 using an arrow function
    const playersBelow70 = players.filter(player => player.score < 70);

    return (
      <div className="player-list-container">
        {/* Section 1: Display all players */}
        <h2 className="section-title">List of Players</h2>
        <ul className="player-ul">
          {/* Use map() to iterate over the players array and display each player's name and score */}
          {players.map((item, index) => (
            <li key={index} className="player-item">
              Mr. {item.name} <span className="player-score">{item.score}</span>
            </li>
          ))}
        </ul>

        <hr className="divider" />

        {/* Section 2: Display players with scores less than 70 */}
        <h2 className="section-title">List of Players having Scores Less than 70</h2>
        <ul className="player-ul">
          {/* Use map() to iterate over the filtered playersBelow70 array */}
          {playersBelow70.map((item, index) => (
            <li key={index} className="player-item">
              Mr. {item.name} <span className="player-score">{item.score}</span>
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

export default ListofPlayers;