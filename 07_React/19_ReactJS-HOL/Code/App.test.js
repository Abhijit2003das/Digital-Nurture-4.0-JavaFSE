import React from 'react';
import { render, screen, waitFor } from '@testing-library/react'; // No need to import 'act' explicitly if using waitFor
import App from './App';
import GitClient from './GitClient'; // Import GitClient to mock it

// Mock the entire GitClient module
jest.mock('./GitClient');

describe('App Component', () => {
  // Define mock data for repositories
  const mockRepositories = [
    { id: 1, name: 'repo-mock-one' },
    { id: 2, name: 'repo-mock-two' },
    { id: 3, name: 'repo-mock-three' },
  ];

  // Clear mocks before each test to ensure test isolation
  beforeEach(() => {
    GitClient.getRepositories.mockClear();
  });

  test('renders Git repositories heading and fetches data', async () => {
    // Configure the mock to return successful data BEFORE rendering
    GitClient.getRepositories.mockResolvedValue({ data: mockRepositories });

    render(<App />);

    // Expect the loading message to appear initially
    expect(screen.getByText(/Loading repositories.../i)).toBeInTheDocument();

    // Wait for the asynchronous operation (API call) to complete and UI to update
    await waitFor(() => {
      // Expect the heading to be present after loading
      expect(screen.getByText(/Git Repositories of User - Octocat/i)).toBeInTheDocument();

      // Expect the mock repository names to be displayed
      mockRepositories.forEach(repo => {
        expect(screen.getByText(repo.name)).toBeInTheDocument();
      });

      // Ensure the loading message is no longer in the document
      expect(screen.queryByText(/Loading repositories.../i)).not.toBeInTheDocument();
    });

    // Verify that GitClient.getRepositories was called
    expect(GitClient.getRepositories).toHaveBeenCalledTimes(1);
    expect(GitClient.getRepositories).toHaveBeenCalledWith('octocat');
  });

  test('renders error message on API call failure', async () => {
    const errorMessage = 'Failed to load repositories. Please try again later.';
    // Configure the mock to return a rejected promise (simulating an error) BEFORE rendering
    GitClient.getRepositories.mockRejectedValue(new Error('Network Error'));

    render(<App />);

    // Expect the loading message initially
    expect(screen.getByText(/Loading repositories.../i)).toBeInTheDocument();

    // Wait for the error state to be reflected in the UI
    await waitFor(() => {
      expect(screen.getByText(errorMessage)).toBeInTheDocument();
      expect(screen.queryByText(/Loading repositories.../i)).not.toBeInTheDocument();
    });

    // Verify that GitClient.getRepositories was called
    expect(GitClient.getRepositories).toHaveBeenCalledTimes(1);
  });

  test('renders "No repositories found" message when no data is returned', async () => {
    // Configure the mock to return an empty array BEFORE rendering
    GitClient.getRepositories.mockResolvedValue({ data: [] });

    render(<App />);

    // Expect the loading message initially
    expect(screen.getByText(/Loading repositories.../i)).toBeInTheDocument();

    // Wait for the asynchronous operation to complete
    await waitFor(() => {
      expect(screen.getByText(/No repositories found for this user./i)).toBeInTheDocument();
      expect(screen.queryByText(/Loading repositories.../i)).not.toBeInTheDocument();
    });
  });

  // Snapshot test for App component after successful data load
  test('App component matches snapshot after successful data load', async () => {
    GitClient.getRepositories.mockResolvedValue({ data: mockRepositories });
    const { asFragment } = render(<App />); // Destructure asFragment from render result

    // Wait for the data to load and UI to update before taking snapshot
    await waitFor(() => {
      expect(screen.getByText(/Git Repositories of User - Octocat/i)).toBeInTheDocument();
    });

    expect(asFragment()).toMatchSnapshot(); // Correct usage of asFragment
  });

  // Snapshot test for App component in loading state
  test('App component matches snapshot in loading state', () => {
    // For the loading state snapshot, we don't resolve the promise immediately.
    // Jest's default mock behavior for an async function (if not explicitly resolved/rejected)
    // is to return a pending promise. This allows us to capture the "loading" UI.
    // We render and immediately take the snapshot.
    const { asFragment } = render(<App />); // Destructure asFragment from render result
    expect(asFragment()).toMatchSnapshot(); // Correct usage of asFragment
  });
});