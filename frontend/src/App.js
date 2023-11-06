import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';

// Pages
import Home from './pages/Home';
import NoPage from './pages/NoPage';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import Portfolio from './pages/Portfolio';
import PortfolioList from './pages/PortfolioList';
import PortfolioCreation from './pages/PortfolioCreation';
import PortfolioEditing from './pages/PortfolioEditing';

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Default Route */}
          <Route index element={<SignIn />} />
          <Route path="/SignIn" element={<SignIn />} />
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/Home" element={<Home />} />
          <Route path="/Portfolio" element={<PortfolioList />} />
          <Route path="/Portfolio/:id" element={<Portfolio />} />
          <Route path="/PortfolioCreation" element={<PortfolioCreation />} />
          <Route path="/Portfolio/:id/Edit" element={<PortfolioEditing />} />
          {/* If a non-existent route is defined, redirect to */}
          <Route path="*" element={<NoPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
