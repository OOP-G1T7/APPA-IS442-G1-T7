import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";

// Pages
import Home from "./pages/Home";
import NoPage from "./pages/NoPage";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import Portfolio from "./pages/Portfolio";
import PortfolioList from "./pages/PortfolioList";
import PortfolioCreation from "./pages/PortfolioCreation";
import RouteGuard from "./util/RouteGuard";
import TestAuth from "./pages/TestAuth";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Default Route */}
          <Route index element={<SignIn />} />
          <Route path="/SignIn" element={<SignIn />} />
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/Home" element={<RouteGuard element={<Home />} />} />
          <Route
            path="/Portfolio"
            element={<RouteGuard element={<PortfolioList />} />}
          />
          <Route
            path="/Portfolio/:id"
            element={<RouteGuard element={<Portfolio />} />}
          />
          <Route
            path="/PortfolioCreation"
            element={<RouteGuard element={<PortfolioCreation />} />}
          />
          {/* If a non-existent route is defined, redirect to */}
          <Route path="*" element={<NoPage />} />
          {/* TEST AUTH PAGE */}
          <Route
            path="test-auth"
            element={<RouteGuard element={<TestAuth />} />}
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
