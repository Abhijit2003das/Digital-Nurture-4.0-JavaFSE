import React from 'react';

// Define the Post class directly within this file for simplicity,
// or keep it in a separate Post.js if preferred.
class Post {
  constructor(id, title, body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }
}

class Posts extends React.Component {
  constructor(props) {
    super(props);
    // Initialize the component's state
    this.state = {
      posts: [], // Array to store the fetched blog posts
      error: null, // To store any error objects
      showErrorModal: false // Boolean to control the visibility of the error modal
    };
  }

  /**
   * Asynchronously fetches blog posts from the JSONPlaceholder API.
   * Updates the component's state with the fetched posts or an error.
   */
  async loadPosts() {
    try {
      // Make a GET request to the posts API endpoint
      const response = await fetch('https://jsonplaceholder.typicode.com/posts');

      // Check if the HTTP response was successful
      if (!response.ok) {
        // If not successful, throw an error with the status
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // Parse the JSON response
      const data = await response.json();

      // Map the raw data to instances of the Post class
      const posts = data.map(item => new Post(item.id, item.title, item.body));

      // Update the state with the fetched posts
      this.setState({ posts: posts });
    } catch (error) {
      // Catch any errors during the fetch operation
      console.error("Failed to fetch posts:", error);
      // Update state to show the error modal and store the error
      this.setState({ error: error, showErrorModal: true });
    }
  }

  /**
   * Lifecycle method: Called immediately after a component is mounted (inserted into the tree).
   * This is a good place to initiate network requests.
   */
  componentDidMount() {
    console.log("Posts component did mount. Fetching posts...");
    this.loadPosts(); // Call the method to load posts
  }

  /**
   * Lifecycle method: Catches errors that occur in child components during rendering,
   * in lifecycle methods, and in constructors.
   *
   * @param {Error} error - The error that was thrown.
   * @param {Object} info - An object with a componentStack key containing information
   * about which component threw the error.
   */
  componentDidCatch(error, info) {
    // Log the error for debugging purposes
    console.error("Error caught by componentDidCatch:", error, info);
    // Update the state to display the error message to the user
    this.setState({
      error: error,
      showErrorModal: true
    });
  }

  /**
   * Method to close the custom error modal.
   * Resets the error state and hides the modal.
   */
  closeErrorModal = () => {
    this.setState({ showErrorModal: false, error: null });
  };

  /**
   * Lifecycle method: Renders the component's UI.
   */
  render() {
    const { posts, error, showErrorModal } = this.state;

    return (
      <div className="p-4 min-h-screen bg-gray-100 font-inter">
        {/* Tailwind CSS CDN - Ensure this is in your public/index.html or App.js if not globally available */}
        <script src="https://cdn.tailwindcss.com"></script>
        {/* Configure Inter font */}
        <style>
          {`
            @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');
            body {
              font-family: 'Inter', sans-serif;
            }
          `}
        </style>

        <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">Blog Posts</h1>

        {/* Custom Error Modal */}
        {showErrorModal && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center z-50 p-4">
            <div className="bg-white p-8 rounded-xl shadow-2xl max-w-md w-full text-center border border-red-300">
              <h3 className="text-2xl font-semibold text-red-700 mb-4">Oops! An Error Occurred</h3>
              <p className="text-gray-700 mb-6 text-lg">
                {error ? error.message : "An unexpected error occurred while loading posts."}
              </p>
              <button
                onClick={this.closeErrorModal}
                className="bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-6 rounded-lg shadow-lg transition duration-300 ease-in-out transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
              >
                Got It!
              </button>
            </div>
          </div>
        )}

        {/* Loading indicator or posts display */}
        {posts.length === 0 && !error ? (
          <p className="text-center text-gray-600 text-lg mt-10">Loading blog posts...</p>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {posts.map(post => (
              <div key={post.id} className="bg-white p-6 rounded-xl shadow-lg border border-gray-200 hover:shadow-xl transition-shadow duration-300">
                <h2 className="text-xl font-semibold text-gray-900 mb-3 leading-tight">{post.title}</h2>
                <p className="text-gray-700 text-base leading-relaxed">{post.body}</p>
              </div>
            ))}
          </div>
        )}
      </div>
    );
  }
}

export default Posts;