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

function PortfolioCard(props) {
    const { portfolio } = props;
    return (
        <Card variant="outlined">
            <CardActionArea>
            <Link href={`/Portfolio/${portfolio.portfolioId}`} underline="none" color='inherit'>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                {portfolio.name}
                </Typography>

                <Typography variant="body2" color="text.secondary">
                    <Grid container spacing={1} direction={"column"}>
                        <Grid item>
                        {portfolio.description}
                        </Grid>
                        <Grid item container spacing={0} direction="column">
                            {
                                // iterate through stocks
                                portfolio.stocks.map((stock) => {
                                    return (
                                        <Grid item xs={6}>
                                        {stock.stockPk.ticker}: {stock.quantity}
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

const userID = "1";

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtdXN5YWZmYXE5QGdtYWlsLmNvbSIsImV4cCI6MTkxNDA1MjYyNn0.ze1n-N7sOvZ2sNlScPXXbcTv4TG1M63dA3Ibd9FIxHA';

export default function PortfolioList() {
    React.useEffect(() => {
        getPortfolios();
    }, []);

    let navigate = useNavigate();

    function handleClick() {
        navigate("/PortfolioCreation");
    }

    const [portfolios, setPortfolios] = React.useState([]);

    const getPortfolios = async () => {
        try {
            const response = await axios.get('/api/portfolios/' + userID, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setPortfolios(response.data);
            // console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

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
                            <Grid item xs={12} sm={5} md={3} lg={2} style={{display: "flex"}}>
                                <Button variant="outlined" onClick={handleClick} style={{fontWeight: "bold", float: "right", flex: 1, alignSelf: "center"}}>
                                    <AddIcon sx={{ mr: 1 }} />
                                    Create Portfolio
                                </Button>
                            </Grid>
                        </Grid>
                        <Grid item minHeight={100} container spacing={2} justifyContent="space-between">
                            { portfolios.length > 0 ?
                                portfolios.map((portfolio) => {
                                    return (
                                        <Grid item xs={12} md={6} lg={4}>
                                        <PortfolioCard portfolio={portfolio}></PortfolioCard>
                                        </Grid>
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