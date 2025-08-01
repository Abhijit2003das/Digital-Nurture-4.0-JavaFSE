import React from 'react';

// Data for books
const booksData = [
  { id: 101, bname: 'Master React', price: 670 },
  { id: 102, bname: 'Deep Dive into Angular 11', price: 800 },
  { id: 103, bname: 'Mongo Essentials', price: 450 },
];

function BookDetails() {
  return (
    <div className="detail-card">
      <h2 className="card-title">Book Details</h2>
      <ul className="details-list">
        {/* Using map() to render a list of book details */}
        {booksData.map((book) => (
          // Using book.id as key for unique identification
          <li key={book.id} className="list-item">
            <h3>{book.bname}</h3>
            <p>Price: Rs. {book.price}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default BookDetails;