import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";

// Pages
import RouteGuard from "./util/RouteGuard";
import TestAuth from "./pages/TestAuth";

import NoPage from "./pages/NoPage";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import ChangePassword from "./pages/ChangePassword";
import Account from "./pages/Account";
import Portfolio from "./pages/Portfolio";
import PortfolioList from "./pages/PortfolioList";
import PortfolioCreation from "./pages/PortfolioCreation";
import ResetPassword from "./pages/ResetPassword";
import PortfolioEditing from "./pages/PortfolioEditing";
import RequestResetPassword from "./pages/RequestResetPassword";
import AuditLogs from "./pages/AuditLogs";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Default Route */}
          <Route index element={<SignIn />} />
          <Route path="/SignIn" element={<SignIn />} />
          <Route path="/SignUp" element={<SignUp />} />
          <Route
            path="/RequestResetPassword"
            element={<RequestResetPassword />}
          />
          <Route path="/ResetPassword/:uniqueid" element={<ResetPassword />} />
          <Route path="/ChangePassword" element={<ChangePassword />} />
          <Route
            path="/Account"
            element={<RouteGuard element={<Account />} />}
          />
          <Route
            path="/Portfolio/:id"
            element={<RouteGuard element={<Portfolio />} />}
          />
          <Route
            path="/Home"
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
          <Route path="/Portfolio/:id/Edit" element={<PortfolioEditing />} />
          {/* If a non-existent route is defined, redirect to */}
          <Route path="*" element={<NoPage />} />
          {/* TEST AUTH PAGE */}
          <Route
            path="test-auth"
            element={<RouteGuard element={<TestAuth />} />}
          />
          <Route
            path="/AuditLogs"
            element={<RouteGuard element={<AuditLogs />} />}
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
