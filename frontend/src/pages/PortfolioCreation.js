import Navbar from "../components/Navbar";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { Link } from "react-router-dom";
import TextField from '@mui/material/TextField';


export default function Portfolio() {

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
                    hello
                </Grid>
            </Grid>
        </>

    )
}