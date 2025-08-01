import React from 'react';

class GetUser extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      person: null, // To store the fetched user data
      loading: true, // To indicate if data is being loaded
      error: null,   // To store any error during fetching
    };
  }

  // componentDidMount is called immediately after a component is mounted.
  // This is the ideal place to make network requests.
  async componentDidMount() {
    try {
      const url = "https://api.randomuser.me/"; // The API endpoint
      const response = await fetch(url); // Make the API call
      
      // Check if the response is OK (status code 200-299)
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json(); // Parse the JSON response
      
      // Update the state with the fetched person data and set loading to false
      this.setState({
        person: data.results[0], // The first user object from the results array
        loading: false,
        error: null, // Clear any previous errors
      });
      
      console.log("Fetched data:", data.results[0]); // Log the fetched data for debugging
    } catch (error) {
      // Catch any errors during the fetch operation
      console.error("Error fetching user data:", error);
      this.setState({
        loading: false,
        error: "Failed to fetch user data. Please try again.", // Set an error message
      });
    }
  }

  render() {
    const { person, loading, error } = this.state;

    // Display a loading message while data is being fetched
    if (loading) {
      return <div className="loading-message">Loading user...</div>;
    }

    // Display an error message if fetching failed
    if (error) {
      return <div className="error-message">{error}</div>;
    }

    // If data is loaded and no errors, display the user details
    if (person) {
      return (
        <div className="user-card">
          {/* Display title and first name */}
          <h2 className="user-name">
            {person.name.title} {person.name.first} {person.name.last}
          </h2>
          {/* Display user image */}
          <img
            src={person.picture.large} // Use the 'large' size picture
            alt={`${person.name.first}'s profile`}
            className="user-image"
            // Optional: Add an onerror handler for broken images
            onError={(e) => { e.target.onerror = null; e.target.src="https://placehold.co/150x150/cccccc/333333?text=No+Image" }}
          />
        </div>
      );
    }

    // Fallback if no person data is available (should ideally not happen if loading/error are handled)
    return <div className="no-data-message">No user data to display.</div>;
  }
}

export default GetUser;