import * as React from 'react';
import Board from '../board/Board';
// import './App.css';

// const logo = require('../../assets/logo.svg');

class App extends React.Component {
    render() {
        return (
            <div className="App">
                <Board/>
            </div>
        );
    }
}

export default App;
