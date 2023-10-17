import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { ThemeProvider } from '@emotion/react';
import theme from "../Theme";
import { useNavigate } from "react-router-dom";
import Grid from '@mui/material/Grid';


export default function Navbar() {
    let navigate = useNavigate();

    function redirectPortfolio() {
        navigate("/Portfolio");
    }
    function redirectHome() {
        navigate("/Home");
    }
    function redirectLogout() {
        navigate("/Logout");
    }
    return (
        <ThemeProvider theme={theme}>
            <Box>
                <AppBar position="static">
                    <Grid container spacing={2}>
                        <Grid item>
                            <Typography><Button onClick={redirectHome} color='buttonFontColor' style={{ textTransform: 'none' }}>Goldman Sachs</Button></Typography>
                        </Grid>
                        <Grid item>
                            <Typography><Button onClick={redirectPortfolio} color='buttonFontColor' style={{ textTransform: 'none' }}>Portfolio</Button></Typography>
                        </Grid>
                        <Grid item>
                            <Typography><Button onClick={redirectLogout} color='buttonFontColor' style={{ textTransform: 'none' }}>Logout</Button></Typography>
                        </Grid>
                    </Grid>
                </AppBar>
            </Box>
        </ThemeProvider>
    );
}