import { Box, Typography, Paper } from '@mui/material';

interface BookDetailsProps {
    selectedBook: any;
}

const BookDetails = ({ selectedBook }: BookDetailsProps) => {
    if (!selectedBook) return <Typography>Выберите книгу из каталога</Typography>;

    return (
        <Box padding={2}>
            <Paper elevation={2} style={{ padding: '1rem', backgroundColor: '#fef5e7' }}>
                <Typography variant="h4">{selectedBook.title}</Typography>
                <Typography>Автор: {selectedBook.author}</Typography>
                <Typography>Рік видання: {selectedBook.year || 'Не указано'}</Typography>
                <Typography>Ціна: {selectedBook.price} грн</Typography>
                <Typography>{selectedBook.description || 'Описание отсутствует'}</Typography>
            </Paper>
        </Box>
    );
};

export default BookDetails;