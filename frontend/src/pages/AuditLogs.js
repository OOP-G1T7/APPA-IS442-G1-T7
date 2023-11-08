import * as React from "react";
import { ThemeProvider } from "@emotion/react";
import theme from "../Theme";
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Navbar from "../components/Navbar";
import axios from "axios";
import Skeleton from '@mui/material/Skeleton';
import Chip from '@mui/material/Chip';
import { useNavigate } from "react-router-dom";
import jwt from "jwt-decode";

export default function AuditLogs() {

    React.useEffect(() => {
        getAuditLogs();
    }, []);

    const navigate = useNavigate();

    const [dataList, setDataList] = React.useState([]);

    const token = sessionStorage.getItem("token");
    const decoded = jwt(token);
    console.log(decoded);

    if (decoded.aud !== "admin") {
        navigate("/NoPage");
    }

    const getAuditLogs = async () => {
        const res = await axios.get("/api/logs", {
            headers: { Authorization: `Bearer ${token}` }
        });
        setDataList(res.data);
    }

    return (
        <ThemeProvider theme={theme}>
            <Navbar />
            <Box
                style={{
                    marginLeft: '10%',
                    marginRight: '10%',
                    marginTop: '3%',
                    marginBottom: '5%',
                }}
            >
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Typography variant="h4" gutterBottom>Audit Logs</Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <TableContainer component={Paper}>
                            <Table stickyHeader sx={{ minWidth: 650}} aria-label="simple table">
                                <TableHead sx={{color: "white"}}>
                                <TableRow
                                    sx={{
                                        backgroundColor: theme.palette.primary.main, 
                                        color: "#ffffff"
                                    }}
                                >
                                    <TableCell align="center">Log ID</TableCell>
                                    <TableCell align="center">User ID</TableCell>
                                    <TableCell align="center">Outcome</TableCell>
                                    <TableCell colSpan={3}>Details</TableCell>
                                    <TableCell align="center">Timestamp</TableCell>
                                </TableRow>
                                </TableHead>
                                <TableBody>
                                { dataList.length === 0 ?
                                    Array.from({ length: 4 }).map((row) => (
                                        <TableRow>
                                            <TableCell colSpan={7}>
                                                <Skeleton animation="wave" />
                                            </TableCell>
                                        </TableRow>
                                    ))  :
                                dataList.map((row) => (
                                    <TableRow
                                    key={row.logId}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                    >
                                    <TableCell component="th" scope="row" align="center">
                                        {row.logId}
                                    </TableCell>
                                    <TableCell align="center">{row.userId}</TableCell>
                                    <TableCell align="center">
                                        <Chip label={row.outcome} color={row.outcome === "Success" ? "success" : "error"} />
                                    </TableCell>
                                    <TableCell colSpan={3}>{row.detail}</TableCell>
                                    <TableCell align="center">{new Date(row.timestamp).toLocaleDateString("en-SG")}</TableCell>
                                    </TableRow>
                                ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Grid>
                </Grid>
                
            </Box>
        </ThemeProvider>
    );
}
