import { createTheme } from "@mui/material/styles";
const theme = createTheme({
    palette: {
        mode: 'light',
        primary: {
            main: '#1C2B36',
        },
        secondary: {
            main: '#343A40',
        },
        button: {
            main: '#0D4EA6',
        },
    },
    typography: {
        fontFamily: `Inter`,
    },
});
export default theme;
