import React from 'react';

// Data for courses
const coursesData = [
  { id: 201, cname: 'Angular', date: '4/5/2021' },
  { id: 202, cname: 'React', date: '6/3/2020' },
];

function CourseDetails() {
  // Example of conditional rendering using an if-else statement
  let courseContent;
  if (coursesData && coursesData.length > 0) {
    courseContent = (
      <ul className="details-list">
        {coursesData.map((course) => (
          <li key={course.id} className="list-item">
            <h3>{course.cname}</h3>
            <p>Date: {course.date}</p>
          </li>
        ))}
      </ul>
    );
  } else {
    courseContent = <p>No course details available.</p>;
  }

  return (
    <div className="detail-card">
      <h2 className="card-title">Course Details</h2>
      {courseContent}
    </div>
  );
}

export default CourseDetails;