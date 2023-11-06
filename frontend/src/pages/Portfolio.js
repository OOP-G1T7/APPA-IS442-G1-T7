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
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import Modal from '@mui/material/Modal';
import ErrorOutlineTwoToneIcon from '@mui/icons-material/ErrorOutlineTwoTone';
import { useNavigate } from "react-router-dom";
import LoadingButton from '@mui/lab/LoadingButton';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';

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

const modalStyle = {
    position: 'absolute',
    top: '30%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid lightgrey',
    boxShadow: 24,
    p: 4,
    borderRadius: 2,
};

export default function Portfolio() {
    const { id } = useParams();

    const token = sessionStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        getPortfolioData(id);
    }, []);

    const today = new Date();

    const [portfolioData, setPortfolioData] = React.useState(null);
    const [portfolioStockData, setPortfolioStockData] = React.useState(null);
    const [seriesData, setSeriesData] = React.useState(null);
    const [filteredSeriesData, setFilteredSeriesData] = React.useState(null);

    
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
            setFilteredSeriesData(stockDataSeries);
        } catch (error) {
            if (error.message === "Portfolio not found") {
                setPortfolioData(error.message)
            } else if (error.message === "API unavailable") {
                console.log("API unavailable")
                setSeriesData([])
            }
        }
        
    };

    const getCurrentTotalValue = () => {
        let total = 0;
        for (let key in portfolioStockData) {
            for (let key2 in seriesData) {
                if (seriesData[key2].name === portfolioStockData[key].stockPk.ticker) {
                    total += seriesData[key2].data[seriesData[key2].data.length - 1][1];
                }
            }
        }
        return total.toFixed(2);
    }

    const [seriesPeriod, setSeriesPeriod] = React.useState("10y");

    const handleSeriesPeriod = (event, newSeriesPeriod) => {
        setSeriesPeriod(newSeriesPeriod);
        let beginningDate = '';

        switch (newSeriesPeriod) {
            case "5y":
                beginningDate = new Date(today.getFullYear() - 5, today.getMonth(), today.getDate());
                break;
            case "3y":
                beginningDate = new Date(today.getFullYear() - 3, today.getMonth(), today.getDate());
                break;
            case "1y":
                beginningDate = new Date(today.getFullYear() - 1, today.getMonth(), today.getDate());
                break;
            case "6m":
                beginningDate = new Date(today.getFullYear(), today.getMonth() - 6, today.getDate());
                break;
            case "1m":
                beginningDate = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
                break;
            default:
                beginningDate = new Date(today.getFullYear() - 10, today.getMonth(), today.getDate());
                break;
        }

        let newSeriesData = [];
        for (let key in seriesData) {
            let newData = seriesData[key].data.filter((data) => {
                return data[0] >= beginningDate.getTime();
            })
            
            newSeriesData.push({
                name: seriesData[key].name,
                data: newData,
            });
        }
        setFilteredSeriesData(newSeriesData);
        console.log(filteredSeriesData)
    };

    const [open, setOpen] = React.useState(false);
    const [deleteError,  setDeleteError] = React.useState("");
    const [loading, setLoading] = React.useState(false);
    const [openSuccss, setOpenSuccess] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleDelete = async () => {
        try {
            setLoading(true);
            await axios.delete("/api/portfolio/" + id, {
                headers: { Authorization: `Bearer ${token}` }
            })
            setTimeout(() => {
                setLoading(false);
                setOpen(false);
                setOpenSuccess(true);
            }
            , 3000);

            setTimeout(() => {
                // setOpenSuccess(false);
                navigate("/Home");
            }, 5000);
        } catch (error) {
            setDeleteError(error.response.data.message)
        }
    }

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
                        <Grid item >
                            <Button variant="outlined" style={{fontWeight: "bold"}}> Edit </Button>
                            <Tooltip title="Delete">
                                <IconButton onClick={handleOpen}>
                                    <DeleteIcon />
                                </IconButton>
                            </Tooltip>

                            <Modal
                                open={openSuccss}
                                onClose={handleClose}
                                aria-labelledby="delte-portfolio-modal-title"
                                aria-describedby="delte-portfolio-modal-description"
                            >
                                <Box sx={modalStyle}>
                                    <Typography id="modal-modal-title" variant="h6" component="h2">
                                        <CheckCircleIcon sx={{color: 'green', mr: 1}}/>
                                        Deletion Success
                                    </Typography>
                                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                                        The portfolio has been successfully deleted. <br />
                                        Redicting you back to the home page...
                                        <LoadingButton loading />
                                    </Typography>
                                </Box>
                            </Modal>

                            <Modal
                                open={open}
                                onClose={handleClose}
                                aria-labelledby="delte-portfolio-modal-title"
                                aria-describedby="delte-portfolio-modal-description"
                            >
                                <Box sx={modalStyle}>
                                    <Typography id="modal-modal-title" variant="h6" component="h2">
                                        <ErrorOutlineTwoToneIcon sx={{color: 'orange', mr: 1}}/>
                                        Are you sure?
                                    </Typography>
                                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                                        Once you delete, you can never restore it.
                                    </Typography>

                                    <Grid container justifyContent="flex-end" sx={{mt: 2}} spacing={1}>
                                        <Grid item>
                                            <Button variant="outlined" onClick={handleClose}>Cancel</Button>
                                        </Grid>
                                        <Grid item>
                                            {/* <Button variant="contained" onClick={handleDelete}  color="error"disabled={loading}>
                                                
                                                Confirm Delete
                                            </Button> */}
                                            <LoadingButton
                                                onClick={handleDelete}
                                                loading={loading}
                                                loadingPosition="start"
                                                variant="contained"
                                                color="error"
                                                startIcon={<DeleteIcon />}
                                                >
                                                <span>Confirm Delete</span>
                                            </LoadingButton>
                                        </Grid>
                                    </Grid>
                                    <Grid container justifyContent="flex-end" sx={{mt: 2}}>
                                        <Typography variant="body2" sx={{color: 'red'}}>{deleteError}</Typography>
                                    </Grid>
                                </Box>
                            </Modal>
                            
                        </Grid>
                    </Grid>
                    <Grid item>
                        <Card variant="outlined">
                            <Box sx={{
                                mb: 2,
                                mx: 4,
                                mt: 2
                            }}>
                                <Typography variant="h6" gutterBottom style={{fontWeight: 'bold'}}>Description</Typography>
                                <Typography variant="body1" gutterBottom>
                                    {portfolioData.description}
                                </Typography>
                            </Box>
                        </Card>
                    </Grid>
                    <Grid item container justifyContent={"space-between"} spacing={[0, 5]}>
                        <Grid item container xs={12} sm={12} md={12} lg={8} xl={9} spacing={3}>
                            <Grid item xs={12} style={{height: '8vh'}}>
                                <ToggleButtonGroup
                                    color="info"
                                    value={seriesPeriod}
                                    exclusive
                                    onChange={handleSeriesPeriod}
                                    style={{display: 'flex', justifyContent: 'center', }}
                                >
                                    <ToggleButton value="10y">10 Yrs</ToggleButton>
                                    <ToggleButton value="5y">5 Yrs</ToggleButton>
                                    <ToggleButton value="3y">3 Yrs</ToggleButton>
                                    <ToggleButton value="1y">1 Yr</ToggleButton>
                                    <ToggleButton value="6m">6 mths</ToggleButton>
                                    <ToggleButton value="1m">1 mth</ToggleButton>
                                </ToggleButtonGroup>
                            </Grid>
                            <Grid item xs={12} style={{ minHeight: "25rem"}}>
                                {
                                    seriesData === null ?
                                    <Skeleton variant="rectangular" width="100%" height={500} /> 
                                    : seriesData.length === 0 ?
                                    <span style={{textAlign: 'right', width: "100%",}}>No Data</span> :
                                    <Chart
                                        options={options}
                                        // series={series}
                                        series={filteredSeriesData}
                                        type="line"
                                        height="100%"
                                        // width="500"
                                        // style={{
                                        //     minHeight: '25rem',
                                        // }}
                                    />
                                }
                            </Grid>
                        </Grid>
                        <Grid item xs={12} sm={12} md={12} lg={4} xl={3} container>
                            <Card variant="outlined">
                                <CardContent>
                                    <Box sx={{
                                        mb: 1,
                                        mx: 1,
                                        mt: 2
                                    }}>
                                        <Grid container spacing={1} justifyContent="center">
                                            <Grid item container justifyContent="space-between" alignItems='center' xs={12}>
                                                <Grid item xs={2}>
                                                    <Typography variant="h6" style={{fontWeight: 'bold'}}>Capital</Typography>
                                                </Grid>
                                                <Grid item>
                                                    <Typography variant="h6">
                                                        ${portfolioData.capital.toFixed(2)}
                                                    </Typography>
                                                </Grid>
                                            </Grid>
                                            <Grid item container justifyContent="space-between" alignItems='center' xs={12}>
                                                <Grid item xs={6}>
                                                    <Typography variant="h6" style={{fontWeight: 'bold'}}>Current Total Value</Typography>
                                                </Grid>
                                                <Grid item>
                                                    <Typography variant="h6">
                                                        $
                                                        { portfolioData !== null ?
                                                            getCurrentTotalValue() : "-"
                                                        }
                                                    </Typography>
                                                </Grid>
                                            </Grid>
                                            <Grid item style={{margin: 10}} xs={12}/>
                                            <Grid item style={{margin: 10}} xs={12}>
                                                <Typography variant="h5" style={{fontWeight: 'bold', textAlign: "center"}}>Quantity</Typography>
                                            </Grid>
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
                                            {/* <Grid item style={{margin: 10}} xs={12}/>   */}
                                            <Grid item container spacing={2} alignItems='flex-start' justifyContent='center' xs={12} sm={6} md sx={{my: 2}}>
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