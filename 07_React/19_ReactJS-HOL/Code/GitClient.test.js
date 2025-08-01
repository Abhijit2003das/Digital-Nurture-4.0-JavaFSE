// Import axios to mock it
import axios from 'axios';
// Import the GitClient module to be tested
import GitClient from './GitClient';

// Jest's mock function for axios.get.
// This tells Jest to replace the actual axios.get implementation
// with our mock implementation for the duration of these tests.
jest.mock('axios', () => ({
  get: jest.fn(), // Mock the .get method of axios
}));

// Define a test suite for the GitClient module
describe('Git Client Tests', () => {

  // Before each test in this suite, reset the mock.
  // This ensures that tests are independent and don't affect each other's mocks.
  beforeEach(() => {
    axios.get.mockClear(); // Clear any previous calls to axios.get
  });

  // Test Case: should return repository names for a given user (e.g., techiesyed)
  test('should return repository names for techiesyed', async () => {
    // Define the dummy data that our mocked axios.get will return.
    // This simulates the structure of the actual GitHub API response.
    const mockResponseData = {
      data: [
        { id: 1, name: 'repo-one' },
        { id: 2, name: 'repo-two' },
        { id: 3, name: 'repo-three' },
      ],
    };

    // Configure the mocked axios.get to resolve with our dummy data.
    // .mockResolvedValue is a Jest helper for mocking async functions that return promises.
    axios.get.mockResolvedValue(mockResponseData);

    const userName = 'techiesyed';
    // Call the method under test. Since it's an async operation, await its result.
    const response = await GitClient.getRepositories(userName);

    // Assert that axios.get was called exactly once
    expect(axios.get).toHaveBeenCalledTimes(1);
    // Assert that axios.get was called with the correct URL
    expect(axios.get).toHaveBeenCalledWith(`https://api.github.com/users/${userName}/repos`);

    // Assert that the response received from GitClient.getRepositories matches our mock data
    expect(response.data).toEqual(mockResponseData.data);
  });

  // Optional: Test case for API call failure
  test('should handle API call failure', async () => {
    const errorMessage = 'Network Error';
    // Configure the mocked axios.get to reject with an error
    axios.get.mockRejectedValue(new Error(errorMessage));

    const userName = 'nonexistentuser';
    
    // Expect the promise to be rejected with the specific error
    await expect(GitClient.getRepositories(userName)).rejects.toThrow(errorMessage);
    
    // Ensure axios.get was still called
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledWith(`https://api.github.com/users/${userName}/repos`);
  });
});