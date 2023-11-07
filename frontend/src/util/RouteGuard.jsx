import React from "react";
import { Navigate } from "react-router-dom";
import jwt from "jwt-decode";

const isTokenValid = (token) => {
  try {
    // Verify the token and check for expiration
    const decoded = jwt(token);
    if (!decoded) {
      return false; // Token is invalid (couldn't be decoded)
    }

    // Check token expiration
    const currentTime = Date.now() / 1000; // Convert to seconds
    if (decoded.exp && decoded.exp < currentTime) {
      return false; // Token has expired
    }

    // Add more custom checks as needed (e.g., issuer, audience, custom claims)

    return true; // Token is valid
  } catch (error) {
    return false; // An error occurred during validation
  }
};

function RouteGuard({ element }) {
  const token = sessionStorage.getItem("token");
  if (token && isTokenValid(token)) {
    return element;
  } else {
    return <Navigate to="/" replace />;
  }
}

export default RouteGuard;
