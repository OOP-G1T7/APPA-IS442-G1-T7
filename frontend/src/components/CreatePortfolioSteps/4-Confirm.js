import React, { Component } from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import { ThemeProvider } from "@emotion/react";
import theme from "./Theme";
import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableBody from '@mui/material/TableBody';
import Navbar from '../Navbar';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import Divider from '@mui/material/Divider';

export class Confirm extends Component {
    render() {
        const {
            values: { userId, portfolioName, portfolioDescription, stocks, stockQuantities }, continues, back } = this.props;
        return (
            <ThemeProvider theme={theme}>
                <Navbar></Navbar>
                <Box sx={{ mt: 5, mb: 2, mx: 10 }}>
                    <Grid container spacing={3} direction="column" justifyContent="center">
                        <Grid item xs={12} container direction="row" justifyContent="space-between">
                            <Grid item xs={5}>
                                <Typography variant="h4" gutterBottom>
                                    Create a Portfolio
                                </Typography>
                            </Grid>
                        </Grid>
                        <Grid item minHeight={100} container spacing={2} justifyContent="space-between">
                            <Grid item xs={6}>
                                <Typography><b>Portfolio Name</b></Typography>
                                <Typography>{portfolioName}</Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography><b>Portfolio Description</b></Typography>
                                <Typography>{portfolioDescription}</Typography>
                            </Grid>
                            <Grid item xs={6}>

                            </Grid>
                            <Divider />
                            {stockQuantities.length > 0 && (
                                <>
                                    <Grid item xs={12}>
                                        <Typography><b>Added stocks</b></Typography>
                                        <TableContainer component={Paper}>
                                            <Table>
                                                <TableBody>
                                                    {stockQuantities.map((equity) => (
                                                        <TableRow key={equity.equity.name}>
                                                            <TableCell><b>{equity.equity.name}</b></TableCell>
                                                            <TableCell><Typography>Quantity: </Typography>{equity.quantity}</TableCell>
                                                        </TableRow>
                                                    ))}
                                                </TableBody>
                                            </Table>
                                        </TableContainer>
                                    </Grid>
                                    <Grid container item xs={12} style={{
                                        position: 'fixed',
                                    }}>

                                        <div style={{
                                            position: 'fixed',
                                            bottom: 100,
                                            left: 100,
                                        }}>
                                            {stockQuantities.length !== 0 && (
                                                <>
                                                    <Button variant="contained">
                                                        <ArrowBackIcon
                                                            onClick={back}
                                                        />
                                                        Back
                                                    </Button>
                                                </>
                                            )}
                                        </div>
                                        <div style={{
                                            position: 'fixed',
                                            bottom: 100,
                                            right: 100,
                                        }}>
                                            {stockQuantities.length !== 0 && (
                                                <>
                                                    <Button variant="contained">
                                                        Next
                                                        <ArrowForwardIcon
                                                            onClick={continues}
                                                        />
                                                    </Button>
                                                </>
                                            )}
                                        </div>

                                    </Grid>
                                </>
                            )}
                        </Grid>
                    </Grid>
                </Box>
            </ThemeProvider >
        );
    }
}
export default Confirm;