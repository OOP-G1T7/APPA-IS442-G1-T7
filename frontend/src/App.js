import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import logo from './logo.svg';

// Pages
import Home from './pages/Home';
import NoPage from './pages/NoPage';
import SignIn from './pages/SignIn';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Default Route */}
          <Route index element={<SignIn/>} />
          <Route path="/SignIn" element={<SignIn/>} />
          <Route path="/Home" element={<Home/>} />
          {/* If a non-existent route is defined, redirect to */}
          <Route path="*" element={<NoPage/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
