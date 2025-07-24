import React from 'react';

class Cart extends React.Component {
  render() {
    return (
      <>
        {/* Loop through items passed as props and display them */}
        {this.props.item.map((item, index) => (
          <tr key={index}> {/* Using index as key for simplicity here, but a unique ID is better in real apps */}
            <td>{item.itemname}</td>
            <td>{item.price}</td>
          </tr>
        ))}
      </>
    );
  }
}

export default Cart;