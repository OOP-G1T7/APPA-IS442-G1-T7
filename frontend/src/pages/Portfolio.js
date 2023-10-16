import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";
import Navbar from "../components/Navbar";
import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Card from '@mui/material/Card';
import { CardContent } from "@mui/material";
import Divider from '@mui/material/Divider';
import { PieChart } from '@mui/x-charts/PieChart';
import { LineChart } from '@mui/x-charts/LineChart';
import Skeleton from '@mui/material/Skeleton';
import axios from "axios";

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

const percentageDistribution = [
    { id: 0, value: 10, label: 'series A' },
    { id: 1, value: 15, label: 'series B' },
    { id: 2, value: 20, label: 'series C' },
    { id: 3, value: 20, label: 'series C' },
    { id: 4, value: 20, label: 'series C' },
    { id: 5, value: 20, label: 'series C' },
    { id: 6, value: 20, label: 'series C' },
    { id: 7, value: 15, label: 'series B' },
    { id: 8, value: 20, label: 'series C' },
    { id: 9, value: 20, label: 'series C' },
    // { id: 4, value: 20, label: 'series C' },
    // { id: 3, value: 20, label: 'series C' },
    // { id: 4, value: 20, label: 'series C' },
]

// const getData = async (ticker) => {
//     try {
//         const res = await axios.get("/api/stockwrapper/" + ticker)
//         console.log(res.data)
//         return res.data
//     } catch (error) {
//         console.log("Error-----")
//         console.log(error)
//     }
// };

// const oldDate = new Date("1 Jan 2013");
const testData = [
    { '1990-12-30': 10 },
    { '1991-1-30': 20 },
    { '1991-2-30': 30 },
]

export default function Portfolio() {
    const ticker = "aapl";
    // const testData = getData(ticker);
    // console.log(testData)
    

    return (
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
                    <Grid item>
                        <Typography variant="h4" gutterBottom>Portfolio</Typography>
                    </Grid>
                    <Grid item container spacing={3} justifyContent={"space-between"}>
                        <Grid item xs={12} sm={12} md={8} lg={8}>
                        { typeof testData === 'object' && typeof testData.then === 'function' ?
                            <Skeleton variant="rectangular" width="100%" height="70vh" /> :
                            <LineChart
                            height={500}
                            series={[
                                { data: testData.map((data) => data[Object.keys(data)]), label: ticker },
                                // { data: uData, label: 'uv' },
                            ]}
                            xAxis={[{ scaleType: 'point', data: testData.map((data) => Object.keys(data)) }]}
                            />
                        }
                        </Grid>
                        <Grid item xs={12} sm={12} md={4} lg={3}>
                            <Card variant="outlined">
                                <CardContent>
                                    <Box sx={{
                                        mb: 2,
                                        mx: 1,
                                        mt: 1
                                    }}>
                                        <Typography variant="h6" gutterBottom style={{fontWeight: 'bold'}}>Description</Typography>
                                        <Typography variant="body1" gutterBottom>
                                            This is a portfolio description.
                                        </Typography>
                                    </Box>
                                    
                                    <Divider variant="middle" />

                                    <Box sx={{
                                        mb: 1,
                                        mx: 1,
                                        mt: 2
                                    }}>
                                        <Grid container spacing={3}>
                                            <Grid item container justifyContent="space-between" alignItems='center' xs={12}>
                                                <Grid item>
                                                    <Typography variant="h6" gutterBottom style={{fontWeight: 'bold'}}>Capital</Typography>
                                                </Grid>
                                                <Grid item>
                                                    <Typography variant="h6" gutterBottom>
                                                        $100,000
                                                    </Typography>
                                                </Grid>
                                            </Grid>
                                            <Grid item xs={6} sm={6} md={12}>
                                                <PieChart
                                                    colors={colours}
                                                    margin={{top: 0, bottom: 0, left: 10, right:10}}
                                                    series={[
                                                        {
                                                        data: percentageDistribution,
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
                                            <Grid item container spacing={2} alignItems='flex-start' justifyContent='center' xs={6} sm={6} md>
                                                {percentageDistribution.map((data) => (
                                                    <Grid item xxl>
                                                        <Typography variant="body1">
                                                        <span style={{
                                                            width: 10,
                                                            height: 10,
                                                            backgroundColor: colours[data.id % colours.length],
                                                            // display: 'inline-block',
                                                            marginRight: 5
                                                        }}>&nbsp;&nbsp;</span>
                                                            {data.label}
                                                        </Typography>
                                                    </Grid>
                                                ))}
                                            </Grid>
                                        </Grid>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                </Grid>
            </Box>
        </ThemeProvider>
    )
};