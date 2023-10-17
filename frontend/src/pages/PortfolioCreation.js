import Navbar from "../components/Navbar";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { Link } from "react-router-dom";
import TextField from '@mui/material/TextField';
import Autocomplete, { autocompleteClasses } from "@mui/material/Autocomplete";
import Chip from '@mui/material/Chip';
import React, { useCallback, useState, useEffect } from "react";
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import Checkbox from '@mui/material/Checkbox';
import SearchIcon from '@mui/icons-material/Search';

export default function Portfolio() {
    const [selectedEquities, setEquities] = useState([]);
    const [showEquities, setShowEquities] = useState([]);

    const equities = [
        { symbol: "TSCO.LON", name: "Tesco PLC", type: "Equity", region: "United Kingdom" },
        { symbol: "TSCDF", name: "Tesco PLC", type: "Equity", region: "United States" },
        { symbol: "TSCDY", name: "Tesco PLC", type: "Equity", region: "United States" },
        { symbol: "TCO2.FRK", name: "TESCO PLC ADR/1 LS-05", type: "Equity", region: "Frankfurt" },
        { symbol: "TCO0.FRK", name: "TESCO PLC LS-0633333", type: "Equity", region: "Frankfurt" },
    ];

    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;

    const handleChange = (e, value) => {
        if (e.target.checked) {
            setEquities([...selectedEquities, e.target.value]);
        } else {
            setEquities(selectedEquities.filter((id) => id !== e.target.value));
        }
    };


    useEffect(() => {
        if (selectedEquities.length === 0) {
            setShowEquities("");
        } else {
            setShowEquities(selectedEquities.join(","));
        }
    }, [selectedEquities]);

    return (
        <>
            <Navbar></Navbar>
            <h2>Create a portfolio</h2>
            <Grid container>
                <Grid item xs={6}>
                    Portfolio Name
                    <TextField id="outlined-basic" value="Portfolio 1" variant="outlined" />
                </Grid>
                <Grid item xs={6}>
                    Add stocks to portfolio
                    <Autocomplete
                        multiple
                        popupIcon={<SearchIcon />}
                        id="checkboxes-tags-demo"
                        options={equities}
                        disableCloseOnSelect
                        sx={{
                            width: 300,
                            [`& .${autocompleteClasses.popupIndicator}`]: {
                                transform: "none"
                            }
                        }}

                        getOptionLabel={(option) => `${option.symbol} (${option.name})`}
                        renderOption={(props, option, { selected }) => (
                            <li {...props}>
                                <Checkbox
                                    onClick={handleChange}
                                    icon={icon}
                                    checkedIcon={checkedIcon}
                                    style={{ marginRight: 8 }}
                                    checked={selected}
                                    value={option.symbol}
                                />
                                {option.symbol} {option.name}
                            </li>
                        )}
                        style={{ width: 500 }}
                        renderInput={(params) => (
                            <TextField {...params} />
                        )}
                    />
                </Grid>
                <Grid item xs={6}>
                    Description
                    <TextField id="outlined-basic" variant="outlined"
                        multiline
                        rows={4}
                        maxRows={Infinity}
                    />
                </Grid>
                <Grid item xs={6}>
                    {showEquities}
                </Grid>

            </Grid>
        </>

    )
}