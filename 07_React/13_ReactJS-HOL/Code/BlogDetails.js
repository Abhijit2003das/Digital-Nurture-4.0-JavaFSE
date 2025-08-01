import React from 'react';

function BlogDetails() {
  const blogInfo = {
    title: "React Learning",
    author: "Stephen Biz",
    description: "Welcome to learning React!",
    section: "Installation",
    installer: "Schewzdenier",
    installGuide: "You can install React from npm."
  };

  // Example of conditional rendering using a logical && operator
  // This section will only render if blogInfo.title exists
  const blogTitleSection = blogInfo.title && (
    <div className="blog-section">
      <h3>{blogInfo.title}</h3>
      <p>By: {blogInfo.author}</p>
      <p>{blogInfo.description}</p>
    </div>
  );

  // Example of conditional rendering using a ternary operator
  const installationSection = blogInfo.section ? (
    <div className="blog-section">
      <h4>{blogInfo.section}</h4>
      <p>Installer: {blogInfo.installer}</p>
      <p>{blogInfo.installGuide}</p>
    </div>
  ) : (
    <p>No installation details available.</p>
  );

  return (
    <div className="detail-card">
      <h2 className="card-title">Blog Details</h2>
      {blogTitleSection}
      {installationSection}
    </div>
  );
}

export default BlogDetails;