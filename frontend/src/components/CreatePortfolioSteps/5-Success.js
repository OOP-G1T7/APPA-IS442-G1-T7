import React, { Component } from "react";
import { ThemeProvider } from "@emotion/react";
import theme from "./Theme";
import { Typography } from "@mui/material";
import Navbar from "../Navbar";

export class Success extends Component {
    render() {
        return (
            <ThemeProvider theme={theme}>
                <Navbar></Navbar>
                <Typography> Success!</Typography>
            </ThemeProvider>
        );
    }
}
export default Success;