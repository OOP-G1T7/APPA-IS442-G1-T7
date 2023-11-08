import React, { Component } from "react";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import { ThemeProvider } from "@emotion/react";
import theme from "./Theme";
import Navbar from '../Navbar';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import axios from 'axios';

export class PortfolioDetails extends Component {
    handleSubmit = event => {
        event.preventDefault();
        const portfolio = {
            userId: 1,
            name: this.props.values.portfolioName,
            description: this.props.values.portfolioDescription
        };

        console.log(portfolio);
        // axios.post(`http://localhost:8080/api/portfolio`, { portfolio })
        //     .then(res => {
        //         console.log(res);
        //         console.log(res.data);
        //         this.props.continues();
        //     })

        this.props.continues();

    }

    render() {
        const { values, handleChange, continues } = this.props;


        return (
            <>
                <ThemeProvider theme={theme}>
                    <Navbar></Navbar>
                    <form onSubmit={this.handleSubmit}>
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
                                        <Typography>Portfolio Name</Typography>
                                        <TextField id="outlined-basic" variant="outlined" style={{ width: "80%" }} onChange={handleChange("portfolioName")} />
                                    </Grid>
                                    <Grid item xs={6}>
                                        <Typography>Description</Typography>
                                        <TextField id="outlined-basic" variant="outlined"
                                            multiline
                                            maxRows={Infinity}
                                            style={{ width: "80%" }}
                                            onChange={handleChange("portfolioDescription")}
                                        />
                                    </Grid>
                                    <Grid container item xs={12} style={{
                                        position: 'fixed',
                                        bottom: 10,
                                        right: 10
                                    }}>
                                        <div style={{
                                            position: 'fixed',
                                            bottom: 100,
                                            right: 100,
                                        }}>
                                            {values.portfolioName.length !== 0 && (
                                                <Button type="submit" variant="contained">
                                                    Next
                                                    <ArrowForwardIcon />
                                                </Button>
                                            )}
                                        </div>

                                    </Grid>

                                </Grid>

                            </Grid>

                        </Box>
                    </form>

                </ThemeProvider >

            </>
        );
    }
}

export default PortfolioDetails;