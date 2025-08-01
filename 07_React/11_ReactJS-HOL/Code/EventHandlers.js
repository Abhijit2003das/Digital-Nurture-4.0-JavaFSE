import React from 'react';

class EventHandlers extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      counter: 0,
      message: ''
    };
    // Binding event handlers to 'this' in the constructor is a common practice
    // to ensure 'this' refers to the component instance inside the methods.
    this.incrementCounter = this.incrementCounter.bind(this);
    this.decrementCounter = this.decrementCounter.bind(this);
    this.sayHello = this.sayHello.bind(this);
    this.handleIncreaseClick = this.handleIncreaseClick.bind(this);
    this.handleSyntheticEvent = this.handleSyntheticEvent.bind(this);
  }

  // Method to increment the counter
  incrementCounter() {
    this.setState(prevState => ({
      counter: prevState.counter + 1
    }));
  }

  // Method to decrement the counter
  decrementCounter() {
    this.setState(prevState => ({
      counter: prevState.counter - 1
    }));
  }

  // Method to display a static message
  sayHello() {
    this.setState({ message: 'Hello from multiple methods!' });
    console.log('Hello from sayHello!');
  }

  // Method that takes an argument
  sayWelcome(greeting) {
    this.setState({ message: `You clicked 'Say Welcome'! Message: ${greeting}` });
    console.log(`Greeting: ${greeting}`);
  }

  // Method to be invoked by the "Increase" button, calling multiple methods
  handleIncreaseClick() {
    this.incrementCounter(); // First method call
    this.sayHello();         // Second method call
  }

  // Method to handle a synthetic event (e.g., onClick)
  handleSyntheticEvent(event) {
    this.setState({ message: `I was clicked! (Synthetic Event Type: ${event.type})` });
    console.log('Synthetic Event:', event);
  }

  render() {
    const { counter, message } = this.state;

    return (
      <div className="event-handlers-container">
        <h2>Counter Example</h2>
        <p className="counter-display">Counter: {counter}</p>
        <div className="button-group">
          <button onClick={this.handleIncreaseClick} className="button primary">
            Increase (Multiple Methods)
          </button>
          <button onClick={this.decrementCounter} className="button secondary">
            Decrement
          </button>
        </div>

        <h2>Function with Argument</h2>
        {/* Using an arrow function in JSX to pass an argument */}
        <button onClick={() => this.sayWelcome('Welcome to React Events!')} className="button info">
          Say Welcome
        </button>

        <h2>Synthetic Event Example</h2>
        {/* Direct event handler for a synthetic event */}
        <button onClick={this.handleSyntheticEvent} className="button success">
          Click Me (Synthetic Event)
        </button>

        {/* Display messages */}
        {message && <p className="message-display">{message}</p>}
      </div>
    );
  }
}

export default EventHandlers;