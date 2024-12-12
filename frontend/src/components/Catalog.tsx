import { Box, Grid, Paper, Typography, Checkbox, FormGroup, FormControlLabel } from '@mui/material';
import { useState } from 'react';
import {mockBooks, mockFilters} from "../data/mockData.ts";
import {useNavigate} from "react-router-dom";

interface CatalogProps {
    onSelectBook: (book: { id: string; image: string; title: string; author: string; price: number; }) => void;
}

const Catalog = ({ onSelectBook }: CatalogProps) => {
    const [filters] = useState<string[]>(mockFilters);
    const [books] = useState<{ id: string; image: string; title: string; author: string; price: number; }[]>(mockBooks);
    const navigate = useNavigate();

    const handleClick = (book: { id: string; image: string; title: string; author: string; price: number; }) => {
        onSelectBook(book)
        navigate('/book');
    }

    return (
        <Box display="flex" padding={2} bgcolor="#fffaf3">
            <Paper
                elevation={2}
                style={{ padding: '1rem', marginRight: '2rem', backgroundColor: '#fef5e7', width: '20%' }}
            >
                <Typography variant="h6">Фільтри</Typography>
                <FormGroup>
                    {filters.map((filter) => (
                        <FormControlLabel
                            key={filter}
                            control={<Checkbox />}
                            label={filter}
                        />
                    ))}
                </FormGroup>
            </Paper>
            <Grid container spacing={2} style={{ flex: 1 }}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} lg={3} key={book.id}>
                        <Paper elevation={2} style={{ padding: '1rem', textAlign: 'center' }}>
                            <div
                                style={{ cursor: 'pointer' }}
                                onClick={() => handleClick(book)}
                            >
                                <img
                                    src={book.image}
                                    alt={book.title}
                                    style={{ marginBottom: '1rem', width: '100px', height: '150px' }}
                                />
                                <Typography>{book.title}</Typography>
                                <Typography>{book.author}</Typography>
                                <Typography>{book.price} грн</Typography>
                            </div>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
};

export default Catalog;