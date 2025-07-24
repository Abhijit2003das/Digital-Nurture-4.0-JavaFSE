import React from 'react';

class CountPeople extends React.Component {
  constructor(props) {
    super(props);
    // Initialize the component's state
    this.state = {
      entrycount: 0, // Counter for people entering
      exitcount: 0   // Counter for people exiting
    };

    // Bind the event handler methods to the current instance of the component.
    // This is necessary for 'this' to refer to the component instance inside the methods.
    this.updateEntry = this.updateEntry.bind(this);
    this.updateExit = this.updateExit.bind(this);
  }

  /**
   * Increments the entrycount state by 1.
   * Uses the functional form of setState to ensure updates are based on the latest state.
   */
  updateEntry() {
    this.setState((prevState) => ({
      entrycount: prevState.entrycount + 1
    }));
  }

  /**
   * Increments the exitcount state by 1.
   * Uses the functional form of setState to ensure updates are based on the latest state.
   */
  updateExit() {
    this.setState((prevState) => ({
      exitcount: prevState.exitcount + 1
    }));
  }

  render() {
    // Destructure state variables for easier access in JSX
    const { entrycount, exitcount } = this.state;

    return (
      <div className="count-people-container">
        {/* Section for Login (Entry) */}
        <div className="count-section">
          <button
            onClick={this.updateEntry} // Attach the updateEntry method to the button's click event
            className="login-button"
          >
            Login
          </button>
          <span className="count-display">
            {entrycount} People Entered!!!
          </span>
        </div>

        {/* Section for Exit */}
        <div className="count-section">
          <button
            onClick={this.updateExit} // Attach the updateExit method to the button's click event
            className="exit-button"
          >
            Exit
          </button>
          <span className="count-display">
            {exitcount} People Left!!!
          </span>
        </div>
      </div>
    );
  }
}

export default CountPeople;