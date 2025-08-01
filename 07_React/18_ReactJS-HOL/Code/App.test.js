import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App'; // Import your main App component

test('renders Cohort Dashboard heading', () => {
  render(<App />);
  // Use screen.getByText to find the heading by its text content
  const headingElement = screen.getByText(/Cohort Dashboard/i);
  expect(headingElement).toBeInTheDocument();
});

// Optional: Add a snapshot test for the App component
test('App component matches snapshot', () => {
  const { asFragment } = render(<App />);
  expect(asFragment()).toMatchSnapshot();
});