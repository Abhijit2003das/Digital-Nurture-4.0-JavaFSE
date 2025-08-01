import axios from "axios"; // Import the axios library

/**
 * GitClient class provides methods to interact with the GitHub API.
 * This class is designed to be a module that can be easily mocked for testing.
 */
class GitClient {
  /**
   * Fetches a list of public repositories for a given GitHub user.
   * @param {string} userName - The GitHub username.
   * @returns {Promise<Array>} A promise that resolves to an array of repository data.
   */
  static getRepositories(userName) {
    // Construct the URL for the GitHub API repositories endpoint
    const url = `https://api.github.com/users/${userName}/repos`;
    
    // Use axios.get to make an HTTP GET request to the URL.
    // Axios returns a promise that resolves with the response object.
    // The actual data is typically in response.data.
    return axios.get(url);
  }
}

export default GitClient;