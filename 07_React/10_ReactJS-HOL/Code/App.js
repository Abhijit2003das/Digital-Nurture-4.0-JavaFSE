import React from 'react';
import './App.css'; // Import the CSS file for general styling

function App() {
  // Create an object for a single office space [cite: 41]
  const singleOffice = {
    Name: "DBS",
    Rent: 50000,
    Address: "Chennai"
  };

  // Create a list of office objects to loop through [cite: 42]
  const officeSpaces = [
    { Name: "Tech Hub", Rent: 75000, Address: "Bangalore" },
    { Name: "Creative Zone", Rent: 45000, Address: "Hyderabad" },
    { Name: "Business Plaza", Rent: 62000, Address: "Mumbai" },
    { Name: "StartUp Loft", Rent: 59000, Address: "Delhi" },
    { Name: "Innovation Center", Rent: 90000, Address: "Pune" }
  ];

  return (
    <div className="App">
      {/* Heading of the page [cite: 39] */}
      <h1>Office Space, at Affordable Range</h1>

      {/* Image of the office space using JSX attributes  */}
      <img src="/office-space.jpg" alt="Office Space" className="office-image" />

      {/* Display details of the single office object */}
      <div className="office-details-card">
        <h2>Name: {singleOffice.Name}</h2>
        {/* Conditional inline CSS for Rent based on its value  */}
        <h3 style={{ color: singleOffice.Rent < 60000 ? 'red' : 'green' }}>
          Rent: Rs. {singleOffice.Rent}
        </h3>
        <h3>Address: {singleOffice.Address}</h3>
      </div>

      <hr className="divider" />

      {/* Loop through the list of office space items to display more data [cite: 42] */}
      <h2>More Office Spaces:</h2>
      <div className="office-list-container">
        {officeSpaces.map((office, index) => (
          <div key={index} className="office-details-card">
            <h2>Name: {office.Name}</h2>
            {/* Conditional inline CSS for Rent  */}
            <h3 style={{ color: office.Rent < 60000 ? 'red' : 'green' }}>
              Rent: Rs. {office.Rent}
            </h3>
            <h3>Address: {office.Address}</h3>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;