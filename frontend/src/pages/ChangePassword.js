import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import axios from "axios";
import { grey } from '@mui/material/colors';
import jwt from "jwt-decode";
import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";

const Swal = require("sweetalert2");

function Copyright(props) {
  
  return (
    <Typography variant="body2" color="textSecondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        APPA
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

// const defaultTheme = createTheme();

export default function ChangePassword() {
  const token = sessionStorage.getItem("token");
  const decoded = jwt(token);
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const changePasswordRequest = {
      email: data.get("email"),
      passwordCurrent: data.get("passwordCurrent"),
      passwordNew: data.get("passwordNew"),
      passwordConfirm: data.get("passwordConfirm"),
    };

    axios
      .post(`http://localhost:8080/api/user/change-password`, changePasswordRequest)
      .then((res) => {
        const responseData = res.data;

        Swal.fire({
          title: "Your Password has been changed!",
          text: "Use your new password the next time you log in!",
          icon: 'success',
          showCloseButton: true,
          focusConfirm: true,
          confirmButtonText: 'OK',
        }).then((result) => {
          // You can perform additional actions after showing the success message.
          window.location.href = "/Account";
        });
      })
      .catch(function (error) {
        if (error.response) {
          const errorMessage = error.response.data.message;
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: errorMessage,
          });
        } else {
          console.log(error.message);
        }
      });
  };

  return (
    <ThemeProvider theme={theme}>
      <Grid container component="main" sx={{ height: '100vh' }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundImage: 'url(https://source.unsplash.com/random?wallpapers)',
            backgroundRepeat: 'no-repeat',
            backgroundColor: (t) =>
              t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
            backgroundSize: 'cover',
            backgroundPosition: 'center',
          }}
        />
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Change Password
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                value={decoded.sub}
                autoComplete="email"
                autoFocus
                inputProps={{
                  readOnly: true,
                }}
                sx={{ color: grey[600] }}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="passwordCurrent"
                label="Current Password"
                type="password"
                id="passwordCurrent"
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="passwordNew"
                label="New Password"
                type="password"
                id="passwordNew"
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="passwordConfirm"
                label="Confirm Password"
                type="password"
                id="passwordConfirm"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Change Password
              </Button>
              <Button
                href="/Account"
                fullWidth
                variant="outlined"
                sx={{ mt: 3, mb: 2 }}
              >
                Back
              </Button>
              <Copyright sx={{ mt: 5 }} />
            </Box>
          </Box>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
