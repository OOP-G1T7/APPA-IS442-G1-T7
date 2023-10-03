import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Portfolio from './pages/Portfolio';

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
          <Route path="/Portfolio" element={<Portfolio/>} />
          {/* If a non-existent route is defined, redirect to */}
          <Route path="*" element={<NoPage/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
