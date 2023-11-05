import Navbar from "../components/Navbar";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Button from '@mui/material/Button';
import Divider from "@mui/material/Divider";
import TextField from "@mui/material/TextField";
import { styled } from "@mui/material/styles";

export default function Account() {
  
  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: "center",
    color: theme.palette.text.secondary,
  }));

  return (
    <div>
      
      <Navbar />

      <Grid
        container
        direction="column"
        justifyContent="center"
        alignItems="center"
        spacing={3}
      >
        <Grid item xs={12}>
          <div
            style={{
              position: "relative",
              display: "block",
              top: "27px",
              backgroundColor: "#c9392e",
              width: "100%",
              height: "150px",
            }}
          >
            <Grid
              container
              direction="column"
              justifyContent="center"
              alignItems="center"
              spacing={3}
            >
              <Grid item xs={12}>
                <div
                  style={{
                    position: "relative",
                    display: "block",
                    top: "27px",
                    backgroundColor: "#c9392e",
                    width: "100%",
                    height: "150px",
                  }}
                >
                  <h2
                    style={{
                      color: "white",
                      position: "absolute",
                      left: "6rem",
                      bottom: "10px",
                    }}
                  >
                    Account Details
                  </h2>

                  <Avatar
                    alt="Profile Pic"
                    src="profile-pic.jpg"
                    sx={{
                      width: "100px",
                      height: "100px",
                      position: "absolute",
                      right: "-2rem",
                      margin: "30px 0px 30px 0",
                    }}
                  />
                </div>
              </Grid>
            </Grid>
          </div>
        </Grid>
      </Grid>

      <Box sx={{ flexGrow: 1, margin: "100px 100px 20px 100px" }}>
        <Grid container spacing={2}>
          <Grid item xs={12} md={12}>
            <TextField
              label="First Name"
              defaultValue="Jennifer"
              InputProps={{
                readOnly: true,
              }}
              sx={{ width: "100%", marginBottom: "20px" }}
            />
            <TextField
              label="Last Name"
              defaultValue="Williams"
              InputProps={{
                readOnly: true,
              }}
              sx={{ width: "100%" }}
            />
          </Grid>

          <Grid item xs={6} md={12}>
            <TextField
              label="Email"
              defaultValue="jenniferwilliams89@gmail.com"
              InputProps={{
                readOnly: true,
              }}
              sx={{ width: "100%", marginBottom: "20px" }}
            />
          </Grid>

          <Grid item xs={6} md={12}>
            <Button
                href="/ChangePassword"
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Change Password
              </Button>
          </Grid>
        </Grid>
      </Box>
    </div>
  );
}
