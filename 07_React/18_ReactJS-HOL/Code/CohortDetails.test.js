import React from 'react';
// Import render and screen from React Testing Library
import { render, screen } from '@testing-library/react';
// Import the component to be tested
import CohortDetails from './CohortDetails';
// Import the sample data
import { CohortData } from './Cohort';

// Define a test suite for the CohortDetails component
describe('Cohort Details Component', () => {

  // Test Case 1: should create the component and render its content
  test('should render the component and display cohort details', () => {
    const testCohort = CohortData[0]; // Use the first cohort for testing

    // Render the CohortDetails component
    render(<CohortDetails cohort={testCohort} />);

    // Assert that the cohort name is displayed
    expect(screen.getByText(testCohort.name)).toBeInTheDocument();

    // --- FIX for text split across elements ---
    // Find the <strong> element containing "Code:"
    // Then navigate to its closest <p> parent and assert its full textContent
    const codeLabel = screen.getByText('Code:');
    expect(codeLabel.closest('p')).toHaveTextContent(`Code: ${testCohort.id}`);

    // Do the same for Status
    const statusLabel = screen.getByText('Status:');
    expect(statusLabel.closest('p')).toHaveTextContent(`Status: ${testCohort.status}`);

    // And for Students
    const studentsLabel = screen.getByText('Students:');
    expect(studentsLabel.closest('p')).toHaveTextContent(`Students: ${testCohort.students}`);
    // --- END FIX ---

    // Assert that the "View Details" button is present
    expect(screen.getByRole('button', { name: /view details/i })).toBeInTheDocument();
  });

  // Test Case 2: should display "No cohort data available." when no cohort prop is passed
  test('should display "No cohort data available." when no cohort prop is passed', () => {
    // Render the component without the cohort prop
    render(<CohortDetails />);

    // Expect the specific text to be in the document
    expect(screen.getByText('No cohort data available.')).toBeInTheDocument();
    // Ensure no other cohort details are rendered (e.g., by checking for text that would only appear with cohort data)
    expect(screen.queryByText(/Code:/i)).not.toBeInTheDocument();
    expect(screen.queryByText(/Status:/i)).not.toBeInTheDocument();
    expect(screen.queryByText(/Students:/i)).not.toBeInTheDocument();
    expect(screen.queryByRole('button', { name: /view details/i })).not.toBeInTheDocument();
  });

  // Test Case 3: Snapshot test for a given cohort
  test('should match snapshot for a given cohort', () => {
    const testCohort = CohortData[1]; // Use a different cohort for this snapshot
    const { asFragment } = render(<CohortDetails cohort={testCohort} />);

    // asFragment returns a DocumentFragment, which Jest's snapshot serializer can use.
    expect(asFragment()).toMatchSnapshot();
  });

  // Test Case 4: Snapshot test for no cohort data
  test('should match snapshot when no cohort data is available', () => {
    const { asFragment } = render(<CohortDetails />);
    expect(asFragment()).toMatchSnapshot();
  });
});