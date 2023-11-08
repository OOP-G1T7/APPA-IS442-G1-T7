import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";
import { useNavigate } from "react-router-dom";
import Grid from "@mui/material/Grid";
import jwt from "jwt-decode";

export default function Navbar() {
  let navigate = useNavigate();

  const token = sessionStorage.getItem("token");
  const decoded = jwt(token);

  // function redirectPortfolio() {
  //     navigate("/Portfolio");
  // }
  function redirectHome() {
    navigate("/Home");
  }
  function redirectLogout() {
    sessionStorage.clear();
    navigate("/SignIn");
  }
  function redirectAccount() {
    navigate("/Account");
  }
  function redirectAuditLogs() {
    navigate("/AuditLogs");
  }

  function redirectGoldmanSachs() {
    window.open("https://www.goldmansachs.com/", { target: "_blank" });
  }
  return (
    <ThemeProvider theme={theme}>
      <Box>
        <AppBar position="static">
          <Grid container spacing={2}>
            <Grid item>
              <Typography>
                <Button
                  onClick={redirectGoldmanSachs}
                  color="buttonFontColor"
                  style={{ textTransform: "none" }}
                >
                  Goldman Sachs
                </Button>
              </Typography>
            </Grid>
            <Grid item>
              <Typography>
                <Button
                  onClick={redirectHome}
                  color="buttonFontColor"
                  style={{ textTransform: "none" }}
                >
                  Home
                </Button>
              </Typography>
            </Grid>
            <Grid item>
              <Typography>
                <Button
                  onClick={redirectAccount}
                  color="buttonFontColor"
                  style={{ textTransform: "none" }}
                >
                  Account
                </Button>
              </Typography>
            </Grid>
            {decoded.aud === "admin" ? (
              <Grid item>
                <Typography>
                  <Button
                    onClick={redirectAuditLogs}
                    color="buttonFontColor"
                    style={{ textTransform: "none" }}
                  >
                    Audit Logs
                  </Button>
                </Typography>
              </Grid>
            ) : (
              <></>
            )}

            <Grid item>
              <Typography>
                <Button
                  onClick={redirectLogout}
                  color="buttonFontColor"
                  style={{ textTransform: "none" }}
                >
                  Logout
                </Button>
              </Typography>
            </Grid>
          </Grid>
        </AppBar>
      </Box>
    </ThemeProvider>
  );
}
