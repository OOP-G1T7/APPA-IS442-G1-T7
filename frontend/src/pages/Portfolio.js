import { ThemeProvider } from "@emotion/react";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { useNavigate } from "react-router-dom";
import theme from "../Theme";
import Navbar from "../components/Navbar";

export default function Portfolio() {
    let navigate = useNavigate();

    function handleClick() {
        navigate("/PortfolioCreation");
    }

    return (
        <>
            <ThemeProvider theme={theme}>
                <Navbar></Navbar>
                <Grid
                    container
                    spacing={0}
                    direction="column"
                    alignItems="center"
                    justifyContent="center"
                >
                    <Grid item xs={8}>
                        <h2>Your Portfolios</h2>
                    </Grid>
                    <Grid item xs={8} minHeight={100}>
                        <p>You have no existing portfolios</p>
                    </Grid>
                    <Grid item xs={8}>
                        <Button onClick={handleClick} style={{ textTransform: 'none' }}>Create a new portfolio</Button>
                    </Grid>
                </Grid>

                {/* <Button onClick={handleClick} color='button' style={{ textTransform: 'none' }}>Create a new portfolio</Button> */}
            </ThemeProvider>

        </>

    )
}