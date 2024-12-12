import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <AppBar position="fixed" style={{ backgroundColor: '#fef5e7', boxShadow: 'none' }}>
            <Toolbar style={{ justifyContent: 'space-between' }}>
                <Typography variant="h6" style={{ color: '#8b5e3c', fontWeight: 'bold' }}>
                    Книжный Магазин
                </Typography>
                <div>
                    <Button component={Link} to="/" style={{ color: '#8b5e3c' }}>Головна</Button>
                    <Button component={Link} to="/find-us" style={{ color: '#8b5e3c' }}>Де нас знайти?</Button>
                    <Button component={Link} to="/about" style={{ color: '#8b5e3c' }}>Про нас</Button>
                    <Button component={Link} to="/contacts" style={{ color: '#8b5e3c' }}>Контакти</Button>
                </div>
            </Toolbar>
        </AppBar>
    );
};

export default Header;