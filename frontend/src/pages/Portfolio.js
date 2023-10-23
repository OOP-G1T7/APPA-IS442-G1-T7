import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";
import Navbar from "../components/Navbar";
import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import { Button, CardContent } from "@mui/material";
import { PieChart } from '@mui/x-charts/PieChart';
import Skeleton from '@mui/material/Skeleton';
import axios from "axios";
import { useEffect } from "react";
import Chart from 'react-apexcharts';
import { useParams } from "react-router-dom";

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

const options = {
    chart: {
        toolbar: {
            show: true,
            autoSelected: 'zoom',
            tools: {
                download: false,
                selection: false,
                zoomin: true,
                zoom: false,
                reset: true,
            }
        },
        stacked: false,
        type: 'area',
    },
    dataLabels: {
        enabled: false
    },
    // title: {
    //     text: 'Stock Price Movement',
    //     align: 'center',
    // },
    yaxis: {
        labels: {
            formatter: function (val) {
                return '$' + (val).toFixed(2);
            },
        },
        title: {
            text: 'Price'
        },
    },
    xaxis: {
        type: 'datetime',
        labels: {
            datetimeFormatter: {
                year: 'yyyy',
                month: 'MMM-yyyy',
                day: 'dd MMM yyyy',
            }
        }
    },
    tooltip: {
        shared: true,
        y: {
            
        },
        x: {
            format: 'dd MMM yyyy',
        },
    },
    colors: colours,
    animations: {
        enabled: true,
        easing: 'easeinout',
        speed: 800,
        animateGradually: {
            enabled: true,
            delay: 150
        },
        dynamicAnimation: {
            enabled: true,
            speed: 350
        }
    },
    responsive: [
        {
            breakpoint: 1200,
            options: {
                chart: {
                    height: 500,
                },
                yaxis: {
                    labels: {
                        formatter: function (val) {
                            return '$' + (val).toFixed(0);
                        },
                    },
                    title: {
                        text: 'Price'
                    },
                },
            }
        },
        {
            breakpoint: 900,
            options: {
                chart: {
                    height: 400,
                },
                
            }
        },
        {
            breakpoint: 600,
            options: {
                chart: {
                    height: 250,
                },
            }
        }
    ],
};

const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtdXN5YWZmYXE5QGdtYWlsLmNvbSIsImV4cCI6MTkxNDA1MjYyNn0.ze1n-N7sOvZ2sNlScPXXbcTv4TG1M63dA3Ibd9FIxHA';

