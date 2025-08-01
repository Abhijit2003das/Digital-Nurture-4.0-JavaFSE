import React from 'react';

// Create a new Context named ThemeContext.
// It's assigned a default value of 'light'. This default value is used
// when a component consumes the context but there is no matching Provider above it in the tree.
const ThemeContext = React.createContext('light');

export default ThemeContext;