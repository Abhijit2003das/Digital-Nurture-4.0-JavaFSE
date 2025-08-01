import React from 'react';
import './App.css'; // Import the main CSS file
import Greeting from './Greeting'; // Import the Greeting component
import LoginButton from './LoginButton'; // Import LoginButton
import LogoutButton from './LogoutButton'; // Import LogoutButton

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isLoggedIn: false }; // Initial state: user is not logged in
    this.handleLoginClick = this.handleLoginClick.bind(this);
    this.handleLogoutClick = this.handleLogoutClick.bind(this);
  }

  handleLoginClick() {
    this.setState({ isLoggedIn: true }); // Set isLoggedIn to true on login
  }

  handleLogoutClick() {
    this.setState({ isLoggedIn: false }); // Set isLoggedIn to false on logout
  }

  render() {
    const isLoggedIn = this.state.isLoggedIn;
    let button; // Declare a variable to hold the button component

    // Conditionally assign the button based on login status
    if (isLoggedIn) {
      button = <LogoutButton onClick={this.handleLogoutClick} />;
    } else {
      button = <LoginButton onClick={this.handleLoginClick} />;
    }

    return (
      <div className="App">
        <h1>Flight Ticket Booking</h1>
        {/* Render the Greeting component, passing the isLoggedIn state as a prop */}
        <Greeting isLoggedIn={isLoggedIn} />
        {/* Render the appropriate button (Login/Logout) */}
        <div className="button-container">
          {button}
        </div>
      </div>
    );
  }
}

export default App;