export default function Portfolio() {
    const { id } = useParams();

    useEffect(() => {
        getPortfolioData(id);
    }, []);

    const [portfolioData, setPortfolioData] = React.useState(null);
    const [portfolioStockData, setPortfolioStockData] = React.useState(null);
    const [seriesData, setSeriesData] = React.useState(null);

    
    const getData = async (ticker) => {
        try {
            const res = await axios.get("/api/stockwrapper/" + ticker, {
                headers: { Authorization: `Bearer ${token}` }
        })
            // console.log(res.data)
            return res.data
        } catch (error) {
            console.log("API unavailable")
            setSeriesData([])
        }
    };

    const getPortfolioData = async (portfolioID) => {
        try {
            const res = await axios.get("/api/portfolio/" + portfolioID, {
                headers: { Authorization: `Bearer ${token}` }
            }).catch((error) => {
                error.message = "Portfolio not found";
                throw error
            })
            // console.log(res.data)
            setPortfolioData(res.data)
            setPortfolioStockData(res.data.stocks)

            let stockDataSeries = [];
            for (let key in res.data.stocks) {
                const stock = res.data.stocks[key];
                const stockData = await getData(stock.stockPk.ticker).catch((error) => {
                    error.message = "API unavailable";
                    throw error
                });
                // console.log(stockData)
                stockDataSeries.push({
                    name: stock.stockPk.ticker,
                    data: stockData.map((data) =>[new Date(data.date).getTime(), parseFloat(data.close) * stock.quantity]).sort((a, b) => a[0] - b[0]),
                });
            }
            setSeriesData(stockDataSeries);
        } catch (error) {
            if (error.message === "Portfolio not found") {
                setPortfolioData(error.message)
            } else if (error.message === "API unavailable") {
                console.log("API unavailable")
                setSeriesData([])
            }
        }
    };

    return (
        <ThemeProvider theme={theme}>
            <Navbar></Navbar>
            <Box sx={{ 
                mt: 5,
                mb: 2,
                mx: 10,
            }}>
                {
                    portfolioData === null ? 
                    <Skeleton variant="rectangular" width="100%" height={100} /> :
                    
                    typeof (portfolioData) === String ? 
                    <Typography variant="h4" gutterBottom style={{textAlign: "center", width: "100%"}}>{portfolioData}</Typography> : 

                <Grid
                    container
                    spacing={3}
                    direction="column"
                    // alignItems="center"
                    justifyContent="center"
                >
                    
                    <Grid item container direction='row' alignItems='center' justifyContent='space-between' >
                        <Grid item>
                            <Typography variant="h4" gutterBottom>{portfolioData.name}</Typography>
                        </Grid>
                        <Grid item>
                            <Button variant="outlined" style={{fontWeight: "bold"}}> Edit </Button>
                        </Grid>
                    </Grid>
                    <Grid item>
                        <Card variant="outlined">
                            <Box sx={{
                                mb: 2,
                                mx: 1,
                                mt: 2
                            }}>
                                <Typography variant="h6" gutterBottom style={{fontWeight: 'bold'}}>Description</Typography>
                                <Typography variant="body1" gutterBottom>
                                    {portfolioData.description}
                                </Typography>
                            </Box>
                        </Card>
                    </Grid>
                    <Grid item container spacing={10} justifyContent={"space-between"}>
                        <Grid item xs={12} sm={12} md={12} lg={8} xl={9}>
                            {
                                seriesData === null ?
                                <Skeleton variant="rectangular" width="100%" height={500} /> 
                                : seriesData.length === 0 ?
                                <span style={{textAlign: 'right', width: "100%"}}>No Data</span> :
                                <Chart
                                    options={options}
                                    // series={series}
                                    series={seriesData}
                                    type="line"
                                    height="100%"
                                    // width="500"
                                    style={{
                                        minHeight: '32rem',
                                    }}
                                />
                            }
                        </Grid>
                        <Grid item xs={12} sm={12} md={12} lg={4} xl={3} container>
                            <Card variant="outlined">
                                <CardContent>
                                    <Box sx={{
                                        mb: 1,
                                        mx: 1,
                                        mt: 2
                                    }}>
                                        <Grid container spacing={1}>
                                            <Grid item container justifyContent="space-between" alignItems='center' xs={12}>
                                                <Grid item>
                                                    <Typography variant="h6" style={{fontWeight: 'bold'}}>Capital</Typography>
                                                </Grid>
                                                <Grid item>
                                                    <Typography variant="h6">
                                                        $-
                                                    </Typography>
                                                </Grid>
                                            </Grid>
                                            <Grid item container justifyContent="space-between" alignItems='center' xs={12}>
                                                <Grid item>
                                                    <Typography variant="h6" style={{fontWeight: 'bold'}}>Current Total Value</Typography>
                                                </Grid>
                                                <Grid item>
                                                    <Typography variant="h6">
                                                        $-
                                                    </Typography>
                                                </Grid>
                                            </Grid>
                                            <Grid item style={{margin: 10}} xs={12}/>
                                            <Grid item xs={12} sm={6} md={12}>
                                                <PieChart
                                                    colors={colours}
                                                    margin={{top: 0, bottom: 0, left: 10, right:10}}
                                                    series={[
                                                        {
                                                        data: portfolioStockData === null ? [] :
                                                        portfolioStockData.map((stock, key) => {
                                                            return { 
                                                                id: key,
                                                                value: stock.quantity,
                                                                label: stock.stockPk.ticker,
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
                                                    height={200}
                                                    />
                                            </Grid>
                                            <Grid item style={{margin: 10}} xs={12}/>  
                                            <Grid item container spacing={2} alignItems='flex-start' justifyContent='center' xs={12} sm={6} md>
                                                { portfolioStockData === null ? <>Loading...</> :
                                                portfolioStockData.map((stock, key) => {
                                                    return (
                                                    <Grid item xxl>
                                                        <Typography variant="body1">
                                                            <span style={{
                                                                width: 10,
                                                                height: 10,
                                                                backgroundColor: colours[key % colours.length],
                                                                // display: 'inline-block',
                                                                marginRight: 5
                                                            }}>&nbsp;&nbsp;</span>
                                                                {stock.stockPk.ticker}
                                                        </Typography>
                                                    </Grid>
                                                    )
                                                })}
                                            </Grid>
                                        </Grid>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                </Grid>
                }
            </Box>
        </ThemeProvider>
    )
};