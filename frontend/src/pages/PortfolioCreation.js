import React, { useState, useEffect } from "react";
import Checkbox from "@mui/material/Checkbox";
import TextField from "@mui/material/TextField";
import Autocomplete, { autocompleteClasses } from "@mui/material/Autocomplete";
import SearchIcon from "@mui/icons-material/Search";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableBody from '@mui/material/TableBody';
import Navbar from '../components/Navbar';


export default function Portfolio() {
    const [selectedEquities, setEquities] = useState([]);

    const equities = [
        { symbol: "TSCDF", name: "Tesco", type: "Equity", region: "United States" },
        { symbol: "APPL", name: "Apple", type: "Equity", region: "United States" },
        { symbol: "AMAZON", name: "Amazon", type: "Equity", region: "Frankfurt" },
        { symbol: "TESLA", name: "TESLA", type: "Equity", region: "Frankfurt" },
    ];

    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;

    const handleChange = (event, value) => {
        setEquities(value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
    };

    return (
        <>
            <ThemeProvider theme={theme}>
                <Navbar></Navbar>
                <Box sx={{ mt: 5, mb: 2, mx: 10 }}>
                    <form onSubmit={handleSubmit}>
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
                                    <Typography>Portfolio Name</Typography>
                                    <TextField id="outlined-basic" defaultValue="Portfolio 1" variant="outlined" style={{ width: "80%" }} />
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography>Capital</Typography>
                                    <TextField id="outlined-basic" defaultValue={0} variant="outlined" style={{ width: "80%" }} type="number" />
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography>Add stocks to portfolio</Typography>
                                    <Autocomplete
                                        onChange={(event, value) => handleChange(event, value)}
                                        multiple
                                        popupIcon={<SearchIcon />}
                                        id="checkboxes-tags-demo"
                                        options={equities}
                                        disableCloseOnSelect
                                        sx={{
                                            width: 300,
                                            [`& .${autocompleteClasses.popupIndicator}`]: {
                                                transform: "none",
                                            },
                                        }}
                                        style={{ width: "80%" }}
                                        getOptionLabel={(option) => `${option.symbol} (${option.name})`}
                                        renderOption={(props, option, { selected }) => (
                                            <li {...props}>
                                                <Checkbox
                                                    icon={icon}
                                                    checkedIcon={checkedIcon}
                                                    style={{ marginRight: 8 }}
                                                    checked={selected}
                                                    value={option}
                                                />
                                                {option.symbol} {option.name}
                                            </li>
                                        )}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography>Description</Typography>
                                    <TextField id="outlined-basic" variant="outlined"
                                        multiline
                                        rows={4}
                                        maxRows={Infinity}
                                        style={{ width: "80%" }}
                                    />
                                </Grid>


                                <Grid item xs={6}>

                                </Grid>
                                {selectedEquities.length > 0 && (
                                    <>
                                        <Grid item xs={12}>
                                            <Typography>Added stocks</Typography>
                                            <TableContainer component={Paper}>
                                                <Table>
                                                    <TableBody>
                                                        {selectedEquities.map((equity) => (
                                                            <TableRow key={equity.name}>
                                                                <TableCell><b>{equity.name}</b></TableCell>
                                                                <TableCell><TextField type="number"
                                                                    label="Quantity"
                                                                    InputProps={{ inputProps: { min: 1 } }}
                                                                    onChange={(event) =>
                                                                        event.target.value < 1
                                                                            ? (event.target.value = 1)
                                                                            : event.target.value
                                                                    }
                                                                ></TextField></TableCell>
                                                                <TableCell><TextField
                                                                    label="Capital"
                                                                    type="number"
                                                                    InputProps={{ inputProps: { min: 1 } }}
                                                                    onChange={(event) =>
                                                                        event.target.value < 1
                                                                            ? (event.target.value = 1)
                                                                            : event.target.value
                                                                    }
                                                                ></TextField></TableCell>
                                                            </TableRow>
                                                        ))}
                                                    </TableBody>
                                                </Table>
                                            </TableContainer>
                                        </Grid>
                                        <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center' }}>
                                            {selectedEquities.length > 0 && (
                                                <div style={{ textAlign: 'center' }}>
                                                    <Button variant="outlined" style={{ fontWeight: "bold", display: "inline-block" }} type="submit">
                                                        Create Portfolio
                                                    </Button>
                                                </div>
                                            )}
                                        </Grid>
                                    </>
                                )}
                            </Grid>
                        </Grid>
                    </form>
                </Box>
            </ThemeProvider >

        </>

    )
}