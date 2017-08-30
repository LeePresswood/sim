import * as React from 'react';
import Boid from '../boid/Boid';

export interface BoardProps {
    // label: string,
}

type BoardState = {
    boids: Array<Boid>,
};

class Board extends React.Component<BoardProps, BoardState> {
    state: BoardState = {
        boids: Array.apply(0, Array<number>(100)).map((value: number, key: number) => <Boid key={key}/>),
    };

    constructor(props: BoardProps) {
        super(props);
    }

    render() {
        const style: object = {
            height: '100vh',
            backgroundColor: 'black'
        };

        return (
            <div className="Board" style={style}>
                {this.state.boids}
            </div>
        );
    }
}

export default Board;
