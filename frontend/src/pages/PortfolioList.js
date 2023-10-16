import { ThemeProvider } from "@emotion/react";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { useNavigate } from "react-router-dom";
import theme from "../Theme";
import Navbar from "../components/Navbar";
import AddIcon from '@mui/icons-material/Add';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { CardActionArea } from '@mui/material';
import Link from '@mui/material/Link';
// import axios from 'axios';

function PortfolioCard(props) {
    return (
        <Card sx={{ width: 400 }} variant="outlined">
            <CardActionArea>
            <Link href={`/Portfolio/${props.portfolio.id}`} underline="none" color='inherit'>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                {props.portfolio.name}
                </Typography>

                <Typography variant="body2" color="text.secondary">
                    <Grid container spacing={1} direction={"column"}>
                        <Grid item>
                        {props.portfolio.description}
                        </Grid>
                        <Grid item container spacing={0} direction="column">
                            {
                                // iterate through stocks
                                Object.keys(props.portfolio.stocks).map((stock) => {
                                    return (
                                        <Grid item xs={6}>
                                        {stock}: {props.portfolio.stocks[stock]}
                                    </Grid>
                                    )
                                })
                            }
                        </Grid>
                    </Grid>
                </Typography>
            </CardContent>
            </Link>
            </CardActionArea>
        </Card>
    );
}

const portfolios = [
    {
        id: 1,
        name: "Portfolio 1",
        description: "This is portfolio 1",
        stocks: {
            "AAPL": 100,
            "TSLA": 200,
            "GOOG": 300
        }
    }
]

export default function PortfolioList() {
    let navigate = useNavigate();

    function handleClick() {
        navigate("/PortfolioCreation");
    }

    // axios.get('http://localhost:5000/api/portfolio')
    //     .then((response) => {
    //         console.log(response.data)
    //     })
    //     .catch((error) => {
    //         console.log(error);
    //     });

    return (
        <>
            <ThemeProvider theme={theme}>
                <Navbar></Navbar>
                <Box sx={{ 
                    mt: 5,
                    mb: 2,
                    mx: 10,
                }}>
                    <Grid
                        container
                        spacing={3}
                        direction="column"
                        // alignItems="center"
                        justifyContent="center"
                    >
                        <Grid item xs={12} container
                            direction="row"
                            justifyContent="space-between"
                        >
                            <Grid item xs={5}>
                                <Typography variant="h4" gutterBottom>Your Portfolios</Typography>
                            </Grid>
                            <Grid item xs={2} style={{display: "flex"}}>
                                <Button variant="outlined" onClick={handleClick} style={{fontWeight: "bold", float: "right", flex: 1, alignSelf: "flex-end"}}>
                                    <AddIcon sx={{ mr: 1 }} />
                                    Create Portfolio
                                </Button>
                            </Grid>
                        </Grid>
                        <Grid item xs={8}>
                            
                        </Grid>
                        <Grid item xs={8} minHeight={100}>
                            { portfolios.length > 0 ?
                                portfolios.map((portfolio) => {
                                    return (
                                        <PortfolioCard portfolio={portfolio}></PortfolioCard>
                                    )
                                })
                                : <Typography variant="body1" color="text.secondary" align="center">You have no existing portfolios</Typography>
                            }
                        </Grid>
                    </Grid>
                </Box>
                {/* <Button onClick={handleClick} color='button' style={{ textTransform: 'none' }}>Create a new portfolio</Button> */}
            </ThemeProvider>

        </>

    )
}