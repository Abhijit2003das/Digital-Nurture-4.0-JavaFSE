import React from 'react';

class CurrencyConverter extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      indianRupees: '',
      euro: '',
      conversionRate: 0.011 // Approximate rate: 1 INR = 0.011 EUR
    };
    this.handleRupeesChange = this.handleRupeesChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // Handles changes in the Indian Rupees input field
  handleRupeesChange(event) {
    const value = event.target.value;
    // Allow only numbers and a single decimal point
    if (/^\d*\.?\d*$/.test(value) || value === '') {
      this.setState({ indianRupees: value });
    }
  }

  // Handles the form submission (Convert button click)
  handleSubmit(event) {
    event.preventDefault(); // Prevent default form submission behavior (page reload)
    const { indianRupees, conversionRate } = this.state;
    const rupees = parseFloat(indianRupees);

    if (!isNaN(rupees) && rupees > 0) {
      const convertedEuro = (rupees * conversionRate).toFixed(2); // Convert and format to 2 decimal places
      this.setState({ euro: convertedEuro });
    } else {
      this.setState({ euro: 'Invalid input', indianRupees: '' });
    }
  }

  render() {
    const { indianRupees, euro } = this.state;

    return (
      <div className="currency-converter-container">
        <h2>Currency Converter (INR to EUR)</h2>
        <form onSubmit={this.handleSubmit} className="converter-form">
          <div className="form-group">
            <label htmlFor="rupeesInput">Indian Rupees (INR):</label>
            <input
              type="text" // Use text to allow for empty string and controlled input
              id="rupeesInput"
              value={indianRupees}
              onChange={this.handleRupeesChange} // Update state on input change
              placeholder="Enter amount in INR"
              className="input-field"
            />
          </div>
          <button type="submit" className="button convert-button">
            Convert to Euro
          </button>
        </form>
        {euro && (
          <p className="conversion-result">
            Converted Euro (EUR): <span className="euro-value">€{euro}</span>
          </p>
        )}
      </div>
    );
  }
}

export default CurrencyConverter;