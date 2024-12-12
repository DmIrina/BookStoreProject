import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import SearchBar from './components/SearchBar';
import Catalog from './components/Catalog';
import BookDetails from './components/BookDetails';
import {useState} from "react";

const App = () => {
    const [selectedBook, setSelectedBook] = useState<unknown | null>(null);

    return (
        <Router>
            <Header />
            <SearchBar />
            <Routes>
                <Route
                    path="/"
                    element={<Catalog onSelectBook={setSelectedBook} />} // Ensure 'onSelectBook' is defined in Catalog's props.
                />
                <Route
                    path="/book"
                    element={<BookDetails selectedBook={selectedBook} />}
                />
            </Routes>
        </Router>
    );
};

export default App;