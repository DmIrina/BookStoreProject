import { Box, Typography, TextField, Button, Paper } from '@mui/material';
import {useEffect, useState} from "react";
import {mockCart} from "../data/mockData.ts";

const Cart = () => {
    interface CartItem {
        id: number;
        title: string;
        price: number;
    }

    const [cartItems, setCartItems] = useState<CartItem[]>(mockCart.map(item => ({ ...item, id: Number(item.id) })) as CartItem[]);

    useEffect(() => {
        fetch('/api/cart')
            .then((res) => res.json())
            .then((data) => setCartItems(data));
    }, []);

    return (
        <Box padding={2}>
            <Paper elevation={2} style={{ padding: '1rem', backgroundColor: '#fef5e7' }}>
                <Typography variant="h4">Кошик</Typography>
                {cartItems.map((item) => (
                    <Typography key={item.id}>{item.title} - {item.price} грн</Typography>
                ))}
                <TextField label="Адреса" fullWidth style={{ margin: '1rem 0' }} />
                <TextField label="Реквізити" fullWidth style={{ marginBottom: '1rem' }} />
                <Button variant="contained" style={{ backgroundColor: '#e6c7a5', color: 'white' }}>Замовити</Button>
            </Paper>
        </Box>
    );
};

export default Cart;
