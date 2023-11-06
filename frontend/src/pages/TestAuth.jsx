import React from "react";
import jwt from "jwt-decode";

function TestAuth() {
  const token = sessionStorage.getItem("token");
  const decoded = jwt(token);
  return (
    <>
      <div>TestAuth</div>
      <p>Email: {decoded.sub}</p>
      <p>User ID: {decoded.jti}</p>
    </>
  );
}

export default TestAuth;
