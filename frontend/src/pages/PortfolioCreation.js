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
import axios from "axios";
const Swal = require("sweetalert2");


export default function Portfolio() {
    const [selectedEquities, setEquities] = useState([]);
    const [allEquities, setAllEquities] = useState([]);
    const [loadedEquities, setLoadedEquities] = useState([]);
    const [selectedTickers, setSelectedTickers] = useState([]);
    const [portfolioName, setPortfolioName] = useState('');
    const [portfolioDescription, setPortfolioDescription] = useState('');
    const [portfolioCapital, setPortfolioCapital] = useState('');

    let totalProportion = 0;

    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;

    const handleChange = (event, value) => {
        setEquities(value);
    };

    const handleAutocompleteSearch = (event, value) => {
        const filteredOptions = allEquities.filter(option => (
            option.symbol.toLowerCase().includes(value.toLowerCase()) ||
            option.name.toLowerCase().includes(value.toLowerCase())
        ));
        setLoadedEquities(filteredOptions.slice(0, 10));
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        const bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpc2hhMDkwOTFAZ21haWwuY29tIiwiZXhwIjoxOTEzNzc3Mzg2fQ.PzTcrkdvWVr4DlYwDC0jOS5D--Jm1vVyL7q6dK6dO-I";

        const headers = {
            Authorization: `Bearer ${bearerToken}`
        };
        let errors = [];
        for (let i = 0; i < selectedTickers.length; i++) {
            totalProportion += selectedTickers[i].proportion;
        }
        if (totalProportion === 1 && portfolioCapital != '' && portfolioDescription != '' && portfolioName != '' && selectedTickers.length != 0) {
            axios.post(`/api/portfolio`, {
                userId: 1,
                name: portfolioName,
                description: portfolioDescription,
                capital: portfolioCapital
            }, { headers })
                .then(res => {
                    console.log(res);
                    let portfolioId = res.data["portfolioId"];
                    if (portfolioId !== 0) {
                        axios.post(`/api/portfolio/${portfolioId}`, selectedTickers, { headers })
                            .then(res => {
                                console.log(res);
                                window.location.href = `/Portfolio/${portfolioId}`;

                            })
                            .catch(err => {
                                console.log(err);
                            });

                    }
                })
                .catch(err => {
                    console.log(err);
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Unable to create portfolio",
                    });
                });
        }
        else {
            let errorsList = '';

            if (totalProportion != 1) {
                errorsList += "<li>Stocks' proportions do not add up to 1</li>";
            }
            if (portfolioCapital == '') {
                errorsList += "<li>Portfolio capital empty</li>";
            }
            if (portfolioDescription == '') {
                errorsList += "<li>Portfolio description empty</li>";
            }
            if (portfolioName == '') {
                errorsList += "<li>Portfolio name empty</li>";
            }
            if (selectedTickers.length == 0) {
                errorsList += "<li>No stocks added</li>";
            }

            if (errorsList !== '') {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    html: `<ul style="text-align: left;font-family: "Inter";">${errorsList}</ul>`,
                });
            }
        }

    };

    const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpc2hhMDkwOTFAZ21haWwuY29tIiwiZXhwIjoxOTE0NzQxMzAyfQ.ON26Ln93QLNjY2xQBJaqXSNCIOEaVR8URuBorxcpujk';

    const getData = async () => {
        try {
            const res = await axios.get("/api/stockwrapper/getStockListing", {
                headers: { Authorization: `Bearer ${token}` }
            })
            setAllEquities(res.data);
            setLoadedEquities(res.data.slice(0, 10));
        } catch (error) {
            console.log("API unavailable")
        }
    };

    const handleTickers = (tickerObj) => {
        const updatedArray = selectedTickers.map(item => {
            if (item.ticker === tickerObj.ticker) {
                return { ...item, proportion: parseFloat(tickerObj.proportion) };
            }
            return item;
        });

        const existingObject = updatedArray.find(item => item.ticker === tickerObj.ticker);
        if (!existingObject) {
            updatedArray.push({ ticker: tickerObj.ticker, proportion: parseFloat(tickerObj.proportion) });
        }

        setSelectedTickers(updatedArray);
    }

    useEffect(() => {
        getData();
    }, []);




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
                                    <TextField id="outlined-basic" variant="outlined" style={{ width: "80%" }} onChange={(event) => setPortfolioName(event.target.value)} />
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography>Capital</Typography>
                                    <TextField id="outlined-basic" variant="outlined" style={{ width: "80%" }} type="number" onChange={(event) => setPortfolioCapital(event.target.value)} />
                                </Grid>
                                <Grid item xs={6}>
                                    <Typography>Add stocks to portfolio</Typography>
                                    <Autocomplete
                                        onInputChange={(event, value) => handleAutocompleteSearch(event, value)}
                                        onChange={(event, value) => handleChange(event, value)}
                                        multiple
                                        popupIcon={<SearchIcon />}
                                        id="checkboxes-tags-demo"
                                        options={loadedEquities}
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
                                        style={{ width: "80%" }}
                                        onChange={(event) => setPortfolioDescription(event.target.value)}
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
                                                                <TableCell><TextField
                                                                    step=".01"
                                                                    label="Proportion"
                                                                    id={equity.symbol}
                                                                    onChange={(event) =>
                                                                        handleTickers({ ticker: event.target.id, proportion: event.target.value })
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
