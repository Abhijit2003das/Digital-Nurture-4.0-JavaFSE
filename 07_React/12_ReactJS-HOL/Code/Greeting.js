import React from 'react';
import UserGreeting from './UserGreeting';
import GuestGreeting from './GuestGreeting';

function Greeting(props) {
  const isLoggedIn = props.isLoggedIn; // Get the isLoggedIn status from props
  if (isLoggedIn) {
    return <UserGreeting />; // Render UserGreeting if logged in
  }
  return <GuestGreeting />; // Render GuestGreeting if not logged in
}

export default Greeting;