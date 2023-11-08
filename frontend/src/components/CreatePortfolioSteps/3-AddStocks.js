import React, { Component } from "react";
import TextField from "@mui/material/TextField";
import Autocomplete, { autocompleteClasses } from "@mui/material/Autocomplete";
import SearchIcon from "@mui/icons-material/Search";
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

export class AddStocks extends Component {


    render() {
        const equities = [
            { symbol: "TSCO.LON", name: "Tesco PLC", type: "Equity", region: "United Kingdom" },
            { symbol: "TSCDF", name: "Tesco PLC", type: "Equity", region: "United States" },
            { symbol: "TSCDY", name: "Tesco PLC", type: "Equity", region: "United States" },
            { symbol: "TCO2.FRK", name: "TESCO PLC ADR/1 LS-05", type: "Equity", region: "Frankfurt" },
            { symbol: "TCO0.FRK", name: "TESCO PLC LS-0633333", type: "Equity", region: "Frankfurt" },
        ];
        const { values, addStocks, handleEquityQuantities, continues, back } = this.props;
        return (
            <>
                <ThemeProvider theme={theme}>
                    <Navbar></Navbar>
                    <Box sx={{ mt: 5, mb: 2, mx: 10 }} alignItems="center"
                        justifyContent="center">
                        <Grid container spacing={3} direction="column" justifyContent="center">
                            <Grid item minHeight={100} container spacing={2} justifyContent="space-between">
                                <Grid item xs={6}>
                                    <Typography>Add stocks to portfolio</Typography>
                                    <Autocomplete
                                        multiple
                                        popupIcon={<SearchIcon />}
                                        id="tags-standard"
                                        sx={{
                                            width: 300,
                                            [`& .${autocompleteClasses.popupIndicator}`]: {
                                                transform: "none",
                                            },
                                        }}
                                        onChange={(event, value) => addStocks(event, value)}
                                        options={equities}
                                        getOptionLabel={(option) => `${option.symbol} (${option.name})`}
                                        renderInput={(params) => (
                                            <TextField
                                                {...params}
                                            />
                                        )}
                                    />
                                </Grid>

                                {values.stocks.length > 0 && (
                                    <>
                                        <Grid item xs={6}>
                                            <Typography>Added stocks</Typography>
                                            <TableContainer component={Paper}>
                                                <Table>
                                                    <TableBody>
                                                        {values.stocks.map((equity) => (
                                                            <TableRow key={equity.name}>
                                                                <TableCell><b>{equity.name}</b></TableCell>
                                                                <TableCell><TextField type="number"
                                                                    label="Quantity"
                                                                    id={`quantity-${equity.name}`} // Unique id for each TextField
                                                                    inputProps={{ min: 0 }} onChange={(event) => {
                                                                        const value = event.target.value;
                                                                        if (value > 0) {
                                                                            handleEquityQuantities(value, equity);
                                                                        }
                                                                    }}

                                                                ></TextField></TableCell>
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
                                                {values.portfolioName.length !== 0 && (
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
                                                {values.portfolioName.length !== 0 && (
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

            </>
        );
    }
}

export default AddStocks;