import Navbar from "../components/Navbar";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import jwt from "jwt-decode";
import PersonIcon from '@mui/icons-material/Person';

import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";


const Container = styled("div")(({ theme }) => ({
  background: `linear-gradient(to bottom, ${
    theme.palette.mode === "dark" ? "#1A2027" : "#fff"
  }, ${
    theme.palette.mode === "dark" ? "#111418" : "#f2f2f2"
  })`,
  paddingBottom: theme.spacing(8), // Adjust the vertical spacing here
}));

const Content = styled("div")(({ theme }) => ({
  padding: theme.spacing(2),
  maxWidth: 400,
  margin: "0 auto",
  backgroundColor: theme.palette.background.paper,
  borderRadius: theme.shape.borderRadius,
  boxShadow: theme.shadows[3],
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  textAlign: "center",
  marginTop:"60px"
}));

const AvatarContainer = styled("div")({
  display: "flex",
  alignItems: "center",
  gap: "20px", // Adjust the gap as needed
  marginBottom:"40px",
  marginTop:"20px"
});

export default function Account() {
  const token = sessionStorage.getItem("token");
  const decoded = jwt(token);
  return (
    <ThemeProvider theme={theme}>
    <Container>
      <Navbar />
      <Content>
        <AvatarContainer>
        <PersonIcon style={{width:"100px",height:"100px"}}/>      
          
          <h2>Account Details</h2>
        </AvatarContainer>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              label="Email"
              defaultValue={decoded.sub}
              InputProps={{
                readOnly: true,
              }}
              sx={{ width: "100%", marginBottom: 2 }}
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              href="/ChangePassword"
              variant="contained"
              sx={{ width: "100%" }}
            >
              Change Password
            </Button>
          </Grid>
        </Grid>
      </Content>
    </Container>
    </ThemeProvider>
  );
}
