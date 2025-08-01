import React, { useEffect, useState } from 'react';
import './App.css'; // Import the main CSS file for styling
import GitClient from './GitClient'; // Import the GitClient module

function App() {
  // State to store the fetched repositories
  const [repositories, setRepositories] = useState([]);
  // State to manage loading status
  const [loading, setLoading] = useState(true);
  // State to manage potential errors
  const [error, setError] = useState(null);

  // useEffect hook to perform side effects (like data fetching)
  // when the component mounts. The empty dependency array `[]` ensures
  // this effect runs only once after the initial render.
  useEffect(() => {
    const fetchRepos = async () => {
      try {
        // Call the static getRepositories method from GitClient
        // We'll fetch repositories for 'octocat' as an example, you can change this.
        const response = await GitClient.getRepositories('octocat'); // Using 'octocat' as a default user
        setRepositories(response.data); // Update state with the fetched data
        setLoading(false); // Set loading to false once data is fetched
      } catch (err) {
        console.error("Error fetching repositories:", err);
        setError("Failed to load repositories. Please try again later.");
        setLoading(false); // Set loading to false even on error
      }
    };

    fetchRepos(); // Invoke the async function
  }, []); // Empty dependency array means this runs once on mount

  if (loading) {
    return <div className="loading-message">Loading repositories...</div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  return (
    <div className="App">
      <h1>Git Repositories of User - Octocat</h1>
      {/* Check if repositories array is not empty before mapping */}
      {repositories.length > 0 ? (
        <ul className="repo-list">
          {/* Map over the repositories array and display each repository name */}
          {repositories.map(repo => (
            <li key={repo.id} className="repo-item"> {/* Use repo.id as a unique key */}
              {repo.name}
            </li>
          ))}
        </ul>
      ) : (
        <p className="no-repos-message">No repositories found for this user.</p>
      )}
    </div>
  );
}

export default App;