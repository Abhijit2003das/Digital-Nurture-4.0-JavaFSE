import React from 'react';

class ComplaintRegister extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      employeeName: '',
      complaint: '',
      referenceNumber: null,
      showModal: false, // State to control modal visibility
      modalMessage: ''  // Message to display in the modal
    };

    // Bind event handlers to 'this'
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  // Generic handleChange for all input fields
  handleChange(event) {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  }

  // Handles the form submission
  handleSubmit(event) {
    event.preventDefault(); // Prevent default form submission (page reload)

    const { employeeName, complaint } = this.state;

    if (!employeeName || !complaint) {
      this.setState({
        modalMessage: 'Please fill in both Employee Name and Complaint fields.',
        showModal: true
      });
      return;
    }

    // Generate a simple reference number (e.g., timestamp + random number)
    const newReferenceNumber = Math.floor(Date.now() / 1000) + Math.floor(Math.random() * 100);

    // Construct the success message
    const successMessage = `Thanks ${employeeName}! Your Complaint was Submitted. Transaction ID is: ${newReferenceNumber}`;

    // Update state with reference number and show modal
    this.setState({
      referenceNumber: newReferenceNumber,
      modalMessage: successMessage,
      showModal: true,
      employeeName: '', // Clear form fields after submission
      complaint: ''
    });
  }

  // Closes the modal
  closeModal() {
    this.setState({ showModal: false, modalMessage: '' });
  }

  render() {
    const { employeeName, complaint, showModal, modalMessage } = this.state;

    return (
      <div className="complaint-register-container">
        <h2 className="form-title">Register your complaints here!!!</h2>
        <form onSubmit={this.handleSubmit} className="complaint-form">
          <div className="form-group">
            <label htmlFor="employeeName">Name:</label>
            <input
              type="text"
              id="employeeName"
              name="employeeName" // 'name' attribute matches state key
              value={employeeName}
              onChange={this.handleChange} // Controlled component
              placeholder="Enter your name"
              className="input-field"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="complaint">Complaint:</label>
            <textarea
              id="complaint"
              name="complaint" // 'name' attribute matches state key
              value={complaint}
              onChange={this.handleChange} // Controlled component
              placeholder="Describe your complaint here..."
              rows="5"
              className="textarea-field"
              required
            ></textarea>
          </div>

          <button type="submit" className="submit-button">
            Submit
          </button>
        </form>

        {/* Custom Modal for displaying messages */}
        {showModal && (
          <div className="modal-overlay">
            <div className="modal-content">
              <p>{modalMessage}</p>
              <button onClick={this.closeModal} className="modal-ok-button">OK</button>
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default ComplaintRegister;