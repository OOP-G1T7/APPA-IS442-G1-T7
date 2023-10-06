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
                <Grid container spacing={2} paddingLeft={2}>
                    <Grid item xs={8}>
                    </Grid>
                    <Grid item xs={4}>
                    </Grid>
                    <Grid item xs={4}>
                    </Grid>
                    <Grid item xs={8}>
                    </Grid>
                </Grid>
                <h2>Your Portfolios</h2>
                <p>You have no existing portfolios</p>
                <Button onClick={handleClick} style={{ textTransform: 'none' }}>Create a new portfolio</Button>
                {/* <Button onClick={handleClick} color='button' style={{ textTransform: 'none' }}>Create a new portfolio</Button> */}
            </ThemeProvider>

        </>

    )
}