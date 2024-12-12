import { Box, TextField, Button } from '@mui/material';

const SearchBar = () => {
    return (
        <Box display="flex" justifyContent="center" marginTop={9} marginBottom={1}>
            <TextField
                placeholder="Search"
                variant="outlined"
                style={{ width: '60%' }}
            />
            <Button
                variant="contained"
                style={{ marginLeft: '0.5rem', backgroundColor: '#e6c7a5', color: 'white' }}
            >
                Поиск
            </Button>
        </Box>
    );
};

export default SearchBar;