import React from 'react';

// Regular expression for email validation
const validEmailRegex = RegExp(
  /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
);

// Helper function to validate the entire form's errors object
// It checks if all error messages are empty and if all fields have been touched.
const validateForm = (errors, formData) => {
  let valid = true;
  Object.values(errors).forEach(val => val.length > 0 && (valid = false));
  // Also check if all form fields have some value (are not empty)
  Object.values(formData).forEach(val => val === '' && (valid = false));
  return valid;
};

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      fullName: '',
      email: '',
      password: '',
      errors: { // Store validation error messages
        fullName: '',
        email: '',
        password: '',
      },
      showModal: false, // State to control modal visibility
      modalMessage: '', // Message to display in the modal
      isFormValid: false, // Tracks overall form validity for submission
    };

    // Bind event handlers to 'this'
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  // Handles changes in input fields and performs real-time validation
  handleChange(event) {
    const { name, value } = event.target;
    let errors = this.state.errors; // Get current errors object

    switch (name) {
      case 'fullName':
        errors.fullName =
          value.length < 5 ? 'Full Name must be at least 5 characters long!' : '';
        break;
      case 'email':
        errors.email = validEmailRegex.test(value) ? '' : 'Email is not valid!';
        break;
      case 'password':
        errors.password =
          value.length < 8 ? 'Password must be at least 8 characters long!' : '';
        break;
      default:
        break;
    }

    // Update state with new value and errors
    this.setState({ errors, [name]: value }, () => {
      // Re-validate form validity after each change
      this.setState({ isFormValid: validateForm(this.state.errors, {
        fullName: this.state.fullName,
        email: this.state.email,
        password: this.state.password
      })});
    });
  }

  // Handles the form submission
  handleSubmit(event) {
    event.preventDefault(); // Prevent default form submission (page reload)

    // Check overall form validity before submission
    if (this.state.isFormValid) {
      this.setState({
        modalMessage: 'Registration Successful!',
        showModal: true,
        // Clear form fields after successful submission
        fullName: '',
        email: '',
        password: '',
        errors: { // Reset errors
          fullName: '',
          email: '',
          password: '',
        },
        isFormValid: false, // Reset form validity
      });
    } else {
      // Display specific error messages if form is invalid
      let errorMessage = '';
      if (this.state.errors.fullName.length > 0) {
        errorMessage += this.state.errors.fullName + '\n';
      }
      if (this.state.errors.email.length > 0) {
        errorMessage += this.state.errors.email + '\n';
      }
      if (this.state.errors.password.length > 0) {
        errorMessage += this.state.errors.password + '\n';
      }
      // If no specific errors but form is not valid (e.g., empty fields)
      if (errorMessage === '') {
        errorMessage = 'Please fill in all fields correctly.';
      }

      this.setState({
        modalMessage: errorMessage,
        showModal: true
      });
    }
  }

  // Closes the modal
  closeModal() {
    this.setState({ showModal: false, modalMessage: '' });
  }

  render() {
    const { fullName, email, password, errors, showModal, modalMessage } = this.state;

    return (
      <div className="register-container">
        <h2 className="register-title">Register Here!!!</h2>
        <form onSubmit={this.handleSubmit} noValidate className="register-form">
          <div className="form-group">
            <label htmlFor="fullName">Name:</label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              value={fullName}
              onChange={this.handleChange}
              className={`input-field ${errors.fullName.length > 0 ? 'input-error' : ''}`}
              noValidate // Disable browser's default validation
            />
            {/* Display error message for full name */}
            {errors.fullName.length > 0 && (
              <span className="error-message">{errors.fullName}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={this.handleChange}
              className={`input-field ${errors.email.length > 0 ? 'input-error' : ''}`}
              noValidate
            />
            {/* Display error message for email */}
            {errors.email.length > 0 && (
              <span className="error-message">{errors.email}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={this.handleChange}
              className={`input-field ${errors.password.length > 0 ? 'input-error' : ''}`}
              noValidate
            />
            {/* Display error message for password */}
            {errors.password.length > 0 && (
              <span className="error-message">{errors.password}</span>
            )}
          </div>

          <button type="submit" className="submit-button">
            Submit
          </button>
        </form>

        {/* Custom Modal for displaying messages/errors */}
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

export default Register;