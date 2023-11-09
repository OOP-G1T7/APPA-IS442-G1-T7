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
import axios from 'axios';
import jwt from "jwt-decode";
import { PieChart } from '@mui/x-charts/PieChart';

const colours = [
    "#23254D",
    "#0D4EA6",
    "#48B8F0",
    "#279C9C",
    "#077D55",
    "#D9A514",
    "#E86427",
    "#AC71F0"
]

function PortfolioCard(props) {
    const { portfolio } = props;
    return (
        <Card variant="outlined">
            <CardActionArea>
            <Link href={`/Portfolio/${portfolio.portfolioId}`} underline="none" color='inherit'>
            <CardContent>
                <Grid container spacing={1} direction="row" justifyContent="space-between">
                    <Grid item xs sm={6}>
                        <Typography gutterBottom variant="h5" component="div">
                        {portfolio.name}
                        </Typography>

                        <Typography variant="body2" color="text.secondary">
                            {portfolio.description}
                        </Typography>
                    </Grid>

                    <Grid item xs sm={4}>
                        <PieChart
                            colors={colours}
                            margin={{top: 0, bottom: 0, left: 10, right:0}}
                            series={[
                                {
                                    data: portfolio.stocks.map((stock, key) => {
                                            return { 
                                                id: key,
                                                value: stock.proportion,
                                                label: `${stock.stockPk.ticker} (%)`,
                                            }
                                        })
                                },
                            ]}
                            slotProps={{
                                legend: {
                                    direction: 'row',
                                    labelStyle: {
                                        fontSize: 0,
                                        fill: 'black',
                                    },
                                    itemMarkWidth: 0,
                                    itemMarkHeight: 0
                                },
                            }}
                            height={100}
                        />
                    </Grid>
                </Grid>
            </CardContent>
            </Link>
            </CardActionArea>
        </Card>
    );
}

export default function PortfolioList() {
    const token = sessionStorage.getItem("token");
    const decoded = jwt(token);

    React.useEffect(() => {
        getPortfolios(decoded.jti);
    }, []);

    let navigate = useNavigate();

    function handleClick() {
        navigate("/PortfolioCreation");
    }

    const [portfolios, setPortfolios] = React.useState([]);

    const getPortfolios = async (userId) => {
        try {
            const response = await axios.get('/api/portfolios/' + userId, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setPortfolios(response.data);
            // console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const [dataLoaded, setDataLoaded] = React.useState(false);
    setTimeout(() => {
        setDataLoaded(true);
    }, 1000);

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
                            <Grid item xs>
                                <Typography variant="h4" gutterBottom>Your Portfolios</Typography>
                            </Grid>
                            <Grid item xs={12} sm={5} md={3} lg={2} style={{display: "flex"}}>
                                <Button variant="outlined" onClick={handleClick} style={{fontWeight: "bold", float: "right", flex: 1, alignSelf: "center"}}>
                                    <AddIcon sx={{ mr: 1 }} />
                                    Create Portfolio
                                </Button>
                            </Grid>
                        </Grid>
                        { portfolios.length > 0 && dataLoaded?
                            <Grid item minHeight={100} container spacing={2} justifyContent="start">
                                {portfolios.map((portfolio) => {
                                    return (
                                        <Grid item xs={12} md={6} lg={4}>
                                        <PortfolioCard portfolio={portfolio}></PortfolioCard>
                                        </Grid>
                                    )
                                })}
                            </Grid> : 
                            dataLoaded ?
                            <Grid item minHeight={100} justifyContent="space-between">
                                <Typography variant="body1" color="text.secondary" align="center">You have no existing portfolios</Typography>
                            </Grid> :
                            <Grid item minHeight={100} justifyContent="space-between">
                                <Typography variant="body1" color="text.secondary" align="center">Loading...</Typography>
                            </Grid>
                        }
                    </Grid>
                </Box>
                {/* <Button onClick={handleClick} color='button' style={{ textTransform: 'none' }}>Create a new portfolio</Button> */}
            </ThemeProvider>

        </>

    )
}