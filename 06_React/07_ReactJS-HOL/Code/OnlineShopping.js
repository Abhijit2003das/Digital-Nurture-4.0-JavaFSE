import React from 'react';
import Cart from './Cart'; // Import the Cart component

class OnlineShopping extends React.Component {
  render() {
    // Create an array of Cart items 
    const CartInfo = [
      { itemname: "Laptop", price: 80000 },
      { itemname: "TV", price: 120000 },
      { itemname: "Washing Machine", price: 50000 },
      { itemname: "Mobile", price: 30000 },
      { itemname: "Fridge", price: 70000 }
    ];

    return (
      <div className="mydi">
        <h1>Items Ordered :</h1>
        {/* Display items in a table using the Cart component  */}
        <table border="1" style={{ width: '50%', margin: '0 auto', borderCollapse: 'collapse' }}>
          <thead>
            <tr style={{ backgroundColor: '#f2f2f2' }}>
              <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Name</th>
              <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Price</th>
            </tr>
          </thead>
          <tbody>
            {/* Pass CartInfo array as 'item' prop to Cart component  */}
            <Cart item={CartInfo} />
          </tbody>
        </table>
      </div>
    );
  }
}

export default OnlineShopping;